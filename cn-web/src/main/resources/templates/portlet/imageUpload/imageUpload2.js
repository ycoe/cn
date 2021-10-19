(function(){
    var edit = {
        renderImageUrlInput: function (){
            var ID = '';
            $('#' + ID + 'PicInput').change(function(){
                var url = $(this).val();
                if(url.length == 0) {
                    return;
                }
                $('#uploadInfo').removeClass("hidden");

                CN.post("/manager/uploader/import.json", url, function(data) {
                    log(data);
                    CN.gritterInfo('上传成功', '图片上传成功');
                    $('#' + ID + 'PicView').attr('src', data.data + '?imageMogr2/thumbnail/230x170!');
                    $('#uploadInfo').addClass("hidden");
                }, function(){
                    CN.gritterError('上传失败', '图片上传失败!');
                    $('#uploadInfo').addClass("hidden");
                });
            });
        },
        renderFileUploader: function () {
            $('.img-uploader').each(function(){
                var id = $(this).find('.img-upload-btn').attr('id');
                var uploader = WebUploader.create({
                    server: 'https://upload-z2.qiniup.com',
                    pick: '#' + id,
                    resize: false,
                    accept: {
                        title: '图片上传',
                        extensions: 'jpg,jpeg,png',
                        mimeTypes: 'image/jpg,image/jpeg,image/png'
                    },
                    auto: true
                });
                uploader.on("error", function (err) {
                    if (err == "Q_TYPE_DENIED") {
                        CN.gritterError('上传失败', '您选择的文件格式有误');
                    }
                })
                uploader.on('uploadSuccess', function (file, response) {
                    $("#" + id).find("input[type=file]").val("");
                    var url = response.data.url;
                    $('#' + id + 'PicView').attr('src', url + '?imageMogr2/thumbnail/230x170!');
                    $('#' + id + 'PicInput').val(url);

                    CN.gritterInfo('上传成功', '图片上传成功');
                });
            });

        },
        init: function (id) {
            var uploaderInterval = setInterval(function () {
                if(typeof(WebUploader) !== 'undefined') {
                    edit.renderFileUploader();      //初始化上传控件
                    clearInterval(uploaderInterval);
                }
            }, 100);

            edit.renderImageUrlInput(); 	//初始化图片URL表单
        }
    };
})();