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
                <form action="admin" method="get">
                   <input type="hidden" name="command" value="show_admin_page">
                   <button class="btn btn-outline-secondary btn-md btn-block" type="submit"><fmt:message key="label.back"/></button>
                </form>
              </a>

              <a>
              <tf:home_button/>
              </a>

              <div class="col-4">
               <form action="admin" method = "get" class="d-flex">
                  <input type="hidden" name="command" value="get_hall_by_number">
                    <div class="col-8">
                       <input class="form-control me-2" type="text" placeholder="<fmt:message key="label.hall.no"/>" name="hall_no">
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
        <h1> <fmt:message key="label.admin.halls.list"/></h1>
    </div>

<hr>

    <!--  Filter  -->

<form action = "admin" method = "get">
<input type = "hidden" name = "command" value = "filter_halls">
<input type="hidden" name="page" value=${page}>
<input type="hidden" name="pageSize" value=${pageSize}>
  <table>
		 <tr>
     <th><fmt:message key="label.floor.space"/></th>
              <td class="text-center">
                     <input type="radio" name="floor_space" value="> 100"> > 100  <fmt:message key="label.sq.m."/>
                     <input type="radio" name="floor_space" value="< 100"> < 100  <fmt:message key="label.sq.m."/>
              </td>
     </tr>

     <tr>
     <th><fmt:message key="label.hall.status"/></th>
        <c:forEach var="hall_status" items="${hall_status_list}">
                 <td class="text-center">
                    <input type="radio" name="hall_status" value="${hall_status.status}">
                    <fmt:message key="label.${hall_status.status}"/>
                 </td>
         </c:forEach>
     </tr>
    </table>
<button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><fmt:message key="label.filter"/></button>
</form>

<form action = "admin" method="get">
       <input type="hidden" name="command" value="show_admin_halls">
       <input type="hidden" name="page" value="1">
       <input type="hidden" name="pageSize" value="5">
       <button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><fmt:message key="label.drop.filter"/></button>
</form>


    <!-- Sort  -->


<table>
  <tr>
    <td>
   <form action="admin" method = "get">
      <input type="hidden" name="command" value="sort_halls">
      <input type="hidden" name="page" value=${page}>
      <input type="hidden" name="pageSize" value=${pageSize}>
       <select name="sorting_option">

           <option value="floor_space" ${sorting_option eq "floor_space" ? 'selected' : ''}><fmt:message key="label.floor.space"/></option>
           <option value="floor" ${sorting_option eq "floor" ? 'selected' : ''}><fmt:message key="label.floor"/></option>
           <option value="hall_no" ${sorting_option eq "hall_no" ? 'selected' : ''}><fmt:message key="label.hall.no"/></option>
           <option value="status_id" ${sorting_option eq "status_id" ? 'selected' : ''}><fmt:message key="label.hall.status"/></option>
       </select>
       <button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><fmt:message key="label.sort"/></button>
    </form>
     <td>
       <form action="admin" method = "get">
          <input type="hidden" name="command" value="sort_halls">
          <input type="hidden" name="page" value=${page}>
          <input type="hidden" name="pageSize" value=${pageSize}>
          <input type="hidden" name="sorting_option" value=${sorting_option}>

           <select name="order">
               <option value="asc" ${order eq "asc" ? 'selected' : ''}>ASC</option>
               <option value="desc" ${order eq "desc" ? 'selected' : ''}>DESC</option>
           </select>
           <button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><fmt:message key="label.ok"/></button>
        </form>
  </tr>
</table>

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

<c:forEach var="hall" items="${hall_list}">
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
</c:forEach>
</table>

<hr>

<form action="admin" method="get">
   <input type="hidden" name="command" value="show_admin_create_hall_page">
   <div class="d-grid gap-2 col-8 mx-auto">
       <button type="submit" class="btn btn-secondary btn-lg btn-block"><fmt:message key="label.add.hall"/></button>
   </div>
</form>

<hr>

 <!-- Pagination -->

      <div class="row justify-content-evenly">
        <div class="col-4">

          <nav>
          <form action="admin" method = "get">
           <input type="hidden" name="command" value="show_admin_halls">
           <input type="hidden" name="order" value=${order}>
           <input type="hidden" name="sorting_option" value=${sorting_option}>
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
                      			<a href="admin?page=${page-1}&pageSize=${pageSize}&sorting_option=${sorting_option}&order=${order}&command=show_admin_halls"><fmt:message key="label.previous"/></a>
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
                  				<a href="admin?page=${p}&pageSize=${pageSize}&command=show_admin_halls">${p}</a>
                  			</c:otherwise>
                  		</c:choose>
                  	</c:forEach>
              </a>
              </li>

              <li class="page-item">
              <a class="page-link" href="#">
              <c:choose>
                  		<c:when test="${page + 1 <= pageCount}">
                  			<a href="admin?page=${page+1}&pageSize=${pageSize}&command=show_admin_halls"><fmt:message key="label.next"/></a>
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