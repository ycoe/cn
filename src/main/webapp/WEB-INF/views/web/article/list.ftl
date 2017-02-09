<#assign pageName="product" />
<@content name="head">
    <title>${'title'?i18n}</title>
    <meta name="description" content="${description!}" />
    <meta name="keywords" content="${keywords!}" />
</@content>
<@content name="body">
    <@slider$ tpl="slider" />
    <div class="wrap_1000" id="content-body">
        <div class="left_nav">
            <@cateList$ type="news" tpl="cateList" selected="${query???string(query.parentId, '-1')}" />
        </div>
        <div class="main_content">
            <#if list??>
            <ul>
                <#list list as article>
                 <li>
                     <a href="/article/${article.id}.html"><img src="${article.coverImage?thumb(100, 100)}" /></a>
                     <a href="/article/${article.id}.html">${article.title}</a>
                     <p>${article.summary}</p>
                 </li>
                </#list>
            </ul>
            </#if>
            <@pager total="${total}" pageNo="${pageNo!1}" pageSize="${pageSize!20}"/>
        </div>
    </div>

</@content>
<@parent path="/web/common/html.ftl" />