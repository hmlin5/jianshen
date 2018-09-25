<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提现订单管理</title>
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
		<li><a href="${ctx}/b_withdraw/cashOrder/">提现订单列表</a></li>
		<li class="active"><a href="${ctx}/b_withdraw/cashOrder/form?id=${cashOrder.id}">提现订单<shiro:hasPermission name="b_withdraw:cashOrder:edit">${not empty cashOrder.id?'修改及操作':'添加'}</shiro:hasPermission><shiro:lacksPermission name="b_withdraw:cashOrder:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cashOrder" action="${ctx}/b_withdraw/cashOrder/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">提现订单号：</label>
			<div class="controls">
				<form:input path="orderNum" htmlEscape="false" maxlength="255" class="input-xlarge " disabled="true"/>
			</div>
		</div>
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">健身房id：</label> -->
<!-- 			<div class="controls"> -->
<!-- 				<form:input path="gymId" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="control-group">
			<label class="control-label">健身房：</label>
			<div class="controls">
				<form:input path="gymName" htmlEscape="false" maxlength="255" class="input-xlarge " disabled="true"/>
			</div>
		</div>
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">教练id：</label> -->
<!-- 			<div class="controls"> -->
<!-- 				<form:input path="coachId" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="control-group">
			<label class="control-label">提现教练名：</label>
			<div class="controls">
				<form:input path="coachName" htmlEscape="false" maxlength="128" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请时间：</label>
			<div class="controls">
				<input name="applyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${cashOrder.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教练手机号：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="128" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教练银行账号：</label>
			<div class="controls">
				<form:input path="bankAccount" htmlEscape="false" maxlength="128" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提现金额：</label>
			<div class="controls">
				<form:input path="withdrawAmount" htmlEscape="false" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提现状态：</label>
			<div class="controls">
				<form:select path="withdrawStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('withdraw_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="b_withdraw:cashOrder:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>