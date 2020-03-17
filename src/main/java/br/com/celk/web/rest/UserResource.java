package br.com.celk.web.rest;

import java.lang.management.ManagementFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import br.com.celk.service.UserService;

@Path("/api/users")
public class UserResource {
	
	@Inject
	UserService userService;
	
    @GET
    @RolesAllowed("ROLE_USER")
    @Path("/me")
    @Produces(MediaType.TEXT_PLAIN)
    public Response me(@Context SecurityContext securityContext) {
    	
        return Response.ok("Usuario: " + securityContext.getUserPrincipal().getName() + "\n" +
        		"Total live threads: " +  ManagementFactory.getThreadMXBean().getThreadCount() + "\n\n").status(200).build();
        
    }
    
	@POST
	@PermitAll
	@Path("/{qtd}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response creteUsers(@PathParam(value = "qtd") Integer qtd) {

		this.userService.createUsers(qtd);
		return Response.ok().status(201).build();
	}
    
}
