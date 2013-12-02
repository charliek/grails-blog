package charliek.blog.security

import charliek.blog.client.GithubApi
import charliek.blog.client.GithubTokenApi
import charliek.blog.transfer.github.AccessToken
import charliek.blog.transfer.github.User
import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import org.codehaus.groovy.grails.commons.GrailsApplication
import retrofit.RetrofitError

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.concurrent.TimeUnit

class GithubAuthService {

    static transactional = false

    static final String TOKEN_COOKIE_NAME = 'blog_token'
    static final String REQUEST_USER_KEY = 'user'

    GrailsApplication grailsApplication
    GithubApi githubApi
    GithubTokenApi githubTokenApi

    private Cache<String, User> userCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(20, TimeUnit.MINUTES)
            .build()

    boolean validUser(User user) {
        return user?.login != null
    }

    boolean isUserRequestAuthenticated(HttpServletRequest request, HttpServletResponse response) {
        String apiToken = getApiToken(request.cookies)
        if(apiToken != null) {
            User user = userCache.getIfPresent(apiToken)
            if(user == null) {
                try {
                    user = githubApi.getUser(apiToken)
                    if(validUser(user)) {
                        userCache.put(apiToken, user)
                    } else {
                        return false
                    }
                } catch (RetrofitError e) {
                    log.warn('Error calling github api', e)
                    return false
                }
            }
            request.setAttribute(REQUEST_USER_KEY, user)
            return true
        }
        return false
    }

    private String getApiToken(Cookie[] cookies) {
        Cookie token = cookies.find {it.name == TOKEN_COOKIE_NAME}
        return token?.value
    }

    AccessToken getAccessToken(String code) {
        String clientSecret = grailsApplication.config.github.clientSecret
        return githubTokenApi.lookupAccessToken(clientId, clientSecret, code)
    }

    User getCurrentUser(HttpServletRequest request) {
        return request.getAttribute(REQUEST_USER_KEY) as User
    }

    String getAuthUrl() {
        String applicationScope = grailsApplication.config.github.applicationScope
        return "${grailsApplication.config.github.reauthenticateUrl}?client_id=${clientId}&scope=${applicationScope}"
    }

    private String getClientId() {
        return grailsApplication.config.github.clientID
    }
}
