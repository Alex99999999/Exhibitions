<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<%@ page isELIgnored="false" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'en'}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.interface"/>


<!DOCTYPE html>
<html>
<head>
	<title>Login Page</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
  <link rel="stylesheet" href="style.css">
</head>

<body>

<!-- Navbar-->

 <nav class="navbar navbar-light bg-light">
          <div class="container-fluid">
             <a href="index.jsp">
                  <button class="btn btn-outline-secondary btn-md btn-block" type="button"><fmt:message key="label.back"/></button>
             </a>

             <a>
              <tf:home_button/>
             </a>

             <a>

     <form action="users" method="get">
       <input type="hidden" name="page" value="1">
       <input type="hidden" name="pageSize" value="5">
       <input type="hidden" name="command" value="get_current_exhibitions_list">
       <button type="submit" class="btn btn-outline-secondary btn-md btn-block"><fmt:message key="label.exhibitions"/></button>
     </form>
             </a>

             <tf:language_switch/>

          </div>
        </nav>


${login_error}

<div class="container">
	<div class="d-flex justify-content-center h-100">
		<div class="card">
			<div class="card-header">
				<h3><fmt:message key="label.sign.in"/></h3>

			</div>
			<div class="card-body">
				<form action = "users" method="post">

					<div class="input-group form-group">
						<div class="input-group-prepend">
							<span class="input-group-text"><i class="fas fa-user"></i></span>
						</div>

						<input type="text" class="form-control" placeholder="<fmt:message key="label.sign.in"/>" name = "login" required>

					</div>
					<div class="input-group form-group">
						<div class="input-group-prepend">
							<span class="input-group-text"><i class="fas fa-key"></i></span>
						</div>
						<input type="password" class="form-control" placeholder="<fmt:message key="label.password"/>" name = "password" required>
					</div>

					<div class="row align-items-center remember">
						<input type="checkbox"> <fmt:message key="label.remember.me"/>
					</div>
					<div class="form-group">
					  <input type="hidden" name="command" value="login">
						<input type="submit" name="command" value="<fmt:message key="label.sign.in"/>" class="btn float-right login_btn">
					</div>
				</form>

			</div>
			<div class="card-footer">
				<div class="d-flex justify-content-center links">

					<fmt:message key="label.dont.have.account"/>
          <span><a href="registration.jsp"><fmt:message key="label.sign.up"/></a></span>

				</div>
				<div class="d-flex justify-content-center">
					<a href="#"><fmt:message key="label.forgot.password"/></a>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
</body>
</html>

<c:remove var = "login_error" />