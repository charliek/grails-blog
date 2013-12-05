package charliek.blog

class Variables {

    static String substituteEnvironment(String s, Map<String, String> defaults = [:]) {
        Map<String, String> values = [:]
        values.putAll(defaults)
        values.putAll(System.getenv())
        return substituteVariables(s, values)
    }

    static private String substituteVariables(String s, Map<String, String> substitutions) {
        substitutions.each { key, value ->
            s = s.replace("\${${key}}", value)
        }
        return s
    }

}
