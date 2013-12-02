package charliek.blog.client

import com.fasterxml.jackson.databind.ObjectMapper
import retrofit.RestAdapter
import retrofit.converter.JacksonConverter

class RetrofitClientFactory {

    ObjectMapper objectMapper
    HttpClient httpClient

    public <T> T build(Class<T> clazz, String endpoint) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setServer(endpoint)
                .setConverter(new JacksonConverter(objectMapper))
                .setClient(httpClient)
                .build()
        return restAdapter.create(clazz)
    }

}
