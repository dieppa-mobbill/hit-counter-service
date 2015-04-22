package com.mobbill.hitcounter.restful;

import com.mobbill.hitcounter.dao.MobbillHitDao;
import com.mobbill.hitcounter.domain.MobbillHitCounter;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by antoniop on 25/02/15.
 */

@Path("/mobbill/hit")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class MobbillHitRestFul extends BaseRestFul{



    private final static Logger LOG = Logger.getLogger(MobbillHitRestFul.class.getName());

    private MobbillHitDao cassandraDAO = new MobbillHitDao();




    @GET
    @Path("/counter/{miliseconds}")
    public List<MobbillHitCounter> getData(@PathParam("miliseconds") long miliseconds){

        return  cassandraDAO.getData(miliseconds);

    }

}
