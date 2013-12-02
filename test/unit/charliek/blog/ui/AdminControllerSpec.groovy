package charliek.blog.ui

import charliek.blog.client.BlogApi
import charliek.blog.security.GithubAuthService
import charliek.blog.transfer.Author
import charliek.blog.transfer.Errors
import charliek.blog.transfer.Post
import charliek.blog.transfer.github.User
import com.fasterxml.jackson.databind.ObjectMapper
import grails.test.mixin.*
import retrofit.RetrofitError
import retrofit.client.Response
import retrofit.converter.JacksonConverter
import retrofit.mime.TypedInput
import retrofit.mime.TypedString
import spock.lang.Specification

@TestFor(AdminController)
class AdminControllerSpec extends Specification {

    BlogApi blogApi
    GithubAuthService githubAuthService

    User user = new User(login: 'bill')
    Author author = new Author(id: 1)
    String slug = 'my_awsome_post'

    def setup() {
        blogApi = Mock(BlogApi)
        githubAuthService = Mock(GithubAuthService)

        controller.blogApi = blogApi
        controller.githubAuthService = githubAuthService
    }

    def 'show main page for user'() {
        given:

        Author author = new Author(id: 1, name: 'bob')

        when:
        Map model = controller.index()

        then:
        1 * githubAuthService.getCurrentUser(controller.request) >> user
        1 * blogApi.getAuthorByGithubName(user.login) >> author
        1 * blogApi.getPostsByAuthor(author.id) >> [new Post()]
        0 * _

        assert model.size() == 1
        assert model.posts.size() == 1
    }

    def 'edit post get request'() {
        given:
        Post post = new Post()

        when:
        controller.params.id = slug
        Map model = controller.edit()

        then:
        1 * githubAuthService.getCurrentUser(controller.request) >> user
        1 * blogApi.getAuthorByGithubName(user.login) >> author
        1 * blogApi.getPostBySlug(author.id, slug) >> post
        0 * _

        assert model.size() == 3
        assert ! model.newPost
        assert model.post.is(post)
        assert model.errors == []
    }

    def 'editing existing post with no errors'() {
        given:
        Post post = new Post(id: 1, slug: slug)

        when:
        controller.request.method = 'POST'
        controller.params.id = slug
        Map model = controller.edit(new PostCommand(id: 1, slug: slug))

        then:
        1 * githubAuthService.getCurrentUser(controller.request) >> user
        1 * blogApi.getAuthorByGithubName(user.login) >> author
        1 * blogApi.updatePost(post.id, {it.id == 1}) >> post
        0 * _

        assert model.size() == 3
        assert ! model.newPost
        assert model.post == post
        assert model.errors == []
    }

    def 'editing existing post with errors'() {
        given:
        Post post = new Post(id: 1, slug: slug)
        String errorMsg = "Non unique slug"
        Errors errors = new Errors(errorMsg)
        Response errorResponse = buildErrorResponse(errors)

        when:
        controller.request.method = 'POST'
        controller.params.id = slug
        Map model = controller.edit(new PostCommand(id: 1, slug: slug))

        then:
        1 * githubAuthService.getCurrentUser(controller.request) >> user
        1 * blogApi.getAuthorByGithubName(user.login) >> author
        1 * blogApi.updatePost(post.id, {it.id == 1}) >> {throw RetrofitError.httpError(null, errorResponse, new JacksonConverter(new ObjectMapper()), null)}
        0 * _

        assert model.size() == 3
        assert ! model.newPost
        assert model.post.id == post.id
        assert model.post.slug == post.slug
        assert model.post.author == author
        assert ! model.post.draft
        assert model.errors == [errorMsg]
    }

    private Response buildErrorResponse(Errors errors) {
        ObjectMapper objectMapper = new ObjectMapper()
        TypedInput errorBody = new TypedString(objectMapper.writeValueAsString(errors))
        return new Response(409, 'validation error', [], errorBody)
    }

}
