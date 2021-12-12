<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<%@ taglib uri="http://com.my.custom.tag" prefix="cost" %>
<%@ page isELIgnored="false" %>


<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'en'}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.interface"/>

<html>
<head>
    <title>Create exhibition</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>
<body>


  <!-- Navbar -->

          <nav class="navbar navbar-light bg-light">
            <div class="container-fluid">
                <a>
                  <form action="admin" method="get">
                     <input type="hidden" name="page" value="1">
                     <input type="hidden" name="pageSize" value="5">
                     <input type="hidden" name="command" value="show_admin_halls">
                     <button class="btn btn-outline-secondary btn-md btn-block" type="submit"><fmt:message key="label.back"/></button>
                  </form>
                </a>

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
          <h1> <fmt:message key="label.add.hall"></h1>
      </div>


<hr>

<!-- Table -->

${infoMessage}
<form action="admin" method="post">
<input type="hidden" name = "hall_status" value = "free">
<table class="table table-striped table-hover">
<tr>
    <th><fmt:message key="label.hall.no"/>:</th>
    <td><input type="number" name = "hall_no" placeholder="<fmt:message key="label.hall.no"/>" required></td>
</tr>

<tr>
    <th><fmt:message key="label.floor"/></th>
    <td><input type="number" name="hall_floor" min="0" placeholder="<fmt:message key="label.floor"/>"></td>
</tr>

<tr>
    <th><fmt:message key="label.floor.space"/></th>
    <td><input type="number" name="hall_floor_space" min="0" placeholder="<fmt:message key="label.floor.space"/>" step=".01"></td>
</tr>
</table>

<hr>

      <input type="hidden" name="command" value = "admin_hall_create">
      <div class="d-grid gap-2 col-6 mx-auto">
            <button class="btn btn-secondary btn-md btn-block" type="submit"><fmt:message key="label.apply"/></button>
        </div>
    </form>


    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
</body>
</html>
