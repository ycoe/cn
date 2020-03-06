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
            <#if list??>
            <ul>
                <#list list as product>
                 <li class="">
                     <a href="/product/${product.id}.html" class="img">
                         <span class="holder"></span>
                         <img src="${product.coverImage?thumb2(150, 150)}" />
                     </a>
                     <a href="/product/${product.id}.html" class="product-name">${product.name}</a>
                 </li>
                </#list>
            </ul>
            </#if>
            <@pager total="${total}" pageNo="${pageNo!1}" pageSize="${pageSize!20}"/>
        </div>
    </div>

</@content>
<@parent path="/web/common/html.ftl" />