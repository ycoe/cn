<div class="wrap_1000" id="header">
    <img src="${assetsUrl}/img/logo.jpg" id="logo-img" alt=""/>
    <div class="logo">
        <strong>${'title'?i18n}</strong>
        ${domain!}
    </div>
    <div class="head-400">
        <i class="icon icon-400"></i>
        ${'phont_400'?i18n}
    </div>
</div>
<div class="full-wrap" id="head-nav">
    <div class="wrap_1000">
        <a ${(pageName=='index')?string('class="active"', '')} href="/">${'nav_index'?i18n}</a>
        <a ${(pageName=='about_us')?string('class="active"', '')} href="/article/about_us.html">${'nav_about_us'?i18n}</a>
        <a ${(pageName=='product')?string('class="active"', '')} href="/product/">${'nav_products'?i18n}</a>
        <a ${(pageName=='article')?string('class="active"', '')} href="/article/">${'nav_news'?i18n}</a>
        <a ${(pageName=='contact')?string('class="active"', '')} href="/article/contact.html">${'nav_contact_us'?i18n}</a>
        <div class="searcher">
            <input type="text" name="keyword" placeholder="${'searchTips'?i18n}" value="${query.keyword!}"/>
            <button id="search-btn">${'search'?i18n}</button>
        </div>
    </div>
</div>
<div id="language">
<#list LANGUAGES as lang>
    <a href="/language/${lang.id}" class="<#if lang.id == CN_LANGUAGE>active</#if>">${lang.name!}</a>
</#list>
</div>
<script>
    var KEYWORD = '${query.keyword!}';
</script>