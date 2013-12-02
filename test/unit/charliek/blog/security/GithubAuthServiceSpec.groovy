package charliek.blog.security

import charliek.blog.client.GithubApi
import charliek.blog.client.GithubTokenApi
import charliek.blog.security.GithubAuthService
import charliek.blog.transfer.github.User
import com.google.common.cache.Cache
import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.commons.GrailsApplication
import retrofit.RetrofitError
import spock.lang.Specification

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest

@TestFor(GithubAuthService)
class GithubAuthServiceSpec extends Specification {

    GrailsApplication grailsApplication
    GithubApi githubApi
    GithubTokenApi githubTokenApi
    Cache<String, User> userCache
    HttpServletRequest request

    String clientId = 'client_id'
    String clientSecret = 'client_secret'
    String authToken = 'abc'
    String applicationScope = 'repo'
    String reauthenticateUrl = 'github.com'
    User user = new User(login: 'charliek')

    def setup() {
        githubApi = Mock(GithubApi)
        githubTokenApi = Mock(GithubTokenApi)
        grailsApplication = ['config' : [
                github : [
                    'clientID': clientId,
                    'clientSecret': clientSecret,
                    'applicationScope' : applicationScope,
                    'reauthenticateUrl' : reauthenticateUrl]
                ]
        ] as GrailsApplication
        userCache = Mock(Cache)
        request = Mock(HttpServletRequest)

        service.githubApi = githubApi
        service.githubTokenApi = githubTokenApi
        service.grailsApplication = grailsApplication
        service.userCache = userCache
    }

    def 'authenticated on a valid uncached user with token'() {
        when:
        boolean valid = service.isUserRequestAuthenticated(request, null)

        then:
        1 * request.getCookies() >> [new Cookie(GithubAuthService.TOKEN_COOKIE_NAME, authToken)].toArray()
        1 * userCache.getIfPresent(authToken) >> null
        1 * githubApi.getUser(authToken) >> user
        1 * userCache.put(authToken, user)
        1 * request.setAttribute(GithubAuthService.REQUEST_USER_KEY, user)
        0 * _

        assert valid
    }

    def 'not authenticated on a service exception'() {
        when:
        boolean valid = service.isUserRequestAuthenticated(request, null)

        then:
        1 * request.getCookies() >> [new Cookie(GithubAuthService.TOKEN_COOKIE_NAME, authToken)].toArray()
        1 * userCache.getIfPresent(authToken) >> null
        1 * githubApi.getUser(authToken) >> {throw RetrofitError.httpError(null, null, null, null)}
        0 * _

        assert ! valid
    }

    def 'authenticated on a valid cached user'() {
        when:
        boolean valid = service.isUserRequestAuthenticated(request, null)

        then:
        1 * request.getCookies() >> [new Cookie(GithubAuthService.TOKEN_COOKIE_NAME, authToken)].toArray()
        1 * userCache.getIfPresent(authToken) >> user
        1 * request.setAttribute(GithubAuthService.REQUEST_USER_KEY, user)
        0 * _

        assert valid
    }

    def 'not authenticated when missing auth cookie'() {
        when:
        boolean valid = service.isUserRequestAuthenticated(request, null)

        then:
        1 * request.getCookies() >> null
        0 * _

        assert ! valid
    }

    def 'user with no login is invalid'() {
        when:
        boolean valid = service.validUser(userToValidate)

        then:
        assert valid == ok

        where:
        ok    | userToValidate
        true  | new User(login: 'xyz')
        false | new User()
        false | null

    }

    def 'get current user'() {
        when:
        User u = service.getCurrentUser(request)

        then:
        1 * request.getAttribute(GithubAuthService.REQUEST_USER_KEY) >> user
        0 * _

        assert u.is(u)
    }

}
