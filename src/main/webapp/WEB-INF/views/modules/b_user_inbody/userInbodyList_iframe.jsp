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
		<li class="active"><a href="${ctx}/b_user_inbody/userInbody/listIframe?userId=${userInbody.userId}">教练评估报告列表</a></li>
		<shiro:hasPermission name="b_user_inbody:userInbody:edit"><li><a href="${ctx}/b_user_inbody/userInbody/form?userId=${userInbody.userId}">教练评估报告添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userInbody" action="${ctx}/b_user_inbody/userInbody/listIframe" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="userId" name="userId" type="hidden" value="${userInbody.userId}"/>
	
		<li><label>评估时间：</label>
				<input name="beginCreateDate" type="text" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${userInbody.beginCreateDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${userInbody.endCreateDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			
			
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"><input id="btnReset" class="btn btn-primary" type="reset" value="重置"/></li>	
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>评估时间</th>
				<th>身高</th>
				<th>体重</th>
				<th>BMI指数</th>
				<th>骨骼肌</th>
				<th>体脂率</th>
				<th>体脂肪</th>
				<th>腰围</th>
				<th>臀围</th>
				<th>腰臀比</th>
				<th>基础代谢</th>
				<th>肌肉控制数</th>
				<th>脂肪控制数</th>
				<th>内脏脂肪等级</th>
				<th>健康评估分数</th>
				<th>骨盆稳定</th>
				<th>核心激活</th>
				<th>呼吸重建</th>
				<shiro:hasPermission name="b_user_inbody:userInbody:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userInbody" varStatus="vs">
			<tr>
				<td><a href="#">${vs.count }</td>
				<td>
					<fmt:formatDate value="${userInbody.testTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${userInbody.height}
				</td>
				<td>
					${userInbody.weight}
				</td>
				<td>
					${userInbody.bmiIndex}
				</td>
				<td>
					${userInbody.skeletalMuscle}
				</td>
				<td>
					${userInbody.bodyFatRate}
				</td>
				<td>
					${userInbody.bodyFat}
				</td>
				<td>
					${userInbody.waistline}
				</td>
				<td>
					${userInbody.hipline}
				</td>
				<td>
					${userInbody.whr}
				</td>
				<td>
					${userInbody.basalMetabolism}
				</td>
				<td>
					${userInbody.muscleControl}
				</td>
				<td>
					${userInbody.fatControl}
				</td>
				<td>
					${userInbody.visceralAdiposeGrade}
				</td>
				<td>
					${userInbody.healthAssessmentScore}
				</td>
				<td>
					${userInbody.pelvicStability}
				</td>
				<td>
					${userInbody.coreActivation}
				</td>
				<td>
					${userInbody.respiratoryReconstruction}
				</td>
				<shiro:hasPermission name="b_user_inbody:userInbody:edit"><td>
    				<%-- <a href="${ctx}/b_user_inbody/userInbody/form?id=${userInbody.id}">修改</a> --%>
					<a href="${ctx}/b_user_inbody/userInbody/delete?id=${userInbody.id}" onclick="return confirmx('确认要删除该用户教练评估报告吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>