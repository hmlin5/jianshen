<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>bmi-推荐课程关系管理</title>
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
		<li class="active"><a href="${ctx}/b_bmi_course/bmiCourse/">bmi-推荐课程关系列表</a></li>
		<%--<shiro:hasPermission name="b_bmi_course:bmiCourse:edit"><li><a href="${ctx}/b_bmi_course/bmiCourse/form">bmi-推荐课程关系添加</a></li></shiro:hasPermission>
	--%></ul>
	<form:form id="searchForm" modelAttribute="bmiCourse" action="${ctx}/b_bmi_course/bmiCourse/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%--<ul class="ul-form">
			<li><label>标准制定者,1:总后台,2:健身房,3:教练：</label>
				<form:input path="creatorType" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>适用用户id：</label>
				<form:input path="toUserId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	--%></form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>增肌BMI</th>
				<th>增肌课程数</th>
				<th>塑型BMI</th>
				<th>塑型课程数</th>
				<th>减脂BMI</th>
				<th>减脂课程数</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="b_bmi_course:bmiCourse:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bmiCourse">
			<tr>
				<td>
				<a href="${ctx}/b_bmi_course/bmiCourse/form?id=${bmiCourse.id}">
				${bmiCourse.zengjiMin}&lt;=BMI指数&lt;${bmiCourse.zengjiMax}
				</a>
				</td>
				<td>
				${bmiCourse.zengjiBase}
				</td>
				<td>
				${bmiCourse.suxingMin}&lt;=BMI指数&lt;${bmiCourse.suxingMax}
				</td>
				<td>
				${bmiCourse.suxingBase}
				</td>
				<td>
				${bmiCourse.jianzhiMin}&lt;=BMI指数&lt;${bmiCourse.jianzhiMax}
				</td>
				<td>
				${bmiCourse.jianzhiBase}
				</td>
				<td>
					<fmt:formatDate value="${bmiCourse.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${bmiCourse.remarks}
				</td>
				<shiro:hasPermission name="b_bmi_course:bmiCourse:edit"><td>
    				<a href="${ctx}/b_bmi_course/bmiCourse/form?id=${bmiCourse.id}">修改</a>
					<%--<a href="${ctx}/b_bmi_course/bmiCourse/delete?id=${bmiCourse.id}" onclick="return confirmx('确认要删除该bmi-推荐课程关系吗？', this.href)">删除</a>
				--%></td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>