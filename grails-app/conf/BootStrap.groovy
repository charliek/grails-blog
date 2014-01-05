import com.charlieknudsen.ribbon.etcd.EtcdPublisher
import org.codehaus.groovy.grails.commons.GrailsApplication

class BootStrap {

    EtcdPublisher etcdPublisher
    GrailsApplication grailsApplication

    def init = { servletContext ->
        log.info("starting etcd publisher")
        etcdPublisher?.publishOnInterval(
                grailsApplication.config.etcd.publish.ttlSeconds as Long,
                grailsApplication.config.etcd.publish.publishSeconds as Long)
    }
    def destroy = {
        log.info("Stopping etcd publisher")
        etcdPublisher?.stop()
    }
}
