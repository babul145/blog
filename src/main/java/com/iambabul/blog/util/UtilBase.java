package com.iambabul.blog.util;

import com.iambabul.blog.entity.BlogResponse;
import com.iambabul.blog.exception.BlogException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;


@Slf4j
@PropertySource("classpath:message.properties")
public class UtilBase {
    @Autowired
    private Environment env;

    public String getText(String propertyKey, String... param) {
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

    public BlogResponse getBlogResponse(String responseType, String responseTitle, String failedReason) {
        if (responseType.equals(Constants.RESPONSE_TYPE_SUCCESS)) {
            return new BlogResponse(getText(responseType), getText("x0.has.been.saved.successfully", responseTitle));
        }
        else if (responseType.equals(Constants.RESPONSE_TYPE_FAILED)) {
            return new BlogResponse(getText(responseType), getText("failed.to.save.x0-x1", responseTitle, failedReason));
        }
        else {
            log.error("response type not found");
            return null;
        }
    }
}
