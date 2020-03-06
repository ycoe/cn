/**
 * 文档：http://fex.baidu.com/webuploader/doc/index.html
 */
var ImageUploader = (function () {
    function ImageUploader(id) {
        this.multiple = true;
        this.id = id;
        this.uploader = $('#' + id);
        var multiple = this.uploader.attr('multiple');
        this.multiple = multiple === '1';
        this.init();
    }
    /**
     * 初始化
     */
    ImageUploader.prototype.init = function () {
        this.initUploadButton();
        //修改
        this.initUploadInput();
    };
    ImageUploader.prototype.initUploadInput = function () {
    };
    ImageUploader.prototype.initUploadButton = function () {
        var uploaderId = this.id;
        var uploader = WebUploader.create({
            server: '/manager/uploader/image.json',
            pick: {
                //{Seletor|dom} 指定选择文件的按钮容器，不指定则不创建按钮。
                id: '#' + uploaderId + '_btn',
                //支持多选
                multiple: this.multiple
            },
            //Drag And Drop拖拽的容器
            dnd: '#' + uploaderId,
            disableGlobalDnd: true,
            resize: false,
            accept: {
                title: '图片上传',
                extensions: 'jpg,jpeg,png',
                mimeTypes: 'image/jpg,image/jpeg,image/png'
            },
            compress: {
                width: 1600,
                height: 1600,
                // 图片质量，只有type为`image/jpeg`的时候才有效。
                quality: 80,
                // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
                allowMagnify: false,
                // 是否允许裁剪。
                crop: false,
                // 是否保留头部meta信息。
                preserveHeaders: true,
                // 如果发现压缩后文件大小比原来还大，则使用原来图片
                // 此属性可能会影响图片自动纠正功能
                noCompressIfLarger: false,
                // 单位字节，如果图片大小小于此值，不会采用压缩。
                compressSize: 0
            },
            auto: true
        });
        uploader.on('fileQueued', function (file) {
            $('#' + uploaderId + '_list').append('<div id="' + file.id + '" class="item">' +
                '<h4 class="info">' + file.name + '</h4>' +
                '<p class="state">等待上传...</p>' +
                '</div>');
        });
        // 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function (file, percentage) {
            var li = $('#' + file.id), $percent = li.find('.progress .progress-bar');
            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<div class="progress progress-striped active">' +
                    '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                    '</div>' +
                    '</div>').appendTo(li).find('.progress-bar');
            }
            li.find('p.state').text('上传中');
            $percent.css('width', percentage * 100 + '%');
        });
        uploader.on("error", function (err) {
            if (err == "Q_TYPE_DENIED") {
                CN.gritterError('上传失败', '您选择的文件格式有误');
            }
        });
        uploader.on('uploadError', function (file) {
            $('#' + file.id).find('p.state').text('上传出错');
        });
        uploader.on('uploadSuccess', function (file, response) {
            $('#' + file.id).find('p.state').text('已上传');
            $("#" + uploaderId).find("input[type=file]").val("");
            var url = response.data.url;
            $('#' + uploaderId + 'PicView').attr('src', url + '?imageMogr2/thumbnail/230x170!');
            $('#' + uploaderId + 'PicInput').val(url);
            CN.gritterInfo('上传成功', '图片上传成功');
        });
        uploader.on('uploadComplete', function (file) {
            $('#' + file.id).find('.progress').fadeOut();
            setTimeout(function () {
                $('#' + file.id).fadeOut();
            }, 1000);
        });
    };
    return ImageUploader;
}());
//# sourceMappingURL=ImageUploader.js.map