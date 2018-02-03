<#assign pageName="index" />
<@content name="head">
    <title>${'title'?i18n}</title>
    <meta name="description" content="${description!}" />
    <meta name="keywords" content="${keywords!}" />
</@content>
<@content name="body">
    <@slider$ tpl="slider" />
<div class="full-wrap p-t-30" id="product-list">
    <div class="wrap_1000">
        <@productList$ tpl="productList-index" flag="Index" size="12" />
    </div>
</div>
<div class="wrap_1000 m-b-50" id="about-us">
        <@article$ code="about_us" tpl="article_index"/>
    </div>

    <div class="full-wrap p-t-20" id="contact-us">
        <h3 class="m-b-30 m-t-20">
            CONTACT US<br>
            联系我们
        </h3>
        <p>
            ${'any_help'?i18n}
        </p>
        <p class="contact-us-400">${'phont_400'?i18n}</p>
        <div class="wrap_1000" id="contact-us-list">
            <ul>
                <li>
                    <img src="${assetsUrl}/img/qq.png" alt="">
                    ${'qq'?i18n}
                </li>
                <li>
                    <img src="${assetsUrl}/img/wechat.png" alt="">
                    ${'wechat'?i18n}
                </li>
                <li>
                    <img src="${assetsUrl}/img/weibo.png" alt="">
                    ${'weibo'?i18n}
                </li>
                <li>
                    <img src="${assetsUrl}/img/weibo.png" alt="">
                ${'weibo'?i18n}
                </li>
            </ul>
        </div>
        <img src="${assetsUrl}/img/qr.jpg" />
    </div>
</@content>
<@parent path="/web/common/html.ftl" />