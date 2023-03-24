package br.gov.caixa.util;

import java.util.logging.Logger;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Misc {

    private static final Logger LOGGER = Logger.getLogger(Misc.class.getName());

    public static JsonObject getOneArrayInToJson(String  response, String name){
        JsonObject jsonObj = new JsonObject(response);
        JsonArray ja_data = jsonObj.getJsonArray(name);
        int length = ja_data.getList().size();
        JsonObject jObj = null;
        if(length==1){
            jObj = ja_data.getJsonObject(0);
        }else if(length==0){
            LOGGER.info("Não foi possivel encontrar");
        }else{
            LOGGER.info("Multiplas ocorrências");
        }

        return jObj;
    }


    public static String textToJsonVariables(String text){
        String json ="";
        String lines[] = text.replaceAll("(?m)^[ \t]*\r?\n", "").split("\\r?\\n");
        int count=1;
        for(String line:lines){
          String key ="";
          String value="";
          String secret="false";
          String valor[];
          if((line.contains("=") || line.contains(";"))  && !line.equals("")){
              line=line.replaceFirst("=", ";");              
              valor = line.split(";(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);             
              key= valor[0];
                if(valor.length>=2){
                    value = valor[1];
                }else{
                    value = "";
                }
                if(valor.length>=3){
                    secret = valor[2].toLowerCase();
                    if(!secret.trim().equals("true")){
                        secret="";
                    }else{
                        secret= ",\"isSecret\": true";
                    }
                }else{
                    secret = "";
                }
          }else{
              key= line;
              value = "";
          }
          if(!value.contains("\\\"")){
              value = value.replaceAll("\"", "");
              System.out.println(value);
          }
          key = key.trim();
          value = value.trim();
          if(count==1){
            json += "{";
          }
          if(lines.length==count){
              json += "\""+key+"\":{\"value\":\""+value+"\""+secret+"}";
          }else{
              json += "\""+key+"\":{\"value\":\""+value+"\""+secret+"},";
          }
          if(lines.length==count){
            json += "}";
          }
          count++;
        }
        return json;
    }
    
}
