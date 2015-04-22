package com.mobbill.hitcounter.restful;

import javax.ws.rs.core.Response;import java.lang.Exception;import java.lang.Object;

/**
 * Created by antoniop on 22/04/15.
 */
public class BaseRestFul {

    protected Response okResponse(Object entity){
        return Response.status(Response.Status.OK).entity(entity).build();
    }

    protected Response internalServerError(Exception ex){
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error\n"+ex.getMessage()).build();
    }

    protected Response notFound(){
        return Response.status(Response.Status.NOT_FOUND).entity("PAGE NOT FOUND").build();
    }
}
