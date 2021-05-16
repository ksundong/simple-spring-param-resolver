package com.example.interceptorresolver.config;

import java.util.List;
import javax.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final ValueAdder valueAdder;

  public WebConfig(ValueAdder valueAdder) {
    this.valueAdder = valueAdder;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(valueAdder)
        .addPathPatterns("/**");
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(interceptorHeaderResolver());
    resolvers.add(interceptorBodyResolver());
    resolvers.add(filterHeaderResolver());
    resolvers.add(filterBodyResolver());
  }

  @Bean
  public InterceptorHeaderResolver interceptorHeaderResolver() {
    return new InterceptorHeaderResolver();
  }

  @Bean
  public InterceptorBodyResolver interceptorBodyResolver() {
    return new InterceptorBodyResolver();
  }

  @Bean
  public FilterHeaderResolver filterHeaderResolver() {
    return new FilterHeaderResolver();
  }

  @Bean
  public FilterBodyResolver filterBodyResolver() {
    return new FilterBodyResolver();
  }

  @Bean
  public Filter filter() {
    return new MyFilter();
  }
}
