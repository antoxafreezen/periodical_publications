<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : 'en'}"
       scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.text" />
<style>
    #footer{
        bottom: 0;
        position: absolute;
        width: 100%;
    }
</style>
<html lang="${language}">
<jsp:include page="partials/meta.jsp" />

<body class="mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
  <jsp:include page="partials/header.jsp" />

  <main class="mdl-layout__content" >
    <div class="home-headings" style="text-align: center; margin-top: 5%">
      <h1>${message}</h1>
    </div>
      <div id="footer">
          <jsp:include page="partials/footer.jsp"/>
      </div>
  </main>
</div>
</body>
</html>
