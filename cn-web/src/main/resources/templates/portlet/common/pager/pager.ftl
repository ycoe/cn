<#if totalPage &gt; 1>
<div class="page">
    <#if hasPre>
        <a href="${urls[(pageNo - 1)?string]}" class="pre-page"> ${'previous_page'?i18n} </a>
    </#if>
    <#if showFirst>
        <a href="${urls['1']}"> 1 </a>
    </#if>

    <#if showLeftMort>
        <span>...</span>
    </#if>
    <#list showPages as page>
        <a href="${urls[page?string]}" <#if page == pageNo>class="active"</#if>>${page}</a>
    </#list>

    <#if showRightMort>
        <span>...</span>
    </#if>
    <#if showLast>
        <a href="${urls[totalPage?string]}"> ${totalPage} </a>
    </#if>
    <#if hasNext>
        <a href="${urls[(pageNo + 1)?string]}" class="next-page"> ${'next_page'?i18n} </a>
    </#if>
</div>
</#if>