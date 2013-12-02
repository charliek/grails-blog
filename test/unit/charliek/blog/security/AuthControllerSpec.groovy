package charliek.blog.security

import charliek.blog.transfer.github.AccessToken
import grails.test.mixin.*
import spock.lang.Specification

@TestFor(AuthController)
class AuthControllerSpec extends Specification {

    GithubAuthService githubAuthService

    def setup() {
        githubAuthService = Mock(GithubAuthService)

        controller.githubAuthService = githubAuthService
    }

    def 'missing code param'() {
        when:
        controller.params.code = null
        controller.index()

        then:
        assert controller.response.cookies.size() == 0
        assert controller.response.redirectUrl == '/post'
    }

    def 'correct code but invalid token call'() {
        when:
        controller.params.code = 'abc'
        controller.index()

        then:
        1 * githubAuthService.getAccessToken('abc') >> null
        0 * _

        assert controller.response.cookies.size() == 0
        assert controller.response.redirectUrl == '/post'
    }

    def 'valid login'() {
        when:
        controller.params.code = 'abc'
        controller.index()

        then:
        1 * githubAuthService.getAccessToken('abc') >> new AccessToken(accessToken: 'xyz')
        0 * _

        assert controller.response.cookies.size() == 1
        assert controller.response.cookies[0].name == GithubAuthService.TOKEN_COOKIE_NAME
        assert controller.response.cookies[0].value == 'xyz'
        assert controller.response.redirectUrl == '/admin'
    }


}
