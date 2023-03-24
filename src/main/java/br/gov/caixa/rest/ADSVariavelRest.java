package br.gov.caixa.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.List;

import br.gov.caixa.controller.ADSVariavelController;
import br.gov.caixa.model.VariablesGroup;
import br.gov.caixa.service.*;
import br.gov.caixa.util.Misc;
import br.gov.caixa.util.Roles;



//@RolesAllowed({ Roles.ADMIN })
@Path("/ads/variavel")
public class ADSVariavelRest {

    @Inject
    ADSVariavelService service;
    @Inject
    ADSVariavelController serviceCtrl;

    
    @POST
    @Path("/cadastrar")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.TEXT_PLAIN)
    public Response cadastrar(String json)
            throws Exception {
        Response variaveis =  service.cadastrar(json);
        ResponseBuilder response = Response.ok((Object) variaveis);
        return response.build();
    }

    @GET
    @Path("/pesquisarByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pesquisarByName(@PathParam("name") String name)
            throws Exception {
                JsonObject variaveis =  service.pesquisarByName(name);
        ResponseBuilder response = Response.ok((Object) variaveis);
        return response.build();
     }

     @GET
    @Path("/pesquisarFakeByName/{name}")
    public Response pesquisarFakeByName(@PathParam("name") String name)
            throws Exception {
            JsonObject variaveis =  service.pesquisarFakeByName(name);
        ResponseBuilder response = Response.ok((Object) variaveis);
        return response.build();
     }

     @GET
    @Path("/pesquisarById/{id}")
    public Response pesquisarByName(@PathParam("id") Integer id)
            throws Exception {
                JsonObject variaveis =  service.pesquisarById(id);
        ResponseBuilder response = Response.ok((Object) variaveis);
        return response.build();
     }

    @GET
    @Path("/teste")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }

    @POST
    @Path("/criarVariaveis/{name}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.TEXT_PLAIN)
    public Response criarVariaveis(@PathParam ("name") String name, String json)
            throws Exception {
        String variaveis =  serviceCtrl.criarVariaveis(name,json);
        ResponseBuilder response = Response.ok((Object) variaveis);
        return response.build();
    }
    
    @POST
    @Path("/criarVariaveisByText/{name}")
    @Consumes({ MediaType.TEXT_PLAIN })
    @Produces(MediaType.TEXT_PLAIN)
    public Response criarVariaveisByText(@PathParam ("name") String name, String text)
            throws Exception {
        String json = Misc.textToJsonVariables(text);
        String variaveis =  serviceCtrl.criarVariaveis(name,json);
        ResponseBuilder response = Response.ok((Object) variaveis);
        return response.build();
    }

    @GET
    @Path("/recuperarVariaveisByText/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response recuperarVariaveisByText(@PathParam ("name") String name)
            throws Exception {
        String variaveis =  serviceCtrl.recuperarVariaveisByText(name);
        ResponseBuilder response = Response.ok((Object) variaveis);
        return response.build();
    }
}