<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <title>CN后台内容管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Bootstrap core CSS -->
    <link href="${assetsUrl}/js/backend/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <link href="${assetsUrl}/css/backend/font-awesome.min.css" rel="stylesheet">

    <!-- ionicons -->
    <link href="${assetsUrl}/css/backend/ionicons.min.css" rel="stylesheet">

    <!-- Morris -->
    <link href="${assetsUrl}/css/backend/morris.css" rel="stylesheet"/>

    <link href="${assetsUrl}/css/backend/datepicker.css" rel="stylesheet"/>

    <!-- Animate -->
    <link href="${assetsUrl}/css/backend/animate.min.css" rel="stylesheet">

    <!-- Owl Carousel -->
    <link href="${assetsUrl}/css/backend/owl.carousel.min.css" rel="stylesheet">
    <link href="${assetsUrl}/css/backend/owl.theme.default.min.css" rel="stylesheet">

    <link href="${assetsUrl}/js/backend/jquery.gritter/jquery.gritter.css" rel="stylesheet">

    <!-- Simplify -->
    <link href="${assetsUrl}/css/backend/simplify.css" rel="stylesheet">

    <@css />
    <!-- Jquery -->
    <script src="${assetsUrl}/js/backend/jquery-1.11.1.min.js"></script>
</head>

<body class="overflow-hidden">

    <div class="wrapper preload">
    <#include "/web/backend/common/sidebar-right.ftl" />
    <!-- sidebar-right -->
    <header class="top-nav">
        <div class="top-nav-inner">
            <div class="nav-header">
                <button type="button" class="navbar-toggle pull-left sidebar-toggle" id="sidebarToggleSM">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>

                <ul class="nav-notification pull-right">
                    <li>
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-cog fa-lg"></i></a>
                        <span class="badge badge-danger bounceIn">1</span>
                        <ul class="dropdown-menu dropdown-sm pull-right user-dropdown">
                            <li class="user-avatar">
                                <img src="${assetsUrl}/img/backend/profile/profile1.jpg" alt="" class="img-circle">
                                <div class="user-content">
                                    <h5 class="no-m-bottom">${(USER_DATA.username)!''}</h5>
                                    <div class="m-top-xs">
                                        <a href="/manager/account/profile.html" class="m-right-sm">个人信息</a>
                                        <a href="/manager/logout.html">安全退出</a>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <a href="inbox.html">
                                    Inbox
                                    <span class="badge badge-danger bounceIn animation-delay2 pull-right">1</span>
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    Notification
                                    <span class="badge badge-purple bounceIn animation-delay3 pull-right">2</span>
                                </a>
                            </li>
                            <li>
                                <a href="#" class="sidebarRight-toggle">
                                    Message
                                    <span class="badge badge-success bounceIn animation-delay4 pull-right">7</span>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">Setting</a>
                            </li>
                        </ul>
                    </li>
                </ul>

                <a href="/manager/" class="brand">
                    <i class="fa fa-building-o"></i><span class="brand-name">CN-后台内容管理</span>
                </a>
            </div>
        <#include "/web/backend/common/head-nav.ftl" />
        </div><!-- ./top-nav-inner -->
    </header>

    <aside class="sidebar-menu fixed">
        <div class="sidebar-inner scrollable-sidebar">
        <#include "/web/backend/common/left-menu.ftl" />
            <div class="sidebar-fix-bottom clearfix">
                <div class="user-dropdown dropup pull-left">
                    <a href="#" class="dropdwon-toggle font-18" data-toggle="dropdown"><i class="ion-person-add"></i>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="inbox.html">
                                Inbox
                                <span class="badge badge-danger bounceIn animation-delay2 pull-right">1</span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                Notification
                                <span class="badge badge-purple bounceIn animation-delay3 pull-right">2</span>
                            </a>
                        </li>
                        <li>
                            <a href="#" class="sidebarRight-toggle">
                                Message
                                <span class="badge badge-success bounceIn animation-delay4 pull-right">7</span>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">Setting</a>
                        </li>
                    </ul>
                </div>
                <a href="/manager/logout.html" class="pull-right font-18"><i class="ion-log-out"></i></a>
            </div>
        </div><!-- sidebar-inner -->
    </aside>

    <div class="main-container">
        <div class="padding-md">
        <@layout name="body" />
        </div>
    </div>
    <footer class="footer">
				<span class="footer-brand">
					<strong class="text-danger">CN</strong> 后台内容管理
				</span>
        <p class="no-margin">
            &copy; 2016 <strong>duoec.com</strong>. ALL Rights Reserved.
        </p>
    </footer>
    <a href="#" class="scroll-to-top hidden-print"><i class="fa fa-chevron-up fa-lg"></i></a>
</div><!-- /wrapper -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->


    <!-- Bootstrap -->
    <script src="${assetsUrl}/js/backend/bootstrap/js/bootstrap.min.js"></script>

    <!-- Slimscroll -->
    <script src='${assetsUrl}/js/backend/jquery.slimscroll.min.js'></script>

    <!-- Morris -->
    <script src='${assetsUrl}/js/backend/rapheal.min.js'></script>
    <script src='${assetsUrl}/js/backend/morris.min.js'></script>

    <!-- Datepicker -->
    <script src='${assetsUrl}/js/backend/uncompressed/datepicker.js'></script>

    <!-- Sparkline -->
    <script src='${assetsUrl}/js/backend/sparkline.min.js'></script>

    <!-- Skycons -->
    <script src='${assetsUrl}/js/backend/uncompressed/skycons.js'></script>

    <!-- Popup Overlay -->
    <script src='${assetsUrl}/js/backend/jquery.popupoverlay.min.js'></script>

    <!-- Easy Pie Chart -->
    <script src='${assetsUrl}/js/backend/jquery.easypiechart.min.js'></script>

    <!-- Sortable -->
    <script src='${assetsUrl}/js/backend/uncompressed/jquery.sortable.js'></script>

    <!-- Owl Carousel -->
    <script src='${assetsUrl}/js/backend/owl.carousel.min.js'></script>

    <!-- Modernizr -->
    <script src='${assetsUrl}/js/backend/modernizr.min.js'></script>


    <script src='${assetsUrl}/js/backend/parsley.min.js'></script>
    <script src='${assetsUrl}/js/jquery.serialize-object.min.js'></script>
    <script src='${assetsUrl}/js/backend/jquery.gritter/jquery.gritter.min.js'></script>

    <!-- Simplify -->
    <script src="${assetsUrl}/js/backend/simplify/simplify.js"></script>
    <script src="${assetsUrl}/js/backend/simplify/simplify_dashboard.js"></script>

    <script src="${assetsUrl}/js/backend/common.js"></script>

    <script>
        $(function()	{

            $('.sortable-list').sortable();

            $('.todo-checkbox').click(function()	{

                var _activeCheckbox = $(this).find('input[type="checkbox"]');

                if(_activeCheckbox.is(':checked'))	{
                    $(this).parent().addClass('selected');
                }
                else	{
                    $(this).parent().removeClass('selected');
                }

            });

            //Delete Widget Confirmation
            $('#deleteWidgetConfirm').popup({
                vertical: 'top',
                pagecontainer: '.container',
                transition: 'all 0.3s'
            });
        });

    </script>
    <@js />
</body>
</html>
