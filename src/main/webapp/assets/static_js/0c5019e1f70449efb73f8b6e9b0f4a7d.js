(function () {
    var editor = {
        init: function () {
            this.initForm();
        },

        initForm: function () {
            $('#form-save-btn').click(function () {
                $('#edit-form').form();
                return false;
            });

        }
    };

    editor.init();
})();