<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : 'en'}"
       scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.text" />

<style>
  .mdl-mini-footer{
    background-color: teal;
  }
</style>
<footer class="mdl-mini-footer">
  <div class="mdl-mini-footer--bottom-section">
    <button style="margin-left: 10mm" id="demo-menu-top-left"
            class="mdl-button  mdl-js-button mdl-button--raised mdl-button--colored">
      <i class="mdl-button-label">
          <fmt:message key="footer.language_button"/>
      </i>
    </button>
    <ul class="mdl-menu mdl-menu--top-left mdl-js-menu mdl-js-ripple-effect"
        for="demo-menu-top-left">
      <li>
        <form>
          <label class="mdl-menu__item" for="language-1">
            <button type="submit" id="language-1" class="mdl-menu__item"
                   name="language" value="en" ${language == 'en' ? 'selected' : ''}>
                <fmt:message key="footer.language_en"/>
            </button>
          </label>
        </form>
      </li>
      <li>
        <form>
          <label class="mdl-menu__item" for="language-2">
            <button type="submit" id="language-2" class="mdl-menu__item"
                   name="language" value="ua" ${language == 'ua' ? 'selected' : ''}>
                <fmt:message key="footer.language_ua"/>
            </button>
          </label>
        </form>
      </li>
    </ul>
  </div>
</footer>
