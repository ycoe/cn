<#macro loopCateList categoryList level>
    <#list categoryList as item>
    <li data-value="${item.id}"><a href="javascript:;" style="text-indent: ${20 * level}px">${item.names[LANGUAGE]}</a></li>
        <#if item.children??>
            <@loopCateList categoryList=item.children level=level + 1/>
        </#if>
    </#list>
</#macro>
<div class="btn-group marginTB-xs category-selector">
    <button type="button" class="btn btn-default selector-text">${selected???string(selected.names[LANGUAGE], defaultText)}</button>
    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
        <span class="caret"></span>
        <span class="sr-only">Toggle Dropdown</span>
    </button>
    <ul class="dropdown-menu" role="menu">
        <li data-value=""><a href="javascript:;">--顶级分类--</a></li>
        <@loopCateList categoryList=categoryList level=0/>
    </ul>
    <input type="hidden" name="${formName!}" value="${selected???string(selected.id, defaultValue)}">
</div>