$(function () {
    $('.category-selector .dropdown-menu>li').unbind("click").click(function () {
        var value = $(this).attr('data-value');
        var selector = $(this).parents('.category-selector');
        selector.find('.selector-text').text($(this).text());
        selector.find('input').val(value);
    });
});