package br.gov.caixa.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "ads")
public interface ADSConfig {

    String url();
    String token();
    String version();
    Project project();

    interface Project {
        Variable variable();

        interface Variable{
            String groups();  
        }
    }
     
}