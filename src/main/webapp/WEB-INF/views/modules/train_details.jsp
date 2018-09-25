<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
 


	<meta name="decorator" content="default"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <!--  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=no" name="format-detection" /> -->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<meta name="format-detection" content="telephone=no,email=no" />
	<meta name="HandheldFriendly" content="true"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/tran/base_forum.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/tran/forum1.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/tran/Tip.css">
	
	<link rel="stylesheet" href="${ctxGimc}/layui/css/layui.css">
	<script type="text/javascript" src="${ctxGimc}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/ckeditor/ckeditor.js"></script>
	<!--  <link media="all" type="text/css" rel="stylesheet" href="http://news.fx678.com/newspaper/css/content.css?it=20171118">
    <link media="all" type="text/css" rel="stylesheet" href="http://news.fx678.com/newspaper/css/font-awesome.min.css"> -->
    


</head>
<body>
		
<div class="container">
    <div class="information-subGage-article fff">
        <div class="top pd15">
            <h1>${train.title}</h1>
            <span><fmt:formatDate value="${train.publishTime}" pattern="yyyy-MM-dd HH:mm"/> &nbsp;${train.publisher}</span>
        </div>
        <div  id="content" class="content">
        	<!-- <div class="main clearfix"> -->
		        <!--左侧文章-->
		       <!--  <div class="col3-2"> -->
					<c:out value="${train.info}"  escapeXml="false" />
				<!-- </div> -->
			<!-- </div> -->	
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        $('.content img').each(function(){
            $(this).css({
                'height':'inherit',
                'max-width':'100%'
            }).parent().css('text-indent','inherit');
        });
        $('.content table').each(function(){
            $(this).attr("width","100%");
        });
        $("table td").attr("align","center");
        $('.content table').each(function(){
            $(this).css({
                'height':'inherit',
                'max-width':'100%'
            }).parent().css('text-indent','inherit');
        });
       /*  $('.content td').each(function(){
            $(this).css({
                'height':'inherit',
                'max-width':'100%'
            }).parent().css('text-indent','inherit');
        });
        $('.content tr').each(function(){
            $(this).css({
                'height':'inherit',
                'max-width':'100%'
            }).parent().css('text-indent','inherit');
        }); */
    }); 
    
    /* function htmlEscape(text){ 
	  return text.replace(/&(lt|gt|nbsp|amp|quot);/ig, function(match, pos, originalText){
	    switch(match){
		    case "&lt;": return "<"; 
		    case "&gt;":return ">";
		    case "&amp;":return "&"; 
		    case "&quot;":return "\""; 
	  	} 
	  }); 
	} */
    
</script>	
</body>
</html>