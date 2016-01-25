<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : 'en'}"
       scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.text" />

<!--div class="mdl-layout mdl-js-layout mdl-layout--fixed-header"-->
  <header class="mdl-layout__header">
    <div class="mdl-layout__header-row">

      <!-- Title -->
      <a class="mdl-navigation__link" href="../home.jsp"><h1>
        <fmt:message key="main.heading"/>
      </h1></a>

      <!-- Add spacer, to align navigation to the right -->
      <div class="mdl-layout-spacer"></div>

      <!-- Navigation. We hide it in small screens. -->
      <nav class="mdl-navigation mdl-layout--large-screen-only">
        <a class="mdl-navigation__link" href="../home.jsp">
          <fmt:message key="navigation.home"/>
        </a>

        <!-- Navigation. Login or register if user doesn't login. -->
        <c:if test="${currentUser eq null}">
          <a class="mdl-navigation__link" href="../login.jsp">
            <fmt:message key="navigation.login"/>
          </a>
          <a class="mdl-navigation__link" href="../register.jsp">
            <fmt:message key="navigation.register"/>
          </a>
        </c:if>

        <!-- Navigation. Only for administrators. -->
        <c:if test="${currentUser ne null && currentUser.admin}">
          <a class="mdl-navigation__link" href="../admin.jsp"><fmt:message key="navigation.admin" /></a>
        </c:if>

        <!-- Navigation. Link to publications list. -->
        <a class="mdl-navigation__link" href="../publications.jsp"><fmt:message key="navigation.publications" /></a>

        <!-- Navigation. If user logs in show number of publications in subscription. -->
        <c:if test="${currentUser ne null}">
          <c:if test="${currentSubscription ne null}">
            <a class="mdl-navigation__link" href="../subscription.jsp">
                        <span class="mdl-badge" data-badge="${currentSubscription.subsParts.size()}">
                            <fmt:message key="navigation.subscription" /></span>
            </a>
          </c:if>
          <c:if test="${currentSubscription eq null}">
          <a class="mdl-navigation__link" href="../subscription.jsp">
                        <span class="mdl-badge" data-badge="0">
                            <fmt:message key="navigation.subscription" />
                        </span>
          </a>
          </c:if>
          <a class="mdl-navigation__link" href="../profile.jsp"><fmt:message key="navigation.profile" /></a>
          <a class="mdl-navigation__link">
            <form action="/home">
              <input type="hidden" name="command" value="logout">
              <button type="submit" class="mdl-button mdl-js-button"
                      style="color:white; margin-top: 12px">
                <fmt:message key="navigation.logout"/>
              </button>
            </form>

          </a>
        </c:if>
      </nav>
    </div>
  </header>