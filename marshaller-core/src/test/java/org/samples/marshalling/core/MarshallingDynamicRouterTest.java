package org.samples.marshalling.core;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Assert;
import org.junit.Test;
import org.samples.marshalling.provider.FakeProvider;

/**
 * Created by marc on 13/07/15.
 */
public class MarshallingDynamicRouterTest extends CamelBlueprintTestSupport {

    public static final String MESSAGE = "{\"string\":\"A String\",\"integer\":1}";

    @Override
    protected String getBlueprintDescriptor() {
        return "/OSGI-INF/blueprint/blueprint.xml" + "," +
                "/OSGI-INF/blueprint/blueprint-camel.xml" + "," +
                "/OSGI-INF.blueprint/test-blueprint.xml";
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext camelContext = super.createCamelContext();
        camelContext.addRoutes(new FakeProvider());
        return camelContext;
    }

    @Test
    public void testMarshallerCore() throws Exception {
        Exchange exchange = new DefaultExchange(context);
        exchange.setPattern(ExchangePattern.InOut);
        exchange.getIn().setHeader("Accept", "application/json");
        exchange.getIn().setHeader("Class", "org.samples.marshalling.dto.SimplePOJO");

        template.send("direct:marshall", exchange);
        Assert.assertEquals(MESSAGE, exchange.getOut().getBody());
    }

    @Override
    public boolean isUseDebugger() {
        return true;
    }

    @Override
    protected void debugBefore(Exchange exchange, org.apache.camel.Processor processor, ProcessorDefinition<?> definition, String id, String label) {
        log.info("Before " + definition + " with body " + exchange.getIn().getBody());
    }

    @Override
    protected void debugAfter(Exchange exchange, org.apache.camel.Processor processor, ProcessorDefinition<?> definition, String id, String label, long timeTaken) {
        log.info("After " + definition + " with body " + exchange.getIn().getBody());
    }
}
