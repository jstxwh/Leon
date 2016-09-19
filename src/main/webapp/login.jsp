<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<title>登录</title>
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.5/themes/icon.css">
<script type="text/javascript" src="jquery-easyui-1.4.5/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.4.5/plugins/jquery.form.js"></script>
<script type="text/javascript" src="jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
</head>
<body style="width:100%;height:100%;overflow:hidden;border:none;visibility:visible;">
	<!-- easyui-window是EasyUI自带的样式，后面的title,border,resizable等均是该样式定义的属性
		title:标题
		resizable:是否可改变窗口大小			|		shadow:是否显示阴影，默认为true
		draggable:是否可拖拽						|		modal:是否显示为模式化窗口，默认为true
		collapsible:是否可折叠						|		zIndex:窗口z轴坐标，默认9000
		minimizable:是否可以最小化				|		inline:定义如何布局窗口，如为true，窗口将显示在它的父容器
		maxmizable:是否可以最大化			|				如果为false，则显示在所有元素上面。默认为false
		closable:是否显示关闭按钮				|
		closed:是否可以关闭 -->
	<div id="mainwindown" class="easyui-window" style="width:500px;height:300px;top:200px;backgroud:#fafafa;overflow:hidden;font-family:Comic Sans MS;color:#3366FF;"
	title="Login" border="false" resizable="false" draggable="false" collapsible="false" closable="false" shadow="true" modal="true"
	minimizable="false" maximizable="false">
		<div class="header" style="height:35px;">
			<div class="toptitle" style="margin-top:15px;font-size:25px;margin-left:20px;">
				<b><i>Saka's System</i></b>
			</div>
		</div>
		<div style="padding:40px 0;">
			<div id="loginForm">
				<div style="padding-left:150px">
					<table cellpadding="0" cellspacing="3">
						<tr>
							<td>Username: </td>
							<td><input id="username" style="width:114px;"/></td>
						</tr>
						<tr>
							<td>Password: </td>
							<td><input id="password" type="password" style="width:114px;"/></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<!-- easyui-linkbutton是EasyUI提供的按钮的一种样式 -->
								<b><a id="btnLogin" class="easyui-linkbutton" style="margin-right:18px;color:#3333CC;">Login</a>
								<a class="easyui-linkbutton" onclick="clearAll();" style="color:#3333CC;">Reset</a></b>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function clearAll(){
			$('#username').val("");
			$('#password').val("");
		}
		$('#btnLogin').click(function(){
			if($('#username').val()=="abc" && $('#password').val()=="123"){
				window.location.href="main.jsp";
			}else{
				//$.messager是EasyUI提供的，在其alert方法中，要传入以下三种参数
				$.messager.alert("Prompt","<div style='font-family:Comic Sans MS;color:#FF0000;font-size:15px;'><b>Username or Password is not Correct</b></div>","error");
			}
		});
	</script>
</body>
</html>
