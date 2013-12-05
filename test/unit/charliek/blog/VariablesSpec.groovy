package charliek.blog

import spock.lang.Specification

class VariablesSpec extends Specification {

    def 'validate variable substitution'() {
        given:
        GroovySpy(System, global: true)

        when:
        String after = Variables.substituteEnvironment(pre, defaults)

        then:
        1 * System.getenv() >> env
        0 * _

        assert after == post

        where:
        post                            | pre                        | defaults                 | env
        'https://www.google.com/search' | 'https://${CLIENT}/search' | ['DB':'jdbc://whatever'] | ['CLIENT' : 'www.google.com']
        'jdbc://whatever/blog'          | '${DB}/blog'               | ['DB':'jdbc://whatever'] | ['CLIENT' : 'www.google.com']
        'Im ${NOT FOUND}'               | 'Im ${NOT FOUND}'          | [:]                      | ['y.x.y' : 'xyz']
    }

}
