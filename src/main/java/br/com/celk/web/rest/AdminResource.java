package br.com.celk.web.rest;

import java.lang.management.ManagementFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.celk.service.UserService;

@Path("/api/admin")
public class AdminResource {
	
	@Inject
	UserService userService;

    @GET
    @RolesAllowed("ROLE_ADMIN")
    @Produces(MediaType.TEXT_PLAIN)
    public Response adminResource() {
    	
    	 return Response.ok("Total live threads: " +  ManagementFactory.getThreadMXBean().getThreadCount() + "\n\n").status(200).build();
    }
    
	@POST
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response creteUsers() {

		this.userService.createUserAdmin();
		return Response.ok().status(201).build();
	}
}
