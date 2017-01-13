<@content name="head">
<title>${title!}</title>
<meta name="description" content="${description!}" />
<meta name="keywords" content="${keywords!}" />
</@content>
<@content name="body">
<div class="row m-bottom-md">
    <div class="col-sm-6 m-bottom-sm">
        <h2 class="no-margin">
            ${CATEGORY_TYPE.typeName}分类管理
        </h2>
    </div>
    <div class="col-sm-6 text-right text-left-sm">
        <a class="btn btn-success btn-sm" href="edit.html"><i class="fa fa-plus"></i> 添加分类</a>
    </div>
</div>

<#macro loopCateList categoryList level>
    <#list categoryList as item>
    <tr>
        <#list LANGUAGES as language>
            <td style="text-indent: ${26 * level}px">${item.names[language.id]}</td>
        </#list>
        <td><i class="fa ${(item.status==1)?string('fa-check-square-o', 'fa-child')} m-right-xs"></i></td>
        <td><i class="fa ${(item.visible==1)?string('fa-check-square-o', 'fa-child')} m-right-xs"></i></td>
        <td>${item.sort}</td>
        <td><a href="edit.html?id=${item.id}" class="label label-success">编辑</a></td>
    </tr>
    <#if item.children??>
        <@loopCateList categoryList=item.children level=level + 1 />
    </#if>
    </#list>
</#macro>

<table class="table table-striped table-hover" id="dataTable">
    <thead>
    <tr>
        <#list LANGUAGES as language>
            <th>${language.id?upper_case}名称</th>
        </#list>
        <th>有效</th>
        <th>可视</th>
        <th>排序</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <@loopCateList categoryList=list level=0/>
    </tbody>
</table>
</@content>
<@parent path="/web/backend/common/html.ftl" />