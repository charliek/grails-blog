package charliek.blog.client

import charliek.blog.transfer.github.AccessToken
import retrofit.http.Field
import retrofit.http.FormUrlEncoded
import retrofit.http.Headers
import retrofit.http.POST

public interface GithubTokenApi {

    @Headers([
    'Accept: application/json'
    ])
    @FormUrlEncoded
    @POST('/login/oauth/access_token')
    AccessToken lookupAccessToken(@Field("client_id") String clientId, @Field('client_secret') String clientSecret, @Field('code') String code)

}
