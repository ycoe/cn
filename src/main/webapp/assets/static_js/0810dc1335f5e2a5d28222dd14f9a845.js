(function () {
    var editor = {
        init: function () {
            this.initForm();
        },

        postForm: function () {
            $('#edit-form').post({
                success: function (json) {
                    console.log(json);
                },
                error: function (info) {
                    console.error(info);
                }
            });
        },

        initForm: function () {
            $('#edit-form').parsley().on('field:validated', function () {
                var ok = $('.parsley-error').length === 0;
                $('.bs-callout-info').toggleClass('hidden', !ok);
                $('.bs-callout-warning').toggleClass('hidden', ok);
            })
            .on('form:submit', function () {
                $('#edit-form').post({
                    success: function (json) {
                        console.info(json);
                        CN.gritterInfo('网站提醒', '保存成功！');
                    },
                    error: function (msg) {
                        console.error(msg);
                    }
                });
                return false; // Don't submit form for this demo
            });
        }
    };

    editor.init();
})();