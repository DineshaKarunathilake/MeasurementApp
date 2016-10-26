package com.mas.lineaintimo.measurementapp.services;

import com.mas.lineaintimo.measurementapp.daoext.BatchJpaControllerExt;
import com.mas.lineaintimo.measurementapp.daoext.GarmentEntryJpaControllerExt;
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
@Path("/GarmentEntryService")
public class GarmentEntryService {

    GarmentEntryJpaControllerExt garmentEntrydao= DaoFactory.getInstance().getGarmentEntryDao();


    @POST
    @Path("/createGarmentEntry")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGarmentEntry(@QueryParam("bid") final Double bid,@QueryParam("sid") final Double sid,@QueryParam("stageid") final Double stageid,@QueryParam("cblength") final Double cblength,@QueryParam("cflength") final Double cflength,@QueryParam("chestwidth") final Double chestwidth,@QueryParam("hemwidth") final Double hemwidth){
        garmentEntrydao.createGarmentEntry(bid,sid,stageid,cblength,cflength,chestwidth,hemwidth);
        
        return Response.ok().build();
        

    }
    
    
    @GET
    @Path("/getGarmentEntry")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getGarmentEntry(@QueryParam("bid") final Integer bid,@QueryParam("sizeid") final Integer sizeid,@QueryParam("stageid") final Integer stageid){
        return garmentEntrydao.getGarmentEntry(bid,sizeid,stageid);

    }
    
    @GET
    @Path("/getGarments")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getGarments(@QueryParam("batchId") final Integer batchId,@QueryParam("sizeid") final Integer sizeid){
        return garmentEntrydao.getGarments(batchId,sizeid);

    }


  @POST
    @Path("/updateGarmentEntry")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGarmentEntry(@QueryParam("id") final Double id,@QueryParam("bid") final Double bid,@QueryParam("sid") final Double sid,@QueryParam("stageid") final Double stageid,@QueryParam("cblength") final Double cblength,@QueryParam("cflength") final Double cflength,@QueryParam("chestwidth") final Double chestwidth,@QueryParam("hemwidth") final Double hemwidth){
        garmentEntrydao.updateGarmentEntry(id,bid,sid,stageid,cblength,cflength,chestwidth,hemwidth);
        return Response.ok().build();

    }

}
