<%@ page language="java" import="java.util.*"%>
<%@ page language="java" contentType="text/html;"
	import="javax.servlet.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>列车能耗评估系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<%@include file="/WEB-INF/pages/includes/head.jsp" %>
</head>
<body>
<!-- header start -->
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="<%=basePath%>">列车能耗评估系统</a>
    </div>
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#">你好</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">用户 <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">个人信息</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">退出</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>
<!-- header end -->
<!-- body start -->
<div class="container-fluid">
	<div id="content" class="row-fluid">
		<div id="left" style="float: left; width: 200px;">
			<iframe id="menuFrame" name="menuFrame" src="<%=basePath%>index/menu" style="overflow: visible; height: 495px;" scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
		</div>
		<div id="openClose" class="close" style="float: left; height: 490px;">&nbsp;</div>
		<div id="right" style="float: left; width: 1106px;">
			<iframe id="mainFrame" name="mainFrame" src="<%=basePath%>index/help" style="overflow: visible; height: 495px;" scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
		</div>
	</div>
</div>
<div id="footer" >
	<nav class="navbar navbar-default">
	    <div class="navbar-inner navbar-content-center" style="text-align: center;">
	        <p class="text-muted credit" style="padding: 5px">
	           	 列车能耗评估系统<a href="#">@copyright</a>
	        </p>
	    </div>
	</nav>
</div>
<!-- body end -->
</body>
</html>
