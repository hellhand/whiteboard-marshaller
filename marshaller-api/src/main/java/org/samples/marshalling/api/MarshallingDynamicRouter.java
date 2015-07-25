package org.samples.marshalling.api;

import org.apache.camel.Exchange;

/**
 * Created by marc on 23/06/15.
 */
public interface MarshallingDynamicRouter {

    String route(Exchange exchange);
}
