package charliek.blog.client

import com.charlieknudsen.ribbon.retrofit.LoadBalancedClient
import com.charlieknudsen.ribbon.retrofit.RibbonClient
import com.fasterxml.jackson.databind.ObjectMapper
import com.netflix.client.ClientFactory
import com.netflix.config.ConfigurationManager
import retrofit.RestAdapter
import retrofit.converter.JacksonConverter

class RetrofitClientFactory {

    ObjectMapper objectMapper
    HttpClient httpClient

    public <T> T buildStatic(Class<T> clazz, String endpoint) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setServer(endpoint)
                .setConverter(new JacksonConverter(objectMapper))
                .setClient(httpClient)
                .build()
        return restAdapter.create(clazz)
    }

    public <T> T buildLoadBalanced(Class<T> clazz, String appName, String etcdHost) {
        // TODO eventually should be able to load client specific config into archaius 'appName.ribbon.*' prefix
        // TODO Etcd host should not be configured here. Will need to fix in ribbon library. Remove parameter once updated.
        RibbonUtil.setConfigValue('blog-service.ribbon.EtcdHost', etcdHost)

        LoadBalancedClient loadBalancedClient = (LoadBalancedClient) ClientFactory.getNamedClient(appName);
        RibbonClient ribbonClient = new RibbonClient(loadBalancedClient)

        RestAdapter restAdapter = new RestAdapter.Builder()
                // TODO http here is still important. This should be discovered
                .setServer("http://example.com")
                .setConverter(new JacksonConverter(objectMapper))
                .setClient(ribbonClient)
                .build()
        return restAdapter.create(clazz)
    }

}
