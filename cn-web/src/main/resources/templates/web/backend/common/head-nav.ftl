<div class="nav-container">
    <button type="button" class="navbar-toggle pull-left sidebar-toggle" id="sidebarToggleLG">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
    </button>
    <ul class="nav-notification">
        <li class="search-list">
            <div class="search-input-wrapper">
                <div class="search-input">
                    <input type="text" class="form-control input-sm inline-block">
                    <a href="#" class="input-icon text-normal"><i class="ion-ios7-search-strong"></i></a>
                </div>
            </div>
        </li>
    </ul>
    <div class="pull-right m-right-sm">
        <div class="user-block hidden-xs">
            <a href="#" id="userToggle" data-toggle="dropdown">
                <img src="${assetsUrl}/img/backend/profile/profile1.jpg" alt="" class="img-circle inline-block user-profile-pic">
                <div class="user-detail inline-block">
                    ${(USER_DATA.username)!''}
                    <i class="fa fa-angle-down"></i>
                </div>
            </a>
            <div class="panel border dropdown-menu user-panel">
                <div class="panel-body paddingTB-sm">
                    <ul>
                        <li>
                            <a href="/manager/account/profile.html">
                                <i class="fa fa-edit fa-lg"></i><span class="m-left-xs">个人信息</span>
                            </a>
                        </li>
                        <li>
                            <a href="/" target="_blank">
                                <i class="fa fa-inbox fa-lg"></i><span class="m-left-xs">网站首页</span>
                                <#--<span class="badge badge-danger bounceIn animation-delay3">2</span>-->
                            </a>
                        </li>
                        <li>
                            <a href="/manager/logout.html">
                                <i class="fa fa-power-off fa-lg"></i><span class="m-left-xs">安全退出</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <ul class="nav-notification">
            <li>
                <a href="#" data-toggle="dropdown"><i class="fa fa-bell fa-lg"></i></a>
                <span class="badge badge-info bounceIn animation-delay6 active">4</span>
                <ul class="dropdown-menu notification dropdown-3 pull-right">
                    <li><a href="#">你有 5 个新提醒</a></li>
                    <li>
                        <a href="#">
												<span class="notification-icon bg-warning">
													<i class="fa fa-warning"></i>
												</span>
                            <span class="m-left-xs">Server #2 not responding.</span>
                            <span class="time text-muted">Just now</span>
                        </a>
                    </li>
                    <li>
                        <a href="#">
												<span class="notification-icon bg-success">
													<i class="fa fa-plus"></i>
												</span>
                            <span class="m-left-xs">New user registration.</span>
                            <span class="time text-muted">2m ago</span>
                        </a>
                    </li>
                    <li>
                        <a href="#">
												<span class="notification-icon bg-danger">
													<i class="fa fa-bolt"></i>
												</span>
                            <span class="m-left-xs">Application error.</span>
                            <span class="time text-muted">5m ago</span>
                        </a>
                    </li>
                    <li>
                        <a href="#">
												<span class="notification-icon bg-success">
													<i class="fa fa-usd"></i>
												</span>
                            <span class="m-left-xs">2 items sold.</span>
                            <span class="time text-muted">1hr ago</span>
                        </a>
                    </li>
                    <li>
                        <a href="#">
												<span class="notification-icon bg-success">
													<i class="fa fa-plus"></i>
												</span>
                            <span class="m-left-xs">New user registration.</span>
                            <span class="time text-muted">1hr ago</span>
                        </a>
                    </li>
                    <li><a href="#">查看所有提醒</a></li>
                </ul>
            </li>
            <#--<li class="chat-notification">-->
                <#--<a href="#" class="sidebarRight-toggle"><i class="fa fa-comments fa-lg"></i></a>-->
                <#--<span class="badge badge-danger bounceIn">1</span>-->

                <#--<div class="chat-alert">-->
                    <#--Hello, Are you there?-->
                <#--</div>-->
            <#--</li>-->
        </ul>
    </div>
</div>