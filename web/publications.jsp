<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : 'en'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.text"/>

<html lang="${language}">
<jsp:include page="partials/meta.jsp"/>

<body class="mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <jsp:include page="partials/header.jsp"/>

    <main class="mdl-layout__content" style="margin-right: auto; margin-left: auto; padding: 10px">
        <c:if test="${fn:length(publications) ne 0}">
            <div class="mdl-card" style="width: auto; min-height: 73.7%; background-color: whitesmoke">
                <div class="mdl-card__title">
                    <c:if test="${currentUser ne null}">
                    <h2 class="mdl-card__title-text">
                        <fmt:message key="publications.title.login"/>
                    </h2>
                    </c:if>
                    <c:if test="${currentUser eq null}">
                        <h2 class="mdl-card__title-text">
                            <fmt:message key="publications.title.logout"/>
                        </h2>
                    </c:if>
                </div>
                <form action="home" method="post">
                    <input type="hidden" name="command" value="addSubscription">
                    <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp" style="margin: 5px">
                        <thead>
                        <tr>
                            <c:if test="${currentUser ne null}">
                                <th></th>
                            </c:if>
                            <th class="mdl-data-table__cell--non-numeric">
                                <fmt:message key="publication.name"/>
                            </th>
                            <th class="mdl-data-table__cell--non-numeric">
                                <fmt:message key="publication.description"/>
                            </th>
                            <th class="mdl-data-table__cell--non-numeric">
                                <fmt:message key="publication.price"/>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${publications}" var="publication">
                            <tr>
                                <c:if test="${currentUser ne null}">
                                    <td>
                                        <label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect">
                                            <input type="checkbox" name="publicationId" value="${publication.id}"
                                                   class="mdl-checkbox__input">
                                        </label>
                                    </td>
                                </c:if>
                                <td class="mdl-data-table__cell--non-numeric">
                                        ${publication.name}
                                </td>
                                <td class="mdl-data-table__cell--non-numeric">
                                        ${publication.description}
                                </td>
                                <td>${publication.price} UAH</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <table style="margin-left: auto; margin-right: auto; margin-top: 2%">
                        <tr>
                            <td>
                                <c:if test="${currentUser ne null}">
                                    <button type="submit"
                                            class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
                                        <fmt:message key="publications.add_subscription.button"/>
                                    </button>
                                </c:if>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </c:if>
        <c:if test="${fn:length(publications) eq 0}">
            <fmt:message key="publications.empty_list"/>
        </c:if>
    </main>
    <div id="footer">
        <jsp:include page="partials/footer.jsp"/>
    </div>
</div>
</body>
</html>