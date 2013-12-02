import java.util.concurrent.TimeUnit
import static grails.util.Environment.DEVELOPMENT
import static grails.util.Environment.CUSTOM
import static grails.util.Environment.TEST
import static grails.util.Environment.currentEnvironment

def configLocation = System.getenv('BLOOM_CONFIG_LOCATION') ?: System.getenv('grails.work.dir') ?: "${userHome}/.grails"
def configFilePath = "file:$configLocation/${appName}-config.groovy"
println "**** Externalized config file directory is $configFilePath"
grails.config.locations = [configFilePath]

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"

// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true

// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false

// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true

// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration
log4j = {

    appenders {

        String logFormat = ''
        if (currentEnvironment in [DEVELOPMENT, TEST, CUSTOM]) {
            logFormat += '%d{HH:mm:ss}'
        } else {
            logFormat += '%d{dd MMM yyyy HH:mm:ss}'
        }
        logFormat += ' [%5p] %-30.30c{2} %m%n'
        def logPattern = pattern(conversionPattern: logFormat)

        console name: 'stdout', layout: logPattern
        //Explicitly setting the app name here so we don't log out an Error everytime we run even though it works
        def logfile = "logs/blog-${currentEnvironment}.log"
        appender new org.apache.log4j.DailyRollingFileAppender(name: 'logfile',
                fileName: logfile, datePattern: "'.'yyyy-MM-dd", layout: logPattern)
    }

    // environment specific overrides
    root {
        if (currentEnvironment in [DEVELOPMENT]) {
            info('stdout', 'logfile')
        } else if (currentEnvironment in [TEST, CUSTOM]) {
            info('logfile')
        } else {
            warn('logfile')
        }
        additivity = false
    }

    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
            'org.codehaus.groovy.grails.web.pages',          // GSP
            'org.codehaus.groovy.grails.web.sitemesh',       // layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping',        // URL mapping
            'org.codehaus.groovy.grails.commons',            // core / classloading
            'org.codehaus.groovy.grails.plugins',            // plugins
            'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'

    if (currentEnvironment in [DEVELOPMENT, TEST, CUSTOM]) {
        debug 'spring.BeanBuilder'
        debug 'charliek'
    } else {
        info 'grails.app'
        info 'grails.app.filters.LoggingFilters'
        info 'charliek'
    }
}

httpClientConfig {
    connectionTimeOut = TimeUnit.SECONDS.toMillis(2)
    readTimeout = TimeUnit.SECONDS.toMillis(2)
    proxyUrl = null
//    proxyUrl = 'http://localhost:8888'
}

github {
    reauthenticateUrl = 'https://github.com/login/oauth/authorize'
    applicationScope = 'repo'

    // It is required that you change these keys before the application will work
    // Visit https://github.com/settings/applications to setup your application
    clientID = 'xxxxxxxxxxxxxxxxxxxx'
    clientSecret = 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx'
}

clients {
    blog {
        url = 'http://localhost:5678'
        apis = [
                'charliek.blog.client.BlogApi'
        ]
    }
}
