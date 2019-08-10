package com.maxcheung.camelsimple.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.model.RouteDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxcheung.camelsimple.model.RouteDef;
import com.maxcheung.camelsimple.route.DefaultRouteBuilder;
import com.maxcheung.camelsimple.route.processor.NoopProcessor;

@Service
public class RouteServiceImpl implements RouteService {

	private static final String CAMELSIMPLE_ROUTE_PATH = "camelsimple.route.path";

	private static final Logger LOG = LoggerFactory.getLogger(RouteServiceImpl.class);

	private final CamelContext camelContext;
	private final Environment env;
	private List<RouteDef> routeDefs;
	private ObjectMapper mapper = new ObjectMapper();
	;
	
	@Autowired
	public RouteServiceImpl(Environment env, CamelContext camelContext) {
		this.env = env;
		this.camelContext = camelContext;
		loadRoutes();
	}

	public void loadRoutes() {
		try {
			this.routeDefs = initRoute();
		} catch (Exception e) {
			LOG.error("Exception ocurred loading routes {}", e);
		}
	}


	@Override
	public List<RouteDef> getRouteDefs() {
		return routeDefs;
	}

	@Override
	public List<String> getCamelRoutes() {
		List<String> routes = new ArrayList<String>();
		List<RouteDefinition> camelDefs = camelContext.getRouteDefinitions();
		for (RouteDefinition routeDefinition : camelDefs) {
			routes.add(routeDefinition.toString());
		}
		return routes;
	}

	@Override
	public List<File> getFiles(String locationPattern) throws IOException {
		List<File> filesInFolder = Files.walk(Paths.get(locationPattern))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
		return filesInFolder;
	}
	
	private RoutesBuilder getRouteBuilder(RouteDef routeOptions) {
		RoutesBuilder routesBuilder;
			routesBuilder = new DefaultRouteBuilder(camelContext, new NoopProcessor(), routeOptions);
		return routesBuilder;
	}

	Resource[] loadResources(String pattern) throws IOException {
		return ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader()).getResources(pattern);
	}

	public List<RouteDef> initRoute() throws Exception {
		List<RouteDef> routes = new ArrayList<RouteDef>();
		String resourcePath = env.getProperty(CAMELSIMPLE_ROUTE_PATH);
		List<File> files = getFiles(resourcePath);
		LOG.info("loading routes files {}", files);
		LOG.info("Loading route files.size {}", files.size());
		for (File file : files) {
			LOG.info("Loading route {}", file.getAbsolutePath());
			 String content = new String(Files.readAllBytes(file.toPath()));
			RouteDef routeDef = mapper.readValue(content, RouteDef.class);
			camelContext.addRoutes(getRouteBuilder(routeDef));
			routes.add(routeDef);
		}

		return routes;
	}

}