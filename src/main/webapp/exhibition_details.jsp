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
    <title>Exhibition details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>
<body>

<tf:greeting/>

  <!-- Navbar -->

    <!-- Admin functions -->

  <c:if test = "${role eq 'admin'}">

          <nav class="navbar navbar-light bg-light">
            <div class="container-fluid">
               <a>
                <form action="admin" method="get">
                   <input type="hidden" name="page" value="1">
                   <input type="hidden" name="pageSize" value="5">
                   <input type="hidden" name="command" value="get_exhibitions_list">
                   <button class="btn btn-outline-secondary btn-md btn-block" type="submit"><fmt:message key="label.back"/></button>
                 </form>
               </a>

                <a>
                <tf:home_button/>
                </a>

                <a>
                <tf:logout_button/>
                </a>

                <tf:language_switch/>

            </div>
          </nav>
  </c:if>


      <!-- User functions -->

<c:if test = "${role ne 'admin'}">
        <nav class="navbar navbar-light bg-light">
          <div class="container-fluid">
             <a>
              <form action="admin" method="get">
                 <input type="hidden" name="page" value="1">
                 <input type="hidden" name="pageSize" value="5">
                 <input type="hidden" name="command" value="get_current_exhibitions_list">
                 <button class="btn btn-outline-secondary btn-md btn-block" type="button"><fmt:message key="label.back"/></button>
               </form>
             </a>
              <a>
              <tf:home_button/>
              </a>

              <div class="col-6">
               <form action="users" method = "get" class="d-flex">
                  <input type="hidden" name="command" value="get_exhibition_by_topic">
                  <input type="hidden" name="page" value="1">
                  <input type="hidden" name="pageSize" value="5">
                    <div class="col-8">
                       <input class="form-control me-2" type="text" placeholder="<fmt:message key="label.search"/>" name="topic">
                    </div>
                    <div class="col-4">
                        <button class="btn btn-outline-secondary btn-md btn-block" type="submit"><fmt:message key="label.search"/></button>
                    </form>
                    </div>
              </div>

              <c:if test="${role ne 'authorized_user'}">
                <a>
                <tf:sign_in_button/>
                </a>
                <a>
                <tf:sign_up_button/>
                </a>
              </c:if>

              <tf:language_switch/>

          </div>
        </nav>

</c:if>

<c:if test = "${role eq 'authorized_user'}">

  <div class="row justify-content-center">
    <div class="col-2">
      <tf:my_account/>
    </div>
    <div class="col-2">
      <tf:my_bookings/>
    </div>
    <div class="col-2">
       <tf:logout_button/>
    </div>
  </div>

</c:if>

<hr>

 <!-- Page title -->

   <div class="row center-block text-center">
        <h1> <fmt:message key="label.exhibition"/> "${exhibition.topic}" </h1>
    </div>

<hr>

<!-- Table -->

${infoMessage}

<table class="table table-striped table-hover">
<thead class="table-light">
  <tr>
    <th class="text-center"><fmt:message key="label.topic"/></th>
    <td class="text-center"><fmt:message key="label.description"/></td>
    <th class="text-center"><fmt:message key="label.start.date"/></th>
    <th class="text-center"><fmt:message key="label.end.date"/></th>
    <th class="text-center"><fmt:message key="label.from"/></th>
    <th class="text-center"><fmt:message key="label.to"/></th>
    <th class="text-center"><fmt:message key="label.price"/></th>
    <th class="text-center"></th>
    <th class="text-center"><fmt:message key="label.tickets.in.stock"/></th>
    <c:if test = "${role ne'admin'}">
    <th></th>
    </c:if>
               <c:if test = "${role eq 'admin'}">
                  <th class="text-center"><fmt:message key="label.id"/></th>
                  <th class="text-center"><fmt:message key="label.status"/></th>
               </c:if>
   </tr>
</thead>

<tr>
       <td class="text-center">${exhibition.topic}</td>
       <td class="text-center">${exhibition.description}</td>
       <td class="text-center">${exhibition.startDate}</td>
       <td class="text-center">${exhibition.endDate}</td>
       <td class="text-center">${exhibition.startTime}</td>
       <td class="text-center">${exhibition.endTime}</td>
       <td class="text-center">${exhibition.price}</td>
       <td class="text-center">${exhibition.currency.currency}</td>
       <td class="text-center">${exhibition.ticketsAvailable}</td>
          <c:if test = "${role eq 'admin'}">
               <td class="text-center">${exhibition.id}</td>
               <td class="text-center">${exhibition.status.status}</td>
          </c:if>

        <c:if test = "${role ne'admin'}">
            <td>
                <form action="auth" method="post">
                     <input type="hidden" name="command" value="buy_command">
                     <input type="number" name="tickets_booked", placeholder = "1", required, min="1">
                     <input type="hidden" name="booking_status", value="paid">
                     <input type="hidden" name="exhibition_id", value=${exhibition.id}>
                     <input type="hidden" name="user_id", value=${currentUser.id}>
                     <button type="submit" class="btn btn-outline-secondary btn-md btn-block"><fmt:message key="label.buy"/></button>
                </form>
             </td>
        </c:if>
</tr>
</table>

<hr>

<c:if test = "${role eq 'admin'}">

        <form action="admin" method="get">
            <input type="hidden" name="command" value="exhibition_delete_confirmation">
            <input type="hidden" name="exhibition" value=${exhibition}>
            <div class="d-grid gap-2 col-8 mx-auto">
                <button class="btn btn-secondary btn-md btn-block" type="submit"><fmt:message key="label.delete"/></button>
            </div>
        </form>
<hr>
        <form action="admin" method="get">
            <input type="hidden" name="command" value="show_admin_edit_page">
            <div class="d-grid gap-2 col-8 mx-auto">
                <button class="btn btn-secondary btn-md btn-block" type="submit"><fmt:message key="label.edit"/></button>
            </div>
        </form>
<hr>
        <form action="admin" method="get">
             <input type="hidden" name="command" value="show_admin_create_page">
             <div class="d-grid gap-2 col-8 mx-auto">
                <button class="btn btn-secondary btn-md btn-block" type="submit"><fmt:message key="label.create"/></button>
             </div>
         </form>
</c:if>



<hr>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
</body>
</html>

<c:remove var = "infoMessage" />