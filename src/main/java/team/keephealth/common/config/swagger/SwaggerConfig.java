package team.keephealth.common.config.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableKnife4j
@EnableSwagger2WebMvc
public class SwaggerConfig {
    // 定义分隔符
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("许影杰的接口")
                .apiInfo(apiInfo1())
                .select()
                .apis(RequestHandlerSelectors.basePackage("team.keephealth.xyj.modules"))
                .paths(PathSelectors.any())
                .build();

    }

    @Bean
    public Docket createRestApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("余婧洁的接口")
                .apiInfo(apiInfo2())
                .select()
                .apis(RequestHandlerSelectors.basePackage("team.keephealth.yjj.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo1() {
        return new ApiInfoBuilder()
                .title("许影杰的接口说明")
                .description("用户接口")
                .version("1.0")
                .build();
    }

    private ApiInfo apiInfo2() {
        return new ApiInfoBuilder()
                .title("余婧洁的接口说明")
                .description("写有哪些模块接口，方便前端找")
                .version("1.0")
                .build();
    }
}
