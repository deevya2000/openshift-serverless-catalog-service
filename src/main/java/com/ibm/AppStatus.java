package com.ibm;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/status")
@ApplicationScoped
@Produces("application/json")
public class AppStatus{    
    @GET
    public String up() {
    	String response="Status: Catalog service is Up and Running Ver 1.0";
        System.out.println(response);
        return response;
    }
 
}
