package au.com.maxcheung.camelsimple.route;

import org.apache.camel.CamelContext;
import org.apache.camel.Processor;

import au.com.maxcheung.camelsimple.model.RouteDef;

public class DefaultRouteBuilder extends AbsRouteBuilder {

	public DefaultRouteBuilder(CamelContext camelContext, Processor processor, RouteDef routeDef) {
		super(camelContext, processor, routeDef);
	}

	@Override
	public void configure() throws Exception {
		from(from)
			.routeId(routeId)
			.setHeader("applicationId", constant(applicationId))
            .process(processor)
			.to(toUris)
			.tracing(tracing)
			.log(log);
	}

}