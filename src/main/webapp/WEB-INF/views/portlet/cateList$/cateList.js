$(function () {
    $('.cateList li').each(function () {
        if($(this).find('li').length > 1) {
            if($(this).find('.active').length > 0) {
            } else {
                $(this).find('>*>.gt').text('+');
                $(this).find('li').hide();
            }
        }
    });

    $('.cateList .gt').click(function () {
        var status = $(this).text().trim();
        if(status == '-') {
            $(this).parent().parent().find('>ul>li').hide();
            $(this).text('+')
        } else {
            $(this).parent().parent().find('>ul>li').show();
            $(this).text('-')
        }
    });

});