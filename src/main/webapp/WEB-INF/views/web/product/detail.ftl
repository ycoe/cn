<#assign pageName="product" />
<@content name="head">
<title>${'title'?i18n}</title>
<meta name="description" content="${description!}" />
<meta name="keywords" content="${keywords!}" />
</@content>
<@content name="body">
<div class="wrap_1000" id="content-body">
    <div class="left_nav">
        <@cateList$ type="product" tpl="cateList" selected="${query???string(query.parentId, '-1')}" />
    </div>
    <div class="main_content">
        <div class="product_info">
            <div class="img">
                <span class="holder"></span>
                <img class="cover" src="${product.coverImage?thumb2(300, 300)}" alt="">
            </div>
            <div class="product_attrs">
                <h1>${product.name}</h1>
                <#if product.summary??>
                <div class="summary">${product.summary}</div>
                </#if>
                <#if product.parentIds??>
                <div class="row">
                    <label>${'cate'?i18n}: </label>
                    <span>
                        <#list product.parentIds as cateId>
                            <a href="/product/list-${cateId}.html">${cateId?string?cate_name("")}</a>
                            <#sep>, </#sep>
                        </#list>
                    </span>
                </div>
                </#if>
                <#if product.spec??>
                <div class="row">
                    <label>${'spec'?i18n}: </label>
                    <span>${product.spec}</span>
                    </div>
                </#if>
                <#if product.num??>
                    <div class="row">
                        <label>${'code'?i18n}: </label>
                        <span>${product.num}</span>
                    </div>
                </#if>
            </div>
        </div>
        <div class="product_detail">${product.content}</div>
    </div>
</div>

</@content>
<@parent path="/web/common/html.ftl" />