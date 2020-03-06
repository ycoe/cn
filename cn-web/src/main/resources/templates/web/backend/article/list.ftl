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
<div class="smart-widget">
    <div class="smart-widget-inner">
        <div class="smart-widget-body">
            <form class="form-inline no-margin" method="get">
                <div class="form-group">
                    <label class="sr-only">文章标题</label>
                    <input type="text" name="keyword" class="form-control" placeholder="文章标题模糊搜索" value="${query.keyword!''}">
                </div><!-- /form-group -->
                <div class="form-group">
                    <label class="sr-only">文章分类</label>
                    <@categorySelector type="${ARTICLE_TYPE.type}" formName="parentId" multi="0" defaultText="--不限分类--" defaultValue="-1" value="${query.parentId!'-1'}" />
                </div><!-- /form-group -->
                <div class="checkbox">
                    <label class="sr-only">语言</label>
                    <select class="form-control" name="lang">
                        <option value="">所有语言</option>
                        <#list LANGUAGES as language>
                            <option ${(language.id==(query.lang)!'')?string('selected="selected"', '')} value="${language.id}">${language.name}</option>
                        </#list>
                    </select>
                </div><!-- /checkbox -->
                <button type="submit" class="btn btn-sm btn-success">搜索</button>
            </form>
        </div>
    </div>
</div>
<table class="table table-striped table-hover" id="dataTable">
    <thead>
    <tr>
        <th>Code</th>
        <th>标题</th>
        <th>分类</th>
        <th>更新时间</th>
        <th>状态</th>
        <th>语言</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
        <#list list as item>
        <tr>
            <td>
                <#if item.code??>
                    <a href="/article/${item.code}.html?language=${item.language}" target="_blank">${item.code}</a>
                </#if>
            </td>
            <td><a href="/article/${item.id}.html?language=${item.language}" target="_blank">${item.title}</a></td>
            <td>
                <#if item.parentIds??>
                    <#list item.parentIds as cateId>
                        <a href="/manager/article/list.html?parentId=${cateId}">${cateId?string?cate_name("")}</a>
                        <#sep>, </#sep>
                    </#list>
                </#if>
            </td>
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