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
    <title>Exhibition details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>
<body>


  <!-- Navbar -->


      <!-- User functions -->

        <nav class="navbar navbar-light bg-light">
          <div class="container-fluid">
             <a>
               <form action="users" method="get">
                      <input type="hidden" name="command" value="show_booking_info">
                      <input type="hidden" name="exhibition_id" value=${exhibition.id}>
                 <button class="btn btn-outline-secondary btn-md btn-block" type="submit"><fmt:message key="label.back"/></button>
               </form>
             </a>

             <a>
             <tf:home_button/>
             </a>

             <a>
             <tf:my_account/>
             </a>

             <a>
             <tf:my_bookings/>
             </a>

             <a>
             <tf:logout_button/>
             </a>

             <a>
             <tf:language_switch/>
             </a>
          </div>
        </nav>


 <!-- Page title -->

   <div class="row center-block text-center">
        <h1> <fmt:message key="label.your.order.details"/></h1>
    </div>


<hr>

 <div class="row center-block text-center">
       <h2><fmt:message key="label.thank.you"/> ${currentUser.login}!</h2>
  </div>

<!-- Table -->

${infoMessage}

<table class="table table-striped table-hover">
<tr>
    <th><fmt:message key="label.your.order.no"/>:</th>
    <td>${booking.id}</td>
</tr>

<tr>
    <th><fmt:message key="label.exhibition"/>:</th>
    <td>${exhibition.topic}</td>
</tr>
<tr>
    <th><fmt:message key="label.quantity.of.tickets.you.ordered"/></th>
    <td>${booking.ticketQty}</td>
</tr>
<tr>
    <th><fmt:message key="label.total.cost"/></th>
    <td><cost:calculate_cost booking="${booking}"/> ${exhibition.currency.currency} </td>
</tr>
</table>

<hr>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
</body>
</html>

