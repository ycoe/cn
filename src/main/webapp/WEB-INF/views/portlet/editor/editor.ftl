<script>
    window.UMEDITOR_HOME_URL = "${assetsUrl}/js/backend/umeditor/";
</script>
<link href='/assets/js/backend/umeditor/themes/default/css/umeditor.css' type="text/css" rel="stylesheet" />
<script src='/assets/js/backend/umeditor/umeditor.config.js' ></script>
<script src='/assets/js/backend/umeditor/umeditor.min.js' /></script>
<script src="/assets/js/backend/umeditor/lang/zh-cn/zh-cn.js" /></script>

<textarea id="${id}" name="${formName}" style="width:100%;height:300px;">${value!}</textarea>
<script type="text/javascript">
    (function () {
        var editorInterval = setInterval(function () {
            if(typeof(UM) !== 'undefined') {
                var ${id}_editor = UM.getEditor('${id}');
                clearInterval(editorInterval);
            }else{

            }
        }, 200);
    })();
</script>

