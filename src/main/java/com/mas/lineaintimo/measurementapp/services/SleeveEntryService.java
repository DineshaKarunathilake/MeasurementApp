package com.mas.lineaintimo.measurementapp.services;

import com.mas.lineaintimo.measurementapp.daoext.BatchJpaControllerExt;
import com.mas.lineaintimo.measurementapp.daoext.GarmentEntryJpaControllerExt;
import com.mas.lineaintimo.measurementapp.daoext.SleeveEntryJpaControllerExt;
import com.mas.lineaintimo.measurementapp.util.DaoFactory;

import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by DineshaK on 22/10/2016.
 */
@Path("/SleeveEntryService")
public class SleeveEntryService {

    SleeveEntryJpaControllerExt sleeveEntrydao= DaoFactory.getInstance().getSleeveEntryDao();


    @POST
    @Path("/createSleeveEntry")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSleeveEntry(@QueryParam("bid") final Double bid,@QueryParam("sid") final Double sid,@QueryParam("stageid") final Double stageid,@QueryParam("sleeveopening") final Double sleeveopening,@QueryParam("sleevewidth") final Double sleevewidth,@QueryParam("sleevelength") final Double sleevelength){
        sleeveEntrydao.createSleeveEntry(bid,sid,stageid,sleeveopening,sleevewidth,sleevelength);
        
        return Response.ok().build();
        

    }
    
    
    @GET
    @Path("/getSleeveEntry")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getSleeveEntry(@QueryParam("bid") final Integer bid,@QueryParam("sizeid") final Integer sizeid,@QueryParam("stageid") final Integer stageid){
        return sleeveEntrydao.getSleeveEntry(bid,sizeid,stageid);

    }
    
    @GET
    @Path("/getSleeves")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getSleeves(@QueryParam("batchId") final Integer batchId,@QueryParam("sizeid") final Integer sizeid){
        return sleeveEntrydao.getSleeves(batchId,sizeid);

    }

  @POST
    @Path("/updateSleeveEntry")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSleeveEntry(@QueryParam("id") final Double id,@QueryParam("bid") final Double bid,@QueryParam("sid") final Double sid,@QueryParam("stageid") final Double stageid,@QueryParam("sleeveopening") final Double sleeveopening,@QueryParam("sleevewidth") final Double sleevewidth,@QueryParam("chestwidth") final Double chestwidth,@QueryParam("sleevelength") final Double sleevelength){
        sleeveEntrydao.updateSleeveEntry(id,bid,sid,stageid,sleeveopening,sleevewidth,sleevelength);
        return Response.ok().build();

    }

}
