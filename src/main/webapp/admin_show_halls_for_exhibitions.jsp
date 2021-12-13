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
                      <input type="hidden" name="command" value="show_admin_edit_page">
                      <button class="btn btn-outline-secondary btn-md btn-block" type="submit"><fmt:message key="label.back"/></button>
                   </form>
                 </a>


                <a>
                  <form action="admin" method="get">
                     <input type="hidden" name="command" value="show_admin_page">
                     <button class="btn btn-outline-secondary btn-md btn-block" type="submit"><fmt:message key="label.home"/></button>
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
          <h1> <fmt:message key="label.set.halls"/></h1>
     </div>

<hr>

<h2><fmt:message key="label.exhibition"/></h2>

<table class="table table-striped table-hover">
<thead>
  <tr>
    <th class="text-center"><fmt:message key="label.id"/></th>
    <th class="text-center"><fmt:message key="label.topic"/></th>
    <th class="text-center"><fmt:message key="label.status"/></th>
   </tr>
</thead>

<tr>
    <td class="text-center">${exhibition.id}</td>
    <td class="text-center">${exhibition.topic}</td>
    <td class="text-center">${exhibition.status.status}</td>
</tr>
</table>

<hr>

<h2><fmt:message key="label.currently.occupied"/></h2>

<table class="table table-striped table-hover">
<thead>
  <tr>
    <th class="text-center"><fmt:message key="label.hall.id"/></th>
    <th class="text-center"><fmt:message key="label.floor"/></th>
    <th class="text-center"><fmt:message key="label.floor.space"/></th>
    <th class="text-center"><fmt:message key="label.hall.no"/></th>
    <th class="text-center"></th>
    <th class="text-center"></th>
   </tr>
</thead>

<c:forEach var="hall" items="${exhibition_halls}">
<tr>
    <td class="text-center">${hall.id}</td>
    <td class="text-center">${hall.floor}</td>
    <td class="text-center">${hall.floorSpace}</td>
    <td class="text-center">${hall.hallNo}</td>
    <td>    </td>

    <td class="text-center"><form action="admin" method="post">
                 <input type="hidden" name="command" value="delete_hall_from_exhibition">
                 <input type="hidden" name="hall_id" value=${hall.id}>
                 <input type="hidden" name="hall_id" value=${exhibition.id}>
                 <button type="submit" class="btn btn-outline-secondary btn-md btn-block"><fmt:message key="label.delete"/></button>
        </form>
     </td>
</tr>
</c:forEach>
</table>

<hr>

<h2><fmt:message key="label.free.halls"/></h2>

<form action="admin" method="post">
<table class="table table-striped table-hover">
<thead>
  <tr>
    <th class="text-center"><fmt:message key="label.hall.id"/></th>
    <th class="text-center"><fmt:message key="label.floor"/></th>
    <th class="text-center"><fmt:message key="label.floor.space"/></th>
    <th class="text-center"><fmt:message key="label.hall.no"/></th>
    <th class="text-center"></th>
    <th class="text-center"></th>
   </tr>
</thead>

<c:forEach var="free" items="${free_halls}">
<tr>
    <td class="text-center">${free.id}</td>
    <td class="text-center">${free.floor}</td>
    <td class="text-center">${free.floorSpace}</td>
    <td class="text-center">${free.hallNo}</td>
    <td class="text-center">
        <input type="checkbox" name="free_hall_id" value=${free.id}>
    </td>
    <td></td>
</tr>
</c:forEach>
</table>

    <input type="hidden" name="command" value="set_halls_for_exhibition">
    <input type="hidden" name="exhibition_id" value=${exhibition.id}>
    <div class="d-grid gap-2 col-6 mx-auto">
      <button class="btn btn-secondary btn-md btn-block" type="submit"><fmt:message key="label.apply"/></button>
    </div>
</form>

<hr>


    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
</body>
</html>
