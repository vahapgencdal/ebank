package com.ebank.api;

import com.ebank.executer.TransactionThreadPoolExecuter;
import com.google.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 19.09.2018
 * @description TODO: Class Description
 */
@Path("/jobs")
public class JobApi {

    private TransactionThreadPoolExecuter transactionThreadPoolExecuter;

    @Inject
    public JobApi(TransactionThreadPoolExecuter transactionThreadPoolExecuter) {
        this.transactionThreadPoolExecuter = transactionThreadPoolExecuter;
    }

    @GET
    @Path("start")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Boolean> start() {
        try {
            transactionThreadPoolExecuter.start();
            return Response.of(true, "", "OK");
        } catch (Exception e) {
            return Response.of(false, e.getMessage(), "NOK");
        }
    }

    @GET
    @Path("stop")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Boolean> stop() {
        try {
            transactionThreadPoolExecuter.stop();
            return Response.of(true, "", "OK");
        } catch (Exception e) {
            return Response.of(false, e.getMessage(), "NOK");
        }
    }

}
