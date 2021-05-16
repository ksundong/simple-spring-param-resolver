package com.example.interceptorresolver;

import com.example.interceptorresolver.config.FilterBody;
import com.example.interceptorresolver.config.FilterHeader;
import com.example.interceptorresolver.config.InterceptorBody;
import com.example.interceptorresolver.config.InterceptorHeader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class InterceptorResolverApplication {

  public static void main(String[] args) {
    SpringApplication.run(InterceptorResolverApplication.class, args);
  }

  @PostMapping("/login")
  public String login(@InterceptorHeader String interceptorHeader, @FilterHeader String filterHeader,
      @InterceptorBody String interceptorBody, @FilterBody String filterBody) {
    return "Filter Header: " + filterHeader + "\nFilter Body: " + filterBody
        + "\nInterceptor Header: " + interceptorHeader + "\nInterceptor Body: " + interceptorBody;
  }
}
