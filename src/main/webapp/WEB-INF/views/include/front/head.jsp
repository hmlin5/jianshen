<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
	String requestUrl = request.getRequestURI();
%>
<div class="head">
    <div class="top-link">
        <div class="container">
            <div class="d1">
                <a href="" target="_blank">南海区文化区委</a> |
                <a href="" target="_blank"><img src="${ctxGimc}/images/wh1_03.png"> 微信</a> |
                <a href="" target="_blank"><img src="${ctxGimc}/images/wh1_03.png"> app</a>
            </div>
            <div class="d3">
                <a href="" target="_blank">登陆</a> |
                <a href="" target="_blank">注册</a>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="logo-search">
            <a href="" class="logo" target="_self"><img src="${ctxGimc}/images/wh1_10.png"></a>
            <div class="search">
                <form>
                    <input type="text" placeholder="按Enter搜索">
                    <div class="btn" title="搜索"></div>
                </form>
            </div>
        </div>
    </div>
    <div class="nav">
        <div class="container">
            <a href="${ctxFront}" id="homeIndex">首页</a>
            <a href="${ctxFront}/cgyy/index" id="cgyy">文化有约</a>
            <a href="${ctxFront}/cyhd/index" id="cyhd">才艺活动</a>
            <a href="${ctxFront}/nhmp/index" id="nhmp">南海名片</a>
            <a href="" >全民阅读</a>
            <a href="${ctxFront}/szzt/index" id="szzt">数字展厅</a>
            <a href="" >政策法规</a>
            <a href="" >在线客服</a>
        </div>
    </div>
</div>
<script>
	$(function(){
		var requestUrl = "<%=requestUrl%>"; 
		
		var isHomePage = true;
		$(".container a").each(function (i){
			if(requestUrl.indexOf($(this).attr('id'))!=-1){
				$(this).addClass('active').siblings().removeClass('active');
				isHomePage = false;
			}
		});
		if(isHomePage){
			$('#homeIndex').addClass('active');
		}
	});
</script>
