package com.iambabul.blog.config;

import com.iambabul.blog.util.UtilBase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class AppSwaggerConfig extends UtilBase {

    @Bean
    public Docket blogsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(getText("blog.api"))
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(getText("my.word.blog"))
                .description(getText("this.is.a.blog.site.for.learning"))
                .termsOfServiceUrl("https://iambabul.blogspot.com/")
                .license(getText("my.word.blog"))
                .licenseUrl("https://iambabul.blogspot.com/")
                .version(getText("1(dot).0"))
                .build();
    }
}
