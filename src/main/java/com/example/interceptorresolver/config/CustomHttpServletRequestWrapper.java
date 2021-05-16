package com.example.interceptorresolver.config;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

  private final String body;

  public CustomHttpServletRequestWrapper(HttpServletRequest request) {
    super(request);

    StringBuilder stringBuilder = new StringBuilder();
    BufferedReader bufferedReader = null;

    try {
      InputStream inputStream = request.getInputStream();

      if (inputStream != null) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        char[] charBuffer = new char[128];

        int bytesRead;
        while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
          stringBuilder.append(charBuffer, 0, bytesRead);
        }
      }
    } catch (IOException ex) {
      log.error("Error reading the request body...");
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException ex) {
          log.error("Error closing bufferedReader...");
        }
      }
    }

    body = stringBuilder.toString();
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    var byteArrayInputStream = new ByteArrayInputStream(body.getBytes());

    return new ServletInputStream() {
      @Override
      public boolean isFinished() {
        return false;
      }

      @Override
      public boolean isReady() {
        return false;
      }

      @Override
      public void setReadListener(ReadListener listener) {

      }

      public int read() throws IOException {
        return byteArrayInputStream.read();
      }
    };
  }
}
