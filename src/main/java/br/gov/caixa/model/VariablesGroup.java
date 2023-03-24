package br.gov.caixa.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.quarkus.qson.QsonIgnore;

public class VariablesGroup {


public Integer id;

public String name;

@QsonIgnore
public Map<String, Object> variables;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    public Map<String,Object> getVariables() {
        return this.variables;
    }

    public void setVariables(Map<String,Object> variables) {
        this.variables = variables;
    }
    

}