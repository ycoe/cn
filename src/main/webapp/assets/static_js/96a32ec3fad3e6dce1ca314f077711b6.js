(function () {
    var editor = {
        init: function () {
            this.initForm();
        },

        initForm: function () {
            $('#form-save-btn').click(function () {
                $('#edit-form').post({
                    success: function (json) {
                        console.log(json);
                    },
                    error: function (info) {
                        console.error(info);
                    }
                });
                return false;
            });
        }
    };

    editor.init();
})();