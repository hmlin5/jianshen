<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户健身房关系管理</title>
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
		<li><a href="${ctx}/b_user_gym/userGym/listCoach">教练列表</a></li>
		<li class="active"><a href="${ctx}/b_user_gym/userGym/formCoach?id=${userGym.id}">教练<shiro:hasPermission name="b_user_gym:userGym:edit">${not empty userGym.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="b_user_gym:userGym:edit">查看</shiro:lacksPermission></a></li>
		<li><a href="${pageContext.request.contextPath}/a/b_rest_time/restTime/listframe">教练休息时间</a></li>
		<li><a href="${pageContext.request.contextPath}/a/b_rest_time/restTime/form">休息时间添加</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="userGym" action="${ctx}/b_user_gym/userGym/saveCoach" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
	
			
		<c:if test="${not empty userGym.id}">
			<div class="control-group">
				<label class="control-label">头像：</label>
				<div class="controls">
					<img style="height:50px;width:50px;" alt="" src="${userGym.userHeadImgUrl }">
				</div>
			</div>	
		</c:if>
		<c:if test="${empty userGym.id}">
			<div class="control-group">
				<label class="control-label">头像(默认)：</label>
				<div class="controls">
					<img style="height:50px;width:50px;" alt="" src="${defaultHeadImgUrl }">
				</div>
			</div>	
		</c:if>
		<div class="control-group">
			<label class="control-label">真实姓名：</label>
			<div class="controls">
				<form:input path="userName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				<form:input path="userPhone" htmlEscape="false" maxlength="64" class="input-xlarge " />
			</div>
		</div>
	
		
	
		<div class="control-group">
			<label class="control-label">入职时间：</label>
			<div class="controls">
				<input name="userOnBoardingTime" type="text" class="input-medium Wdate "
					value="<fmt:formatDate value="${userGym.userOnBoardingTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">教练标签：</label>
			<div class="controls">
				<form:input path="coachLabel" htmlEscape="false" maxlength="64" class="input-xlarge " />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">提成比例(0-1)：</label>
			<div class="controls">
				<form:input path="commissionRatio" htmlEscape="false" maxlength="64" class="input-xlarge "   onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">上一次登录时间：</label>
			<div class="controls">
			<fmt:formatDate value="${userGym.userLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>


		
		<div class="form-actions">
			<shiro:hasPermission name="b_user_gym:userGym:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
</body>
</html>