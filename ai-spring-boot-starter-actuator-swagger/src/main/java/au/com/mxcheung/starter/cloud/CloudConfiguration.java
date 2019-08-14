package au.com.mxcheung.starter.cloud;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Configuration;

import au.com.mxcheung.starter.actuator.ActuatorConfiguration;

/**
 * <p>
 * Configuration class for enabling Eureka and Hystrix dashboard on profile that is active. Note that dev,
 * uat, and prod environments will automatically have cloud enablement (service discovery and hystrix), for
 * other environments by including the 'cloud' profile then cloud enablement will activate.
 * </p>
 * <p>
 * For example by setting: <i>spring.profiles.active=local,cloud</i> you'll enable the local environment to
 * be cloud enabled.
 * </p>
 * 
 */

@Configuration
@EnableHystrixDashboard
@AutoConfigureBefore(ActuatorConfiguration.class)
class CloudConfiguration {
    
}
