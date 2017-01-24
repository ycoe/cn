<#macro loopCateList categoryList level>
    <#list categoryList as item>
    <li data-value="${item.id}" ${(selectedMap[item.id?string])???string('class="active"', '')}><a href="javascript:;" style="text-indent: ${20 * level}px">${item.names[LANGUAGE]}</a></li>
        <#if item.children??>
            <@loopCateList categoryList=item.children level=level + 1/>
        </#if>
    </#list>
</#macro>
<div class="btn-group marginTB-xs category-selector ${(multi==1)?string('multi-selector', '')}">
    <button type="button" class="btn btn-default selector-text">
        <#if selectedMap?? && selectedMap?size &gt; 0>
            <#list selectedMap?keys as cateId>
                ${selectedMap[cateId].names[LANGUAGE]}
                <#sep>,
            </#list>
        <#else>
        ${defaultText}
        </#if>
    </button>
    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
        <span class="caret"></span>
        <span class="sr-only">Toggle Dropdown</span>
    </button>
    <ul class="dropdown-menu" role="menu">
        <li data-value="" class="default-item disabled"><a href="javascript:;">${defaultText!}</a></li>
        <@loopCateList categoryList=categoryList level=0/>
    </ul>
    <input type="hidden" name="${formName!}" value="${selectedMap???string(selectedMap?keys?join(','), defaultValue)}">
</div>