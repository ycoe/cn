<!DOCTYPE html>
<html lang="zh" xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
        <meta name="renderer" content="webkit">
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <meta name="apple-mobile-web-app-capable" content="yes"/>
        <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
        <meta name="format-detection" content="telephone=no, email=no"/>
        <@js src="${assetsUrl}/js/jquery.min.js" order="0"/>
        <@css src="${assetsUrl}/css/site.css" />
        <@layout name="head" />
        <@css />
    </head>
    <body>
        <#include "/web/common/head.ftl" />
        <@layout name="body" />
        <#include "/web/common/foot.ftl" />
        <@js src="${assetsUrl}/js/common.js"/>
        <@js />
    </body>
</html>
