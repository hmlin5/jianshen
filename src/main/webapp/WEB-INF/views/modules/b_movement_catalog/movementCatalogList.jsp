<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>动作分类管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 3}).show();
		});
    	function updateSort() {
			loading('正在提交，请稍等...');
	    	$("#listForm").attr("action", "${ctx}/sys/menu/updateSort");
	    	$("#listForm").submit();
    	}
	</script>
</head>
<body>
	 <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/b_movement_catalog/movementCatalog/">动作分类列表</a></li>
		<shiro:hasPermission name="b_movement_catalog:movementCatalog:edit"><li><a href="${ctx}/b_movement_catalog/movementCatalog/form">添加动作分类</a></li></shiro:hasPermission>
	</ul>

	
	
	<form id="listForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed hide">
			<thead><tr><th>名称</th><th style="text-align:center;">更新时间</th><th>操作</th></tr></thead>
			<tbody><c:forEach items="${list}" var="mc">
				<tr id="${mc.id}" pId="${mc.parentId ne '0'?mc.parentId:'0'}">
					<td nowrap><i class="icon- hide"></i><a href="${ctx}/b_movement_catalog/movementCatalog/form?id=${mc.id}">${mc.name}</a></td>
					<td style="text-align:center;">
							<input type="hidden" name="ids" value="${mc.id}"/>
							<fmt:formatDate value="${mc.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					
					<td nowrap>
						<a href="${ctx}/b_movement_catalog/movementCatalog/form?id=${mc.id}">修改</a>
						<a href="${ctx}/b_movement_catalog/movementCatalog/delete?id=${mc.id}" onclick="return confirmx('要删除该分类及所有子分类项吗？', this.href)">删除</a>
						<c:if test="${mc.parentId == 0 }">
							<a href="${ctx}/b_movement_catalog/movementCatalog/form?parentId=${mc.id}">添加下级菜单</a> 
						</c:if>
					</td>
				</tr>
			</c:forEach></tbody>
		</table>
		<!-- <div class="form-actions pagination-left">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保存修改" onclick="updateSort();"/>
		</div> -->
	 </form>
	
	
</body>
</html>