package charliek.blog.client

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.datatype.joda.JodaModule

class ObjectMapperFactory {

    ObjectMapper build() {
        return new ObjectMapper()
                .registerModule(new JodaModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
                .disable(MapperFeature.AUTO_DETECT_IS_GETTERS)
    }

    ObjectMapper buildSnakeCase() {
        return build().setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES)
    }

}
