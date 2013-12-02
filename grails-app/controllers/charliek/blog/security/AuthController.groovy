package charliek.blog.security

import charliek.blog.transfer.github.AccessToken

import javax.servlet.http.Cookie
import static java.util.concurrent.TimeUnit.DAYS

class AuthController {

    GithubAuthService githubAuthService

    def index() {
        if(params.code != null) {
            AccessToken token = githubAuthService.getAccessToken(params.code)
            if(token != null) {
                Cookie authCookie = new Cookie(GithubAuthService.TOKEN_COOKIE_NAME, token.accessToken)
                authCookie.setMaxAge(DAYS.toSeconds(14).toInteger())
                response.addCookie(authCookie)
                return redirect(controller: 'admin')
            }
        }
        return redirect(controller: 'post')
    }
}
