<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户Inbody数据管理</title>
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
		<li><a href="${ctx}/b_user_inbody/userInbody/listIframe?userId=${userInbody.userId}">教练评估报告列表</a></li>
		<li class="active"><a href="${ctx}/b_user_inbody/userInbody/form?id=${userInbody.id}&userId=${userInbody.userId}">教练评估报告<shiro:hasPermission name="b_user_inbody:userInbody:edit">${not empty userInbody.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="b_user_inbody:userInbody:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="userInbody" action="${ctx}/b_user_inbody/userInbody/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="userId"/>
		<sys:message content="${message}"/>		
		
		<div class="control-group">
			<label class="control-label">评估时间：</label>
			<div class="controls">
				<input name="testTime" type="text"  maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${userInbody.testTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身高：</label>
			<div class="controls">
				<form:input path="height" htmlEscape="false" class="input-xlarge required"/>
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">体重：</label>
			<div class="controls">
				<form:input path="weight" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">BMI指数：</label>
			<div class="controls">
				<form:input path="bmiIndex" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">骨骼肌：</label>
			<div class="controls">
				<form:input path="skeletalMuscle" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">体脂率：</label>
			<div class="controls">
				<form:input path="bodyFatRate" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">体脂肪：</label>
			<div class="controls">
				<form:input path="bodyFat" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">腰围：</label>
			<div class="controls">
				<form:input path="waistline" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">臀围：</label>
			<div class="controls">
				<form:input path="hipline" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">腰臀比：</label>
			<div class="controls">
				<form:input path="whr" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">基础代谢：</label>
			<div class="controls">
				<form:input path="basalMetabolism" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">肌肉控制数：</label>
			<div class="controls">
				<form:input path="muscleControl" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">脂肪控制数：</label>
			<div class="controls">
				<form:input path="fatControl" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内脏脂肪等级：</label>
			<div class="controls">
				<form:input path="visceralAdiposeGrade" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">健康评估分数：</label>
			<div class="controls">
				<form:input path="healthAssessmentScore" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">骨盆稳定：</label>
			<div class="controls">
				<form:select path="pelvicStability" class="input-xlarge ">
					<form:options items="${fns:getDictList('movement_inbody_option')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">核心激活：</label>
			<div class="controls">
				<form:select path="coreActivation" class="input-xlarge ">
					<form:options items="${fns:getDictList('movement_inbody_option')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">呼吸重建：</label>
			<div class="controls">
				<form:select path="respiratoryReconstruction" class="input-xlarge ">
					<form:options items="${fns:getDictList('movement_inbody_option')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="b_user_inbody:userInbody:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>