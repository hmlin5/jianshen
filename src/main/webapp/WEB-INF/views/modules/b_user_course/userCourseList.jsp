<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员推荐课程管理</title>
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
		<li class="active"><a href="${ctx}/b_user_course/userCourse/">会员推荐课程列表</a></li>
		<shiro:hasPermission name="b_user_course:userCourse:edit"><li><a href="${ctx}/b_user_course/userCourse/form">会员推荐课程添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userCourse" action="${ctx}/b_user_course/userCourse/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员id：</label>
				<sys:treeselect id="userId" name="userId" value="${userCourse.userId}" labelName="" labelValue="${userCourse.}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>教练id：</label>
				<form:input path="coachId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>课程id：</label>
				<form:input path="courseId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>排序：</label>
				<form:input path="seq" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>是否已完成上课：</label>
				<form:input path="finishFlag" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>健身房id：</label>
				<form:input path="gymId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>分类id：</label>
				<form:input path="catalogId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>分类名称：</label>
				<form:input path="catalogName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>健身房图片url：</label>
				<form:input path="gymImgUrl" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>推荐范围：</label>
				<form:input path="recomendRage" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>课程名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>课程描述：</label>
				<form:input path="description" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>动作总数：</label>
				<form:input path="movementNum" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>课程上课时长：</label>
				<form:input path="duration" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>健身房名称：</label>
				<form:input path="gymName" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>动作id：</label>
				<form:input path="movementIds" htmlEscape="false" maxlength="128" class="input-medium"/>
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
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="b_user_course:userCourse:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userCourse">
			<tr>
				<td><a href="${ctx}/b_user_course/userCourse/form?id=${userCourse.id}">
					${userCourse.name}
				</a></td>
				<td>
					<fmt:formatDate value="${userCourse.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${userCourse.remarks}
				</td>
				<shiro:hasPermission name="b_user_course:userCourse:edit"><td>
    				<a href="${ctx}/b_user_course/userCourse/form?id=${userCourse.id}">修改</a>
					<a href="${ctx}/b_user_course/userCourse/delete?id=${userCourse.id}" onclick="return confirmx('确认要删除该会员推荐课程吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>