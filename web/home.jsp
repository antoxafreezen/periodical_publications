<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : 'en'}"
       scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.text" />
<style>
    .heading{
        text-align: center;
    }
</style>
<html lang="${language}">
    <jsp:include page="partials/meta.jsp" />

    <body class="mdl-color--white mdl-color-text--grey-700 mdl-base">
    <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
        <jsp:include page="partials/header.jsp" />

        <main class="mdl-layout__content">
            <div class="heading">
                <h1><fmt:message key="home.heading"/> </h1>
            </div>
            <img width="100%" src="images/home.png">
            <jsp:include page="partials/footer.jsp"/>
        </main>
    </div>
    </body>
</html>