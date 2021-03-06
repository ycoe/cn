<#switch type?upper_case>
    <#case 'NEWS'>
        <#assign url='/article' />
        <#break>
    <#case 'PRODUCT'>
        <#assign url='/product' />
        <#break>
</#switch>

<#macro loopCateList categoryList level parentId selected>
<ul parent-id="${parentId}">
    <#list categoryList as item>
        <li style="text-indent: ${26 * level}px" class="<#if !item?has_next>last-item</#if>" cate-id="cate-${item.id}">
            <h${level+3} ${(item.id == selected)?string('class="active"', '')}><#if item.children??><span class="gt"> - </span></#if><a href="${url!}/list-${item.id}.html">${item.names[LANGUAGE]}</a></h${level+3}>
            <#if item.children??>
                <@loopCateList categoryList=item.children level=level + 1 parentId=item.id selected=selected/>
            </#if>
        </li>
    </#list>
</ul>
</#macro>
<div class="box cateList" id="${id}">
    <h2><a href="${url!}/">${'cate_list'?i18n}</a></h2>
    <@loopCateList categoryList=roots level=0 parentId=0 selected=selected/>
</div>