package siwei.ahon.qualitySafetyInspection.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import siwei.ahon.qualitySafetyInspection.interceptor.AuthInterceptor;
import siwei.ahon.qualitySafetyInspection.interceptor.CorsInterceptor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class authConfig extends WebMvcConfigurationSupport {
    @Resource
    AuthInterceptor authInterceptor;

    @Resource
    CorsInterceptor cors;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();

        registry.addInterceptor(cors).addPathPatterns("/**");
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
    }






}

