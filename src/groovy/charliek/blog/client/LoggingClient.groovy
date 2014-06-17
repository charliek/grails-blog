package charliek.blog.client

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import retrofit.client.Client
import retrofit.client.Request
import retrofit.client.Response

@Log4j
@CompileStatic
class LoggingClient implements Client {

    Client clientImpl

    LoggingClient(Client clientImpl) {
        this.clientImpl = clientImpl
    }

    @Override
    Response execute(Request request) throws IOException {
        log.info("Calling service at ${request.url}")
        return clientImpl.execute(request)
    }
}
