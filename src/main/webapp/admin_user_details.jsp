<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<%@ page isELIgnored="false" %>


<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'en'}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.interface"/>

<html >
<head>
    <title>Admin user details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>
<body>

<tf:greeting/>

  <!-- Navbar -->

        <nav class="navbar navbar-light bg-light">
          <div class="container-fluid">
              <a>
                <form action="admin" method="get">
                   <input type="hidden" name="page" value="1">
                   <input type="hidden" name="pageSize" value="5">
                   <input type="hidden" name="command" value="show_admin_users">
                   <button class="btn btn-outline-secondary btn-md btn-block" type="submit"><fmt:message key="label.back"/></button>
                </form>
              </a>

              <a>
              <tf:home_button/>
              </a>

              <div class="col-4">
               <form action="admin" method = "get" class="d-flex">
                  <input type="hidden" name="command" value="find_user_by_login">
                    <div class="col-8">
                       <input class="form-control me-2" type="text" placeholder="<fmt:message key="label.search"/>" name="login">
                    </div>
                    <div class="col-4">
                        <button class="btn btn-outline-secondary btn-md btn-block" type="submit"><fmt:message key="label.search"/></button>
                    </form>
                    </div>
              </div>

                <a>
                   <tf:admin_exhibitions_btn/>
                </a>

                 <a>
                    <tf:admin_halls_btn/>
                 </a>

                 <a>
                   <tf:admin_bookings_btn/>
                 </a>

                 <a>
                   <tf:admin_users_btn/>
                 </a>

              <a>
                <tf:logout_button/>
              </a>

              <tf:language_switch/>

          </div>
        </nav>




 <!-- Page title -->
<hr>
   <div class="row center-block text-center">
        <h1> <fmt:message key="label.user.details"/> </h1>
    </div>


<hr>


<!-- Table -->

${infoMessage}

<table class="table table-striped table-hover">
<thead class="table-light">
  <tr>
        <th class="text-center"><fmt:message key="label.login"/></th>
        <th class="text-center"><fmt:message key="label.role"/></th>
        <th class="text-center"></th>
        <th class="text-center"></th>
   </tr>
</thead>

<tr>
    <td class="text-center">${user.login}</td>
    <td class="text-center"><fmt:message key="label.${user.role.role}"/></td>
    <td class="text-center">
     <form action="auth" method="post">
        <select name="user_role">
          <c:forEach var="user_role" items="${user_role_list}">
               <option value="${user_role.role}"
                     <c:if test="${user_role.role eq user.role.role}">selected="selected"</c:if>>
                       <fmt:message key="label.${user_role.role}"/>
               </option>
           </c:forEach>
        </select>

          <input type="hidden" name="command" value="update_user">
          <input type="hidden" name="user_id", value=${user.id}>
          <button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><fmt:message key="label.set.role"/></button>
      </form>
     </td>
   </tr>

</table>

<hr>

      <form action="users" method="get">
        <input type="hidden" name="page" value="1">
        <input type="hidden" name="pageSize" value="5">
        <input type="hidden" name="command" value="show_admin_users">

         <div class="d-grid gap-2 col-4 mx-auto">
            <button type="submit" class="btn btn-secondary btn-md btn-block"><fmt:message key="label.apply"/></button>
         </div>
      </form>


    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
</body>
</html>

<c:remove var = "infoMessage" />