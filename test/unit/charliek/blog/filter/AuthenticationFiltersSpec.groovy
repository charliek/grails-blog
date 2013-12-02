package charliek.blog.filter

import charliek.blog.security.GithubAuthService
import charliek.blog.ui.AdminController
import grails.test.mixin.*
import org.springframework.beans.factory.config.MethodInvokingFactoryBean
import spock.lang.Specification

@TestFor(AdminController)
@Mock(AuthenticationFilters)
class AuthenticationFiltersSpec extends Specification {

    GithubAuthService githubAuthService

    def setup() {
        githubAuthService = Mock(GithubAuthService)

        defineBeans {
            githubAuthService(MethodInvokingFactoryBean) {
                targetObject = this
                targetMethod = 'getGithubAuthService'
            }
        }
    }

    def 'redirects to posts on authentication error'() {
        when:
        withFilters(action: 'index') {
            controller.index()
        }

        then:
        1 * githubAuthService.isUserRequestAuthenticated(controller.request, controller.response) >> false
        1 * githubAuthService.authUrl >> 'github.com'
        0 * _

        assert response.redirectedUrl == 'github.com'
    }
}
