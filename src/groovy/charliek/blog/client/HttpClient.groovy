package charliek.blog.client

import com.squareup.okhttp.OkHttpClient
import retrofit.client.Request
import retrofit.client.UrlConnectionClient

import java.util.concurrent.TimeUnit

class HttpClient extends UrlConnectionClient {
    private final OkHttpClient client

    HttpClient(HttpConfiguration httpConfiguration) {
        this(buildOkClient(httpConfiguration))
    }

    HttpClient(OkHttpClient client) {
        this.client = client
    }

    @Override protected HttpURLConnection openConnection(Request request) throws IOException {
        return client.open(new URL(request.getUrl()))
    }

    private static OkHttpClient buildOkClient(HttpConfiguration httpConfiguration) {
        OkHttpClient client = new OkHttpClient()
        if(httpConfiguration.proxyUrl != null) {
            URL url = new URL(httpConfiguration.proxyUrl)
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(url.host, url.port))
            client.setProxy(proxy)
        }
        client.setReadTimeout(httpConfiguration.readTimeout, TimeUnit.MILLISECONDS)
        client.setConnectTimeout(httpConfiguration.connectionTimeOut, TimeUnit.MILLISECONDS)
        return client
    }
}
