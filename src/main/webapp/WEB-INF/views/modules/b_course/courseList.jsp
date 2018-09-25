<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/b_course/course/">课程列表</a></li>
		<shiro:hasPermission name="b_course:course:edit"><li><a href="${ctx}/b_course/course/form">课程添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="course" action="${ctx}/b_course/course/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		
			
		
			<li><label>课程名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			
			<li><label>课程分类：</label>
				<form:input path="catalogName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>课程名称</th>
				<!-- <th>课程分类</th> -->
				<th>推荐范围</th>
				<th>课程时长</th>
				<th>更新时间</th>
				<shiro:hasPermission name="b_course:course:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="course">
			<tr>
				<td><a href="${ctx}/b_course/course/form?id=${course.id}">
					${course.name}
				</a></td>
				
				<%-- <td>
					${course.catalogName }
				</td> --%>
				<td>
					${ fns:getDictLabel (course.recomendRage, "course_recommend_type", "")}
				</td>
				<td>
					<fmt:formatNumber value="${course.duration}" type="number" maxFractionDigits="0" />
				</td>
				<td>
					<fmt:formatDate value="${course.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="b_course:course:edit"><td>
    				<a href="${ctx}/b_course/course/form?id=${course.id}">修改</a>
					<a href="${ctx}/b_course/course/delete?id=${course.id}" onclick="return confirmx('确认要删除该课程吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>