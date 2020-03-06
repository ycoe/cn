<@content name="head">
<title>${title!}</title>
<meta name="description" content="${description!}" />
<meta name="keywords" content="${keywords!}" />
</@content>
<@content name="body">
    <div class="row m-bottom-md">
        <div class="col-sm-6 m-bottom-sm">
            <h2 class="no-margin">
            语言包编辑
            </h2>
        </div>
        <div class="col-sm-6 text-right text-left-sm">
            <a class="btn btn-success btn-sm" href="list.html"><i class="fa  fa-bars"></i> 语言包列表</a>
        </div>
    </div>
    <form class="form-horizontal" method="post" id="edit-form" action="/manager/i18n/edit.json">
        <div class="col-md-8">
            <h3 class="header-text m-bottom-md">
                基本信息
                <span class="sub-header">语言包基本信息</span>
            </h3>
            <div class="smart-widget m-b-50">
                <div class="smart-widget-inner">
                    <div class="smart-widget-body">
                        <div class="form-group">
                            <label class="col-lg-2 control-label"><span class="required">*</span> 编码：</label>
                            <div class="col-lg-10">
                                <#if i18N??>
                                    <input type="text" readonly="readonly" class="form-control" data-parsley-required="true" value="${(i18N.id)!''}" name="code" placeholder="请使用字母开头，可使用数字、下划线"/>
                                <#else >
                                    <input type="text" class="form-control" data-parsley-required="true" name="code" placeholder="请使用字母开头，可使用数字、下划线"/>
                                </#if>

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">描述：</label>
                            <div class="col-lg-10">
                                <#if i18N??>
                                    <input type="text" class="form-control" name="desc" value="${(i18N.desc)!''}" placeholder="用一句话描述此记录" />
                                <#else >
                                    <input type="text" class="form-control" name="desc" placeholder="用一句话描述此记录" />
                                </#if>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <h3 class="header-text m-bottom-md">
                值
                <span class="sub-header">各语言值</span>
            </h3>
            <div class="smart-widget">
                <div class="smart-widget-inner">
                    <div class="smart-widget-body">
                    <#list LANGUAGES as language>
                        <div class="form-group">
                            <label class="col-lg-2 control-label"><span class="required">*</span> ${language.name}：</label>
                            <div class="col-lg-10">
                                <#if i18N??>
                                    <input type="text" class="form-control" name="value[${language.id}]" value="${(i18N.values[language.id])!''}"/>
                                <#else>
                                    <input type="text" class="form-control" name="value[${language.id}]" />
                                </#if>
                            </div>
                        </div>
                    </#list>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <h3 class="header-text m-bottom-md">管理</h3>
            <div class="smart-widget">
                <div class="smart-widget-inner">
                    <div class="smart-widget-body">
                        1. 语言包保存后1分钟内生效<br>
                        2. 语言包需要配合前端模板使用，慎重修改！
                        <div class="text-right m-t-20">
                            <button type="submit" class="btn btn-success" id="form-save-btn">保 存</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</@content>
<@parent path="/web/backend/common/html.ftl" />