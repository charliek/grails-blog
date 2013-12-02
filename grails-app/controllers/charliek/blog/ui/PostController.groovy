package charliek.blog.ui

import charliek.blog.client.BlogApi
import charliek.blog.transfer.Author
import charliek.blog.transfer.Post
import retrofit.RetrofitError

class PostController {

    BlogApi blogApi

    def list() {
        List<Post> posts = blogApi.allPosts.findAll {! it.draft}
        return ['posts': posts]
    }

    def show() {
        try {
            Author author = blogApi.getAuthorByGithubName(params.author)
            Post post = blogApi.getPostBySlug(author.id, params.slug)
            return ['post': post]
        } catch (RetrofitError e) {
            log.info('error retrieving post', e)
        }
        return redirect(action: 'list')
    }

}
