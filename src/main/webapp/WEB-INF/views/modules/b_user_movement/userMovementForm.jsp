<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户推荐动作管理</title>
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
		<li><a href="${ctx}/b_user_movement/userMovement/">用户推荐动作列表</a></li>
		<li class="active"><a href="${ctx}/b_user_movement/userMovement/form?id=${userMovement.id}">用户推荐动作<shiro:hasPermission name="b_user_movement:userMovement:edit">${not empty userMovement.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="b_user_movement:userMovement:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="userMovement" action="${ctx}/b_user_movement/userMovement/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">动作名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">一级分类id：</label>
			<div class="controls">
				<form:input path="catalog1Id" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">一级分类名称：</label>
			<div class="controls">
				<form:input path="catalog1Name" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">二级分类id：</label>
			<div class="controls">
				<form:input path="catalog2Id" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">二级分类名称：</label>
			<div class="controls">
				<form:input path="catalog2Name" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">动作难度：</label>
			<div class="controls">
				<form:input path="difficulty" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否使用器材：</label>
			<div class="controls">
				<form:input path="equipmentType" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">增肌区间动作组数：</label>
			<div class="controls">
				<form:input path="zengjiGroupNum" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">塑型区间动作组数：</label>
			<div class="controls">
				<form:input path="suxingGroupNum" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">减脂区间动作组数：</label>
			<div class="controls">
				<form:input path="jianzhiGroupNum" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">增肌区间动作次数：</label>
			<div class="controls">
				<form:input path="zengjiMovementNum" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">塑型区间动作次数：</label>
			<div class="controls">
				<form:input path="suxingMovementNum" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">减脂区间动作次数：</label>
			<div class="controls">
				<form:input path="jianzhiMovementNum" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">动作图片url：</label>
			<div class="controls">
				<form:input path="imgUrl" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">动作说明：</label>
			<div class="controls">
				<form:input path="introduction" htmlEscape="false" maxlength="256" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束动作要领：</label>
			<div class="controls">
				<form:input path="endMainPoint" htmlEscape="false" maxlength="256" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教练提示：</label>
			<div class="controls">
				<form:input path="coachTip" htmlEscape="false" maxlength="256" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">常见错误：</label>
			<div class="controls">
				<form:input path="commonFault" htmlEscape="false" maxlength="256" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预备：</label>
			<div class="controls">
				<form:input path="prepare" htmlEscape="false" maxlength="256" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执行：</label>
			<div class="controls">
				<form:input path="action" htmlEscape="false" maxlength="256" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">骨盆稳定：</label>
			<div class="controls">
				<form:input path="pelvicStability" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">核心激活：</label>
			<div class="controls">
				<form:input path="coreActivation" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">呼吸重建：</label>
			<div class="controls">
				<form:input path="respiratoryReconstruction" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="b_user_movement:userMovement:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>