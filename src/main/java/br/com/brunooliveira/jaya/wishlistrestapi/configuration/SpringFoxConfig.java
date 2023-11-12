package br.com.brunooliveira.jaya.wishlistrestapi.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@PropertySource("classpath:application.properties")
public class SpringFoxConfig {
    @Bean
    public WebFluxConfigurer webConfigurer() {
        return new WebFluxConfigurer() {
            /*@Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("Content-Type");
            }*/

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/3.14.2/");
            }
        };
    }

}
