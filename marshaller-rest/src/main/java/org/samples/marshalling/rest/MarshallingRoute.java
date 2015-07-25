package org.samples.marshalling.rest;

import org.apache.camel.builder.RouteBuilder;

public class MarshallingRoute extends RouteBuilder {

    public void configure() {

        rest("/marshaller")
                .id(MarshallingRoute.class.getName())
                .get().route()
                .to("direct:marshall")
                .endRest();
    }

}
