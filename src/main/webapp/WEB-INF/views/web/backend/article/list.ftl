<@content name="head">
<title>${title!}</title>
<meta name="description" content="${description!}" />
<meta name="keywords" content="${keywords!}" />
</@content>
<@content name="body">
<div class="row m-bottom-md">
    <div class="col-sm-6 m-bottom-sm">
        <h2 class="no-margin">
            文章管理
        </h2>
    </div>
    <div class="col-sm-6 text-right text-left-sm">
        <a class="btn btn-success btn-sm" href="edit.html"><i class="fa fa-plus"></i> 添加文章</a>
    </div>
</div>

<table class="table table-striped table-hover" id="dataTable">
    <thead>
    <tr>
        <th>标题</th>
        <th>发布时间</th>
        <th>状态</th>
        <th>语言</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
        <#list list as item>
        <tr>
            <td>${item.title}</td>
            <td>${item.updateTime?number_to_date}</td>
            <td><i class="fa ${(item.status != -1)?string('fa-check-square-o', 'fa-child')} m-right-xs"></i></td>
            <td>${item.language}</td>
            <td><a href="edit.html?id=${item.id}" class="label label-success">编辑</a></td>
        </tr>
        </#list>
    </tbody>
</table>
<@pager total="${total}" pageNo="${pageNo!1}" pageSize="${pageSize!20}" tpl="page-backend"/>
</@content>
<@parent path="/web/backend/common/html.ftl" />