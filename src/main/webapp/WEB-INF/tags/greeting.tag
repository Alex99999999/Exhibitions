<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.interface"/>



    <c:choose>
   			<c:when test="${role == null }">
   			  <fmt:message key="label.hello"/>, <fmt:message key="label.guest"/>!
   			</c:when>

   			<c:otherwise>
	        <fmt:message key="label.hello"/>, ${currentUser.login}!
   			</c:otherwise>
   		</c:choose>