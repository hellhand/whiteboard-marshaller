package org.samples.marshalling.rest;

import org.apache.camel.EndpointInject;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.apache.http.client.fluent.Request;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by marc on 13/07/15.
 */
public class MarshallingRouteTest extends CamelBlueprintTestSupport {

    @EndpointInject(uri = "mock:direct:marshall")
    private MockEndpoint mockEndpoint;

    @Override
    protected String getBlueprintDescriptor() {
        return "/OSGI-INF/blueprint/blueprint.xml" + "," +
                "/OSGI-INF/blueprint/blueprint-camel.xml"+","+
                "/OSGI-INF/blueprint/blueprint-properties.xml";
    }

    @Before
    public void init() throws Exception {
        context().getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                weaveByToString(".*marshall.*").replace().log(LoggingLevel.INFO, "Into : " + MarshallingRouteTest.class.getName()).to("mock:direct:marshall");
            }
        });
        // we must manually start when we are done with all the advice with
        context().start();
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Test
    public void testMarshallerService() throws Exception {
        mockEndpoint.expectedMessageCount(1);

        Request.Get("http://localhost:" + 50000 + "/marshaller")
                .addHeader("Accept", "application/json")
                .addHeader("body", "java.lang.Exception")
                .execute()
                .returnContent()
                .asString();

        mockEndpoint.assertIsSatisfied();
    }
}