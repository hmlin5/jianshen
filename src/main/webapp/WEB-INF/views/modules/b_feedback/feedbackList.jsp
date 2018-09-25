<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>反馈列表</title>
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
		<li class="active"><a href="${ctx}/b_feedback/feedback/">反馈列表</a></li>
<!-- 		<shiro:hasPermission name="b_feedback:feedback:edit"><li><a href="${ctx}/b_feedback/feedback/form">反馈列表添加</a></li></shiro:hasPermission> -->
	</ul>
	<form:form id="searchForm" modelAttribute="feedback" action="${ctx}/b_feedback/feedback/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>反馈类型：</label>
				<form:select path="feedbackType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('feedback_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>反馈时间：</label>
				<input name="beginFeedbackTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${feedback.beginFeedbackTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endFeedbackTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${feedback.endFeedbackTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>反馈手机号：</label>
				<form:input path="feedbackPhone" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			<li><label>反馈内容：</label>
				<form:input path="feedbackContent" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>回复状态：</label>
				<form:select path="replyStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('reply_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>反馈类型</th>
				<th>反馈时间</th>
				<th>反馈手机号</th>
<!-- 				<th>反馈标题</th> -->
				<th>反馈内容</th>
				<th>回复状态</th>
				<th>回复时间</th>
				<th>回复人</th>
				<th>回复内容</th>
				<shiro:hasPermission name="b_feedback:feedback:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="feedback">
			<tr>
				<td><a href="${ctx}/b_feedback/feedback/form?id=${feedback.id}">
					${fns:getDictLabel(feedback.feedbackType, 'feedback_type', '')}
				</a></td>
				<td>
					<fmt:formatDate value="${feedback.feedbackTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${feedback.feedbackPhone}
				</td>
<!-- 				<td> -->
<!-- 					${feedback.feedbackTitle} -->
<!-- 				</td> -->
				<td>
					${feedback.feedbackContent}
				</td>
				<td>
					${fns:getDictLabel(feedback.replyStatus, 'reply_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${feedback.replyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${feedback.replyPerson}
				</td>
				<td>
					${feedback.replyContent}
				</td>
				<shiro:hasPermission name="b_feedback:feedback:edit"><td>
    				<a href="${ctx}/b_feedback/feedback/form?id=${feedback.id}">反馈详情</a>
					<a href="${ctx}/b_feedback/feedback/delete?id=${feedback.id}" onclick="return confirmx('不可恢复！确认要删除吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>