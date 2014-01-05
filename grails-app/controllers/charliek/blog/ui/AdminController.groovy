package charliek.blog.ui

import charliek.blog.security.GithubAuthService
import charliek.blog.client.BlogApi
import charliek.blog.transfer.Author
import charliek.blog.transfer.Errors
import charliek.blog.transfer.Post
import charliek.blog.transfer.github.User
import grails.validation.Validateable
import retrofit.RetrofitError

class AdminController {

    BlogApi blogApi
    GithubAuthService githubAuthService

    def index() {
        User user = githubAuthService.getCurrentUser(request)
        Author author = blogApi.getAuthorByGithubName(user.login)
        List<Post> posts = blogApi.getPostsByAuthor(author.id)
        return ['posts': posts]
    }

    def edit(PostCommand command) {
        User user = githubAuthService.getCurrentUser(request)
        Author author = blogApi.getAuthorByGithubName(user.login)

        Post post = new Post()
        boolean newPost = true
        List<String> errors = []
        boolean success = false

        if(request.method == 'POST') {
            post = command.toPost()
            try {
                post.author = author
                if(post.id == null) {
                    post = blogApi.addPost(post)
                } else {
                    newPost = false
                    post = blogApi.updatePost(post.id, post)
                }
                newPost = false
                success = true
            } catch (RetrofitError e) {
                errors.addAll(handleErrors(e))
            }
        } else {
            success = true
            String slug = params.id as String
            if(slug) {
                post = blogApi.getPostBySlug(author.id, slug)
                newPost = false
            }
        }
        return ['post': post, 'newPost': newPost, 'errors': errors, 'success': success]
    }

    private List<String> handleErrors(RetrofitError e) {
        List<String> errors = []
        if(e.response?.status == 409) {
            Errors serviceErrors = e.getBodyAs(Errors) as Errors
            serviceErrors?.objectErrors?.each {
                errors << it.message
            }
            serviceErrors?.fieldErrors?.each {
                errors << "${it.fieldName} : ${it.message}"
            }
            return errors
        } else {
            throw e
        }
    }
}

@Validateable
class PostCommand {

    Long id
    String slug
    String title
    String body
    boolean draft

    Post toPost() {
        Post post = new Post()
        post.id = id
        post.slug = slug
        post.title = title
        post.body = body
        post.draft = draft
        return post
    }
}