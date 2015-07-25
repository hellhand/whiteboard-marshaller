package org.samples.marshalling.provider;

import org.apache.camel.LoggingLevel;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.dataformat.StringDataFormat;
import org.apache.camel.spi.DataFormat;
import org.samples.marshalling.api.AbstractMarshallingProvider;

/**
 * Created by marc.boulanger on 19/07/15.
 */
public class FakeProvider extends AbstractMarshallingProvider {

    private static final String ROUTE_NAME = "direct:test-json";

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
        from(ROUTE_NAME)
                .id(FakeProvider.class.getName())
                .log(LoggingLevel.INFO, "In " + ROUTE_NAME)
                .marshal()
                .json(JsonLibrary.Jackson, String.class)
                .end();
    }
}
