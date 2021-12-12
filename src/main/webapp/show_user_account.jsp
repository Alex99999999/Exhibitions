<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<%@ page isELIgnored="false" %>


<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'en'}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.interface"/>

<html>
<head>
    <title>My Account</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>
<body>

${currentUser.login}

  <!-- Navbar -->

        <nav class="navbar navbar-light bg-light">
          <div class="container-fluid">
              <a>
              <tf:home_button/>
              </a>

              <a>
              <tf:my_bookings/>
              </a>

              <a>
              <form action="users" method="get">
                     <input type="hidden" name="page" value="1">
                     <input type="hidden" name="pageSize" value="5">
                     <input type="hidden" name="command" value="get_current_exhibitions_list">
                     <button type="submit" class="btn btn-outline-secondary btn-md btn-block"><fmt:message key="label.exhibitions"/></button>
                   </form>
              </a>

              <a>
              <tf:logout_button/>
              <a/>

              <a>
              <tf:language_switch/>
              <a/>

          </div>
        </nav>


 <!-- Page title -->
<hr>

   <div class="row center-block text-center">
        <h1> <fmt:message key="label.my.account"/> </h1>
    </div>


${infoMessage}

<table class="table table-striped table-hover">
<tr>
    <th><fmt:message key="label.change.login"/></th>
    <td>
      <form action = "auth" method = "post">
        <p><input type="text" name = "login" placeholder="<fmt:message key="label.new.login"/>" required></p>
        <p><input type="hidden" name="command" value="change_login_command"></p>
        <p><button class="btn btn-outline-secondary btn-md btn-block" type="submit"><fmt:message key="label.change"/></button></p>
      </form>
    </td>
</tr>

<tr>
  <hr>
</tr>

<tr>
    <th><fmt:message key="label.change.password"/></th>
    <td>
      <form action = "auth" method = "post">
        <p><input type="password" placeholder="<fmt:message key="label.current.password"/>" name = "current_pass"></p>
        <p><input type="password" placeholder="<fmt:message key="label.new.password"/>" name = "new_pass"></p>
        <p><input type="password" placeholder="<fmt:message key="label.confirm.new.password"/>" name = "confirm_pass"></p>
        <p><input type="hidden" name="command" value="change_pass_command"></p>
        <p><button class="btn btn-outline-secondary btn-md btn-block" type="submit"><fmt:message key="label.change"/></button></p>
      </form>
    </td>
</tr>

</table>

















    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
</body>
</html>

<c:remove var="infoMessage"/>