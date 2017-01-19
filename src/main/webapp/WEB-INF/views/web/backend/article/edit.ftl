<@content name="head">
<title>${title!}</title>
<meta name="description" content="${description!}" />
<meta name="keywords" content="${keywords!}" />
</@content>
<@content name="body">
    <div class="row m-bottom-md">
        <div class="col-sm-6 m-bottom-sm">
            <h2 class="no-margin">
            文章编辑
            </h2>
        </div>
        <div class="col-sm-6 text-right text-left-sm">
            <a class="btn btn-success btn-sm" href="list.html"><i class="fa  fa-bars"></i> 文章列表</a>
        </div>
    </div>
    <form class="form-horizontal" method="post" id="edit-form" action="edit.json">
        <div class="row">
            <div class="col-md-8">
                <h3 class="header-text">
                    基本信息
                    <span class="sub-header">各语言的标题、概要、内容</span>
                </h3>
                <div class="smart-widget">
                    <div class="smart-widget-inner">
                        <div class="smart-widget-body">
                            <div class="form-group">
                                <label class="col-lg-2 control-label"><span class="required">*</span> 标题：</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control" name="title" data-parsley-required="true" value="${article???string(article.title, '')}" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-2 control-label">概要：</label>
                                <div class="col-lg-10">
                                    <textarea name="summary" class="form-control">${article???string(article.summary!, '')}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <h3 class="header-text">
                    文章属性
                    <span class="sub-header">文章其它属性</span>
                </h3>
                <div class="smart-widget m-b-50">
                    <div class="smart-widget-inner">
                        <div class="smart-widget-body">
                            <div class="form-group">
                                <label class="col-lg-2 control-label">语言：</label>
                                <div class="col-lg-10">
                                    <#if article??>
                                    <#assign defaultLanguage = article.language />
                                    <#else >
                                        <#assign defaultLanguage = LANGUAGE />
                                    </#if>
                                    <select class="form-control" name="language">
                                        <#list LANGUAGES as language>
                                            <option ${(language.id == defaultLanguage)?string('selected="selected"', '')} value="${language.id}">${language.name}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-2 control-label">上级分类：</label>
                                <div class="col-lg-10">
                                    <@categorySelector type="${ARTICLE_TYPE.type}" formName="cateIds[]" defaultText="--暂无分类--" value="${article???string(article.parentId, '')}" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-2 control-label">排序：</label>
                                <div class="col-lg-10">
                                    <input type="text" class="form-control" data-parsley-type="integer" name="sort" placeholder="请使用整数，越小越前，默认为255" value="${article???string(article.sort, '')}"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-lg-2 control-label">标识：</label>
                                <div class="col-lg-10">
                                    <#list ArticleFlagEnums as articleFlag>
                                        <div class="checkbox inline-block m-right-md">
                                            <div class="custom-checkbox">
                                                <input type="checkbox" id="${articleFlag.name()}_checkbox" name="flags[]" value="${articleFlag.name()}" ${(article?? && article.flags?seq_contains(articleFlag.name()))?string('checked="checked"', '')} >
                                                <label for="${articleFlag.name()}_checkbox"></label>
                                            </div>
                                            <div class="inline-block vertical-top">${articleFlag.text}</div>
                                        </div>
                                    </#list>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <@imageUpload formName="coverImage" />

                <div class="smart-widget">
                    <div class="smart-widget-inner">
                        <div class="smart-widget-body">
                            文章保存后立即生效
                            <div class="text-right m-t-20">
                                <input type="hidden" name="id" value="${article???string(article.id, '')}" />
                                <button type="submit" class="btn btn-success" id="form-save-btn">保 存</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="smart-widget m-b-50">
            <div class="smart-widget-inner">
                <div class="smart-widget-title">
                    <h3 class="header-text">
                        内容
                        <span class="sub-header"></span>
                    </h3>
                </div>
                <div class="smart-widget-body">
                    <div class="form-group">
                        <@editor id="content" formName="content">${(article ?? && article.content[LANGUAGE])?string(article.content[LANGUAGE], '')}</@editor>
                    </div>
                </div>
            </div>
        </div>
    </form>
</@content>
<@parent path="/web/backend/common/html.ftl" />