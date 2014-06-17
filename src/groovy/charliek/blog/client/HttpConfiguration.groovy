package charliek.blog.client

import groovy.transform.CompileStatic

import java.util.concurrent.TimeUnit

@CompileStatic
class HttpConfiguration {
    Long connectionTimeOut = TimeUnit.SECONDS.toMillis(5)
    Long readTimeout = TimeUnit.SECONDS.toMillis(5)
    String proxyUrl = null
}
