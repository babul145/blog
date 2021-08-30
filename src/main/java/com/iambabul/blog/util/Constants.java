package com.iambabul.blog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

@Configuration
@PropertySource("classpath:message.properties")
public class Constants {
    private final Environment env;

    @Autowired
    public Constants(Environment env) {
        this.env = env;
    }

    public String getMessage(String propertyKey, String... param) {
        String propertyValue = env.getProperty(propertyKey);
        List<String> params = Arrays.asList(param);
        if (propertyValue != null && params.size() > 0) {
            for (int i = 0; i < params.size(); i++) {
                int paramIndex = propertyValue.indexOf("{" + i + "}");
                if (paramIndex != -1) {
                    propertyValue = propertyValue.replace("{" + i + "}", params.get(i));
                }
            }
        }
        return propertyValue;
    }

    public static final Integer FIRST_INDEX = 0;
}
