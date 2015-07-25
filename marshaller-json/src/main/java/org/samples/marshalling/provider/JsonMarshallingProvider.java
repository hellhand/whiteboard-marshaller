package org.samples.marshalling.provider;

import org.apache.camel.model.dataformat.JsonLibrary;
import org.samples.marshalling.api.AbstractMarshallingProvider;

/**
 * Created by marc on 23/06/15.
 */
public class JsonMarshallingProvider extends AbstractMarshallingProvider {

    private static final String ROUTE_NAME = "direct:marshaller-json";

    @Override
    public String getFormat() {
        return "application/json";
    }

    @Override
    public String getRoute() {
        return ROUTE_NAME;
    }

    @Override
    public void configure() throws Exception {
        from(ROUTE_NAME).marshal().json(JsonLibrary.Jackson).end();
    }
}
