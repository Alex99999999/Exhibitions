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
    <title>Exhibitions</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>
<body>

<tf:greeting/>

  <!-- Navbar -->

        <nav class="navbar navbar-light bg-light">
          <div class="container-fluid">
             <a href="index.jsp">
                  <button class="btn btn-outline-secondary btn-md btn-block" type="button"><fmt:message key="label.back"/></button>
             </a>
              <a>
                  <tf:home_button/>
              </a>

              <div class="col-6">
               <form action="users" method = "get" class="d-flex">
                  <input type="hidden" name="command" value="get_exhibition_by_topic">
                  <input type="hidden" name="page" value=${page}>
                                <input type="hidden" name="pageSize" value=${pageSize}>
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



<c:if test = "${role eq 'authorized_user'}">
<hr>
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



 <!-- Page title -->
<hr>
   <div class="row center-block text-center">
        <h1> <fmt:message key="label.exhibitions"/> </h1>
    </div>


<hr>

    <!-- Sort and Filter block -->

<table>
<thead>
  <tr>
     <tr>
        <td>
       <form action="users" method = "get">
           <select name="sorting_option">
               <option value="price" ${sorting_option eq "price" ? 'selected' : ''}><fmt:message key="label.price"/></option>
               <option value="topic" ${sorting_option eq "topic" ? 'selected' : ''}><fmt:message key="label.topic"/></option>
               <option value="start_date" ${sorting_option eq "start_date" ? 'selected' : ''}><fmt:message key="label.start.date"/></option>
               <option value="end_date" ${sorting_option eq "end_date" ? 'selected' : ''}><fmt:message key="label.end.date"/></option>
               <option value="start_time" ${sorting_option eq "start_time" ? 'selected' : ''}><fmt:message key="label.start.time"/></option>
               <option value="end_time" ${sorting_option eq "start_time" ? 'selected' : ''}><fmt:message key="label.end.time"/></option>
               <option value="tickets_available" ${sorting_option eq "tickets_available" ? 'selected' : ''}><fmt:message key="label.tickets"/></option>
           </select>
                <input type="hidden" name="page" value=${page}>
                <input type="hidden" name="pageSize" value=${pageSize}>
                <input type="hidden" name="command" value="sort_exhibition_by_string_value">
                <button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><fmt:message key="label.sort"/></button>
        </form>
        <td>
        <form action="users" method = "get">
              <input type="hidden" name="command" value="sort_exhibition_by_string_value">
              <input type="hidden" name="page" value=${page}>
              <input type="hidden" name="pageSize" value=${pageSize}>
              <input type="hidden" name="sorting_option" value=${sorting_option}>

               <select name="order">
                   <option value="asc" ${order eq "asc" ? 'selected' : ''}>ASC</option>
                   <option value="desc" ${order eq "desc" ? 'selected' : ''}>DESC</option>
               </select>
               <button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><fmt:message key="label.ok"/></button>
            </form>
        </td>

    </td>

    <td>
      <form action="users" method = "get">
          <input type="date" name = "input_date" >
          <input type="hidden" name="page" value=1>
          <input type="hidden" name="pageSize" value=${pageSize}>
          <input type="hidden" name="command" value="filter_exhibitions_by_date">

          <button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><fmt:message key="label.filter"/></button>
      </form>
    </td>
     <td>
          <form action="users" method = "get">
              <input type="hidden" name="command" value="get_current_exhibitions_list">
              <input type="hidden" name="page" value=1>
              <input type="hidden" name="pageSize" value=${pageSize}>
              <button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><fmt:message key="label.drop.filter"/></button>
          </form>
      </td>
  </tr>
</thead>
</table>

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
    <th class="text-center"><fmt:message key="label.price"/></th>
    <th></th>
    <th class="text-center"><fmt:message key="label.tickets"/></th>
    <th></th>
   </tr>
</thead>

<c:forEach var="exhibition" items="${exhibitions_list}">
<tr>
    <td class="text-center">${exhibition.topic}</td>
    <td class="text-center">${exhibition.startDate}</td>
    <td class="text-center">${exhibition.endDate}</td>
    <td class="text-center">${exhibition.startTime}</td>
    <td class="text-center">${exhibition.endTime}</td>
    <td class="text-center">${exhibition.price}</td>
    <td class="text-center">${exhibition.currency.currency}</td>
    <td class="text-center">${exhibition.ticketsAvailable}</td>

      <td><form action="users" method="get">
                    <input type="hidden" name="command" value="show_booking_info">
                    <input type="hidden" name="exhibition_id" value=${exhibition.id}>
                    <button class="btn btn-outline-dark btn-sm btn-block" type="submit"><fmt:message key="label.go"/></button>
             </form>
      </td>
</tr>
</c:forEach>
</table>

<hr>


 <!-- Pagination -->

      <div class="row justify-content-evenly">
        <div class="col-4">

          <nav>
          <form action="users" method = "get">
           <input type="hidden" name="command" value="get_current_exhibitions_list">
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
                      			<a href="users?page=${page-1}&pageSize=${pageSize}&command=get_current_exhibitions_list"><fmt:message key="label.previous"/></a>
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
                  				<a href="users?page=${p}&pageSize=${pageSize}&command=get_current_exhibitions_list">${p}</a>
                  			</c:otherwise>
                  		</c:choose>
                  	</c:forEach>
              </a>
              </li>

              <li class="page-item">
              <a class="page-link" href="#">
              <c:choose>
                  		<c:when test="${page + 1 <= pageCount}">
                  			<a href="users?page=${page+1}&pageSize=${pageSize}&command=get_current_exhibitions_list"><fmt:message key="label.next"/></a>
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