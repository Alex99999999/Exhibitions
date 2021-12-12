   <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

   <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />

   <fmt:setLocale value="${language}"/>
   <fmt:setBundle basename="i18n.interface"/>

   <a href="registration.jsp">
       <button type="submit" class="btn btn-outline-secondary btn-md btn-block"><fmt:message key="label.sign.up"/></button>
   </a>