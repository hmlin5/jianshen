<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>今日实时管理</title>
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
		<li><a href="${ctx}/visualization/visualization/">今日实时列表</a></li>
		<li class="active"><a href="${ctx}/visualization/visualization/form?id=${visualization.id}">今日实时<shiro:hasPermission name="visualization:visualization:edit">${not empty visualization.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="visualization:visualization:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="visualization" action="${ctx}/visualization/visualization/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">健身房数，用来得知是否有添加：</label>
			<div class="controls">
				<form:input path="gymNum" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教练数，新增教练（激活或者未激活的都算&middot;&middot;）：</label>
			<div class="controls">
				<form:input path="coachNum" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教练登录数，当前时间内：</label>
			<div class="controls">
				<form:input path="coachLogin" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">私教会员数，新增的会员：</label>
			<div class="controls">
				<form:input path="privateUser" htmlEscape="false" maxlength="120" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会员激活量，新激活的会员数量：</label>
			<div class="controls">
				<form:input path="userActivation" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">私教会员登录量：</label>
			<div class="controls">
				<form:input path="privateLogin" htmlEscape="false" maxlength="110" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">约单订单数量：</label>
			<div class="controls">
				<form:input path="contractOrder" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">约单成功人数：</label>
			<div class="controls">
				<form:input path="contractSuccess" htmlEscape="false" maxlength="160" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">抢单数量：</label>
			<div class="controls">
				<form:input path="robbingOrder" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">抢单成功数量：</label>
			<div class="controls">
				<form:input path="robbingSuccess" htmlEscape="false" maxlength="160" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">安排订单数：</label>
			<div class="controls">
				<form:input path="arrangeOrder" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">安排订单成功数量：</label>
			<div class="controls">
				<form:input path="arrangeSuccess" htmlEscape="false" maxlength="160" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="visualization:visualization:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>