<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : 'en'}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="request"/>
<fmt:setBundle basename="i18n.text" />
<style>
  .mdl-layout__content .register-card {
    margin-left: auto;
    margin-right: auto;
    margin-top: 10%;
    width: 400px;
  }
  .register-form{
    text-align: center;
  }

  .register-card > .mdl-card__title {
    background-color: teal;
    color: white;
  }

  #register-card{
    position: absolute;
    left: 40%;
    top: 10%;
  }

  .footer{
    bottom: 0;
    position: relative;
  }


</style>


<html lang="${language}">
<jsp:include page="partials/meta.jsp" />

<body class="mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
  <jsp:include page="partials/header.jsp" />

  <main class="mdl-layout__content" style="position: relative">
    <img src="images/home.png" border="0" width="100%"/>
    <div id="register-card">
      <div class="register-card mdl-card mdl-shadow--16dp">
        <div class="mdl-card__title mdl-card--expand">
          <h2 class="mdl-card__title-text" style="margin-left: auto; margin-right: auto">
            <fmt:message key="register.card_title"/>
          </h2>
        </div>
        <div class="register-form">
          <form action="home" method="post">

            <input type="hidden" name="command" value="register">
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
              <label class="mdl-textfield__label" for="inp-first-name">
                <fmt:message key="form.first_name"/>
              </label>
              <input type="text" class="mdl-textfield__input" id="inp-first-name"
                     name="first_name" pattern="[a-zA-Z\u0400-\u04ff]{2,30}" required>
              <span class="mdl-textfield__error">
                <fmt:message key="form.first_name.error" />
              </span>
            </div>

            <input type="hidden" name="command" value="register">
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
              <label class="mdl-textfield__label" for="inp-second-name">
                <fmt:message key="form.second_name"/>
              </label>
              <input type="text" class="mdl-textfield__input" id="inp-second-name"
                     name="second_name" pattern="[a-zA-Z\u0400-\u04ff]{2,30}" required>
              <span class="mdl-textfield__error">
                <fmt:message key="form.second_name.error" />
              </span>
            </div>

            <input type="hidden" name="command" value="register">
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
              <label class="mdl-textfield__label" for="inp-email">
                <fmt:message key="form.email"/>
              </label>
              <input type="email" class="mdl-textfield__input" id="inp-email" name="email" required>
              <span class="mdl-textfield__error">
                <fmt:message key="form.email.support" />
              </span>
            </div>

            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
              <label class="mdl-textfield__label" for="inp-pass">
                <fmt:message key="form.password"/>
              </label>
              <input type="password" class="mdl-textfield__input" id="inp-pass"
                     name="password" pattern="[a-z0-9_-]{6,18}" required>
               <span class="mdl-textfield__error">
                 <fmt:message key="form.password.error" />
               </span>
            </div>

            <input type="hidden" name="command" value="register">
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
              <label class="mdl-textfield__label" for="inp-address">
                <fmt:message key="form.address"/>
              </label>
              <input type="text" class="mdl-textfield__input" id="inp-address"
                     name="address" pattern="[a-zA-Z\u0400-\u04ff\s,]{2,30}" required>
              <span class="mdl-textfield__error">
                <fmt:message key="form.address.support" />
              </span>
            </div>

            <button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored" style="top: 6px">
              <fmt:message key="register.button"/>
            </button>

          </form>
        </div>
      </div>
    </div>
    <div class="footer">
      <jsp:include page="partials/footer.jsp"/>
    </div>
  </main>

</div>
</body>
</html>
