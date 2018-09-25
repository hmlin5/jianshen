<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程管理</title>
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
		<li><a href="${ctx}/b_course/course/">课程列表</a></li>
		<li class="active"><a href="${ctx}/b_course/course/form?id=${course.id}">课程<shiro:hasPermission name="b_course:course:edit">${not empty course.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="b_course:course:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="course" action="${ctx}/b_course/course/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">课程分类：</label>
			<div class="controls">
				<%-- <form:select path="catalogId" class="required input-medium">
					<form:option value=""></form:option>
					<form:options items="${catalogList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select> --%>
				<select multiple="multiple" style="width:400px;" id="catalogIds" name="catalogIds" class="required input-medium">
					<c:forEach items="${catalogList }" var="catalog" varStatus="vs">
						<c:if test="${catalog.level == '1' }">
							<c:if test="${vs.first }">
								<optgroup label='${catalog.name }'> 
							</c:if>
							<c:if test="${!vs.first }">
								</optgroup><optgroup label='${catalog.name }'> 
							</c:if>
						</c:if>
						<c:if test="${catalog.level == '2' }">
							<c:if test="${vs.last }">
								<option value="${catalog.id }"  ${fn:contains(course.catalogIds,catalog.id ) ? 'selected' : '' }>${catalog.parentName }-${catalog.name }</option>
								</optgroup>
							</c:if>
							<c:if test="${!vs.last }">
								<option value="${catalog.id }"  ${fn:contains(course.catalogIds,catalog.id ) ? 'selected' : '' }>${catalog.parentName }-${catalog.name }</option>
							</c:if>
						</c:if>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">动作选择：</label>
			<div class="controls">
				<%-- <form:select path="catalogId" class="required input-medium">
					<form:option value=""></form:option>
					<form:options items="${catalogList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select> --%>
				<select multiple="multiple" style="width:400px;" id="movementIds" name="movementIds" class="required input-medium">
					<c:forEach items="${movementsList }" var="catalog" varStatus="vs">
						<c:if test="${catalog.difficulty == '1'}">
							<optgroup label='初级'>
								<option value="${catalog.id}" <c:if test="${fn:contains(course.movementIds,catalog.id)}">selected</c:if> >${ catalog.name}</option>
							</optgroup>		
						</c:if>
						<c:if test="${catalog.difficulty == '2' }">
							<optgroup label='中级'>
								<option value="${catalog.id}" <c:if test="${fn:contains(course.movementIds,catalog.id)}">selected</c:if>>${ catalog.name}</option>
							</optgroup>		
						</c:if>
						<c:if test="${catalog.difficulty == '3'}">
							<optgroup label='高级'>
								<option value="${catalog.id}" <c:if test="${fn:contains(course.movementIds,catalog.id)}">selected</c:if>>${ catalog.name}</option>
							</optgroup>		
						</c:if>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推荐范围：</label>
			<div class="controls">
			<!--<select name="recomendRage">	
				<c:forEach items="${list2 }" var="var">
					<option<c:if test="${var.value==course.recomendRage }">selected</c:if>>${var.label }</option>
				</c:forEach>
			</select>
			-->
				<form:select path="recomendRage" class="required input-medium">
					<form:option value=""></form:option>
					<form:options items="${fns:getDictList('course_recommend_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课程名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" style="width:500px;" maxlength="30" class="required input-xlarge "/><span class="help-inline"><font color="red">*</font> </span>
			</div>
			
		</div>
		<div class="control-group">
			<label class="control-label">课程上课时长：</label>
			<div class="controls">
                  <input type="text" name="duration" class="required " style="width:100px;" value="<fmt:formatNumber value="${empty course.duration?60:course.duration}" type="number" maxFractionDigits="0" />" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  
                  onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}" /> 分钟
                  <span class="help-inline"><font color="red">*</font> </span>
			</div>
			
		</div>
		<div class="control-group">
			<label class="control-label">课程描述：</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4" maxlength="300" class="required input-xxlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
			
		</div>
	
		<div class="form-actions">
			<shiro:hasPermission name="b_course:course:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>




</html>