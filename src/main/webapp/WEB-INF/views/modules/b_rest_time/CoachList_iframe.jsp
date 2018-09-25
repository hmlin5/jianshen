<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户Inbody数据管理</title>
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
		<li class="active"><a href="${ctx}/b_rest_time/restTime/listframe?gym_id=${restTime.gymId}">休息时间列表</a></li>
		<shiro:hasPermission name="b_rest_time:restTime:edit"><li><a href="${ctx}/b_rest_time/restTime/form?gym_id=${restTime.gymId}">休息时间添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="restTime" action="${ctx}/b_rest_time/restTime/listframe" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>休息开始时间：</label>
				<input name="restStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${restTime.restStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>休息结束时间：</label>
				<input name="restEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${restTime.restEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>持续时间：</label>
				<form:input path="duration" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>星期：</label>
				<form:input path="dayOfWeek" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>休息年月日：</label>
				<input name="restDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${restTime.restDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>休息开始时间</th>
				<th>休息结束时间</th>
				<th>持续时间</th>
				<th>星期</th>
				<th>休息年月日</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="b_rest_time:restTime:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="restTime">
			<tr>
				<td><a href="${ctx}/b_rest_time/restTime/form?id=${restTime.id}">
					<fmt:formatDate value="${restTime.restStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					<fmt:formatDate value="${restTime.restEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${restTime.duration}
				</td>
				<td>
					${restTime.dayOfWeek}
				</td>
				<td>
					<fmt:formatDate value="${restTime.restDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${restTime.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${restTime.remarks}
				</td>
				<shiro:hasPermission name="b_rest_time:restTime:edit"><td>
    				<a href="${ctx}/b_rest_time/restTime/form?id=${restTime.id}">修改</a>
					<a href="${ctx}/b_rest_time/restTime/delete?id=${restTime.id}" onclick="return confirmx('确认要删除该休息时间吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>