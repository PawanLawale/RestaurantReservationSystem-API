package egen.project.rrs.configuration;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("/api")
public class RRSApiConfiguration extends ResourceConfig{

	public RRSApiConfiguration(){
		packages("egen.project.rrs");
		
		register(io.swagger.jaxrs.listing.ApiListingResource.class);
		register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
		
		BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setBasePath("/RRSApi/api");
        beanConfig.setResourcePackage("egen.project.rrs");
        beanConfig.setTitle("Restaurant Reservation System API");
        beanConfig.setDescription("This api is build for for providing features like Creating, deleting, modifying restaurant reservations."
        		+ " The below methods provides details of each feature.");
        beanConfig.setScan(true);
	}
}
