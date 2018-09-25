<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>反馈列表</title>
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
		<li><a href="${ctx}/b_feedback/feedback/">反馈列表</a></li>
		<li class="active"><a href="${ctx}/b_feedback/feedback/form?id=${feedback.id}">反馈<shiro:hasPermission name="b_feedback:feedback:edit">${not empty feedback.id?'详情':'添加'}</shiro:hasPermission><shiro:lacksPermission name="b_feedback:feedback:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="feedback" action="${ctx}/b_feedback/feedback/replyFeedback" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">反馈类型：</label>
			<div class="controls">
				<form:select path="feedbackType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('feedback_type')}" itemLabel="label" itemValue="value" htmlEscape="false"  readonly="readonly" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反馈时间：</label>
			<div class="controls">
				<input name="feedbackTime" type="text" readonly="readonly" maxlength="30" class="input-medium  "
					value="<fmt:formatDate value="${feedback.feedbackTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					  readonly="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反馈联系方式：</label>
			<div class="controls">
				<form:input path="feedbackPhone" htmlEscape="false" maxlength="128" class="input-xlarge "  readonly="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反馈人：</label>
			<div class="controls">
				<form:input path="feedbackPerson" htmlEscape="false" maxlength="255" class="input-xlarge "  readonly="true" />
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">反馈标题：</label>
			<div class="controls">
				<form:input path="feedbackTitle" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">反馈内容：</label>
			<div class="controls">
				<form:textarea path="feedbackContent" htmlEscape="false" rows="4" class="input-xxlarge "  readonly="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反馈图片：</label>
			<div class="controls">
			<form:hidden path="pictureUrl"/>
				<c:forEach items="${fn:split(feedback.pictureUrl,',')}" var="url" >
					<img src="${url }"> 
				</c:forEach>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回复状态：</label>
			<div class="controls">
				<form:select path="replyStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('reply_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回复时间：</label>
			<div class="controls">
				<input name="replyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${feedback.replyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回复人：</label>
			<div class="controls">
				<form:input path="replyPerson" htmlEscape="false" maxlength="255" class="input-xlarge required"/><span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">回复内容：</label>
			<div class="controls">
				<form:textarea path="replyContent" htmlEscape="false" rows="4" class="input-xxlarge required"/><span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="b_feedback:feedback:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="回复"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>