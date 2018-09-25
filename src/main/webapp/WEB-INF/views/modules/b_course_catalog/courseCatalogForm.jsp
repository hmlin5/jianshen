<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程分类管理</title>
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
		<li><a href="${ctx}/b_course_catalog/courseCatalog/">课程分类列表</a></li>
		<li class="active"><a href="${ctx}/b_course_catalog/courseCatalog/form?id=${courseCatalog.id}"><shiro:hasPermission name="b_course_catalog:courseCatalog:edit">${not empty courseCatalog.id?'修改':'添加'}</shiro:hasPermission>课程分类<shiro:lacksPermission name="b_course_catalog:courseCatalog:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="courseCatalog" action="${ctx}/b_course_catalog/courseCatalog/save" method="post" class="form-horizontal">
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
				<%-- <select  style="width:400px;" id="parentId" name="parentId" class="required input-medium">
					<c:forEach items="${parentCatalogList }" var="catalog" varStatus="vs">
						<c:if test="${catalog.level == '1' }">
							<option ${courseCatalog.parentId == catalog.id ? 'selected' : '' } value="${catalog.id }">${catalog.name }</option>
						</c:if>
						<c:if test="${catalog.level == '2' }">
							<option ${courseCatalog.parentId == catalog.id ? 'selected' : '' } value="${catalog.id }">&nbsp;&nbsp;${catalog.parentName }-${catalog.name }</option>
						</c:if>
					</c:forEach>
				</select> --%>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">是否显示：</label>
			<div class="controls">
				<%-- <form:input path="isShow" htmlEscape="false" maxlength="6" class="input-xlarge required"/> --%>
				<label><input name="isShow"  type="radio" value="1" ${courseCatalog.isShow == null ? 'checked' : '' } ${courseCatalog.isShow == '1' ? 'checked' : '' } />是 </label> 
				<label><input name="isShow" type="radio" value="0"  ${courseCatalog.isShow == '0' ? 'checked' : '' } />否 </label> 
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
			<shiro:hasPermission name="b_course_catalog:courseCatalog:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>