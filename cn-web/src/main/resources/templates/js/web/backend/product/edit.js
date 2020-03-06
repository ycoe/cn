(function () {
    var editor = {
        init: function () {
            this.initForm();
        },

        initForm: function () {
            $('#edit-form').autoForm({
                success: function (json) {
                    if(json.code != 200) {
                        CN.gritterError('网站警告', json.msg);
                    }else{
                        CN.gritterInfo('网站提醒', '保存成功！');
                    }
                    console.info(json);
                },
                error: function (msg) {
                    console.error(msg);
                }
            });
        }
    };

    editor.init();
})();