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

              <div class="col-6">
               <form action="admin" method = "get" class="d-flex">
                  <input type="hidden" name="command" value="get_exhibition_for_hall">
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
              </a>

              <tf:language_switch/>

          </div>
        </nav>




 <!-- Page title -->
<hr>
   <div class="row center-block text-center">
        <h1> <fmt:message key="label.admin.appoint.exhibition"/></h1>
    </div>

<hr>

 <div class="row center-block text-center">
        <h3> <fmt:message key="label.hall"/></h3>
 </div>


<!-- Hall table -->

${infoMessage}

<table class="table table-striped table-hover">
<thead class="table-light">
  <tr>
    <th class="text-center"><fmt:message key="label.hall.no"/></th>
    <th class="text-center"><fmt:message key="label.floor"/></th>
    <th class="text-center"><fmt:message key="label.floor.space"/></th>
    <th class="text-center"><fmt:message key="label.status"/></th>
   </tr>
</thead>

<tr>
    <td class="text-center">${hall.hallNo}</td>
    <td class="text-center">${hall.floor}</td>
    <td class="text-center">${hall.floorSpace}</td>
    <td class="text-center"><fmt:message key="label.${hall.status.status}"/></td>
</tr>
</table>

<hr>

      <!-- Exhibitions table -->

 <div class="row center-block text-center">
        <h3> <fmt:message key="label.exhibitions"/></h3>
 </div>

<table class="table table-striped table-hover">
<thead class="table-light">
  <tr>
    <th class="text-center"><fmt:message key="label.topic"/></th>
    <th class="text-center"><fmt:message key="label.start.date"/></th>
    <th class="text-center"><fmt:message key="label.end.date"/></th>
    <th></th>
   </tr>
</thead>

<c:forEach var="exhibition" items="${exhibitions_list}">
  <form action="admin" method="post">
    <tr>
        <td class="text-center">${exhibition.topic}</td>
        <td class="text-center">${exhibition.startDate}</td>
        <td class="text-center">${exhibition.endDate}</td>
        <td class="text-center">
            <input type="hidden" name="command" value="set_halls_for_exhibition">
            <input type="hidden" name="free_hall_id" value=${hall.id}>
            <input type="hidden" name="exhibition_id" value=${exhibition.id}>
            <div class="d-grid gap-2 col-6 mx-auto">
              <button class="btn btn-secondary btn-md btn-block" type="submit"><fmt:message key="label.appoint"/></button>
            </div>
        </td>
    </tr>
  </form>
</c:forEach>
</table>



<hr>

 <!-- Pagination -->

      <div class="row justify-content-evenly">
        <div class="col-4">

          <nav>
          <form action="admin" method = "get">
           <input type="hidden" name="command" value="show_appoint_exhibition_for_hall">
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
                      			<a href="users?page=${page-1}&pageSize=${pageSize}&command=show_appoint_exhibition_for_hall"><fmt:message key="label.previous"/></a>
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
                  				<a href="users?page=${p}&pageSize=${pageSize}&command=show_appoint_exhibition_for_hall">${p}</a>
                  			</c:otherwise>
                  		</c:choose>
                  	</c:forEach>
              </a>
              </li>

              <li class="page-item">
              <a class="page-link" href="#">
              <c:choose>
                  		<c:when test="${page + 1 <= pageCount}">
                  			<a href="users?page=${page+1}&pageSize=${pageSize}&command=show_appoint_exhibition_for_hall"><fmt:message key="label.next"/></a>
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