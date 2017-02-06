<#if articles>
<h3 class="m-b-30">
    NEWS<br>
    新闻资讯
</h3>
<ul>
    <#list articles as article>
    <li>
        <img src="${article.coverImage!}" alt="${article.title}">
        <h6><a href="/news/${article.id}.html">${article.title}</a></h6>
        <span class="time">${article.updateTime?number_to_date?string("yyyy-MM-dd")}</span>
        <p>${article.summary!}</p>
    </li>
    </#list>
</ul>
</#if>