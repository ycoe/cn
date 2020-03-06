var page, KEYWORD;
(function () {
    page = {
        init: function () {
            this.initSearcher();
        },

        initSearcher: function () {
            if(KEYWORD !== '') {
                $('.product-name').each(function () {
                    var productName = $(this).text();
                    if(productName.indexOf(KEYWORD) > -1) {
                        productName = productName.replace(KEYWORD, '<span>' + KEYWORD + '</span>');
                        $(this).html(productName);
                    }
                });
            }

            $('#search-btn').click(function () {
                var keyword = $('input[name="keyword"]').val().trim();
                if(keyword == '') {
                    return;
                }
                window.location.href = '/product/?keyword=' + encodeURI(keyword);
            });
        }
    };

    var envTestInterval = setInterval(function () {
        if (typeof ($) !== 'undefined') {
            clearInterval(envTestInterval);
            page.init();
        }
    }, 10);
})();