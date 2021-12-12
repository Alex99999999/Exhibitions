<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.interface"/>

<form action="admin" method="get">
     <input type="hidden" name="page" value="1">
     <input type="hidden" name="pageSize" value="5">
     <input type="hidden" name="command" value="get_exhibitions_list">
     <button type="submit" class="btn btn-outline-secondary btn-md btn-block"><fmt:message key="label.exhibitions"/></button>
</form>