<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户帮助管理</title>
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
	
	<script type="text/javascript" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${ctxStatic}/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/ueditor/zh-cn.js"></script>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/b_user_help/userHelp/">用户帮助列表</a></li>
		<li class="active"><a href="${ctx}/b_user_help/userHelp/form?id=${userHelp.id}">用户帮助<shiro:hasPermission name="b_user_help:userHelp:edit">${not empty userHelp.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="b_user_help:userHelp:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="userHelp" action="${ctx}/b_user_help/userHelp/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">内容：</label>
			<%-- <div class="controls">
				<form:textarea id="content" htmlEscape="false" path="content" rows="4" class="input-xxlarge"/>
			</div> --%>
			<div class="controls">
			    <script id="editor" type="text/plain" name="content" style="width:1024px;height:500px;">${userHelp.content}</script>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:input path="type" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div> --%>
		<div class="form-actions">
			<shiro:hasPermission name="b_user_help:userHelp:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
<script type="text/javascript">

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');



</script>
</body>
</html>