package br.gov.caixa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



import br.gov.caixa.config.ADSConfig;
import br.gov.caixa.model.VariablesGroup;
import br.gov.caixa.util.Misc;
import io.quarkus.qson.generator.QsonMapper;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;


@RequestScoped
public class ADSVariavelService {

    private static final Logger LOGGER = Logger.getLogger(ADSVariavelService.class.getName());

    @Inject
    ADSConfig adsConfig;


    public Response  cadastrar(final String variaveis) {
        Client client = ClientBuilder.newClient();
        final String url = adsConfig.url() + adsConfig.project().variable().groups() +"?"+adsConfig.version();
        Response  response = client.target(url)
        .request()
        .header(HttpHeaders.AUTHORIZATION, "Basic "+adsConfig.token())
        .post(Entity.json(variaveis));
        
        return response;
    }

    public Response  atualizar(final String id,final String variaveis) {
 
        Client client = ClientBuilder.newClient();
        final String url = adsConfig.url() + adsConfig.project().variable().groups()+"/"+id +"?"+adsConfig.version();
        Response  response = client.target(url)
        .request()
        .header(HttpHeaders.AUTHORIZATION, "Basic "+adsConfig.token())
        .put(Entity.json(variaveis));
        
        return response;
    }


    public JsonObject  pesquisarByName(final String name) throws Exception {
 
        Client client = ClientBuilder.newClient();
        final String url = adsConfig.url() + adsConfig.project().variable().groups() +"?groupName="+name;
        String  response = client.target(url)
        .request()
        .header(HttpHeaders.AUTHORIZATION, "Basic "+adsConfig.token())
        .get(String.class);
        JsonObject jsonObj = new JsonObject(response);
        return jsonObj;
    }

    public JsonObject  pesquisarFakeByName(final String name) throws Exception {
        String response = "{\"count\":1,\"value\":[{\"variables\":{\"key1\":{\"value\":\"value\"},\"key2\":{\"value\":\"value1\",\"isSecret\":true}},\"id\":2,\"type\":\"Vsts\",\"name\":\"TestVariableGroup2\",\"description\":\"A test variable group\",\"createdBy\":{\"id\":\"4adb1680-0eac-6149-b5ee-fc8b4f6ca227\",\"displayName\":\"Chuck Reinhart\",\"uniqueName\":\"fabfiber@outlook.com\"},\"createdOn\":\"2017-12-12T06:35:52.08Z\",\"modifiedBy\":{\"id\":\"4adb1680-0eac-6149-b5ee-fc8b4f6ca227\",\"displayName\":\"Chuck Reinhart\",\"uniqueName\":\"fabfiber@outlook.com\"},\"modifiedOn\":\"2017-12-12T06:35:52.08Z\"}]}";
        JsonObject jsonObj = new JsonObject(response);
        return jsonObj;
    }
    
    
    public JsonObject  pesquisarById(final int id) throws Exception {
 
        Client client = ClientBuilder.newClient();
        final String url = adsConfig.url() + adsConfig.project().variable().groups()  +"/"+id;
        String  response = client.target(url)
        .request()
        .header(HttpHeaders.AUTHORIZATION, "Basic "+adsConfig.token())
        .get(String.class);
        JsonObject jsonObj = new JsonObject(response);
        return jsonObj;
    }

    public JsonObject setVariaveis(String response) throws Exception{
        JsonObject jObj = Misc.getOneArrayInToJson(response, "value");
        if(jObj==null){
            throw new Exception("Erro ao pesquisar");
        }
        return jObj;
    }

}