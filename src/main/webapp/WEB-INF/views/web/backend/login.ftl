<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <title>Simplify Admin</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Bootstrap core CSS -->
    <link href="${assetsUrl}/js/backend/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <link href="${assetsUrl}/css/backend/font-awesome.min.css" rel="stylesheet">

    <!-- ionicons -->
    <link href="${assetsUrl}/css/backend/ionicons.min.css" rel="stylesheet">

    <link href="${assetsUrl}/css/backend/simplify.css" rel="stylesheet">

</head>

<body class="overflow-hidden light-background">
<div class="wrapper no-navigation preload">
    <div class="sign-in-wrapper">
        <div class="sign-in-inner">
            <div class="login-brand text-center">
                <i class="fa fa-building-o m-right-xs"></i> <strong class="text-skin">CN</strong> 后台管理系统
            </div>

            <form method="POST" action="/manager/login.html">
                <div class="form-group m-bottom-md">
                    <input type="text" name="username" class="form-control" placeholder="用户名" value="${loginInfo.username!}">
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" placeholder="密码">
                </div>

                <div class="form-group">
                    <div class="custom-checkbox">
                        <input type="checkbox" id="chkRemember" name="saveMe" value="1" <#if loginInfo?? && loginInfo.saveMe == 1>checked="checked"</#if>>
                        <label for="chkRemember"></label>
                    </div>
                    记住我
                </div>

                <div class="m-top-md p-top-sm text-center">
                    <#if loginInfo??>
                        <input type="hidden" name="returnUrl" value="${loginInfo.returnUrl!'/manager/'}">
                    <#else >
                        <input type="hidden" name="returnUrl" value="${RequestParameters['returnUrl']!'/manager/'}">
                    </#if>
                    <button class="btn btn-success">登 陆</button>
                </div>
            </form>
        </div><!-- ./sign-in-inner -->
    </div><!-- ./sign-in-wrapper -->
</div><!-- /wrapper -->

<a href="" id="scroll-to-top" class="hidden-print"><i class="icon-chevron-up"></i></a>

<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->

<!-- Jquery -->
<script src="${assetsUrl}/js/backend/jquery-1.11.1.min.js"></script>

<!-- Bootstrap -->
<script src="${assetsUrl}/js/backend/bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
