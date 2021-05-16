package com.example.interceptorresolver.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class ValueAdder implements HandlerInterceptor {

  private final ObjectMapper objectMapper;

  public ValueAdder(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    var customRequest = new CustomHttpServletRequestWrapper(request);
    String interceptorHeader = customRequest.getHeader("Interceptor");
    JsonNode jsonNode = objectMapper.readTree(customRequest.getInputStream());
    String interceptorBody = jsonNode.get("interceptor").asText();

    log.info("header: {}, body: {}", interceptorHeader, interceptorBody);
    customRequest.setAttribute("convertedInterceptorHeader", "hello " + interceptorHeader);
    customRequest.setAttribute("convertedInterceptorBody", "yohoho " + interceptorBody);
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {

  }
}
