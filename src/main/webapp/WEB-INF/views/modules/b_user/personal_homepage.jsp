<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no,email=no" />
<meta name="HandheldFriendly" content="true"/>
<!--公共资源-->
	<link rel="stylesheet" href="${ctxBurn}/css/base.css">
	<script type="text/javascript" src="${ctxBurn}/js/jquery.min.js"></script>
	<link rel="stylesheet" href="${ctxBurn}/layui/css/layui.css">
	<script type="text/javascript" src="${ctxBurn}/layui/layer.js"></script>


<title>Hi 教练来了！伙伴们，约吗?</title>

<style type="text/css">
	/* .personal_homepage .bg{ height:190px; width:100%; background:url(${ctxBurn}/images/img_p_bg.jpg) center center no-repeat; background-size:cover;} */
	.bg{ height:80%; width:100%; background:url(${user.bgImgUrl}) center center no-repeat; background-size:cover;}

</style>


</head>
<body>
<!--顶部固定条，打开App-->
<%-- <div class="appurl">
	<div class="bar">
		<p class="appname"><img src="${ctxBurn}/images/icon_logo.png">Hi Coach! - <span> 教练来啦！</span></p><a href="#" class="download">打开APP</a>
	</div>
	<div class="fill"><!--顶部固定条空白填充--></div>
</div> --%>
<div class="bg"></div>
<div class="personal_homepage all">

	<div class="cont">
		<div class="empty200"></div>
	<div class="p_content">
		<div class="c_block survey">
			<div class="s_out">
				<div class="s_inner">
					<div class="main_inf">
						<div class="img"><img src="${user.headImgUrl}"></div>
						<div class="name">
							<p class="nickname">
								<c:if test="${user.homePageType == '4' }">${user.realName }</c:if>
								<c:if test="${user.homePageType != '4' }">${user.nickName }</c:if>
							<c:if test="${user.sex == '0' }"><i class="sex_female"></i></p></c:if>
							<c:if test="${user.sex == '1' }"><i class="sex_male"></i></p></c:if>
							<c:if test="${user.homePageType == '4' }">
								<p class="type">${user.label }</p>
							</c:if>
						</div>
					</div>
					<div class="fans">
						<p onclick="pClick('1')"><span>关注</span> ${user.followNum }</p>
						<p onclick="pClick('2')"><span>粉丝</span> ${user.followerNum }</p>
					</div>
					<c:if test="${user.meFlag != '1' }">
						
						<div class="follow">
							<c:if test="${user.relationFlag == '1' }">
								<a  href="javascript:void(0)" onclick="aClick('1')">+ 关注</a>
							</c:if>
							<c:if test="${user.relationFlag == '2' }">
								<a  href="javascript:void(0)" onclick="aClick('2')">已关注</a>
							</c:if>
							<c:if test="${user.relationFlag == '3' }">
								<a  href="javascript:void(0)" onclick="aClick('2')">互相关注</a>
							</c:if>
						</div>
					</c:if>
				</div>
			</div>
		</div>
		<c:if test="${user.homePageType == '4' }">
			<div class="c_block top10">
				<div class="mymember">
					<p><span class="num">${user.totalCourseNum }</span><span>累计课程/节</span></p>
					<p><span class="num">${user.myStuNum }</span><span>累计私教会员</span></p>
				</div>
			</div>
		</c:if>
		<div class="c_block top10">
			<div class="title_h" id="title">关于我</div>
			<div class="content_b"><p class="branddesc">${user.intro }</p></div>
		</div>
		<c:if test="${user.homePageType == '4' }">
			<div id="commentDiv" class="c_block top10">
				<div class="title_h" >会员评价</div>
				<div class="evaluation" id="commentList">
					<%-- <c:forEach items="${comments }" var="comment">
						<div class="e_list">
							<div class="e_h">
								<p class="img"><img src="${comment.headImgUrl }"></p>
								<p class="name">${comment.stuName }<i class="sex_female"></i></p>
								<p class="score"><span>评分:</span> <fmt:formatNumber type="number" maxIntegerDigits="2" pattern="0.0" value="${comment.total }" /></p>
								<p class="time"><fmt:formatDate value="${comment.commentTime}" pattern="yyyy-MM-dd HH:mm"/></p>
							</div>
							<div class="e_c">${comment.content }</div>
						</div>
					</c:forEach> --%>
					
				</div>
			</div>
		</c:if>
		<!-- <div class="c_block top10">
			<ul class="d_level">
				<li><a href="#"><p class="lc">会员有效期</p><p class="rc">截止2018-05-31</p></a></li>
				<li><a href="#"><p class="lc">剩余私教课</p><p class="rc">33节<i class="arrow_r"></i></p></a></li>
				<li><a href="#"><p class="lc">教练评诂报告</p><p class="rc">查看<i class="arrow_r"></i></p></a></li>
			</ul>
		</div> -->
		
	</div>
	</div>
</div>
<script>
//内容超过规定字数隐藏或显示
(function($){
	$.fn.moreText = function(options){
		var defaults = {
			maxLength:50,
			mainCell:".branddesc",
			openBtn:'展开查看更多',
			closeBtn:'收起'
		}
		return this.each(function() {
			var _this = $(this);
			
			var opts = $.extend({},defaults,options);
			var maxLength = opts.maxLength;
			var TextBox = $(opts.mainCell,_this);
			var openBtn = opts.openBtn;
			var closeBtn = opts.closeBtn;
			
			var countText = TextBox.html();
			var newHtml = '';
			if(countText.length > maxLength){
				newHtml = countText.substring(0,maxLength)+'...<a hraf="javascripts:;" class="more">'+openBtn+'</a>';
			}else{
				newHtml = countText;
			}
			TextBox.html(newHtml);
			TextBox.on("click",".more",function(){
				if($(this).text()==openBtn){
					TextBox.html(countText+' <a hraf="javascripts:;" class="more">'+closeBtn+'</a>');
				}else{
					TextBox.html(newHtml);
				}
			})
		})
	}
})(jQuery);
$(function(){
	$(".c_block .content_b").moreText({
		maxLength: 35, //默认最大显示字数，超过...
		mainCell: '.branddesc' //文字容器
	});
})
</script>

<script type="text/javascript">
	<!-- relation=1:目前状态未关注, relation=2:目前状态已关注, -->
	function aClick (relation) {  
		try{
	         webkit.messageHandlers.callbackHandle.postMessage(relation)
	     }catch(error){
	         console.error('The native context not exist ')
	     }
		app.focus(relation); 
    }

	<!-- relation=1:查看关注列表, relation=2:查看粉丝列表, -->
	function pClick (relation) {  
		try{
        	 webkit.messageHandlers.callbackHandle2.postMessage(relation)
	     }catch(error){
	         console.error('The native context not exist ')
	     }
		 burning.list(relation);

    }


	
</script>



<!--评论分页方法  -->
<script>
	$(function() {
		if(document.getElementById("commentDiv")){
		
			getCommentListData();
		}
		
	});
	var pageIng = 1;//初始页
	var pages = 3;//模拟总页数
	var inBottom = true;
	
	var pageSize = 4;
	
	//滑动监听
	window.onscroll = function() {
		
		var scrolltop=document.documentElement.scrollTop||document.body.scrollTop;
		if (($(window).scrollTop() + 50) >= ($(document).height() - $(window).height())) {
			 if (pageIng >= pages && !inBottom) {
				inBottom = true;
			} else {
				if (!inBottom) {
					pageIng++;
					inBottom = true;
					getCommentListData(pageIng);
				}
			}
			
		}
	};
	
	
	
	function getCommentListData(currentPage){
		if(currentPage==undefined){
			currentPage = 1;
		}
	    
	    //loading层
		//var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
		$.ajax({
	            cache: true,
	            type: "POST",
	            url:"${pageContext.request.contextPath}/api/user/commentList",
	            data:{userId:'${user.id}',currentPage:currentPage,pageSize:pageSize} ,
	            async: true,
	            dataType:"json",
	            error: function(request) {
	            	/* layer.close(index);
	                layer.msg("加载中,请稍等..."); */
	            },
	            success: function(data) {
	            	if(data.data.pageData.length>0){
	                	var html = "";
	                	$(data.data.pageData).each(function(i,comment){
	                		
	                		var sex = comment.sex == '1' ? 'sex_male' : 'sex_female';
	                		
		                	html += "	<div class='e_list'>                                                     "+
									"	<div class='e_h'>                                                        "+
									"		<p class='img'><img src='"+comment.headImgUrl+"'></p>                "+
									"		<p class='name'>"+comment.stuName+"<i class='"+sex+"'></i></p>    "+
									"		<p class='score'><span>评分:</span>"+comment.total.toFixed(1)+" </p>                             "+
									"		<p class='time'>"+dateConvert(comment.commentTime)+"</p>                                                 "+
									"	</div>                                                                   "+
									"	<div class='e_c'>"+comment.content+"</div>                               "+
									"	</div>";

	                	});
			    		$("#commentList").append(html);
			    		
			    		pages = Math.ceil(data.data.totalRecord/4);
	            	}
		    		
		    		inBottom = false;
	            }
		  });
		
	}
</script>


<script type="text/javascript">
 
//定义日期格式化函数
function dateConvert(dateParms){
	<%--这里datetime定义在局部,下面可以用到这个datetime,为什么 --%>
    // 对传入的时间参数进行判断
    if(dateParms instanceof Date){
        var datetime=dateParms;
        
    }else if((typeof dateParms=="string")&&dateParms.constructor==String){//判断是否为字符串
         
        //将字符串日期转换为日期格式
        var datetime= new Date(Date.parse(dateParms.replace(/-/g,   "/")));
      //  var datetime= new Date(dateParms);
     
    }else {//参数不符合,直接返回
    	return 
    }
    
    //var datetime= new Date(Date.parse(dateParms.replace(/-/g,   "/")));
     
    //获取年月日时分秒
     var year = datetime.getFullYear();
     var month = datetime.getMonth()+1; 
     var date = datetime.getDate(); 
     var hour = datetime.getHours(); 
     var minutes = datetime.getMinutes(); 
     var second = datetime.getSeconds();
     
     //月，日，时，分，秒 小于10时，补0
     if(month<10){
      month = "0" + month;
     }
     if(date<10){
      date = "0" + date;
     }
     if(hour <10){
      hour = "0" + hour;
     }
     if(minutes <10){
      minutes = "0" + minutes;
     }
     if(second <10){
      second = "0" + second ;
     }
      
     //拼接日期格式【例如：yyyymmdd】
     var time = year+"-"+month+"-"+date+" "+hour+":"+minutes; 
      
     //或者：其他格式等
     //var time = year+"年"+month+"月"+date+"日"+hour+":"+minutes+":"+second; 
      
     //返回处理结果
     return time;
    }
 
//document.write(dateConvert("2015-07-12 12:23:20"));
//输出结果：20150712
</script>

<script>
    //下拉露出更多背景图片
    var _sroll = document.querySelector('.all');
    var outscroll = document.querySelector('.cont');
    var _test = document.querySelector('#test');
    var _star = 0;
    //获取手指最初的位置,添加一个触摸开始的监听事件
    _sroll.addEventListener('touchstart',function(event){
        //获取手指数组中的第一个（可以用targetTouches）
        var _touch = event.touches[0];
        //获取滑动时手指的y坐标+
        _star = _touch.pageY;
    }, false);
    //获取滑动的距离，添加一个触摸滑动的监听事件
    _sroll.addEventListener('touchmove',function(event){
        //获取手指数组中的第一个（可以用targetTouches）
        var _touch = event.touches[0];
        outscroll.style.top = outscroll.offsetTop + _touch.pageY-_star + 'px';
        //获取滑动后手指的y坐标
        _star = _touch.pageY;
        _test.innerHTML = '放开刷新';
    }, false);
    //添加屏幕触摸接触结束的事件
    _sroll.addEventListener('touchend',function(event){
        //初始化手指触摸的y坐标
        _star = 0;
        //获取下拉元素的top值
        var top = outscroll.offsetTop;
        //如果大于40就刷新
        if(top>0){
            //循环慢慢的收缩
            var time = setInterval(function(){
                outscroll.style.top = outscroll.offsetTop -2+'px';
                //如果top等于原始值，停止收缩
                if(outscroll.offsetTop<=-40){
                    clearInterval(time);
                    //刷新页面
                    //暂时不需要刷新 location.reload();
                }
            },1);
        }
    }, false);
</script>




</body>
</html>