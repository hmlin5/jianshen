
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>动作管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
	
	
	<link rel="stylesheet" href="${ctxBurn}/layui/css/layui.css">
	<%-- <script type="text/javascript" src="${ctxBurn}/js/jquery.min.js"></script> --%>
	<script type="text/javascript" src="${ctxBurn}/layui/layui.js"></script>
	
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/b_movement/movement/">动作列表</a></li>
		<li class="active"><a href="${ctx}/b_movement/movement/form?id=${movement.id}">动作<shiro:hasPermission name="b_movement:movement:edit">${not empty movement.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="b_movement:movement:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="movement" action="${ctx}/b_movement/movement/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">动作名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="required input-xlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">动作分类：</label>
			<div class="controls">
				<%-- <form:select path="catalogIds" class="input-medium">
					<form:option value=""></form:option>
					<form:options items="${catalogList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select> --%>
				<select multiple="multiple" style="width:400px;" id="catalogIds" name="catalogIds" class="required input-medium">
					<c:forEach items="${catalogList }" var="catalog" varStatus="vs">
						<c:if test="${catalog.level == '1' }">
							<c:if test="${vs.first }">
								<optgroup label='${catalog.name }'> 
							</c:if>
							<c:if test="${!vs.first }">
								</optgroup><optgroup label='${catalog.name }'> 
							</c:if>
						</c:if>
						<c:if test="${catalog.level == '2' }">
							<c:if test="${vs.last }">
								<option value="${catalog.id }"   ${fn:contains(movement.catalogIds,catalog.id ) ? 'selected' : '' }>${catalog.parentName }-${catalog.name }</option>
								</optgroup>
							</c:if>
							<c:if test="${!vs.last }">
								<option value="${catalog.id }" ${fn:contains(movement.catalogIds,catalog.id ) ? 'selected' : '' }>${catalog.parentName }-${catalog.name }</option>
							</c:if>
						</c:if>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">动作难度：</label>
			<div class="controls">
				<form:select path="difficulty" class="required input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('movement_difficulty')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否使用器材：</label>
			<div class="controls">
				<form:select path="equipmentType" class="required input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('equipment_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">增肌区间动作组数：</label>
			<div class="controls">
				<form:input path="zengjiGroupNum" placeholder="例如'5-10'" htmlEscape="false" maxlength="32" class="required input-xlarge "/>
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">塑型区间动作组数：</label>
			<div class="controls">
				<form:input path="suxingGroupNum" placeholder="例如'5-10'" htmlEscape="false" maxlength="32" class="required input-xlarge "/>
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">减脂区间动作组数：</label>
			<div class="controls">
				<form:input path="jianzhiGroupNum" placeholder="例如'5-10'" htmlEscape="false" maxlength="32" class="required input-xlarge "/>
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">增肌区间动作次数：</label>
			<div class="controls">
				<form:input path="zengjiMovementNum" placeholder="例如'5-10'" htmlEscape="false" maxlength="32" class="required input-xlarge "/>
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">塑型区间动作次数：</label>
			<div class="controls">
				<form:input path="suxingMovementNum" placeholder="例如'5-10'" htmlEscape="false" maxlength="32" class="required input-xlarge "/>
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">减脂区间动作次数：</label>
			<div class="controls">
				<form:input path="jianzhiMovementNum" placeholder="例如'5-10'" htmlEscape="false" maxlength="32" class="required input-xlarge "/>
			<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预备动作图片：</label>
			<div class="controls">
				<form:hidden path="prepareImgUrl" />
				<div class="layui-upload">
				  <button type="button" class="layui-btn" id="test1"><i class="layui-icon">&#xe67c;</i>选择上传图片</button>
				<span class="help-inline"><font color="red">*</font> </span>
				  <div class="layui-upload-list">
				    <img class="layui-upload-img" id="demo1">
				    <p id="demoText1"></p>
				  </div>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执行动作图片：</label>
			<div class="controls">
				<form:hidden path="actionImgUrl" />
				<div class="layui-upload">
				  <button type="button" class="layui-btn" id="test2"><i class="layui-icon">&#xe67c;</i>选择上传图片</button>
				  <span class="help-inline"><font color="red">*</font> </span>
				  <div class="layui-upload-list">
				    <img class="layui-upload-img" id="demo2">
				    <p id="demoText2"></p>
				  </div>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">动作说明：</label>
			<div class="controls">
				<form:textarea path="introduction"  placeholder="限150字" htmlEscape="false" rows="4" maxlength="150" class="input-xxlarge "/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">结束动作要领：</label>
			<div class="controls">
				<form:input path="endMainPoint" htmlEscape="false" maxlength="256" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">预备：</label>
			<div class="controls">
				<form:textarea path="prepare"  placeholder="限150字" htmlEscape="false" rows="4" maxlength="150" class="required input-xxlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执行：</label>
			<div class="controls">
				<form:textarea path="action"  placeholder="限150字" htmlEscape="false" rows="4" maxlength="150" class="required input-xxlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提醒：</label>
			<div class="controls">
				<form:textarea path="coachTip"  placeholder="限150字" htmlEscape="false" rows="4" maxlength="150" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">避免：</label>
			<div class="controls">
				<form:textarea path="commonFault"  placeholder="限150字" htmlEscape="false" rows="4" maxlength="150" class="input-xxlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">骨盆稳定：</label>
			<div class="controls">
				<form:select path="pelvicStability" class="required input-xlarge ">
					<form:options items="${fns:getDictList('movement_inbody_option')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">核心激活：</label>
			<div class="controls">
				<form:select path="coreActivation" class="required input-xlarge ">
					<form:options items="${fns:getDictList('movement_inbody_option')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">呼吸重建：</label>
			<div class="controls">
				<form:select path="respiratoryReconstruction" class="required input-xlarge ">
					<form:options items="${fns:getDictList('movement_inbody_option')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="b_movement:movement:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
	
<script>
	layui.use('upload', function(){
	  var upload = layui.upload;

		//普通图片上传
		var uploadInst = upload.render({
			elem : '#test1',
			url : '${ctx}/file/upload',
			done : function(res) {
				//如果上传失败
				if (res.code != 1) {
					return layer.msg('上传失败');
				}
				//上传成功
				$('#demo1').attr('src', res.data);
				$('#prepareImgUrl').val(res.data);
			},
			error : function() {
				//演示失败状态，并实现重传
				var demoText = $('#demoText1');
				demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
				demoText.find('.demo-reload').on('click', function() {
					uploadInst.upload();
				});
			}
		});


	});
</script>	
<script>
	layui.use('upload', function(){
	  var upload = layui.upload;

		//普通图片上传
		var uploadInst2 = upload.render({
			elem : '#test2',
			url : '${ctx}/file/upload',
			done : function(res) {
				//如果上传失败
				if (res.code != 1) {
					return layer.msg('上传失败');
				}
				//上传成功
				$('#demo2').attr('src', res.data);
				$('#actionImgUrl').val(res.data);
			},
			error : function() {
				//演示失败状态，并实现重传
				var demoText = $('#demoText2');
				demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
				demoText.find('.demo-reload').on('click', function() {
					uploadInst2.upload();
				});
			}
		});


	});
</script>	
	
<script type="text/javascript">
	$(function(){
		$('#demo1').attr('src', '${movement.prepareImgUrl}');
	})
	$(function(){
		$('#demo2').attr('src', '${movement.actionImgUrl}');
	})
	
</script>	
	
</body>
</html>