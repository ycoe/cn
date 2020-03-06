<script src="/assets/js/backend/tinymce/tinymce.min.js"></script>
<script src="/assets/js/backend/jquery.form.min.js"></script>

<textarea id="${id}" name="${formName}" style="width:100%;height:300px;">${value!}</textarea>
<script type="text/javascript">
    (function () {
        var editorInterval = setInterval(function () {
            if(typeof(tinymce) !== 'undefined') {
                tinymce.init({
                    selector:'#${id}',
                    language: 'zh_CN',
                    plugins: [
                        'advlist autolink autosave lists link image charmap print preview anchor',
                        'searchreplace visualblocks code fullscreen textcolor colorpicker textpattern code uploadimage',
                        'contextmenu paste'
                    ],
                    toolbar1: "undo redo | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | fontselect fontsizeselect ",
                    toolbar2: "forecolor backcolor | bullist numlist | outdent indent | removeformat | link unlink uploadimage | preview",
                    menubar: false,
                    images_upload_url: '/manager/uploader/image.json',

                    init_instance_callback: function (editor) {
                        editor.on('Change', function () {
                            console.log('onchange');
                            editor.save();
                        });
                    }
                });
                clearInterval(editorInterval);
            }
        }, 200);
    })();
</script>

