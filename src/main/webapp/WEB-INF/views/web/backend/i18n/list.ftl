<@content name="head">
<title>${title!}</title>
<meta name="description" content="${description!}" />
<meta name="keywords" content="${keywords!}" />
</@content>
<@content name="body">
<ul class="breadcrumb">
    <li><span class="primary-font"><i class="icon-home"></i></span><a href="/manager/"> 后台首页</a></li>
    <li>语言包管理</li>
</ul>

<table class="table table-striped table-hover" id="dataTable">
    <thead>
    <tr>
        <th>Code</th>
        <th>Desc</th>
        <th>Lang</th>
        <th>Value</th>
        <th>OPS</th>
    </tr>
    </thead>
    <tbody>
    <#list list as item>
    <tr>
        <td>${item.code}</td>
        <td>${item.desc}</td>
        <td>${item.language}</td>
        <td>${item.value}</td>
        <td><a href="/manager/i18n/edit.html?code=${item.code}" class="label label-success">编辑</a></td>
    </tr>
    </#list>
    </tbody>
</table>
<@pager total="${total}" pageNo="${pageNo!1}" pageSize="${pageSize!20}" tpl="page-backend"/>

</@content>
<@parent path="/web/backend/common/html.ftl" />