import org.apache.log4j.net.SyslogAppender

import java.util.concurrent.TimeUnit
import static grails.util.Environment.DEVELOPMENT
import static grails.util.Environment.CUSTOM
import static grails.util.Environment.TEST
import static grails.util.Environment.currentEnvironment
import static org.apache.log4j.Level.ALL

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

        appender new SyslogAppender(name:"syslog",
                syslogHost: "localhost:514",
                facility: "local1",
                threshold: ALL,
                layout: pattern(conversionPattern:"grails-blog [%p] %-25.25c{2} %m%n"))
    }

    root {
        info('stdout')
        info('syslog')
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
        debug 'com.charlieknudsen'
    } else {
        info 'grails.app'
        info 'grails.app.filters.LoggingFilters'
        info 'charliek'
        debug 'com.charlieknudsen'
    }
}

jodatime {
    format.org.joda.time.DateTime = "yyyy-MM-dd HH:mm:ss"
    format.org.joda.time.LocalDate = "yyyy-MM-dd"
    format.org.joda.time.LocalTime = "HH:mm:ss"
    format.org.joda.time.LocalDateTime = "MMM dd, yyyy"
}

defaultEnvironment = [
        'CLIENT_PREFIX': 'http://localhost:5678',
        'ETCD_URL': 'http://127.0.0.1:4001',
        'HOST_IP': '127.0.0.1',
        'PROXY_URL' : ''
]

httpClientConfig {
    connectionTimeOut = TimeUnit.SECONDS.toMillis(2)
    readTimeout = TimeUnit.SECONDS.toMillis(2)
    proxyUrl = '${PROXY_URL}'
}

etcd {
    enabled = true

    ribbon {
        MaxAutoRetries=2
        MaxAutoRetriesNextServer=1
        OkToRetryOnAllOperations=true
        ServerListRefreshInterval=2000
        ribbon.ConnectTimeout=3000
        ribbon.ReadTimeout=3000
        ClientClassName='com.charlieknudsen.ribbon.retrofit.LoadBalancedClient'
        NIWSServerListClassName='com.charlieknudsen.ribbon.etcd.EtcdServerList'
    }

    client {
        host = '${ETCD_URL}'
    }
    publish {
        host = '${HOST_IP}'
        ttlSeconds = 10
        publishSeconds = 3
    }
}

github {
    reauthenticateUrl = 'https://github.com/login/oauth/authorize'
    applicationScope = 'repo'

    // It is required that you change these keys before the application will work
    // Visit https://github.com/settings/applications to setup your application
    clientID = '${GITHUB_CLIENT_ID}'
    clientSecret = '${GITHUB_SECRET}'
}

clients {
    blog {
        // Allow client prefix to be set by environment variables with defaults set in config
        url = '${CLIENT_PREFIX}'
        discover = false
        name = 'blog-service'
        apis = [ 'charliek.blog.client.BlogApi' ]
    }
}

// Uncomment and edit the following lines to start using Grails encoding & escaping improvements

/* remove this line 
// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside null
                scriptlet = 'none' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        filteringCodecForContentType {
            //'text/html' = 'html'
        }
    }
}
remove this line */
