import charliek.blog.Variables
import charliek.blog.client.HttpClient
import charliek.blog.client.HttpConfiguration
import charliek.blog.client.ObjectMapperFactory
import charliek.blog.client.RetrofitClientFactory
import charliek.blog.client.RibbonUtil
import com.charlieknudsen.etcd.EtcdClient
import com.charlieknudsen.ribbon.etcd.EtcdPublisher
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
        proxyUrl = Variables.substituteEnvironment(grailsApplication.config.httpClientConfig.proxyUrl, grailsApplication.config.defaultEnvironment)
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

    // TODO this should go into the if block once the etcd host issue in ribbon library is updated.
    String etcdHost = Variables.substituteEnvironment(grailsApplication.config.etcd.client.host, grailsApplication.config.defaultEnvironment)

    if ( grailsApplication.config.etcd.enabled ) {
        String publishHost = Variables.substituteEnvironment(grailsApplication.config.etcd.publish.host, grailsApplication.config.defaultEnvironment)
        RibbonUtil.initializeRibbonDefaults(grailsApplication.config.etcd.ribbon)
//        RibbonUtil.setConfigValue('ribbon.EtcdHost', etcdHost)
        etcdClient(EtcdClient, etcdHost)
        etcdPublisher(EtcdPublisher, ref('etcdClient'), publishHost, 8080, 'grails-blog')
    }

    // TODO move snake case setup to general config
    githubTokenApi(MethodInvokingFactoryBean) { bean ->
        bean.singleton = true
        targetObject = ref('snakeCaseRetrofitClientFactory')
        targetMethod = 'buildStatic'
        arguments = [grailsApplication.classLoader.loadClass('charliek.blog.client.GithubTokenApi'), 'https://github.com']
    }

    githubApi(MethodInvokingFactoryBean) { bean ->
        bean.singleton = true
        targetObject = ref('snakeCaseRetrofitClientFactory')
        targetMethod = 'buildStatic'
        arguments = [grailsApplication.classLoader.loadClass('charliek.blog.client.GithubApi'), 'https://api.github.com']
    }

    grailsApplication.config.clients.each { name, config ->
        String clientUrl = Variables.substituteEnvironment(config.url, grailsApplication.config.defaultEnvironment)
        config.apis.each { apiClassPath ->
            Class apiClass = grailsApplication.classLoader.loadClass(apiClassPath)
            "${GrailsNameUtils.getPropertyName(apiClass)}"(MethodInvokingFactoryBean) { bean ->
                    bean.singleton = true
                    targetObject = ref('retrofitClientFactory')
                    if (config.discover) {
                        targetMethod = 'buildLoadBalanced'
                        arguments = [apiClass, config.name, etcdHost]
                    } else {
                        targetMethod = 'buildStatic'
                        arguments = [apiClass, clientUrl]
                    }
            }
        }
    }
}
