package charliek.blog.transfer.github

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class User {
    String email
    String type
    String gistsUrl
    String url
    String receivedEventsUrl
    String company
    String login
    String publicGists
    String organizationsUrl
    Integer followers
    Integer following
    String starredUrl
    String name
    String location
    String reposUrl
    String followersUrl
    String followingUrl
    String gravatarId
    String avatarUrl
    BigInteger id
    String htmlUrl
    String eventsUrl
    String subscriptionsUrl

    /**
     * This token is not returned by the application, but is populated by the application to be used for future
     * requests.
     */
    String authToken
}