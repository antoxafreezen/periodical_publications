<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : 'en'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.text"/>
<style>
    .mdl-tabs__tab-bar {
        margin-top: 10%;
        margin-left: auto;
        margin-right: auto;
    }

    .publication-card {
        margin-left: auto;
        margin-right: auto;
        margin-top: 5%;
    }

    .publication-form {
        text-align: center;
    }

    .publication-card > .mdl-card__title {
        background-color: teal;
        color: white;
    }

    #publication-card {
        margin-right: auto;
        margin-left: auto;
        margin-top: 5%;
    }

    #footer {
        position: absolute;
        width: 100%;
        bottom: 0;
    }

    #all-users > table {
        margin-right: auto;
        margin-left: auto;
        margin-top: 20px;
    }
</style>

<html lang="${language}">
<jsp:include page="partials/meta.jsp"/>

<body class="mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <jsp:include page="partials/header.jsp"/>
    <main class="mdl-layout__content">
        <div class="mdl-tabs mdl-js-tabs mdl-js-ripple-effect">
            <div class="mdl-tabs__tab-bar">
                <a href="#all-users" class="mdl-tabs__tab is-active">
                    <fmt:message key="admin.tab.all_users"/>
                </a>
                <a href="#add_publication" class="mdl-tabs__tab">
                    <fmt:message key="admin.tab.add_publication"/>
                </a>
            </div>

            <div class="mdl-tabs__panel is-active" id="all-users">
                <table class="mdl-data-table mdl-js-data-table">
                    <thead>
                    <tr class="table-head-row">
                        <th class="mdl-data-table__cell--non-numeric">
                            <fmt:message key="admin.table.name"/>
                        </th>
                        <th class="mdl-data-table__cell--non-numeric">
                            <fmt:message key="admin.table.email"/>
                        </th>
                        <th class="mdl-data-table__cell--non-numeric">
                            <fmt:message key="admin.table.pending"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td class="mdl-data-table__cell--non-numeric">
                                    ${user.first_name} ${user.second_name}
                            </td>
                            <td class="mdl-data-table__cell--non-numeric">${user.email}</td>
                            <td>${user.subscriptions.size()}</td>
                        </tr>
                    </c:forEach>

                    <c:if test="${fn:length(users) eq 0}">
                        <tr>
                            <td><fmt:message key="admin.table.all_users.empty"/></td>
                            <td>
                            <td>
                            <td>
                            <td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>

            <div class="mdl-tabs__panel" id="add_publication">
                <div id="publication-card">
                    <div class="publication-card mdl-card mdl-shadow--16dp">
                        <div class="mdl-card__title mdl-card--expand">
                            <h2 class="mdl-card__title-text" style="margin-left: auto; margin-right: auto">
                                <fmt:message key="publication.card_title"/>
                            </h2>
                        </div>
                        <div class="publication-form">
                            <form action="home" method="post">
                                <input type="hidden" name="command" value="addPublication">

                                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                    <label class="mdl-textfield__label" for="inp-name">
                                        <fmt:message key="publication.name"/>
                                    </label>
                                    <input type="text" class="mdl-textfield__input" id="inp-name"
                                           name="name" pattern="[a-zA-Z\u0400-\u04ff\s0-9]{2,45}" required>
                                    <span class="mdl-textfield__error"><fmt:message key="publication.name.error"/></span>
                                </div>
                                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                    <label class="mdl-textfield__label" for="inp-desc">
                                        <fmt:message key="publication.description"/>
                                    </label>
                                    <input type="text" class="mdl-textfield__input" id="inp-desc"
                                           name="name" pattern="[a-zA-Z\u0400-\u04ff\s0-9]{5,100}" required>
                                    <span class="mdl-textfield__error"><fmt:message key="publication.description.error"/></span>
                                </div>
                                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                    <label class="mdl-textfield__label" for="inp-price">
                                        <fmt:message key="publication.price"/>
                                    </label>
                                    <input type="text" class="mdl-textfield__input" id="inp-price"
                                           name="price" pattern="[0-9]*\.?[0-9]{0,2}" required>
                <span class="mdl-textfield__error">
                <fmt:message key="publication.price.error"/>
              </span>
                                </div>
                                <button type="submit"
                                        class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored"
                                        style="top: 6px">
                                    <fmt:message key="publication.button.add"/>
                                </button>
                            </form>
                        </div>
                    </div>
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