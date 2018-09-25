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
		<li class="active"><a href="${ctx}/b_user_inbody/userInbody/">用户Inbody数据列表</a></li>
		<shiro:hasPermission name="b_user_inbody:userInbody:edit"><li><a href="${ctx}/b_user_inbody/userInbody/form">用户Inbody数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userInbody" action="${ctx}/b_user_inbody/userInbody/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户id：</label>
				<sys:treeselect id="userId" name="userId" value="${userInbody.userId}" labelName="" labelValue="${}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>教练id：</label>
				<form:input path="coachId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>健身房id：</label>
				<form:input path="gymId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>身高：</label>
				<form:input path="height" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>体重：</label>
				<form:input path="weight" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>BMI指数：</label>
				<form:input path="bmiIndex" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>骨骼肌：</label>
				<form:input path="skeletalMuscle" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${userInbody.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${userInbody.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="b_user_inbody:userInbody:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userInbody">
			<tr>
				<td><a href="${ctx}/b_user_inbody/userInbody/form?id=${userInbody.id}">
					<fmt:formatDate value="${userInbody.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${userInbody.remarks}
				</td>
				<shiro:hasPermission name="b_user_inbody:userInbody:edit"><td>
    				<a href="${ctx}/b_user_inbody/userInbody/form?id=${userInbody.id}">修改</a>
					<a href="${ctx}/b_user_inbody/userInbody/delete?id=${userInbody.id}" onclick="return confirmx('确认要删除该用户Inbody数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>