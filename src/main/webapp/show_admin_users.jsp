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
    <title> <fmt:message key="label.admin.users"/></title>
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
                  <input type="hidden" name="command" value="find_user_by_login">
                    <div class="col-8">
                       <input class="form-control me-2" type="text" placeholder="<fmt:message key="label.search"/>" name="login">
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
                  <tf:admin_halls_btn/>
               </a>

               <a>
                 <tf:admin_bookings_btn/>
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
        <h1> <fmt:message key="label.admin.users"/> </h1>
    </div>

<hr>

    <!-- Sort and Filter block -->

<form action = "admin" method = "get">
<input type = "hidden" name = "command" value = "filter_users">
<input type="hidden" name="page" value="1">
<input type="hidden" name="pageSize" value="5">
  <table>
        <tr>
           <c:forEach var="user_role" items="${user_role_list}">
              <td>
                 <input type="radio" name="user_role" value=${user_role.role}>
                 <fmt:message key="label.${user_role.role}"/>
              </td>
           </c:forEach>
        </tr>
    </table>
<button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><fmt:message key="label.filter"/></button>
</form>

<form action = "admin" method="get">
       <input type="hidden" name="command" value="show_admin_users">
       <input type="hidden" name="page" value="1">
       <input type="hidden" name="pageSize" value="5">
      <button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><fmt:message key="label.drop.filter"/></button>
</form>

<hr>

<table>
<thead>
  <tr>
      <td>
        <form action="admin" method = "get">
           <select name="sorting_option">

               <option value="login" ${sorting_option eq "user_login" ? 'selected' : ''}><fmt:message key="label.login"/></option>
               <option value="user_roles_id" ${sorting_option eq "user_roles_id" ? 'selected' : ''}><fmt:message key="label.role"/></option>
           </select>
           <input type="hidden" name="command" value="sort_users">
           <input type="hidden" name="page" value=${page}>
           <input type="hidden" name="pageSize" value=${pageSize}>
           <button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><fmt:message key="label.sort"/></button>
         </form>
         <td>
             <form action="admin" method = "get">
                   <input type="hidden" name="command" value="sort_users">
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
  </tr>
</thead>
</table>


<hr>


<!-- Table -->

${infoMessage}

<table class="table table-striped table-hover">
<thead class="table-light">
  <tr>
        <th class="text-center"><fmt:message key="label.login"/></th>
        <th class="text-center"><fmt:message key="label.role"/></th>
        <th class="text-center"></th>
        <th class="text-center"></th>
   </tr>
</thead>

<c:forEach var="user" items="${user_list}">
<tr>
    <td class="text-center">${user.login}</td>
    <td class="text-center"><fmt:message key="label.${user.role.role}"/></td>
    <td class="text-center">
     <form action="admin" method="post">
        <select name="user_role">
          <c:forEach var="user_role" items="${user_role_list}">
               <option value="${user_role.role}"
                     <c:if test="${user_role.role eq user.role.role}">selected="selected"</c:if>>
                       <fmt:message key="label.${user_role.role}"/>
               </option>
           </c:forEach>
        </select>

          <input type="hidden" name="command" value="update_user">
          <input type="hidden" name="page" value=${page}>
          <input type="hidden" name="pageSize" value=${pageSize}>
          <input type="hidden" name="user_id", value=${user.id}>
          <button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><fmt:message key="label.set.role"/></button>
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
          <form action="admin" method = "get">
           <input type="hidden" name="command" value="show_admin_users">
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
                      			<a href="users?page=${page-1}&pageSize=${pageSize}&command=show_admin_users"><fmt:message key="label.previous"/></a>
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
                  				<a href="users?page=${p}&pageSize=${pageSize}&command=show_admin_users">${p}</a>
                  			</c:otherwise>
                  		</c:choose>
                  	</c:forEach>
              </a>
              </li>

              <li class="page-item">
              <a class="page-link" href="#">
              <c:choose>
                  		<c:when test="${page + 1 <= pageCount}">
                  			<a href="users?page=${page+1}&pageSize=${pageSize}&command=show_admin_users"><fmt:message key="label.next"/></a>
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