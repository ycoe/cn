<div class="wrap_1000" id="header">
    <img src="${assetsUrl}/img/logo.png" id="logo-img" alt=""/>
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
        <a href="#" class="active">${'nav_index'?i18n}</a>
        <a href="#">${'nav_about_us'?i18n}</a>
        <a href="#">${'nav_products'?i18n}</a>
        <a href="#">${'nav_news'?i18n}</a>
        <a href="#">${'nav_contact_us'?i18n}</a>
    </div>
</div>
<div id="language">
<#list LANGUAGES as lang>
    <a href="/language/${lang.id}" class="<#if lang.id == CN_LANGUAGE>active</#if>">${lang.name!}</a>
</#list>
</div>