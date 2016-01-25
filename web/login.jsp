<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : 'en'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.text"/>
<style>
    .login-card {
        margin-left: auto;
        margin-right: auto;
        margin-top: 5%;
    }

    .login-form {
        text-align: center;
    }

    .login-card > .mdl-card__title {
        background-color: teal;
        color: white;
    }

    #login-card {
        position: absolute;
        left: 40%;
        top: 10%;
    }

    #footer {
        bottom: 0;
        position: relative;
    }
</style>


<html lang="${language}">
<jsp:include page="partials/meta.jsp"/>

<body class="mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <jsp:include page="partials/header.jsp"/>

    <main class="mdl-layout__content" style="position: relative">
        <img src="images/home.png" border="0" width="100%"/>

        <div id="login-card">
            <div class="login-card mdl-card mdl-shadow--16dp">
                <div class="mdl-card__title mdl-card--expand">
                    <h2 class="mdl-card__title-text" style="margin-left: auto; margin-right: auto">
                        <fmt:message key="login.card_title"/>
                    </h2>
                </div>
                <div class="login-form">
                    <form action="home" method="post">
                        <input type="hidden" name="command" value="login">

                        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                            <label class="mdl-textfield__label" for="inp-email">
                                <fmt:message key="form.email"/>
                            </label>
                            <input type="email" class="mdl-textfield__input" id="inp-email" name="email" required>
                        </div>
                        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                            <label class="mdl-textfield__label" for="inp-pass">
                                <fmt:message key="form.password"/>
                            </label>
                            <input type="password" class="mdl-textfield__input" id="inp-pass" name="password" required>
                        </div>
                        <button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
                            <fmt:message key="login.button"/>
                        </button>
                    </form>
                </div>
            </div>
        </div>
        <div id="footer">
            <jsp:include page="partials/footer.jsp"/>
        </div>
    </main>

</div>
</body>
</html>
