<@content name="body">
<div class="row m-bottom-md">
    <div class="col-sm-6 m-bottom-sm">
        <h2 class="no-margin">
        语言管理
        </h2>
    </div>
</div>

<table class="table table-striped table-hover" id="dataTable">
    <thead>
    <tr>
        <th>Code</th>
        <th>名称</th>
        <th>默认</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#list LANGUAGES as language>
    <tr>
        <td>${language.id}</td>
        <td>${language.name}</td>
        <td><i class="fa ${(language.status==1)?string('fa-check-square-o', 'fa-child')} m-right-xs"></i></td>
        <td><#if language.status != 1><a href="set.html?id=${language.id}" class="label label-success">设为默认</a></#if></td>
    </tr>
    </#list>
    </tbody>
</table>
</@content>
<@parent path="/web/backend/common/html.ftl" />