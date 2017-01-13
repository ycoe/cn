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
        <h3 class="m-b-30">
            ABOUT US<br>
            关于我们
        </h3>
        <div class="about-us-content">
            <p>XX致力于高品质人才寻访服务的持续追求者，公司立足于北，并在成都、深圳、重庆、南京等地均有分支机构及业务合作伙伴。为客户提供专业、细致、深入的服务精神是我们的宗指，并更好的协助客户解决好人的问题。</p>
            <p>XX拥有众多训练有素的全职顾问，其所建立起来的强大专业服务能力，以其灵活性和高效率，为客户提供高端人才招聘解决方案，持续为客户带来超越期望的企业价值。</p>
            <a class="more" href="#">${'more'?i18n} &gt;</a>
        </div>
        <img src="${assetsUrl}/img/about_us.png" alt="关于我们">
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
        <h3 class="m-b-30">
            NEWS<br>
            新闻资讯
        </h3>
        <ul>
            <li>
                <img src="${assetsUrl}/img/news_1.png" alt="">
                <h6><a href="#">中国能建承建电站工程电除尘变压器全部就位</a></h6>
                <span class="time">2015-11-19</span>
                <p>印度XX项目位于海边，且每年的6月至9月为雨季，空气湿度特别大，对电除尘电场整流变压器安装产生一定的影响。中国能建安徽电建一</p>
            </li>
            <li>
                <img src="${assetsUrl}/img/news_2.png" alt="">
                <h6><a href="#">微型化兴起于20世纪80年代末，指的是机电一体化向微型机器和微观领域发展的趋势</a></h6>
                <span class="time">2015-11-18</span>
                <p>微型化兴起于20世纪80年代末，指的是机电一体化向微型机器和微观领域发展的趋势。国外称其为微电子机械系统(MEMS)，泛指几何尺寸不超过25px3的机电一体化产品</p>
            </li>
            <li>
                <img src="${assetsUrl}/img/news_3.png" alt="">
                <h6><a href="#">始了机电一体化技术向智能化方向迈进的新阶段</a></h6>
                <span class="time">2015-11-17</span>
                <p>20世纪90年代后期，开始了机电一体化技术向智能化方向迈进的新阶段，机电一体化进入深入发展时期。一方面，光学、通信技术等进入了机电一体化</p>
            </li>
        </ul>
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