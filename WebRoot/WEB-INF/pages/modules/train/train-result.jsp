<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/WEB-INF/pages/includes/head.jsp"%>
<link rel="stylesheet" style="text/css"
	href="<%=basePath%>resources/css/train-result.css" />
<script src="<%=basePath%>resources/highstock/highcharts.js"></script>
<script src="<%=basePath%>resources/highstock/exporting.js"></script>
<script type="text/javascript">
var data = ${data };
	$(function () {
    $('#chart').highcharts({
   		title: {
            text: '${title }'
        },
        credits: {
          enabled:false //去水印
		},
        yAxis: [
        {
			title:{
			    text :'Speed (km/h)'
			},
			lineWidth : 1
			
        },
        {
			title:{
			       text :'power(Kw)'
			   },
			lineWidth : 1,
			opposite:true
        },
        {
			title:{
			       text :'slope(‰)'
			   },
			lineWidth : 1,
			opposite:true
        },
        {
			title:{
			       text :'force(N)'
			   },
			lineWidth : 1,
			opposite:true
        }
        ],
        xAxis: {
            type: 'datetime',
            dateTimeLabelFormats: {
                day: '%H:%M:%S:%L'
            }
        },
        tooltip:{
        	xDateFormat: '%H:%M:%S.%L',
        	shared: true
        },
		/* plotOptions :{
			series : {
				events : {
					legendItemClick: function(event) {
						alert("点击了："+this.name)
						yAxis.title.text="sdfsd"
					}
				}
			}
		}, */
        /* series: [{
        	name: 'Tokyo',
            data: [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4],
            pointInterval: 200
        },{
        	name: 'New York',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6, 176.0, 135.6],
            pointInterval: 200
        }] */
        series:data
    });
    var data2 = ${data2 };
    $('#chart2').highcharts({
   		title: {
            text: '${title }'
        },
        credits: {
          enabled:false //去水印
		},
        yAxis: [
        {
			title:{
			    text :'Speed (km/h)'
			},
			lineWidth : 1
			
        },
        {
			title:{
			       text :'power(Kw)'
			   },
			lineWidth : 1,
			opposite:true
        },
        {
			title:{
			       text :'slope(‰)'
			   },
			lineWidth : 1,
			opposite:true
        },
        {
			title:{
			       text :'force(N)'
			   },
			lineWidth : 1,
			opposite:true
        }
        ],
        xAxis: {
        },
        tooltip:{
        	shared: true
        },
        series: data2
    });
});
	</script>
</head>
<body data-spy="scroll" data-target="#myScrollspy">
	<div class="container">
		<h2>列车能耗评估系统</h2>
		<hr>
		<br>
		<div class="row">
			<div class="col-md-2">
				<ul class="nav nav-pills nav-stacked">
					<li class="active"><a href="<%=basePath%>train/upload">列车能耗评估系统</a></li>
					<li><a href="#">仿真计算数据</a></li>
					<li><a href="#">****系统</a></li>
				</ul>
			</div>
			<div class="col-md-10">
				<h3 id="section-1">以时间为依据</h3>
				<div id="chart" style="min-width: 700px; height: 400px"></div>
				<hr>
				<h3 id="section-2">以距离为依据</h3>
				<div id="chart2" style="min-width: 700px; height: 400px"></div>
				<hr>
				<h3 id="section-3">统计</h3>
				<p>
					<%-- ${data } --%>
				</p>
				<table class="table table-striped">
					<thead>
						<tr>
							<th>文件</th>
							<th>最大加速度</th>
							<th>最大加速度变化率</th>
							<th>惰行区间</th>
							<th>牵引区间</th>
							<th>制动区间</th>
							<th>停车精度</th>
							<th>EBI速度限制触发次数</th>
							<th>评价指标</th>
						</tr>
					</thead>
					<c:forEach items="${trains}" var="train" varStatus="status">
						<tr>
							<td>${train.file}</td>
							<td>${train.maxAcceleration}(正)<br>${train.minusAcceleration}(负)</td>
							<td>${train.maxAcceleration_rate}(正)<br>${train.minusAcceleration_rate}(负)</td>
						</tr>
					</c:forEach>
				</table>
				<hr>
			</div>
		</div>
	</div>
</body>
</html>
