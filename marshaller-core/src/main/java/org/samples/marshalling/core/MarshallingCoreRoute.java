package org.samples.marshalling.core;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by marc.boulanger on 18/07/15.
 */
public class MarshallingCoreRoute extends RouteBuilder {

    private static final String ROUTE_NAME = "direct:marshall";

    @Override
    public void configure() throws Exception {

        from(ROUTE_NAME)
                .id(MarshallingCoreRoute.class.getName())
                .log(LoggingLevel.INFO, "In direct:marshall")
                .transform(method("marshallingTransformer", "transform"))
                .dynamicRouter(method("marshallingService", "route"))
                .convertBodyTo(String.class)
                .end();
    }
}
