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
    <title>Admin exhibitions</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>
<body>

<tf:greeting/>

  <!-- Navbar -->

        <nav class="navbar navbar-light bg-light">
          <div class="container-fluid">

             <a>
                <tf:home_button/>
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
        <h1> <fmt:message key="label.hall"/></h1>
    </div>

<hr>

<form action = "admin" method="get">
       <input type="hidden" name="command" value="show_admin_halls">
       <input type="hidden" name="page" value="1">
       <input type="hidden" name="pageSize" value="5">
       <button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><fmt:message key="label.drop.filter"/></button>
</form>


<hr>

<!-- Table -->

${infoMessage}

<table class="table table-striped table-hover">
<thead class="table-light">
  <tr>
    <th class="text-center"><fmt:message key="label.hall.no"/></th>
    <th class="text-center"><fmt:message key="label.floor"/></th>
    <th class="text-center"><fmt:message key="label.floor.space"/></th>
    <th class="text-center"><fmt:message key="label.status"/></th>
    <th></th>
   </tr>
</thead>

<tr>
    <td class="text-center">${hall.hallNo}</td>
    <td class="text-center">${hall.floor}</td>
    <td class="text-center">${hall.floorSpace}</td>
    <td class="text-center"><fmt:message key="label.${hall.status.status}"/></td>
    <td>
    <c:if test="${hall.status.status eq 'FREE'}">
        <form action="admin" method="get">
                <input type="hidden" name="page" value="1">
                <input type="hidden" name="pageSize" value="5">
                <input type="hidden" name="command" value="show_appoint_exhibition_for_hall">
                <input type="hidden" name="hall_id" value=${hall.id}>
                <button class="btn btn-outline-dark btn-sm btn-block" type="submit"><fmt:message key="label.assign.exhibition"/></button>
        </form>
    </c:if>
    </td>
</tr>
</table>

<hr>

<form action="admin" method="get">
   <input type="hidden" name="command" value="show_admin_create_hall_page">
   <div class="d-grid gap-2 col-8 mx-auto">
       <button type="submit" class="btn btn-secondary btn-lg btn-block"><fmt:message key="label.add.hall"/></button>
   </div>
</form>

<hr>



    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
</body>
</html>

<c:remove var = "infoMessage" />