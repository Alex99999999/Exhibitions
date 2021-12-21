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
    <title>My Bookings</title>
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
              <tf:my_account/>
              </a>

              <a>
              <form action="users" method="get">
                     <input type="hidden" name="page" value="1">
                     <input type="hidden" name="pageSize" value="5">
                     <input type="hidden" name="command" value="get_current_exhibitions_list">
                     <button type="submit" class="btn btn-outline-secondary btn-md btn-block"><fmt:message key="label.exhibitions"/></button>
              </form>
              </a>

              <div class="col-6">
               <form action="users" method = "get" class="d-flex">
                  <input type="hidden" name="command" value="get_exhibition_by_topic">
                    <div class="col-8">
                       <input class="form-control me-2" type="text" placeholder="<fmt:message key="label.search"/>" name="topic">
                    </div>
                    <div class="col-4">
                        <button class="btn btn-outline-secondary btn-md btn-block" type="submit"><fmt:message key="label.search"/></button>
                    </form>
                    </div>
              </div>
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
        <h1> <fmt:message key="label.my.bookings"/> </h1>
    </div>

<hr>

  <!-- Table -->

${infoMessage}

<table class="table table-striped table-hover">
<thead class="table-light">
 <tr>
     <th class="text-center"><fmt:message key="label.topic"/></th>
     <th class="text-center"><fmt:message key="label.start.date"/></th>
     <th class="text-center"><fmt:message key="label.end.date"/></th>
     <th class="text-center"><fmt:message key="label.from"/></th>
     <th class="text-center"><fmt:message key="label.to"/></th>
     <th class="text-center"><fmt:message key="label.booking.status"/></th>
     <th class="text-center"><fmt:message key="label.tickets.quantity"/></th>
     <th></th>
     <th></th>
     <th></th>
 </tr>
</thead>

<c:forEach var="booking" items="${booking_list}">
<tr>
    <td class="text-center">${booking.exhibition.topic}</td>
    <td class="text-center">${booking.exhibition.startDate}</td>
    <td class="text-center">${booking.exhibition.endDate}</td>
    <td class="text-center">${booking.exhibition.startTime}</td>
    <td class="text-center">${booking.exhibition.endTime}</td>
    <td class="text-center">${booking.status.status}</td>
    <td class="text-center">${booking.ticketQty}</td>

    <td>
      <form action="auth" method="post">
           <input type="hidden" name="command" value="cancel_booking_command">
           <input type="hidden" name="booking_id", value=${booking.id}>
           <button type="submit" class="btn btn-outline-secondary btn-md btn-block"><fmt:message key="label.cancel"/></button>
      </form>
    </td>
    <td><form action="users" method="get">
                     <input type="hidden" name="command" value="show_booking_info">
                     <input type="hidden" name="exhibition_id" value=${booking.exhibition.id}>
                     <button class="btn btn-outline-dark btn-sm btn-block" type="submit"><fmt:message key="label.go"/></button>
              </form>
    </td>
    <td>
          <form action="auth" method="post">
               <input type="hidden" name="command" value="buy_command">
               <input type="number" name="tickets_booked", value = "1", required, min="1">
               <input type="hidden" name="booking_status", value="paid">
               <input type="hidden" name="exhibition_id", value=${booking.exhibition.id}>
               <input type="hidden" name="user_id", value=${currentUser.id}>
               <button type="submit" class="btn btn-outline-secondary btn-md btn-block"><fmt:message key="label.buy"/></button>
          </form>
    </td>

   </tr>
</c:forEach>
</table>

<hr>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
</body>
</html>

<c:remove var="infoMessage"/>