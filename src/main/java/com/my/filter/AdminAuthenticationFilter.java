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

@WebFilter
public class AdminAuthenticationFilter implements Filter {

  private ServletContext ctx;

  @Override
  public void init(FilterConfig config){
    ctx = config.getServletContext();
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain chain) throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) servletRequest;
    HttpServletResponse resp = (HttpServletResponse) servletResponse;

    HttpSession session = req.getSession(false);

    if (session == null) {
      chain.doFilter(req, resp);
      return;
    }

    String role = (String) session.getAttribute(Params.SESSION_ROLE);

    boolean isLoggedIn = false;
    if (role != null) {
      isLoggedIn = (session != null && role.equals(Params.ADMIN));
    }
    boolean adminUri = req.getRequestURI().contains(Params.ADMIN);
    boolean adminUriStarts = req.getRequestURI().startsWith(Params.ADMIN);
    if (!isLoggedIn && (adminUri || adminUriStarts)) {
      resp.sendRedirect(Jsp.LOGIN_PAGE);
    } else {
      chain.doFilter(req, resp);
    }
  }
}
