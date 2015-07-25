package org.samples.marshalling.core;

import org.apache.camel.Exchange;
import org.samples.marshalling.api.MarshallingDynamicRouter;
import org.samples.marshalling.api.MarshallingProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by marc on 23/06/15.
 */
public class MarshallingCoreDynamicRouter implements MarshallingDynamicRouter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarshallingCoreDynamicRouter.class);

    private final Map<String, MarshallingProvider> providers;

    public MarshallingCoreDynamicRouter() {
        this.providers = new ConcurrentHashMap<>();
    }

    @SuppressWarnings(value = "unused")
    public void register(final MarshallingProvider marshallingProvider) {
        Optional.ofNullable(marshallingProvider).ifPresent(provider -> LOGGER.info("registering provider : {} for {}", provider.getClass(), provider.getFormat()));
        Optional.ofNullable(marshallingProvider).ifPresent(provider -> providers.putIfAbsent(provider.getFormat(), provider));
    }

    @SuppressWarnings(value = "unused")
    public void unregister(final MarshallingProvider marshallingProvider) {
        Optional.ofNullable(marshallingProvider).ifPresent(provider -> LOGGER.info("un-registering provider : {} for {}", provider.getClass(), provider.getFormat()));
        Optional.ofNullable(marshallingProvider).ifPresent(provider -> providers.remove(provider.getFormat()));
    }

    @Override
    public String route(final Exchange exchange) {
        Optional.ofNullable(providers.get(exchange.getIn().getHeader("Accept"))).ifPresent(provider -> exchange.getIn().setHeader("NextRoute", provider.getRoute()));
        if (Optional.ofNullable(exchange.getIn().getHeader("Routed")).isPresent()) {
            exchange.getIn().removeHeader("NextRoute");
        }
        else {
            exchange.getIn().setHeader("Routed", Boolean.TRUE);
        }
        return Optional.ofNullable(exchange.getIn().getHeader("NextRoute")).isPresent() ? exchange.getIn().getHeader("NextRoute").toString() : null;
    }
}