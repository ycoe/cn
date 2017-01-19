jQuery.fn.extend({
    //POST 一个表单
    post: function (options) {
        options = $.extend({
            success: function (data) {
                log(['POST response:', data]);
            },
            error: function (data) {
                log(data);
            }
        }, options);
        return this.each(function () {
            var data = $(this).serializeJSON();
            var url = $(this).prop('action');
            var method = $(this).prop('method');
            if (!method) {
                method = 'POST';
            }
            $.ajax({
                type: method,
                url: url,
                contentType: 'application/json; charset=utf-8',
                data: typeof (data) === 'string' ? data : JSON.stringify(data),
                error: options.error
            }).done(function (data) {
                options.success(data);
            }).fail(function (e) {
                options.error(e);
            });
        });
    },

    autoForm: function (options) {
        return this.each(function () {
            $(this).parsley().on('field:validated', function () {
                var ok = $('.parsley-error').length === 0;
                $('.bs-callout-info').toggleClass('hidden', !ok);
                $('.bs-callout-warning').toggleClass('hidden', ok);
            })
                .on('form:submit', function (event) {
                    $(event.$element).post(options);
                    return false;
                });
        });
    }
});


var CN = {
    gritterError: function(title, message){
        $.gritter.add({
            title: '<i class="fa fa-2x fa-exclamation-triangle f-s-12"></i>' + title,
            text: message
        });
    },
    gritterWarn: function(title, message) {
        $.gritter.add({
            title: '<i class="fa fa-2x fa-exclamation-circle f-s-12"></i>' + title,
            text: message
        });
    },
    gritterInfo: function(title, message){
        $.gritter.add({
            title: '<i class="fa fa-2x fa-check f-s-12"></i>' + title,
            text: message
        });
    }
};



function matchActive(navs, pathname) {
    for (var href in navs) {
        var link = navs[href];
        if (href && href.indexOf(pathname) > -1) {
            link.parents('li').addClass('active');
            link.parents('ul').show();
            return true;
        }
    }
    return false;
};

function initActiveNav() {
    var navs = {};
    $('.sidebar-menu a').each(function(){
        var href = $(this).attr('href');
        if(href === 'javascript:;') {
            //尝试找 url
            href = $(this).attr('url');
        }
        if(href && href !== 'javascript:;') {
            navs[href] = $(this);
            // $(this).click(function(){
                // $('.nav li.active').removeClass('active');
                // $(this).parents('li').addClass('active');
            // });
        }
    });

    var pathname = window.location.pathname;
    if(!matchActive(navs, pathname)) {
        //尝试使用referrer
        pathname = window.top.document.referrer;
        matchActive(navs, pathname);
    }
}

$(function () {
    initActiveNav();
});