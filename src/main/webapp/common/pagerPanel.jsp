<!--
分页查询控制面板.

Created by HuQingmiao on 2008-03-28
-->
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page session="false" %>

<%
    int currPageNum = Integer.parseInt((String) request.getAttribute("CURR_PAGENUM"));  //当前页码
    int totalPages = Integer.parseInt((String) request.getAttribute("TOTAL_PAGES"));    //总页数
    int naviPageLinks = Integer.parseInt((String) request.getAttribute("NAVI_PAGE_LINKS")); //导航栏的页码索引数

    //计算导航项的范围
    int a = (int) Math.ceil((double) currPageNum / naviPageLinks);

    int startIndex = (a - 1) * naviPageLinks + 1;
    int endIndex = startIndex + naviPageLinks - 1;

    if (endIndex > totalPages) {
        endIndex = totalPages;
    }
%>


<ul class="pagination">
<%
    if (totalPages > 0) {
%>
    <li><a href="#" onclick="indexPage('1')" title="首页"><<</a></li>
    &nbsp;&nbsp;&nbsp;
<%
} else {
%>
    <li><span style='color:#616161' title="首页"><<</span></li>&nbsp;&nbsp;&nbsp;
<%
    }
%>

 <%
    if (currPageNum > 1) {
%>
    <li><a href="#" onclick="indexPage('<%= currPageNum -1%>')" title="上一页"><</a></li>
<%
} else {
%>
    <li><span style='color:#616161' title="上一页"><</span></li>
<%
    }
%>

<%
    for (int i = startIndex; i <= endIndex; i++) {
        if (i != currPageNum) {
%>
    <li><a href="#" onclick="indexPage('<%= i%>')"><%= i%></a></li>
<%
} else {
%>
    <li><span style='color:#616161'><%= i%></span></li>
<%
        }
    }
%>


<%
    if (currPageNum < totalPages) {
%>
    <li><a href="#" onclick="indexPage('<%= currPageNum +1%>')" title="下一页">></a></li>
<%
} else {
%>
    <li><span style='color:#616161' title="下一页">></span></li>
<%
    }
%>


<%
    if (totalPages > 0) {
%>
    &nbsp;&nbsp;&nbsp;<li><a href="#" onclick="indexPage('<%= totalPages %>')" title="末页">>></a></li>
<%
} else {
%>
    &nbsp;&nbsp;&nbsp;<li><span style='color:#616161' title="末页">>></span></li>
<%
    }
%>
</ul>

<span style="vertical-align: middle">
    Page
    <input type="text" id="CURR_PAGE_NUMBER" value="<%= currPageNum %>"
           size="2" maxLength="3" onkeydown="jump(this.value)"
           style="vertical-align: middle; "
           onkeyup="this.value=this.value.replace(/\D/g,'')"
           onafterpaste="this.value=this.value.replace(/\D/g,'')" />
    of <%= totalPages %>

</span>

    <script>
        //分页查询的页面定位方法
        function indexPage(pageNum) {
            if (parseInt(pageNum) <= 0) {
                alert("您输入的页码不正确, 请输入大于0的数字!");
                return;
            }

            $('#CURR_PAGENUM').val(pageNum);
            $("Form")[0].submit();
        }

        function jump(pageNum)
        {
            if (event.keyCode == 13)
            {
                event.returnValue=false;
                event.cancel = true;
                indexPage(pageNum);
            }
        }
    </script>