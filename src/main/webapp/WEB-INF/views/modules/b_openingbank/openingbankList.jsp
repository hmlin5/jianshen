<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>开户行管理</title>
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
		<li class="active"><a href="${ctx}/b_openingbank/openingbank/">开户行列表</a></li>
		<shiro:hasPermission name="b_openingbank:openingbank:edit"><li><a href="${ctx}/b_openingbank/openingbank/form">开户行添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="openingbank" action="${ctx}/b_openingbank/openingbank/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>开户银行：</label>
				<form:input path="openingbankName" htmlEscape="false" maxlength="128" class="input-medium"/>
			</li>
			
		
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			
<!-- 			<a  class="btns" href="${ctx}/file/upload">上传</a> -->
			<li class="clearfix"></li>
			
			
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
<!-- 				<th>开户银行logo</th> -->
				<th>开户银行</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="b_openingbank:openingbank:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="openingbank">
			<tr>
<!-- 				<td><a href="${ctx}/b_openingbank/openingbank/form?id=${openingbank.id}"> -->
<!-- 					${openingbank.openingbankLogoUrl} -->
                
<!-- 				</a></td> -->
				
				
				<td>
					${openingbank.openingbankName}
				</td>
				<td>
					<fmt:formatDate value="${openingbank.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${openingbank.remarks}
				</td>
				<shiro:hasPermission name="b_openingbank:openingbank:edit"><td>
    				<a href="${ctx}/b_openingbank/openingbank/form?id=${openingbank.id}">修改</a>
					<a href="${ctx}/b_openingbank/openingbank/delete?id=${openingbank.id}" onclick="return confirmx('确认要删除该开户行管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>