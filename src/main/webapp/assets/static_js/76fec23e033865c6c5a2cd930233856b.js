(function () {
    var editor = {
        init: function () {
            this.initForm();
        },

        initForm: function () {
            $('#edit-form').parsley({
                listeners: {
                    onFormSubmit: function (isFormValid, event) {
                        if (isFormValid) {
                            return false;
                        }
                    }
                }
            });

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