package br.com.celk.web.rest;

import java.lang.management.ManagementFactory;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.celk.service.UserService;

@Path("/api/auth")
public class AuthResource {
	
	@Inject
	UserService userService;
	
    @GET
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @Produces(MediaType.TEXT_PLAIN)
    public Response auth() {
    	
        return Response.ok("Total live threads: " +  ManagementFactory.getThreadMXBean().getThreadCount() + "\n\n").status(200).build();
        
    }
    
}
