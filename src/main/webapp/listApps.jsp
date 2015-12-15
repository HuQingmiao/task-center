<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>Form Elements - Ace Admin</title>

    <meta name="description" content="Common form elements and layouts"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>

    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="${base}/common/ace/css/bootstrap.css"/>
    <link rel="stylesheet" href="${base}/common/ace/css/font-awesome.css"/>

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="${base}/common/ace/css/jquery-ui.custom.css"/>
    <link rel="stylesheet" href="${base}/common/ace/css/chosen.css"/>
    <link rel="stylesheet" href="${base}/common/ace/css/datepicker.css"/>
    <link rel="stylesheet" href="${base}/common/ace/css/bootstrap-timepicker.css"/>
    <link rel="stylesheet" href="${base}/common/ace/css/daterangepicker.css"/>
    <link rel="stylesheet" href="${base}/common/ace/css/bootstrap-datetimepicker.css"/>
    <link rel="stylesheet" href="${base}/common/ace/css/colorpicker.css"/>

    <!-- text fonts -->
    <link rel="stylesheet" href="${base}/common/ace/css/ace-fonts.css"/>

    <!-- ace styles -->
    <link rel="stylesheet" href="${base}/common/ace/css/ace.css" class="ace-main-stylesheet" id="main-ace-style"/>

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="${base}/common/ace/css/ace-part2.css" class="ace-main-stylesheet"/>
    <![endif]-->

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="${base}/common/ace/css/ace-ie.css"/>
    <![endif]-->

    <!-- inline styles related to this page -->

    <!-- ace settings handler -->
    <script src="${base}/common/ace/js/ace-extra.js"></script>

    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

    <!--[if lte IE 8]>
    <script src="${base}/common/ace/js/html5shiv.js"></script>
    <script src="${base}/common/ace/js/respond.js"></script>
    <![endif]-->

</head>


<body class="no-skin">

<!-- #section:basics/navbar.layout -->
<div id="navbar" class="navbar navbar-default">
    <script type="text/javascript">
        try {
            ace.settings.check('navbar', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="navbar-container" id="navbar-container">

        <!-- /section:basics/sidebar.mobile.toggle -->
        <div class="navbar-header pull-left">
            <!-- #section:basics/navbar.layout.brand -->
            <a href="#" class="navbar-brand">
                <small>
                    <i class="fa fa-leaf"></i>
                    Ace Admin
                </small>
            </a>

        </div>

    </div>
</div>

<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <!-- #section:basics/sidebar -->
    <div id="sidebar" class="sidebar responsive">
        <script type="text/javascript">
            try {
                ace.settings.check('sidebar', 'fixed')
            } catch (e) {
            }
        </script>

        <div class="sidebar-shortcuts" id="sidebar-shortcuts">
            <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                <button class="btn btn-success">
                    <i class="ace-icon fa fa-signal"></i>
                </button>

                <button class="btn btn-info">
                    <i class="ace-icon fa fa-pencil"></i>
                </button>

                <button class="btn btn-warning">
                    <i class="ace-icon fa fa-users"></i>
                </button>

                <button class="btn btn-danger">
                    <i class="ace-icon fa fa-cogs"></i>
                </button>

            </div>

        </div>


        <!-- #section:basics/sidebar.layout.minimize -->
        <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
            <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left"
               data-icon2="ace-icon fa fa-angle-double-right"></i>
        </div>

        <!-- /section:basics/sidebar.layout.minimize -->
        <script type="text/javascript">
            try {
                ace.settings.check('sidebar', 'collapsed')
            } catch (e) {
            }
        </script>
    </div>


    <div class="main-content">
        <div class="main-content-inner">

            <!-- #section:basics/content.breadcrumbs -->
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {
                    }
                </script>

                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="#">Home</a>
                    </li>

                    <li>
                        <a href="#">应用管理</a>
                    </li>
                    <!--
                  <li class="active">Form Elements</li>
                  -->
                </ul>
                <!-- /.breadcrumb -->
            </div>

            <!-- /section:basics/content.breadcrumbs -->
            <div class="page-content">

                <!-- /section:settings.box -->
                <!--
                <div class="page-header">
                    <h1>
                        Form Elements
                        <small>
                            <i class="ace-icon fa fa-angle-double-right"></i>
                            Common form elements and layouts
                        </small>
                    </h1>
                </div>
                -->


                <c:if test='${error != null}'>
                    <div id="errorPanel"
                         style="visibility: visible; color:#ff5a29; font-size: 18px; font-weight: bolder; height: 20px; text-align: center; position:relative; left:-4%; vertical-align: middle">
                            ${error}
                    </div>
                </c:if>
                <c:if test='${success != null}'>
                    <div id="successPanel"
                         style="visibility: visible;color:#0eb839;font-size: 18px;  font-weight: bolder; height: 20px; text-align: center; position:relative; left:-4%; vertical-align: middle">
                            ${success}
                    </div>
                </c:if>


                <div class="widget-box">
                    <div class="widget-header widget-header-small">
                        <h5 class="widget-title lighter">Search Form</h5>
                    </div>

                    <div class="widget-body">
                        <div class="widget-main">
                            <sf:form id="queryForm" action="${base}/appmgr/query" method="get" modelAttribute="form">
                                <sf:input path="id" cssStyle="visibility: hidden"/>
                                <sf:input path="CURR_PAGENUM" cssStyle="visibility: hidden"/>
                                <div class="row" style="width: 100%;">
                                    <div class="input-group" style="width: 100%;">
                                        <label style="width: 12%; text-align: right">编码或名称&nbsp;</label>

                                        <span style="width: 20%; text-align: left">
                                            <sf:input class="search-query" path="criaAppName" size="15" maxlength="15"
                                                      placeholder="输入应用的编码或名称"/>
                                        </span>


                                        <label style="width: 12%; text-align: right">主机IP&nbsp;</label>

                                        <span style="width: 20%; text-align: left">
                                            <sf:input class="search-query" path="criaHostName" size="15" maxlength="15"
                                                      placeholder="输入部署应用的主机名或IP"/>
                                        </span>


                                        <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <button id="queryApps" type="button" class="btn btn-purple btn-sm">
                                                <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                                                Search
                                            </button>
                                        </span>
                                    </div>
                                </div>
                            </sf:form>
                        </div>
                    </div>
                </div>


                <!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="simple-table" class="table table-striped table-bordered table-hover" >
                                    <thead>
                                    <tr>
                                        <!--
                                        <th class="center">
                                            <label class="pos-rel">
                                                <input type="checkbox" class="ace"/>
                                                <span class="lbl"></span>
                                            </label>
                                        </th>
                                        -->
                                        <th class="center">应用编码</th>
                                        <th class="center">应用名称</th>
                                        <th class="center">类型</th>
                                        <th class="center">部署主机</th>
                                        <th class="center">运行指令</th>
                                        <th class="center">时间调度</th>
                                        <th class="center">事件调度</th>
                                        <th class="center">日志</th>
                                        <th class="center">
                                            <button id="toAdd" class="btn btn-sm btn-primary" style="height: 32px;">
                                                <i class="ace-icon fa fa-pencil align-top bigger-125"></i>
                                                <span style="font-size: 16px; font-weight: bolder">新 增</span>
                                            </button>
                                        </th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach var="app" items="${appList}">
                                        <tr>
                                            <!--
                                            <td class="center">
                                                <label class="pos-rel">
                                                    <input type="checkbox" class="ace"/>
                                                    <span class="lbl"></span>
                                                </label>
                                            </td>
                                            -->
                                            <td>${app.appCode}</td>
                                            <td>${app.appName}</td>
                                            <td>${app.type}</td>
                                            <td>${app.hostName}</td>
                                            <td>${app.command}</td>
                                            <td style="text-align: center; white-space: nowrap">
                                                <c:if test="${app.scheduleEnable==1}">
                                                    <span style="color: #0eb839">已启用</span>
                                                </c:if>
                                                <c:if test="${app.scheduleEnable==0}">
                                                    <span style="color: #d96822">已禁用</span>
                                                </c:if>
                                                <c:if test="${app.scheduleEnable==null}">
                                                    <span style="color: #c19023">未设置</span>
                                                </c:if>
                                                <button id="toSetSchedule" onclick="toSetSchedule(${app.id})"
                                                        class="btn btn-xs btn-warning">
                                                    <i class="ace-icon fa fa-clock-o bigger-120"
                                                       title="设置调度时间"></i>
                                                </button>
                                            </td>
                                            <td style="text-align: center; white-space: nowrap">
                                                <c:if test="${app.eventEnable==1}">
                                                    <span style="color: #0eb839">已启用</span>
                                                </c:if>
                                                <c:if test="${app.eventEnable==0}">
                                                    <span style="color: #d96822">已禁用</span>
                                                </c:if>
                                                <c:if test="${app.eventEnable==null}">
                                                    <span style="color: #c19023">未设置</span>
                                                </c:if>
                                                <button id="toSetEvent" onclick="toSetEvent(${app.id})"
                                                        class="btn btn-xs btn-warning">
                                                    <i class="ace-icon fa fa-cog icon-only bigger-120"
                                                       title="设置调度事件"></i>
                                                </button>
                                            </td>
                                            <td style="text-align: center; white-space: nowrap">
                                                <a href="${base}/appmgr/log/${app.id}" target="_blank">
                                                    调度日志
                                                </a>
                                            </td>


                                            <td style="text-align: center; white-space: nowrap">
                                                <button id="callNow" onclick="callNow(${app.id})"
                                                        class="btn btn-xs btn-warning">
                                                    <i class="ace-icon fa fa-flag icon-only bigger-120"
                                                       title="立即调用"></i>
                                                    <span title="立即调用">EXE</span>
                                                </button>

                                                &nbsp;
                                                <button id="toUpdate" onclick="toUpdate(${app.id})"
                                                        class="btn btn-xs btn-info">
                                                    <i class="ace-icon fa fa-pencil bigger-120"
                                                       title="修改"></i>
                                                </button>

                                                <!--
                                                    <i class="ace-icon fa fa-cog icon-only bigger-120"
                                                       title="设置调度时间"></i>
                                                    <i class="ace-icon fa fa-clock-o bigger-120"
                                                       title="设置调度时间"></i>
                                                -->

                                                &nbsp;
                                                <button id="delete" onclick="delApp(${app.id})"
                                                        class="btn btn-xs btn-danger">
                                                    <i class="ace-icon fa fa-trash-o bigger-120"
                                                       title="删除"></i>
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <!-- 分页控件栏 begin -->
                                <div style="text-align: center">
                                    <jsp:include page="/common/pagerPanel.jsp" flush="true"/>
                                </div>
                                <!-- 分页控件栏 end -->
                            </div>


                            <!-- /.span -->
                        </div>
                        <!-- /.col-xs-12 -->
                    </div>
                </div>
                <!-- /Row -->
            </div>
            <!-- /.page-content -->
        </div>
        <!-- /.main-content-inner -->
    </div>
    <!-- /.main-content -->

    <div class="footer">
        <div class="footer-inner">
            <!-- #section:basics/footer -->
            <div class="footer-content">
                <span class="bigger-120">
                    <span class="blue bolder">Ace</span>
                    Application &copy; 2015-2020
                </span>

                &nbsp; &nbsp;
                <span class="action-buttons">
                    <a href="#">
                        <i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
                    </a>

                    <a href="#">
                        <i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
                    </a>

                    <a href="#">
                        <i class="ace-icon fa fa-rss-square orange bigger-150"></i>
                    </a>
                </span>
            </div>

            <!-- /section:basics/footer -->
        </div>
    </div>
</div>
<!-- /.main-container -->


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

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
<script src="${base}/common/ace/js/excanvas.js"></script>
<![endif]-->
<script src="${base}/common/ace/js/jquery-ui.custom.js"></script>
<script src="${base}/common/ace/js/jquery.ui.touch-punch.js"></script>
<script src="${base}/common/ace/js/chosen.jquery.js"></script>
<script src="${base}/common/ace/js/fuelux/fuelux.spinner.js"></script>
<script src="${base}/common/ace/js/date-time/bootstrap-datepicker.js"></script>
<script src="${base}/common/ace/js/date-time/bootstrap-timepicker.js"></script>
<script src="${base}/common/ace/js/date-time/moment.js"></script>
<script src="${base}/common/ace/js/date-time/daterangepicker.js"></script>
<script src="${base}/common/ace/js/date-time/bootstrap-datetimepicker.js"></script>
<script src="${base}/common/ace/js/bootstrap-colorpicker.js"></script>
<script src="${base}/common/ace/js/jquery.knob.js"></script>
<script src="${base}/common/ace/js/jquery.autosize.js"></script>
<script src="${base}/common/ace/js/jquery.inputlimiter.1.3.1.js"></script>
<script src="${base}/common/ace/js/jquery.maskedinput.js"></script>
<script src="${base}/common/ace/js/bootstrap-tag.js"></script>

<!-- ace scripts -->
<script src="${base}/common/ace/js/ace/elements.scroller.js"></script>
<script src="${base}/common/ace/js/ace/elements.colorpicker.js"></script>
<script src="${base}/common/ace/js/ace/elements.fileinput.js"></script>
<script src="${base}/common/ace/js/ace/elements.typeahead.js"></script>
<script src="${base}/common/ace/js/ace/elements.wysiwyg.js"></script>
<script src="${base}/common/ace/js/ace/elements.spinner.js"></script>
<script src="${base}/common/ace/js/ace/elements.treeview.js"></script>
<script src="${base}/common/ace/js/ace/elements.wizard.js"></script>
<script src="${base}/common/ace/js/ace/elements.aside.js"></script>
<script src="${base}/common/ace/js/ace/ace.js"></script>
<script src="${base}/common/ace/js/ace/ace.ajax-content.js"></script>
<script src="${base}/common/ace/js/ace/ace.touch-drag.js"></script>
<script src="${base}/common/ace/js/ace/ace.sidebar.js"></script>
<script src="${base}/common/ace/js/ace/ace.sidebar-scroll-1.js"></script>
<script src="${base}/common/ace/js/ace/ace.submenu-hover.js"></script>
<script src="${base}/common/ace/js/ace/ace.widget-box.js"></script>
<script src="${base}/common/ace/js/ace/ace.settings.js"></script>
<script src="${base}/common/ace/js/ace/ace.settings-rtl.js"></script>
<script src="${base}/common/ace/js/ace/ace.settings-skin.js"></script>
<script src="${base}/common/ace/js/ace/ace.widget-on-reload.js"></script>
<script src="${base}/common/ace/js/ace/ace.searchbox-autocomplete.js"></script>


<script type="text/javascript">


    var maxTime = 5;
    for (var i = 1; i <= maxTime; i++) {
        window.setTimeout("display(" + i + ")", i * 1000);
    }

    function display(time) {
        if (time == maxTime) {
            $('#successPanel').attr("style", "visibility: hidden");
        }
    }


    $("#queryApps").click(function () {
        $('#CURR_PAGENUM').val("1");//重置页码
        $("#queryForm").submit();
    });

    $("#toAdd").click(function () {
        var path = "${base}/appmgr/toAdd";
        $('#queryForm').attr("action", path).submit();
    });

    function toUpdate(id) {
        var path = "${base}/appmgr/toUpdate";
        $("#id").attr("value", id);
        $('#queryForm').attr("action", path).submit();
    }

    function delApp(id) {
        if (confirm("您确定要删除此应用吗？")) {
            var path = "${base}/appmgr/delApp";
            $("#id").attr("value", id);
            $('#queryForm').attr("action", path).submit();
        }
    }

    function toSetSchedule(id) {
        var path = "${base}/appmgr/toSetSchedule";
        $("#id").attr("value", id);
        $('#queryForm').attr("action", path).submit();
    }

    function toSetEvent(id) {
        var path = "${base}/appmgr/toSetEvent";
        $("#id").attr("value", id);
        $('#queryForm').attr("action", path).submit();
    }

    function callNow(id) {
        var path = "${base}/appmgr/callNow";
        $("#id").attr("value", id);
        $('#queryForm').attr("action", path).submit();
    }
</script>

</body>
</html>