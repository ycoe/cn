<@js src="/assets/js/backend/webuploader.html5only.min.js"/>
<h3 class="header-text">
    ${title!}
    <#if subTitle??>
        <span class="sub-header">${subTitle}</span>
    </#if>
</h3>
<div class="smart-widget img-uploader">
    <div class="smart-widget-inner">
        <div class="smart-widget-body">
            <img id="${id}PicView" src="${value!}" class="picView m-bottom-md"/>

            <div class="form-group">
                <label class="col-lg-4 control-label" for="${id}PicInput">图片URL <span id="uploadInfo" class="red hidden">(正在上传...)</span></label>
                <div class="col-lg-8">
                    <input type="text" class="form-control" id="${id}PicInput" name="${formName}" value="${value!}" placeholder="图片URL">
                </div>
            </div>

            <div class="text-right m-t-20">
                <a href="javascript:;" class="img-upload-btn" id="${id}">点击上传</a>
            </div>
        </div>
    </div>
</div>