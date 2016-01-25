<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : 'en'}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.text"/>
<script language="javascript">
      function showMessage(value){
         document.getElementById("message").innerHTML = value;
      }

</script>
<script language="javascript">
  function calcCost(value){
    showMessage(value);
    document.getElementById("cost").innerHTML = value * ${currentSubscription.price};
  }

</script>
<style>
    .confirm-form {
        margin-left: auto;
        margin-right: auto;
        text-align: center;
    }

    .mdl-data-table {
        text-align: center;
    }
</style>
<html lang="${language}">
<jsp:include page="partials/meta.jsp"/>
<body class="mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <jsp:include page="partials/header.jsp"/>


    <main class="mdl-layout__content" style="margin-right: auto; margin-left: auto;">
        <c:if test="${fn:length(currentSubscription.subsParts) ne 0}">
            <div class="mdl-card" style="width: auto; min-height: 73.7%; background-color: whitesmoke">
                <div class="mdl-card__title">
                    <h2 class="mdl-card__title-text">
                        <fmt:message key="subscription.title"/>
                    </h2>
                </div>
                <form action="home" method="post">
                    <input type="hidden" name="command" value="deleteSubscriptionPart">
                    <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp"
                           style="margin-left: auto; margin-right: auto; margin-top: 5%">
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
                            <th class="mdl-data-table__cell--non-numeric">
                                <fmt:message key="subscription.action"/>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="cur" value="0" scope="page"/>
                        <c:forEach items="${currentSubscription.subsParts}" var="part">
                            <tr>
                                <td class="mdl-data-table__cell--non-numeric">
                                        ${part.publication.name}
                                </td>
                                <td class="mdl-data-table__cell--non-numeric">
                                        ${part.publication.description}
                                </td>
                                <td>${part.publication.price} UAH</td>
                                <td>
                                    <button type="submit"
                                            class="mdl-button mdl-js-button mdl-button--icon mdl-button--colored"
                                            name="id" value="${cur}">
                                        <i class="material-icons">cancel</i>
                                    </button>
                                </td>
                            </tr>
                            <c:set var="cur" value="${cur + 1}"/>
                        </c:forEach>
                        </tbody>
                    </table>
                </form>
                <form action="home" method="post">
                    <input type="hidden" name="command" value="confirmSubscription">

                    <div class="confirm-form">
                        <div style="width: 300px; margin-left: auto; margin-right: auto; margin-bottom: 5px">
                            <input class="mdl-slider mdl-js-slider" type="range" id="duration" name="duration"
                                   min="1" max="12" value="6" tabindex="0" step="1"
                                   oninput="calcCost(this.value)" onchange="calcCost(this.value)">
                        </div>

                        <span><fmt:message key="subscription.duration"/> :</span>
                        <span id="message">6</span>
                        <span> <fmt:message key="subscription.duration.month"/></span>

                        <div>
                            <span><fmt:message key="subscription.cost"/></span>
                            <span id="cost">${6 * currentSubscription.price}</span>
                            <span> UAH</span>
                        </div>
                        <div style="margin-top: 10px">
                            <button type="submit"
                                    class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
                                <fmt:message key="subscription.confirm.button"/>
                            </button>
                        </div>
                    </div>


                </form>

            </div>
        </c:if>
        <c:if test="${fn:length(currentSubscription.subsParts) eq 0}">
            <h2 style="margin-left: auto; margin-right: auto"><fmt:message key="subscription.empty_list"/></h2>

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