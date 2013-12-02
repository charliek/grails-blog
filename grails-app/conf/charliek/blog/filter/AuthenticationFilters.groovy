package charliek.blog.filter

import charliek.blog.security.GithubAuthService

class AuthenticationFilters {

    GithubAuthService githubAuthService

    def filters = {
        all(controller: '(auth|post|about)', invert: true) {
            before = {
                if (!githubAuthService.isUserRequestAuthenticated(request, response)) {
                    if (request.xhr) {
                        log.info('Failed to authenticate AJAX... responding with 401')
                        response.sendError(401)
                    } else {
                        log.info('Failed to authenticate... redirecting')
                        redirect(url: githubAuthService.authUrl)
                    }
                    return false
                }
                return true
            }
        }
    }
}
