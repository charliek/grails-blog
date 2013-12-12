grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.7
grails.project.source.level = 1.7
//grails.project.war.file = "target/${appName}-${appVersion}.war"

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()
        mavenRepo "http://dl.bintray.com/content/charliek/maven"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.

        test 'org.spockframework:spock-grails-support:0.7-groovy-2.0'
        test 'cglib:cglib-nodep:2.2.2'
        test 'org.objenesis:objenesis:1.2'

        compile('com.github.charliek.service:blog-api:0.0.1') {
            excludes group: 'org.codehaus.groovy', module: 'groovy-all'
        }

//        compile 'org.pegdown:pegdown:1.4.1'
        compile 'com.google.guava:guava:15.0'

        compile 'com.fasterxml.jackson.core:jackson-databind:2.2.3'
        compile 'com.fasterxml.jackson.datatype:jackson-datatype-joda:2.2.3'

        compile 'com.netflix.rxjava:rxjava-core:0.15.0'
        compile 'com.squareup.retrofit:retrofit:1.3.0'
        compile 'com.squareup.retrofit:retrofit-converters:1.3.0'
        compile 'com.squareup.retrofit:converter-jackson:1.3.0'
        compile 'com.squareup.okhttp:okhttp:1.2.1'
    }

    plugins {
        test(':spock:0.7') {
            exclude 'spock-grails-support'
        }
        compile ":fields:1.3"
        compile ":joda-time:1.4"
        build ":tomcat:$grailsVersion"
    }
}
