package com.theam.crmrestapi;


import com.theam.crmrestapi.resources.CustomerResource;
import com.theam.crmrestapi.resources.UserResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(UserResource.class);
        register(CustomerResource.class);

    }
}