<@content name="head">
<title>${title!}</title>
<meta name="description" content="${description!}" />
<meta name="keywords" content="${keywords!}" />
</@content>
<@content name="body">
    <div class="row m-bottom-md">
        <div class="col-sm-6 m-bottom-sm">
            <h2 class="no-margin">
            ${CATEGORY_TYPE.typeName}分类编辑
            </h2>
        </div>
        <div class="col-sm-6 text-right text-left-sm">
            <a class="btn btn-success btn-sm" href="list.html"><i class="fa  fa-bars"></i> 分类列表</a>
        </div>
    </div>
    <form class="form-horizontal" method="post" id="edit-form" action="edit.json">
        <div class="col-md-8">
            <h3 class="header-text m-bottom-md">
                分类名称
                <span class="sub-header">各语言的名称</span>
            </h3>
            <div class="smart-widget">
                <div class="smart-widget-inner">
                    <div class="smart-widget-body">
                        <#list LANGUAGES as language>
                            <div class="form-group">
                                <label class="col-lg-2 control-label"><span class="required">*</span> ${language.name}：</label>
                                <div class="col-lg-10">
                                    <#if category??>
                                        <input type="text" class="form-control" data-parsley-required="true" name="names[${language.id}]" value="${(category.names[language.id])!''}" />
                                    <#else >
                                        <input type="text" class="form-control" data-parsley-required="true" name="names[${language.id}]" />
                                    </#if>
                                </div>
                            </div>
                        </#list>
                    </div>
                </div>
            </div>

            <h3 class="header-text m-bottom-md">
                分类属性
                <span class="sub-header">分类其它属性</span>
            </h3>
            <div class="smart-widget m-b-50">
                <div class="smart-widget-inner">
                    <div class="smart-widget-body">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">排序：</label>
                            <div class="col-lg-10">
                                <#if category??>
                                <input type="text" class="form-control" data-parsley-type="integer" name="sort" placeholder="请使用整数，越小越前，默认为255" value="${(category.sort)!''}"/>
                                <#else >
                                <input type="text" class="form-control" data-parsley-type="integer" name="sort" placeholder="请使用整数，越小越前，默认为255" />
                                </#if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">类型：</label>
                            <div class="col-lg-10">
                                <select class="form-control disabled" name="type">
                                    <#list categoryTypeEnums as cateType>
                                    <option class="" value="${cateType.type}" ${(category?? && category.type == cateType.type)?string('selected="selected"', '')}>${cateType.typeName}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">上级分类：</label>
                            <div class="col-lg-10">
                                <@categorySelector type="${CATEGORY_TYPE.type}" formName="parentId" value="${category???string((category.parentId)!'', '')}" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">是否有效：</label>
                            <div class="col-lg-10">
                                <div class="checkbox inline-block">
                                    <div class="custom-checkbox">
                                        <input type="checkbox" id="available_checkbox" value="1" name="status" ${(category?? && category.status == -1)?string('', 'checked="checked"')} >
                                        <label for="available_checkbox"></label>
                                    </div>
                                    <div class="inline-block vertical-top">
                                        （无效状态相当于被标识为已删除）
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">是否可见：</label>
                            <div class="col-lg-10">
                                <div class="checkbox inline-block">
                                    <div class="custom-checkbox">
                                        <input type="checkbox" id="visible_checkbox" value="1" name="visible" ${(category?? && category.visible == -1)?string('', 'checked="checked"')} >
                                        <label for="visible_checkbox"></label>
                                    </div>
                                    <div class="inline-block vertical-top">
                                        （隐藏的分类不会显示）
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <h3 class="header-text m-bottom-md">保存</h3>
            <div class="smart-widget">
                <div class="smart-widget-inner">
                    <div class="smart-widget-body">
                        1. 分类保存后1分钟内生效
                        <div class="text-right m-t-20">
                            <#if category??>
                            <input type="hidden" name="id" value="${(category.id)!''}" />
                            <#else >
                            <input type="hidden" name="id" />
                            </#if>
                            <button type="submit" class="btn btn-success" id="form-save-btn">保 存</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</@content>
<@parent path="/web/backend/common/html.ftl" />