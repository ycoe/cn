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
            $('#edit-form').parsley({
                listeners: {
                    onFormSubmit: function (isFormValid, event) {
                        if (isFormValid) {
                            return false;
                        }else{
                            editor.postForm();
                            return false;
                        }
                    }
                }
            });

        }
    };

    editor.init();
})();