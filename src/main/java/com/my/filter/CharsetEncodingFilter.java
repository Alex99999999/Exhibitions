package com.my.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter
public class CharsetEncodingFilter implements Filter {

  private String encoding;

  @Override
  public void init(FilterConfig filterConfig) {
    encoding = filterConfig.getInitParameter("charset-encoding");
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp,
      FilterChain filterChain) throws IOException, ServletException {

    req.setCharacterEncoding(encoding);
    filterChain.doFilter(req, resp);
  }
}
