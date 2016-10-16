package com.mas.lineaintimo.measurementapp.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by shenal on 10/16/16.
 */

@Path("/TestService")
public class TestService {

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public String test(){
        return "{status:'UP'}";
    }
}
