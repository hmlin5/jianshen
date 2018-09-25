<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>动作分类管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
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
		<li><a href="${ctx}/b_movement_catalog/movementCatalog/">动作分类列表</a></li>
		<li class="active"><a href="${ctx}/b_movement_catalog/movementCatalog/form?id=${movementCatalog.id}"><shiro:hasPermission name="b_movement_catalog:movementCatalog:edit">${not empty movementCatalog.id?'修改':'添加'}</shiro:hasPermission>动作分类<shiro:lacksPermission name="b_movement_catalog:movementCatalog:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="movementCatalog" action="${ctx}/b_movement_catalog/movementCatalog/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">分类名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="required input-xlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">上级分类：</label>
			<div class="controls">
				<%-- <form:input path="classCatalogId" htmlEscape="false" maxlength="11" class="input-xlarge required"/> --%>
				<form:select path="parentId" class="input-medium">
					<form:option value=""></form:option>
					<form:options items="${parentCatalogList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">是否显示：</label>
			<div class="controls">
				<%-- <form:input path="isShow" htmlEscape="false" maxlength="6" class="input-xlarge required"/> --%>
				<label><input name="isShow"  type="radio" value="1" ${movementCatalog.isShow == null ? 'checked' : '' } ${movementCatalog.isShow == '1' ? 'checked' : '' } />是 </label> 
				<label><input name="isShow" type="radio" value="0"  ${movementCatalog.isShow == '0' ? 'checked' : '' } />否 </label> 
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="b_movement_catalog:movementCatalog:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
	
	
	
	
</body>
</html>