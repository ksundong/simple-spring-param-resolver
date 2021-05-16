package com.example.interceptorresolver.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void destroy() {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    var httpServletRequest = new CustomHttpServletRequestWrapper((HttpServletRequest) request);
    String filterHeader = httpServletRequest.getHeader("Filter");

    JsonNode jsonNode = new ObjectMapper().readTree(httpServletRequest.getInputStream());
    String filterBody = jsonNode.get("filter").asText();

    log.info("header: {}, body: {}", filterHeader, filterBody);
    request.setAttribute("convertedFilterHeader", "hello " + filterHeader);
    request.setAttribute("convertedFilterBody", "yohoho " + filterBody);
    chain.doFilter(httpServletRequest, response);
  }
}
