package com.mas.lineaintimo.measurementapp.services;

import com.mas.lineaintimo.measurementapp.daoext.BatchJpaControllerExt;
import com.mas.lineaintimo.measurementapp.util.DaoFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by DineshaK on 22/10/2016.
 */
@Path("/BatchService")
public class BatchService {

    BatchJpaControllerExt batchDao= DaoFactory.getInstance().getBatchDao();


    @GET
    @Path("/getBatchList")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getBatchList(){
        return batchDao.getBatchList();
        //return "{status:'UP'}";

    }

    @GET
    @Path("/getBatch")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getBatch(@QueryParam("id") final Integer id){
        return batchDao.getBatch(id);

    }
    
    
     @GET
    @Path("/checkBatchNo")
    @Produces(MediaType.APPLICATION_JSON)
    public Object checkBatchNo(@QueryParam("id") final Integer id){
        return batchDao.checkBatchNo(id);

    }

}
