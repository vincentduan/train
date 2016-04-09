<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/WEB-INF/pages/includes/head.jsp"%>
<link rel="stylesheet" style="text/css"
	href="<%=basePath%>resources/upload/css/fileinput.css" />
<script type="text/javascript"
	src="<%=basePath%>resources/upload/js/fileinput.js"></script>
<script src="<%=basePath%>resources/upload/js/fileinput_locale_zh.js"
	type="text/javascript"></script>
</head>
<body>
	<div class="container">
	    <h2>列车能耗评估系统</h2>
	    <hr><br>
		<div class="row">
			<div class="col-md-2">
				<ul class="nav nav-pills nav-stacked">
					<li class="active"><a href="<%=basePath%>train/upload">列车能耗评估系统</a></li>
					<li><a href="#">仿真计算数据</a></li>
					<li><a href="#">****系统</a></li>
				</ul>
			</div>
			<div class="col-md-10">
				<div class="page-header">
					<h2>
						<small>请选择excel文件 </small>
					</h2>
				</div>
				<form enctype="multipart/form-data"
					action="<%=basePath%>train/upload" method="post">
					<input id="file-1" type="file" name="file" multiple class="file" data-overwrite-initial="false" data-min-file-count="1" accept=".xlsx"> <br>
					<button type="submit" class="btn btn-primary">上传</button>
					<button type="reset" class="btn btn-default">重置</button>
				</form>
			</div>

		</div>

	</div>
	<script>
		$("#file-1").fileinput({
			showUpload : false,
			allowedFileExtensions : [ 'xlsx' ],
		})
	</script>
</body>
</html>
