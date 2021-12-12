package com.my.controller;

import com.my.command.Command;
import com.my.command.CommandContainer;
import com.my.exception.CommandException;
import com.my.utils.constants.Jsp;
import com.my.utils.constants.Params;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet("/auth")
public class AuthController extends HttpServlet {

  private static final Logger LOG = Logger.getLogger(AuthController.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Command command;
    String address = Jsp.ERROR_PAGE;
    try {
      command = CommandContainer.getCommand(req);
      address = command.execute(req, resp);
    } catch (Exception | CommandException ex) {
      req.setAttribute(Params.ERROR_MESSAGE, ex.getMessage());
      req.getRequestDispatcher(address).forward(req, resp);
    }
    req.getRequestDispatcher(address).forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Command command;
    String address = Jsp.ERROR_PAGE;
    try {
      command = CommandContainer.getCommand(req);
      address = command.execute(req, resp);
    } catch (Exception | CommandException ex) {
      LOG.error("Redirect address : " + address + "\nError message : " + ex.getMessage(), ex.getCause());
      req.getSession().setAttribute(Params.ERROR_MESSAGE, ex.getMessage());
      resp.sendRedirect(address);
    }
    resp.sendRedirect(address);
  }
}
