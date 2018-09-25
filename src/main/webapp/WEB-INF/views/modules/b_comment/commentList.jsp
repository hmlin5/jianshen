<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>评价管理</title>
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
		<li class="active"><a href="${ctx}/b_comment/comment/">评价列表</a></li>
		<shiro:hasPermission name="b_comment:comment:edit"><li><a href="${ctx}/b_comment/comment/form">评价添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="comment" action="${ctx}/b_comment/comment/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单编号：</label>
				<form:input path="orderId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>会员id：</label>
				<form:input path="stuId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>会员姓名：</label>
				<form:input path="stuName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>评价时间：</label>
				<input name="beginCommentTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${comment.beginCommentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCommentTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${comment.endCommentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>教练id：</label>
				<form:input path="coachId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>教练姓名：</label>
				<form:input path="coachName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>回复时间：</label>
				<input name="beginReplyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${comment.beginReplyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endReplyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${comment.endReplyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>专业评星:1-5：</label>
				<form:input path="major" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>服务评星:1-5：</label>
				<form:input path="service" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>总分：</label>
				<form:input path="total" htmlEscape="false" maxlength="11" class="input-medium"/>
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
				<shiro:hasPermission name="b_comment:comment:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="comment">
			<tr>
				<td><a href="${ctx}/b_comment/comment/form?id=${comment.id}">
					<fmt:formatDate value="${comment.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${comment.remarks}
				</td>
				<shiro:hasPermission name="b_comment:comment:edit"><td>
    				<a href="${ctx}/b_comment/comment/form?id=${comment.id}">修改</a>
					<a href="${ctx}/b_comment/comment/delete?id=${comment.id}" onclick="return confirmx('确认要删除该评价吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>