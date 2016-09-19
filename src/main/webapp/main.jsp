<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Main</title>
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.5/themes/default/layout.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.5/themes/icon.css">
<script type="text/javascript" src="jquery-easyui-1.4.5/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.4.5/easyloader.js"></script>
<script type="text/javascript" src="jquery-easyui-1.4.5/plugins/jquery.layout.js"></script>
</head>
<body style="border:none;visibility:visible;width:100%;height:100%">
	<!-- 高度不能设置为100%？？？？？ -->
	<div id="cc" class="easyui-layout" style="width:100%;height:900px">
		<!-- 页面顶部top及菜单栏 -->
		<!-- 如果定义了title，则默认会出现一个可折叠图标进行折叠，如果不定义，则没有 -->
		<div data-options="region:'north'" style="height:96px;">
			<div class="header" style="backgroud:#fff url('css/images/banner.jpg') no-repeat center;">
				<div style="text-align:right;padding-right:20px;padding-top:30px;padding-bottom:14px;">
					<span style="color:#FDFDFD;" id="loginuserInfo">Welcome , abc</span>
					<span style="color:#FDFDFD;" id="loginuserArea"> Japan</span>
					<span style="color:#FDFDFD;" id="timeInfo"></span>
					<a href="login.jsp" style="color:#FDFDFD;text-decoration:none;">Logout</a>
				</div>
				<div class="maintitle" style="top:12;font-size:20px;color:#FDFDFD;margin-left:5px;">WEB Manage System</div>
			</div>
			<div id="topmenu" class="topmenu" style="height:34px;backgroud:url('css/images/maintop1.png');color:#fff;font-size:14px;font-weight:bold;margin-top:-10px;">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:addTab('Main','index.jsp')">Main</a>
			</div>
		</div>
		<!-- 页面底部bottom信息 -->
		<div data-options="region:'south'" style="height:30px;">
			<center>Tempotary Message !</center>
		</div>
		<!-- 左侧导航菜单 -->
		<div data-options="region:'west',title:'Navigation'"  style="width:200px;">
			<!-- 用的是EasyUI的Tree插件
			使用在<ul>标签中，节点用<li>标签定义
			也可以定义在空的<ul>标签中，然后用js加载数据，通过使用$('#tt').tree({url:treedata.json}); -->
			<ul id="ttl" class="easyui-tree" data-options="animate:true,dnd:true">
			</ul>
			<ul id="tt"></ul>
		</div>
		<!-- 主显示区选项卡界面 -->
		<div data-options="region:'center'" >
			<!-- 添加了一个EasyUI的tabs样式，参数为title -->
			<div class="easyui-tabs" fit="true" id="tt">
				<div title="Main">
					<iframe width="100%" height="100%" id="iframe" frameborder="0" scrolling="auto" src="index.jsp"></iframe>
				</div>
			</div>
		</div>
		<div id="dd"></div>
	</div>
	<script type="text/javascript">
		/* $('#tt').tree({
			url:'json/tree.json'
		}); */
	</script>
</body>
</html>
