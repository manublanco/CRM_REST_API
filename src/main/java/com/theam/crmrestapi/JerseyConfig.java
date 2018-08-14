package com.theam.crmrestapi;

import com.theam.crmrestapi.resources.CustomerResource;
import com.theam.crmrestapi.resources.UserResource;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(UserResource.class);
        register(CustomerResource.class);
        register(SecurityFilter.class);
        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, "true");
        register(MultiPartFeature.class);
    }
}