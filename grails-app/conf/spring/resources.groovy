import charliek.blog.client.HttpClient
import charliek.blog.client.HttpConfiguration
import charliek.blog.client.ObjectMapperFactory
import charliek.blog.client.RetrofitClientFactory
import com.fasterxml.jackson.databind.ObjectMapper
import grails.util.GrailsNameUtils
import org.springframework.beans.factory.config.MethodInvokingFactoryBean

beans = {
    objectMapperFactory(ObjectMapperFactory)

    objectMapper(ObjectMapper) { bean ->
        bean.factoryBean = 'objectMapperFactory'
        bean.factoryMethod = 'build'
    }

    snakeCaseObjectMapper(ObjectMapper) { bean ->
        bean.factoryBean = 'objectMapperFactory'
        bean.factoryMethod = 'buildSnakeCase'
    }

    httpConfiguration(HttpConfiguration) {
        connectionTimeOut = grailsApplication.config.httpClientConfig.connectionTimeOut
        readTimeout = grailsApplication.config.httpClientConfig.readTimeout
        proxyUrl = grailsApplication.config.httpClientConfig.proxyUrl
    }

    httpClient(HttpClient, ref('httpConfiguration'))

    retrofitClientFactory(RetrofitClientFactory) {
        objectMapper = ref('objectMapper')
        httpClient = ref('httpClient')
    }

    snakeCaseRetrofitClientFactory(RetrofitClientFactory) {
        objectMapper = ref('snakeCaseObjectMapper')
        httpClient = ref('httpClient')
    }

    // TODO move snake case setup to general config
    githubTokenApi(MethodInvokingFactoryBean) { bean ->
        bean.singleton = true
        targetObject = ref('snakeCaseRetrofitClientFactory')
        targetMethod = 'build'
        arguments = [grailsApplication.classLoader.loadClass('charliek.blog.client.GithubTokenApi'), 'https://github.com']
    }

    githubApi(MethodInvokingFactoryBean) { bean ->
        bean.singleton = true
        targetObject = ref('snakeCaseRetrofitClientFactory')
        targetMethod = 'build'
        arguments = [grailsApplication.classLoader.loadClass('charliek.blog.client.GithubApi'), 'https://api.github.com']
    }

    grailsApplication.config.clients.each { name, config ->
        String clientUrl = config.url
        config.apis.each { apiClassPath ->
            Class apiClass = grailsApplication.classLoader.loadClass(apiClassPath)
            "${GrailsNameUtils.getPropertyName(apiClass)}"(MethodInvokingFactoryBean) { bean ->
                    bean.singleton = true
                    targetObject = ref('retrofitClientFactory')
                    targetMethod = 'build'
                    arguments = [apiClass, clientUrl]
            }
        }
    }
}
