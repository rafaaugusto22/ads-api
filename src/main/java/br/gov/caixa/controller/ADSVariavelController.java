package br.gov.caixa.controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import br.gov.caixa.service.ADSVariavelService;
import br.gov.caixa.util.Misc;
import io.vertx.core.json.JsonObject;

@RequestScoped
public class ADSVariavelController {

    @Inject
    ADSVariavelService vgService;

    private static final Logger LOGGER = Logger.getLogger(ADSVariavelController.class.getName());

    public String criarVariaveis(String name,String json) throws Exception{

        
        name = name.toUpperCase();
        JsonObject addJson = new JsonObject(json);        
        JsonObject vgObj=  vgService.pesquisarByName(name);

        int count = Integer.parseInt(vgObj.getString("count"));

        if(count==0){
            //Cadastra variáveis
            
            String data="{\"name\": \""+name+"\",\"description\": \"Grupo de variáveis de "+name+"\",\"variables\": "+json+"}";     
            
            Response response = vgService.cadastrar(data);

            

            if(response.getStatus()==200){
                LOGGER.info("Variáveis incluidas com sucesso");
            }else{
                throw new Exception("Erro na criação da variável");
            }

        }else{
            //Atualiza variáveis
            JsonObject vgUpObj= Misc.getOneArrayInToJson(vgObj.toString(), "value");
            JsonObject jsonVariaveisObj = vgUpObj.getJsonObject("variables");
            
            Set<String> keys = jsonVariaveisObj.fieldNames();
            Set<String> keysAdd = addJson.fieldNames();
            Set<String> keysRemove = new HashSet<>();

            for(String keyadd:keysAdd){
                for(String key:keys){
                    if (key.toString().toUpperCase().equals(keyadd.toString().toUpperCase())) {
                        keysRemove.add(key);
                    }
                }
            }
            for(String key:keysRemove){
                    keys.remove(key);
            }
            for(String keyadd:keysAdd){
                jsonVariaveisObj.put(keyadd.toUpperCase(), addJson.getValue(keyadd));
            }
            String data="{\"name\": \""+name+"\",\"description\": \"Grupo de variáveis de "+name+"\",\"variables\": "+jsonVariaveisObj.toString()+"}";     
            Response response = vgService.atualizar(vgUpObj.getString("id"),data);

            if(response.getStatus()==200){
                LOGGER.info("Variáveis incluídas com sucesso");
            }else{
                throw new Exception("Erro na atualização do grupo de variáveis");
            }


        }
       
        return json;

    }

    public String recuperarVariaveisByText(String name) throws Exception{

        String data="";
        JsonObject vgObj=  vgService.pesquisarByName(name);
        System.out.println(vgObj.toString());
        int count = Integer.parseInt(vgObj.getString("count"));

        if(count!=0){
            
            JsonObject vgUpObj= Misc.getOneArrayInToJson(vgObj.toString(), "value");
            JsonObject jsonVariaveisObj = vgUpObj.getJsonObject("variables");

            Set<String> keys = jsonVariaveisObj.fieldNames();

            int contador= 1;
            for(String key:keys){
                

                String valor = jsonVariaveisObj.getValue(key).toString();
                JsonObject jsonValor = new JsonObject(valor); 
                String value = jsonValor.getString("value");
                
                
                value = (value!=null && value.contains("\""))?value.replaceAll("\"", "\\\\\""):value;
                if(!jsonValor.fieldNames().contains("isSecret")) {
                    data += key+";" +(value!=null && value.contains(";")?"\""+value+"\"":value)+ (contador==keys.size()?"":System.lineSeparator());
                }else{
                    data += key+";" +jsonValor.getString("value")+ ";"+jsonValor.getString("isSecret")+ (contador==keys.size()?"":System.lineSeparator());
                }
                contador++;
            }
          
        }else{
                throw new Exception("Variáveis não encontradas");
            
        }
       
        return data;

    }
    
}
