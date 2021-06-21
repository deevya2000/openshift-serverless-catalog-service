package com.ibm;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/")
@RegisterRestClient
public interface NotificationService {

	@GET
	@Path("/pricechanged/{id}/{price}")
	@Produces(MediaType.TEXT_PLAIN)
	String handePriceChange(@PathParam String id, @PathParam String price);

}
