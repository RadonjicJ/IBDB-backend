package com.ibdbcompany.ibdb.config;

import com.fasterxml.classmate.TypeResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StopWatch;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRuleConvention;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static springfox.documentation.schema.AlternateTypeRules.newRule;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    /**
     * Swagger Springfox configuration.
     *
     * @return the Swagger Springfox configuration
     */
    private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);
    @Bean
    public Docket swaggerSpringfoxApiDocket()
    {
        log.debug("Starting Swagger...");
        StopWatch watch = new StopWatch();
        watch.start();

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .securitySchemes(securitySchemes())
            .directModelSubstitute(LocalTime.class, String.class)
            .securityContexts(securityContexts())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.ibdbcompany.ibdb"))
            .paths(PathSelectors.any())
            .build();
        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("IBDB")
            .description("IBDB project")
            .version("0.0.1")
            .build();
    }
    private ApiKey apiKeyScheme()
    {
        return new ApiKey("token", "Authorization", "header");
    }

    private SecurityScheme basicAuthScheme() {
        return new BasicAuth("basic");
    }
    private List<SecurityScheme> securitySchemes()
    {
        return Arrays.asList(apiKeyScheme(), basicAuthScheme());
    }

    private List<SecurityContext> securityContexts()
    {
        return Collections.singletonList(SecurityContext.builder().securityReferences(securityReferences()).forPaths(PathSelectors.any()).build());
    }
    private List<SecurityReference> securityReferences()
    {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("token", authorizationScopes), new SecurityReference("basic", authorizationScopes));
    }

    @Bean
    public AlternateTypeRuleConvention pageableConvention(final TypeResolver resolver)
    {
        return new AlternateTypeRuleConvention()
        {
            @Override
            public int getOrder()
            {
                return Ordered.HIGHEST_PRECEDENCE;
            }

            @Override
            public List<AlternateTypeRule> rules()
            {
                return Collections.singletonList(
                    newRule(resolver.resolve(Pageable.class), resolver.resolve(pageableMixin()))
                );
            }
        };
    }

    private Type pageableMixin()
    {
        return new AlternateTypeBuilder()
            .fullyQualifiedClassName(
                String.format("%s.generated.%s",
                    Pageable.class.getPackage().getName(),
                    Pageable.class.getSimpleName()))
            .withProperties(Arrays.asList(
                property(Integer.class, "page"),
                property(Integer.class, "size"),
                property(String.class, "sort")
            ))
            .build();
    }

    private AlternateTypePropertyBuilder property(Class<?> type, String name)
    {
        return new AlternateTypePropertyBuilder()
            .withName(name)
            .withType(type)
            .withCanRead(true)
            .withCanWrite(true);
    }
}
