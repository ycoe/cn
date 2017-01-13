(function () {
    var editor = {
        init: function () {
            this.initForm();
        },

        initForm: function () {
            $('#edit-form').autoForm({
                success: function (json) {
                    console.info(json);
                    CN.gritterInfo('网站提醒', '保存成功！');
                },
                error: function (msg) {
                    console.error(msg);
                }
            });
        }
    };

    editor.init();
})();$(function () {
    $('.category-selector .dropdown-menu>li').unbind("click").click(function () {
        var value = $(this).attr('data-value');
        var selector = $(this).parents('.category-selector');
        selector.find('.selector-text').text($(this).text());
        selector.find('input').val(value);
    });
});