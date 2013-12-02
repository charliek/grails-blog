package charliek.blog.ui

import charliek.blog.client.BlogApi
import charliek.blog.transfer.Author
import charliek.blog.transfer.Post
import grails.test.mixin.*
import retrofit.RetrofitError
import spock.lang.Specification

@TestFor(PostController)
class PostControllerSpec extends Specification {

    BlogApi blogApi

    def setup() {
        blogApi = Mock(BlogApi)

        controller.blogApi = blogApi
    }

    def 'list controller with all posts'() {
        when:
        Map model = controller.list()

        then:
        1 * blogApi.allPosts >> [new Post(draft: true), new Post(draft: false)]
        0 * _

        assert model.size() == 1
        assert model.posts.size() == 1
    }

    def 'show individual post for valid data'() {
        given:
        String author = 'marktwain'
        String slug = 'Adventures+of+Huckleberry Finn'
        Author authorObj = new Author(id: 1)
        Post post = new Post()

        when:
        params.author = author
        params.slug = slug
        Map model = controller.show()

        then:
        1 * blogApi.getAuthorByGithubName(author) >> authorObj
        1 * blogApi.getPostBySlug(1, slug) >> post
        0 * _

        assert model.size() == 1
        assert model.post.is(post)
    }

    def 'show post with invalid data'() {
        given:
        String author = 'marktwain'
        String slug = 'Adventures+of+Huckleberry Finn'

        when:
        params.author = author
        params.slug = slug
        controller.show()

        then:
        1 * blogApi.getAuthorByGithubName(author) >> {throw RetrofitError.httpError(null, null, null, null)}
        0 * _

        assert controller.response.redirectedUrl == '/post/list'
    }
}
