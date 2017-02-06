<@content name="head">
    <title>${'title'?i18n}</title>
    <meta name="description" content="${description!}" />
    <meta name="keywords" content="${keywords!}" />
</@content>
<@content name="body">
    <@js src="${assetsUrl}/js/unslider.min.js" />
    <div class="full-wrap m-b-30" id="main-banner">
        <div class="unslider-banner">
            <ul>
                <li><a href="#"><img src="${assetsUrl}/img/banner_1.jpeg" title="产品图片1"/></a></li>
                <li><img src="${assetsUrl}/img/banner_2.jpeg"/></li>
            </ul>
        </div>
    </div>
    <div class="wrap_1000 m-b-50" id="about-us">
        <@article$ code="about_us" tpl="article_index"/>
    </div>
    <div class="full-wrap p-t-30" id="product-list">
        <div class="wrap_1000">
            <h3>
                PRODUCTS<br>
                产品展示
            </h3>
            <ul>
                <li>
                    <h6><a href="#">产品名称1</a></h6>
                    <div class="desc">
                        <p>
                            XX的核心客户群，主要集中在已进入中国市场的跨国公司、行业领先的外企、知名的中国大中企业和更多成长迅速的新兴企业。XX尤其擅长为中外企业推荐中
                        </p>
                    </div>
                    <img src="${assetsUrl}/img/product_1.png"/>
                </li>
                <li>
                    <h6><a href="#">产品名称2</a></h6>
                    <div class="desc">
                        <p>
                            XX的核心客户群，主要集中在已进入中国市场的跨国公司、行业领先的外企、知名的中国大中企业和更多成长迅速的新兴企业。XX尤其擅长为中外企业推荐中
                        </p>
                    </div>
                    <img src="${assetsUrl}/img/product_2.png"/>
                </li>
            </ul>
        </div>
    </div>
    <div class="wrap_1000" id="news-list">
        <@articleList$ tpl="articleList-index" size="3" flag="Index" />
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
            </ul>
        </div>
        <img src="${assetsUrl}/img/qr.jpg" />
    </div>
</@content>
<@parent path="/web/common/html.ftl" />