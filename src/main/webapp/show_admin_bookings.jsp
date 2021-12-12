<%@ page contentType="text/html; charset=UTF-8" %>
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
    <title>Bookings</title>
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
                   <input type="hidden" name="command" value="show_admin_page">
                   <button class="btn btn-outline-secondary btn-md btn-block" type="submit"><fmt:message key="label.back"/></button>
                </form>
              </a>

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
        <h1><fmt:message key="label.bookings"/></h1>
    </div>

<hr>

    <!-- Sort and Filter block -->

<form action = "admin" method = "get">
<input type = "hidden" name = "command" value = "filter_bookings">
<input type="hidden" name="page" value=${page}>
<input type="hidden" name="pageSize" value=${pageSize}>
  <table>

     <tr>
       <th><fmt:message key="label.user"/></th>
       <td> <input type="text" name="user_login"> </td>
     </tr>

		 <tr>
     <th><fmt:message key="label.booking.status"/></th>
		  <c:forEach var="booking_status" items="${booking_status_list}">
              <td>
                     <input type="radio" name="booking_status" value="${booking_status.status}">
                     <fmt:message key="label.${booking_status.status}"/>
              </td>
           </c:forEach>
        </tr>
    </table>
<button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><fmt:message key="label.filter"/></button>
</form>

<form action = "admin" method="get">
       <input type="hidden" name="command" value="show_admin_bookings">
       <input type="hidden" name="page" value="1">
       <input type="hidden" name="pageSize" value="5">
       <button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><fmt:message key="label.drop.filter"/></button>
</form>

<hr>

<table>
  <tr>
    <th>
        <form action="admin" method = "get">
           <select name="sorting_option">

           	<option value="booking_user" ${sorting_option eq "booking_user" ? 'selected' : ''}><fmt:message key="label.user"/></option>
           	<option value="booking_price" ${sorting_option eq "booking_price" ? 'selected' : ''}><fmt:message key="label.price"/></option>
           	<option value="booking_topic" ${sorting_option eq "booking_topic" ? 'selected' : ''}><fmt:message key="label.topic"/></option>
           	<option value="booking_exhibition_status" ${sorting_option eq "booking_exhibition_status" ? 'selected' : ''}><fmt:message key="label.exhibition.status"/></option>
           	<option value="booking_status" ${sorting_option eq "booking_status" ? 'selected' : ''}><fmt:message key="label.booking.status"/></option>
           	<option value="booking_tickets" ${sorting_option == "booking_tickets" ? 'selected' : ''}><fmt:message key="label.tickets.quantity"/></option>

           </select>
           <input type="hidden" name="command" value="sort_bookings">
           <input type="hidden" name="page" value="1">
           <input type="hidden" name="pageSize" value="5">
           <button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><fmt:message key="label.sort"/></button>
           </form>
      </th>
  </tr>
</table>

<hr>

<c:if test="${empty booking_list}">
    ${infoMessage}
</c:if>

<c:if test="${!empty booking_list}">
<table class="table table-striped table-hover">
<thead>
  <tr>
    <th><fmt:message key="label.topic"/></th>
    <th><fmt:message key="label.start.date"/></th>
    <th><fmt:message key="label.end.date"/></th>
    <th><fmt:message key="label.from"/></th>
    <th><fmt:message key="label.to"/></th>
    <th><fmt:message key="label.price"/></th>
    <th></th>
    <th><fmt:message key="label.exhibition.status"/></th>
    <th><fmt:message key="label.tickets.booked"/></th>
    <th><fmt:message key="label.booking.status"/></th>
    <th><fmt:message key="label.user"/></th>
    <th></th>
    <th></th>
   </tr>

</thead>

<c:forEach var="booking" items="${booking_list}">
<tr>
    <td>${booking.exhibition.topic}</td>
    <td>${booking.exhibition.startDate}</td>
    <td>${booking.exhibition.endDate}</td>
    <td>${booking.exhibition.startTime}</td>
    <td>${booking.exhibition.endTime}</td>
    <td>${booking.exhibition.price}</td>
    <td>${booking.exhibition.currency.currency}</td>
    <td>${booking.exhibition.status.status}</td>
    <td>${booking.ticketQty}</td>
    <td>${booking.status.status}</td>
    <td>${booking.user.login}</td>
   </tr>
</c:forEach>
</table>
</c:if>







<hr>

 <!-- Pagination -->

      <div class="row justify-content-evenly">
        <div class="col-4">

          <nav>
          <form action="admin" method = "get">
           <input type="hidden" name="command" value="show_admin_bookings">
              <ul class="pagination justify-content-center">
                <li class="page-item">
                  <a class="page-link">
                    <span aria-hidden="true"><fmt:message key="label.go.to.page"/></span>
                  </a>
                </li>

                <li>
                   <select class="form-select form-select-md" name="page">
                   		 <c:forEach begin="1" end="${pageCount}" var="p">
                   		 <option value="${p}" ${p == param.page ? 'selected' : ''}>${p}</option>
                   		 </c:forEach>
                   	</select>
                </li>

                <li>
                <a class="page-link">
                  <span aria-hidden="true"><fmt:message key="label.display"/></span>
                </a>
              </li>
              <li>
                 <select class="form-select form-select-md " name="pageSize">
                     				<option value="2" ${pageSize == 2 ? 'selected' : ''}>2</option>
                     				<option value="5" ${pageSize == 5 ? 'selected' : ''}>5</option>
                     				<option value="10" ${pageSize == 10 ?'selected' : ''}>10</option>
                     				<option value="15" ${pageSize == 15 ? 'selected' : ''}>15</option>
                 </select>
              </li>
              <li>
                    <button type="submit" class="btn btn-outline-secondary btn-md btn-block"> <fmt:message key="label.ok"/> </button>
              </li>
              </ul>
            </form>
            </nav>
      </div>


        <div class="col-4">
          <nav>
              <ul class="pagination justify-content-center">
                <li class="page-item">
                  <a class="page-link" href="#">
                    <span aria-hidden="true"><fmt:message key="label.page"/></span>
                  </a>
                </li>

                <li class="page-item">
                    <a class="page-link" href="#">${page} </a>
                </li>

                <li class="page-item">
                  <a class="page-link" href="#">
                    <span aria-hidden="true"><fmt:message key="label.of"/></span>
                  </a>
                </li>

                <li class="page-item"><a class="page-link" href="#">${pageCount}</a></li>
              </ul>
            </nav>
      </div>


      <div class="col-4">
        <nav>
            <ul class="pagination justify-content-center">
              <li class="page-item">
                <a class="page-link" href="#">
                         <c:choose>
                      		<c:when test="${page - 1 > 0}">
                      			<a href="admin?page=${page-1}&pageSize=${pageSize}&command=show_admin_bookings"><fmt:message key="label.previous"/></a>
                      		</c:when>
                      		<c:otherwise>
                      			<fmt:message key="label.previous"/>
                      		</c:otherwise>
                      	</c:choose>
                </a>
              </li>

              <li class="page-item">
                <a class="page-link" href="#">
                  <c:forEach var="p" begin="${minPossiblePage}" end="${maxPossiblePage}">
                  		<c:choose>
                  			<c:when test="${page == p}">${p}</c:when>
                  			<c:otherwise>
                  				<a href="admin?page=${p}&pageSize=${pageSize}&command=show_admin_bookings">${p}</a>
                  			</c:otherwise>
                  		</c:choose>
                  	</c:forEach>
              </a>
              </li>

              <li class="page-item">
              <a class="page-link" href="#">
              <c:choose>
                  		<c:when test="${page + 1 <= pageCount}">
                  			<a href="admin?page=${page+1}&pageSize=${pageSize}&command=show_admin_bookings"><fmt:message key="label.next"/></a>
                  		</c:when>
                  		<c:otherwise>
                  			<fmt:message key="label.next"/>
                  		</c:otherwise>
                  	</c:choose>
              </a>
              </li>
            </ul>
          </nav>
    </div>
 </div>




    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
</body>
</html>

<c:remove var = "infoMessage" />