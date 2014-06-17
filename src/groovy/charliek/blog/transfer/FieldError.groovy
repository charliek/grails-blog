package charliek.blog.transfer

import groovy.transform.CompileStatic

@CompileStatic
class FieldError {
    String fieldName
    String message
    String messageProperty

    FieldError(){}
}
