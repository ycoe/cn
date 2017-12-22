<#assign pageName="article" />
<@content name="head">
    <title>${'title'?i18n}</title>
    <meta name="description" content="${description!}" />
    <meta name="keywords" content="${keywords!}" />
</@content>
<@content name="body">
    <div class="wrap_1000" id="content-body">
        <div class="left_nav">
            <@cateList$ type="news" tpl="cateList" selected="${query???string(query.parentId, '-1')}" />
        </div>
        <div class="main_content">
            <#if list??>
            <ul id="article-ls">
                <#list list as article>
                 <li class="item ${article.coverImage???string('item-img', '')}">
                     <#if article.coverImage??>
                     <a href="/article/${article.id}.html" class="img-link"><img src="${article.coverImage?thumb(100, 100)}" /></a>
                     </#if>
                     <div class="news-cont">
                         <h3><a href="/article/${article.id}.html">${article.title}</a></h3>
                         <div class="summary">${article.summary}</div>
                     </div>
                 </li>
                </#list>
            </ul>
            </#if>
            <@pager total="${total}" pageNo="${pageNo!1}" pageSize="${pageSize!20}"/>
        </div>
    </div>

</@content>
<@parent path="/web/common/html.ftl" />