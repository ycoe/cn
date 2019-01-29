<#assign pageName="product" />
<@content name="head">
<title>${'title'?i18n}</title>
<meta name="description" content="${description!}" />
<meta name="keywords" content="${keywords!}" />
</@content>
<@content name="body">
<div class="wrap_1000" id="content-body">
    <div class="left_nav">
        <@cateList$ type="product" tpl="cateList" selected="${product.parentIds[0]}" />
    </div>
    <div class="main_content">
        <div class="product_info">
            <div class="img" id="cover-image">
                <span class="holder"></span>
                <img class="cover" src="${product.coverImage?thumb2(300, 300)}" alt="">
            </div>
            <div class="product_attrs">
                <h1>${product.name}</h1>
                <#if product.summary??>
                <div class="summary">${product.summary}</div>
                </#if>
                <#if product.parentIds??>

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
        <div class="product_detail">
            <h5>${'spec'?i18n}</h5>
        ${product.content}
        </div>
    </div>
</div>
<@js src="${assetsUrl}/js/jquery.zoom.min.js"/>
<script>
    var detailPage;
    (function () {
        detailPage = {
            init: function () {
                console.log(2345);
                console.log(typeof(jQuery));
                $('#cover-image').zoom({url: '${product.coverImage}'});
            }
        };

        var pageEnv = setInterval(function () {
            if (typeof(jQuery) !== 'undefined') {
                clearInterval(pageEnv);
                detailPage.init();
            }
        }, 10);
    })();

</script>
</@content>
<@parent path="/web/common/html.ftl" />