﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String realPath1 = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/"; 
%>
<link rel="stylesheet"  href="<%=realPath1 %>css/foot.css" type="text/css"  />
<div class="footArea">
			<div id="footlink">
				<ul>
					<li style="margin-left:10px;"><a href="<%=realPath1 %>index.jspx">首页</a></li>
					<li style="margin-left:10px;"><a href="<%=realPath1 %>groups.jspx">窝窝</a></li>
					<li style="margin-left:10px;"><a href="<%=realPath1 %>square.jspx">广场</a></li>
				</ul>
			<div id="footright">&copy; 2011-2012&nbsp;&nbsp;ulewo.com 有乐窝 All rights reserved.</div>
<!-- Baidu Button BEGIN -->
<script type="text/javascript" id="bdshare_js" data="type=slide&amp;img=0&amp;pos=right&amp;uid=0" ></script>
<script type="text/javascript" id="bdshell_js"></script>
<script type="text/javascript">
document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000);
</script>
<!-- Baidu Button END -->
			<div style="margin-top:15px;">
			</div>
	</div>
</div>