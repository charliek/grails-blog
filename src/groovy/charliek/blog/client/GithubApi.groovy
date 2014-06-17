package charliek.blog.client

import charliek.blog.transfer.github.User
import groovy.transform.CompileStatic
import retrofit.http.GET
import retrofit.http.Headers
import retrofit.http.Query

@CompileStatic
public interface GithubApi {

    @Headers([
    'Content-Type: application/json',
    'Accept: application/json'
    ])
    @GET('/user')
    User getUser(@Query('access_token') String access_token)
}