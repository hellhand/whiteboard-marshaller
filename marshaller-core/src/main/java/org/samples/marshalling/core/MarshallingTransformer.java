package org.samples.marshalling.core;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by marc.boulanger on 19/07/15.
 */
public class MarshallingTransformer {

    private final static Logger LOGGER = LoggerFactory.getLogger(MarshallingTransformer.class);

    @SuppressWarnings(value = "unused")
    public void transform(Exchange exchange) {
        try {
            exchange.getOut()
                    .setBody(Class.forName((String) exchange.getIn().getHeader("Class")).newInstance());
        }
        catch (ClassNotFoundException e) {
            LOGGER.error("Cannot find class {}", exchange.getIn().getHeader("body"));
        }
        catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("Cannot instantiate class {}", exchange.getIn().getHeader("body"));
        }
    }
}
