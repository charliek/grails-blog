package charliek.blog.client

import charliek.blog.transfer.Author
import charliek.blog.transfer.Post
import groovy.transform.CompileStatic
import retrofit.http.Body
import retrofit.http.GET
import retrofit.http.POST
import retrofit.http.PUT
import retrofit.http.Path

@CompileStatic
interface BlogApi {

    @GET('/post')
    List<Post> getAllPosts()

    @GET('/post/{id}')
    Post getPostById(@Path('id') Long id)

    @POST('/post')
    Post addPost(@Body Post post)

    @PUT('/post/{id}')
    Post updatePost(@Path('id') Long id, @Body Post post)

    @GET('/author/{githubName}')
    Author getAuthorByGithubName(@Path('githubName') String githubName)

    @GET('/author/{githubName}')

    @PUT('/author/{id}')
    Author updateAuthor(@Path('id') Long id, @Body Author author)

    @POST('/author')
    Author addAuthor(@Body Author author)

    @GET('/author/{id}/post')
    List<Post> getPostsByAuthor(@Path('id') Long id)

    @GET('/author/{id}/post/{slug}')
    Post getPostBySlug(@Path('id') Long id, @Path('slug') String slug)
}
