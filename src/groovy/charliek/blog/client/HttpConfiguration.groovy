package charliek.blog.client

import java.util.concurrent.TimeUnit

class HttpConfiguration {
    Long connectionTimeOut = TimeUnit.SECONDS.toMillis(5)
    Long readTimeout = TimeUnit.SECONDS.toMillis(5)
    String proxyUrl = null
}
