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
                            console.log('isFormValid = true;');
                            return false;
                        }else{
                            console.info("post form");
                            return false;
                        }
                        return false;
                    }
                }
            });

        }
    };

    editor.init();
})();