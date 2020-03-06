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
})();$(function () {
    $('.category-selector .dropdown-menu>li').unbind("click").click(function () {
        var value = $(this).attr('data-value');
        var text = $(this).text();
        var selector = $(this).parents('.category-selector');
        if(selector.hasClass('multi-selector')) {
            //多选
            if(value == '') {
                //选中默认值
                return false;
            }

            if($(this).hasClass('active')) {
                $(this).removeClass('active');
            }else{
                $(this).addClass('active');
            }


            var names = '';
            var ids = '';
            $(selector).find('.dropdown-menu>li.active').each(function(i, item) {
                var text = $(this).text();
                var value = $(this).attr('data-value');
                if(names != '') {
                    names += ', ';
                    ids += ',';
                }
                names += text;
                ids += value;
            });

            if(names == '') {
                var defaultText = $(selector).find('.default-item').text();
                names = defaultText;
            }

            selector.find('.selector-text').text(names);
            selector.find('input').val(ids);
            return false;
        }else{
            //单选
            selector.find('.selector-text').text(text);
            selector.find('input').val(value);
            console.log(value)
        }
    });
});