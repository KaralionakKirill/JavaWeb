package com.epam.bar.tag;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.log4j.Log4j2;

/**
 * The class convert java object to json
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class ObjectToJSON {

    /**
     * Obj to json string.
     *
     * @param object the object
     * @return the string
     */
    public static String objToJSON(Object object) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e);
        }
        return json;
    }
}
