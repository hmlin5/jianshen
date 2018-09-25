<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>开户行管理</title>
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
		<li><a href="${ctx}/b_openingbank/openingbank/">开户行列表</a></li>
		<li class="active"><a href="${ctx}/b_openingbank/openingbank/form?id=${openingbank.id}">开户行<shiro:hasPermission name="b_openingbank:openingbank:edit">${not empty openingbank.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="b_openingbank:openingbank:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="openingbank" action="${ctx}/b_openingbank/openingbank/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		


<!--         <div class="control-group"> -->
<!-- 			<label class="control-label">开户银行logo:</label> -->
<!-- 			<div class="controls"> -->
<!-- 				<form:hidden id="openingbankLogo" path="openingbankLogoUrl" htmlEscape="false" maxlength="255" class="input-xlarge"/> -->
<!-- 				<sys:ckfinder input="openingbankLogo" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/> -->
<!-- 			</div> -->
<!-- 		</div> -->
        <div class="control-group">
			<label class="control-label">开户银行logo：</label>
			<div class="controls">
				<form:hidden path="openingbankLogoUrl" />
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
			<label class="control-label">开户银行：</label>
			<div class="controls">
				<form:input path="openingbankName" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="b_openingbank:openingbank:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
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
				$('#openingbankLogoUrl').val(res.data);
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
   <script type="text/javascript">
	$(function(){
		$('#demo1').attr('src', '${openingbank.openingbankLogoUrl}');
	})
	
   </script>		
</body>
</html>