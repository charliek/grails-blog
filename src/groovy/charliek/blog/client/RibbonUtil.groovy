package charliek.blog.client

import com.netflix.config.ConfigurationManager
import groovy.util.logging.Slf4j
import org.apache.commons.configuration.AbstractConfiguration

@Slf4j
class RibbonUtil {

    private static AbstractConfiguration archaiusConfig = ConfigurationManager.getConfigInstance()

    static public setConfigValue(String keyName, Object value) {
        log.info("Setting archaius from groovy for ribbon values ${keyName} = ${value}")
        archaiusConfig.setProperty(keyName, value)
    }

    static public initializeRibbonDefaults(ConfigObject grailsConfig) {
        setConfig("ribbon", grailsConfig)
    }

    static private setConfig(String prefix, ConfigObject grailsConfig) {
        for( String key in grailsConfig.keySet() ) {
            String keyName = prefix + '.' + key
            Object value = grailsConfig[key]
            if (value instanceof ConfigObject) {
                setConfig(keyName, value)
            } else {
                setConfigValue(keyName, value)
            }
        }
    }
}
