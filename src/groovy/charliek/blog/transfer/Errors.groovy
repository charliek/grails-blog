package charliek.blog.transfer

import groovy.transform.CompileStatic

@CompileStatic
class Errors {
    List<FieldError> fieldErrors = []
    List<ClassError> objectErrors = []

    Errors() {}

    Errors(String objectError) {
        objectErrors << new ClassError(message: objectError)
    }
}
