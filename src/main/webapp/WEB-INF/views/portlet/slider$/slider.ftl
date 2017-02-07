<@js src="${assetsUrl}/js/unslider.min.js" />
<div class="full-wrap m-b-30" id="main-banner">
    <div class="unslider-banner">
        <ul>
            <#list images as image>
            <li><a href="javascript:;"><img src="${image}"/></a></li>
            </#list>
        </ul>
    </div>
</div>