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
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

@WebFilter
public class UserAuthenticationFilter implements Filter {

  private ServletContext ctx;
  private static final Logger LOG = Logger.getLogger(UserAuthenticationFilter.class);

  @Override
  public void init(FilterConfig config) throws ServletException {
    ctx = config.getServletContext();
  }

  private static final String loginRequiredURL = "/auth";
  private HttpServletRequest req;
  private HttpServletResponse resp;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    req = (HttpServletRequest) request;
    resp = (HttpServletResponse) response;
    String path = req.getRequestURI();

    if (path != null && path.contains("/admin")) {
      chain.doFilter(request, response);
      return;
    }

    HttpSession session = req.getSession(false);
    boolean isLoggedIn = (session != null && session.getAttribute(Params.CURRENT_USER) != null);

    if (!isLoggedIn && isLoginRequired()) {
      resp.sendRedirect(Jsp.LOGIN_PAGE);
    } else {
      chain.doFilter(request, response);
    }
  }

  private boolean isLoginRequired() {
    String requestURL = req.getRequestURL().toString();
    return requestURL.contains(loginRequiredURL);
  }

}
