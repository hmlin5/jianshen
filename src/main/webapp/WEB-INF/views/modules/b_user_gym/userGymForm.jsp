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
				var op = $("#options:selected").val();
					if(op==null){
				        form.submit(false);
				    }else{
				        loading('正在提交，请稍等...');
					    form.submit(true);
				    }
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
	
	<link rel="stylesheet" href="${ctxStatic}/layer/skin/default/layer.css">
	<script type="text/javascript" src="${ctxStatic}/layer/layer.js"></script>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/b_user_gym/userGym/">会员列表</a></li>	
		<li class="active"><a href="${ctx}/b_user_gym/userGym/form?id=${userGym.id}">会员<shiro:hasPermission name="b_user_gym:userGym:edit">${not empty userGym.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="b_user_gym:userGym:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="userGym" action="${ctx}/b_user_gym/userGym/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		
		<c:if test="${not empty userGym.id}">
			<div class="control-group">
				<label class="control-label"><input id="btnInbody" class="btn btn-primary" type="button" value="查看教练评估报告"/></label>
				<div class="controls">
					
				</div>
			</div>	
		</c:if>
		
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

	<%--	<c:if test="${!empty userGym.id}">
			<div class="control-group">
				<label class="control-label">真实姓名：</label>
				<div class="controls">
						${userGym.userName }

				</div>
			</div>
		</c:if>--%>

		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				<form:input path="userPhone" htmlEscape="false" maxlength="64" class="input-xlarge " />
			</div>
		</div>

		<%--<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				${userGym.userPhone }
			</div>
		</div>--%>


		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:radiobuttons path="userSex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身高(cm)：</label>
			<div class="controls">
				<form:input path="userHeight" htmlEscape="false" maxlength="11" class="input-xlarge  digits" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">体重(kg)：</label>
			<div class="controls">
				<form:input path="userWeight" htmlEscape="false" maxlength="11" class="input-xlarge" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">剩余课程数：</label>
			<div class="controls">
				<form:input path="restNumber" htmlEscape="false" maxlength="11" class="input-xlarge"  onkeyup="value=value.replace(/[^\d]/g,'')" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课程单价：</label>
			<div class="controls">
				<form:input path="unitPrice" htmlEscape="false" maxlength="11" class="input-xlarge"  onkeyup="value=value.replace(/[^\d]/g,'')" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">生日：</label>
			<div class="controls">
				<input name="userBirthDate" type="text"  maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${userGym.userBirthDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			</div>
		</div>
		
	

		<div class="control-group">
			<label class="control-label">用户类型：</label>
			<div class="controls">
				<select name="userType">
					<option value="2">私教用户</option>
				</select>	
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">开卡日期：</label>
			<div class="controls">
					<input name="cardStartDate" type="text"  maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${userGym.cardStartDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">截止日期：</label>
			<div class="controls">
				<input name="cardEndDate" type="text"  maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${userGym.cardEndDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课程主管：</label>
			<div class="controls">
				<form:select path="coachId" class="input-xlarge ">
					<form:options id="options" items="${coachList}" itemLabel="userName" itemValue="userId" htmlEscape="false"/>
				</form:select>
			</div>
		</div>



		<%--<c:if test="${not empty userGym.id}">
			<div class="control-group">
				<label class="control-label">性别：</label>
				<div class="controls">
						${fns:getDictLabel(userGym.userSex, 'sex', '')}
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">身高(cm)：</label>
				<div class="controls">
					${userGym.userHeight}
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">体重(kg)：</label>
				<div class="controls">
						${userGym.userWeight}

				</div>
			</div>

			<div class="control-group">
				<label class="control-label">生日：</label>
				<div class="controls">

					<input name="userBirthDate" type="text"  maxlength="20" class="input-medium Wdate " readonly="true"
						   value="<fmt:formatDate value="${userGym.userBirthDate}" pattern="yyyy-MM-dd"/>"
						   />
				</div>
			</div>



			<div class="control-group">
				<label class="control-label">用户类型：</label>
				<div class="controls">
					<select name="userType">
						<option value="2">私教用户</option>
					</select>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">开卡日期：</label>
				<div class="controls">

					<input name="cardStartDate" type="text"  maxlength="20" class="input-medium Wdate " readonly="true"
						   value="<fmt:formatDate value="${userGym.cardStartDate}" pattern="yyyy-MM-dd"/>"
						    />
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">截止日期：</label>
				<div class="controls">

					<input name="cardEndDate" type="text"  maxlength="20" class="input-medium Wdate "
						   value="<fmt:formatDate value="${userGym.cardEndDate}" pattern="yyyy-MM-dd"/>"
						   readonly="true" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">课程主管：</label>
				<div class="controls">
					<form:select path="coachId" class="input-xlarge " disabled="true">
						<form:options id="options" items="${coachList}" itemLabel="userName" itemValue="userId" htmlEscape="false"/>
					</form:select>
				</div>
			</div>
		</c:if>--%>
		<%-- <div class="control-group">
			<label class="control-label">推荐课程数量：</label>
			<div class="controls">
				<form:input path="courseCount" htmlEscape="false" maxlength="11" class="input-xlarge  digits" />
			</div>
		</div> --%>
		
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
	
	
<script type="text/javascript">
	
	$(function(){
		

   
   
		$('#btnInbody').click(function(){
		
			var layerIndex = layer.open({
				  type: 2,
				  area: ['95%', '95%'],
				  skin: 'layui-layer-rim', //加上边框
				  content: ['${pageContext.request.contextPath}/a/b_user_inbody/userInbody/listIframe?userId=${userGym.userId}']
				});
			layer.style(layerIndex, {
				'top':'10px'
			});
		});
	});


</script>	
	   
</body>
</html>