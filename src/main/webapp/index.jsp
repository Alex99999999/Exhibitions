<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<%@ page isELIgnored="false" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.interface"/>

<html>
<head>
    <title>Start</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>

<body>

<div class="full-height align-items-center">

       <!-- Navbar -->

        <nav class="navbar navbar-light bg-light">
          <div class="container-fluid">
            <a class="navbar-brand"><fmt:message key="label.exhibitions"/></a>
              <tf:language_switch/>
          </div>
        </nav>


        <!-- Carousel -->

        <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
          <div class="carousel-inner">
            <div class="carousel-item active">
              <img src="ex.jpg" class="d-block w-100" alt="Exhibitions">
              <div class="carousel-caption d-none d-md-block">
                <div class="row justify-content-center align-items-center">
                  <h1 class="display-2"><fmt:message key="label.welcome"/></h1>
                </div>
                <div class="row justify-content-center align-items-center">
                  <div class="col-sm-4 center-block text-center">
                    <div class="p-3">

    <!-- Go to login page -->

                        <form action="users" method="get">
                            <input type="hidden" name="command" value="show_login_page">
                            <button type="submit" class="btn btn-light btn-lg"><fmt:message key="label.sign.in"/></button>
                        </form>
                      </div>
                    </div>

            <!-- Go to exhibitions button -->
                    <div class="col-sm-4 center-block text-center">
                    <div class="p-3">
                        <form action="users" method="get">
                          <input type="hidden" name="page" value="1">
                          <input type="hidden" name="pageSize" value="5">
                          <input type="hidden" name="command" value="get_current_exhibitions_list">
                          <button type="submit" class="btn btn-primary btn-lg"><fmt:message key="label.exhibitions"/></button>
                        </form>
                      </div>
                    </div>
                </div>
              </div>
            </div>
          </div>
        </div>
</div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
</body>
</html>