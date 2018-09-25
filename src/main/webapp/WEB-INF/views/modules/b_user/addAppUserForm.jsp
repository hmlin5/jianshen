<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/b_user/appUser/">用户列表</a></li>
		<li class="active"><a href="${ctx}/b_user/appUser/addform?id=${appUser.id}">用户<shiro:hasPermission name="b_user:appUser:edit">${not empty appUser.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="b_user:appUser:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="appUser" action="${ctx}/b_user/appUser/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">用户名：</label>
			<div class="controls">
				<form:input path="loginName" htmlEscape="false" maxlength="64" class="required input-xlarge "/>
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码：</label>
			<div class="controls">
				<form:input path="password" htmlEscape="false" maxlength="128" class="required input-xlarge "/>
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">性别：</label> -->
<!-- 			<div class="controls"> -->
<!-- 				<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/> -->
<!-- 			<span class="help-inline"><font color="red">*</font> </span> -->
<!-- 			</div> -->
<!-- 		</div> -->
		
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
			<label><input name="sex" type="radio" value="1" ${appUser.sex==null ? 'checked' : ''}  ${appUser.sex==1 ? 'checked' : ''}        />男</label>
			<label><input name="sex" type="radio" value="2"   ${appUser.sex==2 ? 'checked' : ''}        />女</label>
			<span class="help-inline"><font color="red">*</font> </span>
			</div>	
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="b_user:appUser:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>