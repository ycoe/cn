<@content name="head">
<title>${title!}</title>
<meta name="description" content="${description!}" />
<meta name="keywords" content="${keywords!}" />
</@content>
<@content name="body">
<div class="row m-bottom-md">
    <div class="col-sm-6 m-bottom-sm">
        <h2 class="no-margin">
            产品管理
        </h2>
    </div>
    <div class="col-sm-6 text-right text-left-sm">
        <a class="btn btn-success btn-sm" href="edit.html"><i class="fa fa-plus"></i> 添加产品</a>
    </div>
</div>
<div class="smart-widget">
    <div class="smart-widget-inner">
        <div class="smart-widget-body">
            <form class="form-inline no-margin" method="get">
                <div class="form-group">
                    <label class="sr-only">产品名称</label>
                    <input type="text" name="keyword" class="form-control" placeholder="产品名称模糊搜索" value="${query.keyword!''}">
                </div><!-- /form-group -->
                <div class="form-group">
                    <label class="sr-only">产品分类</label>
                    <@categorySelector type="${PRODUCT_TYPE.type}" formName="parentId" multi="0" defaultText="--不限分类--" defaultValue="-1" value="${query.parentId!'-1'}" />
                </div><!-- /form-group -->
                <div class="form-group">
                    <label class="sr-only">标识</label>
                    <select name="flag" class="form-control">
                        <option value="">-- 不限制标识 --</option>
                        <option value="Index" ${(query.flag?? && query.flag == 'Index') ? string('selected= "selected"', '')}>首页显示</option>

                    </select>
                </div>
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
        <th>编码</th>
        <th>名称</th>
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
                <#if item.num??>
                    <a href="/product/${(item.code)!''}.html?language=${item.language}" target="_blank">${item.num}</a>
                </#if>
            </td>
            <td>
                <a href="/product/${item.id}.html?language=${item.language}" target="_blank">${item.name}</a>
            </td>
            <td>
                <#if item.parentIds??>
                <#list item.parentIds as cateId>
                    <a href="/manager/product/list.html?parentId=${cateId}">${cateId?string?cate_name("")}</a>
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