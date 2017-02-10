<#if code??>
    <#assign pageName=code />
<#else>
    <#assign pageName="article" />
</#if>

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
        <h1>${article.title}</h1>
        <div class="news-info">
            ${article.updateTime?number_to_date?string("yyyy年M月d日")}
            <#if article.parentIds??>
                <#list article.parentIds as cateId>
                    <a href="/article/list-${cateId}.html">${cateId?string?cate_name("")}</a>
                    <#sep>, </#sep>
                </#list>
            </#if>
        </div>
        <#if article.summary??>
            <div class="news-summary">${article.summary}</div>
        </#if>
        <div class="news-con">${article.content}</div>
    </div>
</div>

</@content>
<@parent path="/web/common/html.ftl" />