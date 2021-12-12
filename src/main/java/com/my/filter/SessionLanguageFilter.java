package com.my.filter;

import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebFilter
public class SessionLanguageFilter implements Filter {

  private ServletContext ctx;
  private static final Logger LOG = Logger.getLogger(SessionLanguageFilter.class);

  @Override
  public void init(FilterConfig config) {
    ctx = config.getServletContext();
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    if (req.getParameter(Params.LANGUAGE) != null) {
      req.getSession().setAttribute(Params.LANGUAGE, req.getParameter(Params.LANGUAGE));
      String pathTrace = req.getHeader("referer");
      String requestUri = req.getRequestURI();
      String redirectAddress = pathTrace.substring(pathTrace.indexOf(requestUri));

      if (redirectAddress.equals("/")) {
        resp.sendRedirect(Jsp.START_PAGE);
      } else {
        resp.sendRedirect(redirectAddress);
      }
    } else {
      chain.doFilter(request, response);
    }
  }

  public void destroy() {

  }

}

