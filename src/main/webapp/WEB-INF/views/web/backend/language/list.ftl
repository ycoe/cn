<@content name="head">
<title>${title!}</title>
<meta name="description" content="${description!}" />
<meta name="keywords" content="${keywords!}" />
</@content>
<@content name="body">
<div class="row m-bottom-md">
    <div class="col-sm-6 m-bottom-sm">
        <h2 class="no-margin">
        语言包管理
        </h2>
    </div>
    <div class="col-sm-6 text-right text-left-sm">
        <a class="btn btn-success btn-sm" href="edit.html"><i class="fa fa-plus"></i> 添加</a>
    </div>
</div>

<table class="table table-striped table-hover" id="dataTable">
    <thead>
    <tr>
        <th>Code</th>
        <th>描述</th>
        <#list LANGUAGES as language>
            <th>${language.id?upper_case}值</th>
        </#list>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#list list as item>
    <tr>
        <td>${item.id}</td>
        <td>${item.desc}</td>
        <#list LANGUAGES as language>
            <td>${item.values[language.id]}</td>
        </#list>
        <td><a href="/manager/i18n/edit.html?id=${item.id}" class="label label-success">编辑</a></td>
    </tr>
    </#list>
    </tbody>
</table>
<@pager total="${total}" pageNo="${pageNo!1}" pageSize="${pageSize!20}" tpl="page-backend"/>

</@content>
<@parent path="/web/backend/common/html.ftl" />