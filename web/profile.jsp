<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/customTags.tld" prefix="ct" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : 'en'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.text"/>
<jsp:useBean id="now" class="java.util.Date"/>
<script language="javascript">
      function showMessage(value){
         document.getElementById("message").innerHTML = value;
      }

</script>

<html lang="${language}">
<jsp:include page="partials/meta.jsp"/>
<body class="mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <jsp:include page="partials/header.jsp"/>


    <main class="mdl-layout__content" style="margin-right: auto; margin-left: auto">
    <c:if test="${fn:length(currentUser.subscriptions) ne 0}">
        <div class="mdl-card" style="width: auto; background-color: whitesmoke; min-height: 73.7%; padding: 10px">
            <div class="mdl-card__title">
                <h2 class="mdl-card__title-text">
                    <fmt:message key="profile.subscriptions"/>
                </h2>
            </div>
            <c:set var="cur" value="0" scope="page"/>
            <c:forEach items="${currentUser.subscriptions}" var="subscription">
                <c:if test="${fn:length(subscription.subsParts) ne 0}">
                    <div style="border-color: teal whitesmoke; border-style: solid;">
                        <div style="margin-bottom: 5px; margin-top: 5px; margin-left: 10px">
                            <fmt:message key="profile.subscription.duration"/>
                            <%--<fmt:formatDate value="${subscription.startDate}" pattern="dd-MM-yyyy"/> -
                            <fmt:formatDate value="${subscription.endDate}" pattern="dd-MM-yyyy"/>--%>
                            <ct:duration format="dd-MM-yyyy"
                                         startDate="${subscription.startDate}" endDate="${subscription.endDate}"/>
                        </div>
                        <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp" style="margin: 5px">
                            <thead>
                            <tr>
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
                            <c:forEach items="${subscription.subsParts}" var="part">
                                <tr>
                                    <td class="mdl-data-table__cell--non-numeric">
                                            ${part.publication.name}
                                    </td>
                                    <td class="mdl-data-table__cell--non-numeric">
                                            ${part.publication.description}
                                    </td>
                                    <td>${part.publication.price} UAH</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <c:if test="${subscription.endDate < now}">
                            <form action="home" method="post">
                                <input type="hidden" name="command" value="extendSubscription">

                                <div class="extend-form" style="text-align: center">
                                    <div style="width: 300px; margin-left: auto; margin-right: auto; margin-bottom: 5px">
                                        <input class="mdl-slider mdl-js-slider" type="range" id="duration"
                                               name="duration"
                                               min="1" max="12" value="6" tabindex="0" step="1"
                                               oninput="calcCost(this.value)" onchange="calcCost(this.value)">
                                    </div>
                                    <script language="javascript">
                                        function calcCost(value){
                                        showMessage(value);
                                        document.getElementById("cost").innerHTML = value * ${subscription.price};
                                        }
                                    </script>
                                    <span><fmt:message key="subscription.duration"/> :</span>
                                    <span id="message">6</span>
                                    <span> <fmt:message key="subscription.duration.month"/></span>

                                    <div>
                                        <span><fmt:message key="subscription.cost"/></span>
                                        <span id="cost">${6 * subscription.price}</span>
                                        <span> UAH</span>
                                    </div>
                                    <div style="margin-top: 5px;">
                                        <button type="submit"
                                                class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored"
                                                name="id" value="${cur}">
                                            <fmt:message key="profile.subscription.extend"/>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </c:if>
                        <c:if test="${subscription.endDate >= now}">

                            <form action="home" method="post">
                                <div class="interrupt-form" style="text-align: center">
                                    <input type="hidden" name="command" value="interruptSubscription">
                                    <button style="margin-top: 5px;" name="id" value="${cur}"
                                            type="submit"
                                            class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
                                        <fmt:message key="profile.subscription.interrupt"/>
                                    </button>
                                </div>
                            </form>

                        </c:if>
                    </div>
                    <c:set var="cur" value="${cur + 1}"/>
                </c:if>
            </c:forEach>

        </div>
        </c:if>
        <c:if test="${fn:length(currentUser.subscriptions) eq 0}">
            <h2 style="margin-left: auto; margin-right: auto"><fmt:message key="profile.subscriptions.empty_list"/></h2>

            <div style="margin-top: 30px">
                <form action="publications.jsp">
                    <button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
                        <fmt:message key="subscription.publications.button"/>
                    </button>
                </form>
            </div>
        </c:if>
    </main>
    <div id="footer">
        <jsp:include page="partials/footer.jsp"/>
    </div>
</div>
</body>
</html>
