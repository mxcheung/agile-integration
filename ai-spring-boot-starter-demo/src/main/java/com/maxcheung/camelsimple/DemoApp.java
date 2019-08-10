package com.maxcheung.camelsimple;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.MoreObjects.firstNonNull;
import static java.lang.String.format;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.google.common.collect.ImmutableMap;

@SpringBootApplication
@EnableJpaRepositories("com.maxcheung.camelsimple.repo")
public class DemoApp extends SpringBootServletInitializer {
    
    /**
     * Tool used: http://patorjk.com/software/taag/
     */
    private static final String[] BANNER = { 
        "    ____  ________  _______", 
        "   / __ \\/ ____/  |/  / __ \\",
        "  / / / / __/ / /|_/ / / / /",
        " / /_/ / /___/ /  / / /_/ /", 
        "/_____/_____/_/  /_/\\____/"  
         };

     private static final String INDENTATION = "    ";

     private static final String DEFAULT_TITLE = "Agile Integration Starter Demo";
     private static final String DEFAULT_VERSION = "1.0.0";
     private static final String DEFAULT_VENDOR = "Agile Integration";
     private static final String DEFAULT_DESCRIPTION = "Agile Integration Starter Demo App.";

     /**
      * Start entry point for the application service.
      * @param args command line args.
      */
     public static void main(String[] args) {
         final DemoApp app = new DemoApp();
         app.run(app.configure(new SpringApplicationBuilder(DemoApp.class)).build());
     }
}
