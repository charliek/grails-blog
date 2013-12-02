package charliek.blog.ui

import grails.test.mixin.*
import spock.lang.Specification

@TestFor(AboutController)
class AboutControllerSpec extends Specification {

    def 'test basic page'() {
        when:
        Map model = controller.index()

        then:
        assert model.size() == 0
    }

}
