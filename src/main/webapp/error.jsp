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
    <title>Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>
<body>



  <!-- Navbar -->

        <nav class="navbar navbar-light bg-light">
          <div class="container-fluid">

             <tf:language_switch/>

          </div>
        </nav>




 <!-- Page title -->
<hr>

   <div class="row center-block text-center">
        <h1><fmt:message key="label.error"/></h1>
    </div>



<div class="row center-block text-center">
<h1>${requestScope['javax.servlet.error.status_code']}</h1>
<p>${requestScope['javax.servlet.error.message']}</p>
</div>



  <div class="row center-block text-center">
        <h3>${errorMessage}</h3>
    </div>
<hr>


<div class="row align-items-evenly">
    <div class="col-6">

     <c:choose>
            <c:when test="${role == 'admin' }">
                 <form action="admin" method="get">
                     <input type="hidden" name="command" value = "show_admin_page">
                     <input type="hidden" name="exhibition" value = ${exhibition}>
              	      <div class="d-grid gap-2 col-8 mx-auto">
                          <button type="submit" class="btn btn-secondary btn-lg btn-block"><fmt:message key="label.admin.page"/></button>
                      </div>
                 </form>
            </c:when>
            <c:otherwise>
                  <form action="users" method="get">
                         <input type="hidden" name="page" value="1">
                         <input type="hidden" name="pageSize" value="5">
                         <input type="hidden" name="command" value="get_current_exhibitions_list">
                         <div class="d-grid gap-2 col-8 mx-auto">
                            <button type="submit" class="btn btn-secondary btn-lg btn-block"><fmt:message key="label.exhibitions"/></button>
                         </div>
                  </form>
        			</c:otherwise>
        		</c:choose>
    </div>
    <div class="col-6">
            <a href="index.jsp">
	                  <div class="d-grid gap-2 col-8 mx-auto">
                        <button type="submit" class="btn btn-secondary btn-lg btn-block"><fmt:message key="label.home"/></button>
                    </div>
            </a>
    </div>
 </div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
</body>
</html>

<c:remove var = "infoMessage" />