<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>Login Page - Ace Admin</title>

    <meta name="description" content="User login page"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>

    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="${base}/common/ace/css/bootstrap.css"/>
    <link rel="stylesheet" href="${base}/common/ace/css/font-awesome.css"/>

    <!-- text fonts -->
    <link rel="stylesheet" href="${base}/common/ace/css/ace-fonts.css"/>

    <!-- ace styles -->
    <link rel="stylesheet" href="${base}/common/ace/css/ace.css"/>

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="${base}/common/ace/css/ace-part2.css"/>
    <![endif]-->
    <link rel="stylesheet" href="${base}/common/ace/css/ace-rtl.css"/>

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="${base}/common/ace/css/ace-ie.css"/>
    <![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

    <!--[if lt IE 9]>
    <script src="${base}/common/ace/js/html5shiv.js"></script>
    <script src="${base}/common/ace/js/respond.js"></script>
    <![endif]-->

</head>


<body class="login-layout">
<div class="main-container">
    <div class="main-content">
        <div class="row">
            <div class="col-sm-10 col-sm-offset-1">
                <div class="login-container">
                    <div class="center">
                        <h1>
                            <i class="ace-icon fa fa-leaf green"></i>
                            <span class="red"></span>
                            <span class="white" id="id-text2">Task Center</span>
                        </h1>
                        <h4 class="blue" id="id-company-text">&copy; 招联金融</h4>
                    </div>

                    <!--
                    <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                        <span class="btn btn-success">aaaa</span>

                        <span class="btn btn-info">bbb</span>

                        <span class="btn btn-warning">ccc</span>

                        <span class="btn btn-danger">ddd</span>
                    </div>
                    -->

                    <div class="position-relative">
                        <div id="login-box" class="login-box visible widget-box no-border">

                            <div style="color:#ff5a29; height: 40px; text-align: left; position:relative; top:1%;">
                                ${error}
                            </div>

                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header blue lighter bigger">
                                        <i class="ace-icon fa fa-coffee green"></i>
                                        Please Enter Your Information
                                    </h4>

                                    <!-- 如果没有这行代码，modelAttribute="form"将无法识别；但如果在进入本页面前，在controller中
                                         设置modelAttribute，那也可不会造成识别问题-->
                                    <!--
                                    <jsp:useBean id="form" class="com.mucfc.taskcenter.control.LoginForm" scope="request">
                                    </jsp:useBean>
                                    -->

                                    <sf:form id="userForm" action="${base}/portal/login" method="post" modelAttribute="form">
                                        <fieldset>
                                            <label class="block clearfix">
                                                <span class="block input-icon input-icon-right">
                                                    <sf:input class="form-control" path="username" size="15"
                                                              maxlength="15"/>
                                                    <i class="ace-icon fa fa-user"></i>
                                                </span>
                                            </label>

                                            <label class="block clearfix">
                                                <span class="block input-icon input-icon-right">
                                                    <sf:input class="form-control" path="password" size="15"
                                                              maxlength="15"/>
                                                    <i class="ace-icon fa fa-lock"></i>
                                                </span>
                                            </label>

                                            <div class="space"></div>

                                            <div class="clearfix">
                                                <label class="inline">
                                                    <input type="checkbox" class="ace"/>
                                                    <span class="lbl"> Remember Me</span>
                                                </label>

                                                <button id="login" type="button"
                                                        class="width-35 pull-right btn btn-sm btn-primary">
                                                    <i class="ace-icon fa fa-key"></i>
                                                    <span class="bigger-110">Login</span>
                                                </button>
                                            </div>

                                            <div class="space-4"></div>
                                        </fieldset>
                                    </sf:form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- basic scripts -->
<!--[if !IE]> -->
<script type="text/javascript">
    window.jQuery || document.write("<script src='${base}/common/ace/js/jquery.js'>" + "<" + "/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='${base}/common/ace/js/jquery1x.js'>" + "<" + "/script>");
</script>
<![endif]-->
<script type="text/javascript">
    if ('ontouchstart' in document.documentElement) document.write("<script src='${base}/common/ace/js/jquery.mobile.custom.js'>" + "<" + "/script>");
</script>
<script src="${base}/common/ace/js/bootstrap.js"></script>


<!-- inline scripts related to this page -->
<script type="text/javascript">
    $("#login").click(function () {
        $("#userForm").submit();
    });
</script>

</body>
</html>
