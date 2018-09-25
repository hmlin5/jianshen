//全屏滚动
$(function () {
	$('body').append('<style>.slider-banner{width:100%; height:475px;overflow:hidden;position:relative;font-size:0}.slider-banner .b-img{height:475px;position:absolute;left:0;top:0}.slider-banner .b-img a{display:block;height:475px;float:left}.slider-banner .b-list{height:50px;padding-top:450px;position:relative;margin:0 auto;z-index:1}.slider-banner .b-list span{display:block;cursor:pointer;width:10px;height:10px;border-radius:50%;background:#fff;float:left;margin:0 5px;_margin:0 3px}.slider-banner .b-list .spcss{background:#855a27;border:1px solid #855a27}.slider-banner .bar-left{position:absolute;z-index:1;display:block;width:100px;height:100%;left:0;background:0;top:0}.slider-banner .bar-right{position:absolute;z-index:1;display:block;width:100px;height:100%;right:0;background:0;top:0}.slider-banner .bar-left em{display:block;width:50px;height:100px;background:url(images/arrow.png) 0 0 no-repeat;margin:0 auto;margin-top:170px;opacity:.7}.slider-banner .bar-right em{display:block;width:50px;height:100px;background:url(images/arrow.png) -50px 0 no-repeat;margin:0 auto;margin-top:170px;opacity:.7}.slider-banner .bar-left .emcss{background-position:0 -100px}.slider-banner .bar-right .emcss{background-position:-50px -100px}</style>');

});
$(function(){
	var n=0;
	var imgLength=$(".b-img a").length;
	var ctWidth=imgLength*100;
	var itemWidth=1/imgLength*100;
	$(".b-img").width(ctWidth+"%");
	$(".b-img a").width(itemWidth+"%");
	$(".b-list").width(imgLength*30);
	if(imgLength>1)
	{
	for(var i=0;i<imgLength;i++)
	{
		var listSpan=$("<span></span>")
		$(".b-list").append(listSpan);
		}
	}
	$(".b-list span:eq(0)").addClass("spcss").siblings("span").removeClass("spcss");
	$(".bar-right em").click(function(){
		if(n==imgLength-1)
		{
			var ctPosit=(n+1)*100;
			$(".slider-banner").append($(".b-img").clone());
			$(".b-img:last").css("left","100%");
			$(".b-img:first").animate({"left":"-"+ctPosit+"%"},1000);
			$(".b-img:last").animate({"left":"0"},1000);
			var setTime0=setTimeout(function () {
            $(".slider-banner .b-img:first").remove();
            }, 1000);
			n=0;
			$(".b-list span:eq("+n+")").addClass("spcss").siblings("span").removeClass("spcss");
			}
		else
		{
		n++;
		var ctPosit=n*100;
		$(".b-img").animate({"left":"-"+ctPosit+"%"},1000);
		$(".b-list span:eq("+n+")").addClass("spcss").siblings("span").removeClass("spcss");
		}
		})
	$(".bar-left em").click(function(){
		if(n==0)
		{
			var stPosit=imgLength*100;
			var etPosit=(imgLength-1)*100;
			$(".slider-banner").prepend($(".b-img").clone());
			$(".b-img:first").css("left","-"+stPosit+"%");
			$(".b-img:last").animate({"left":"100%"},1000);
			$(".b-img:first").animate({"left":"-"+etPosit+"%"},1000);
			var setTime0=setTimeout(function () {
            $(".slider-banner .b-img:last").remove();
            }, 1000);
			n=imgLength-1;
			$(".b-list span:eq("+n+")").addClass("spcss").siblings("span").removeClass("spcss");
			}
		else
		{
		n--;
		var ctPosit=n*100;
		$(".b-img").animate({"left":"-"+ctPosit+"%"},1000);
		$(".b-list span:eq("+n+")").addClass("spcss").siblings("span").removeClass("spcss");
		}
		})
	$(".b-list span").click(function(){
		var lsIndex=$(this).index();
		n=lsIndex;
		var ctPosit=n*100;
		$(".b-img").animate({"left":"-"+ctPosit+"%"},1000);
		$(this).addClass("spcss").siblings("span").removeClass("spcss");
		})
	function rollEnvent(){
		if(n==imgLength-1)
		{
			var ctPosit=(n+1)*100;
			$(".slider-banner").append($(".b-img").clone());
			$(".b-img:last").css("left","100%");
			$(".b-img:first").animate({"left":"-"+ctPosit+"%"},1000);
			$(".b-img:last").animate({"left":"0"},1000);
			var setTime0=setTimeout(function () {
            $(".slider-banner .b-img:first").remove();
            }, 1000);
			n=0;
			$(".b-list span:eq(0)").addClass("spcss").siblings("span").removeClass("spcss");
			}
		else
		{
		n++;
		var ctPosit=n*100;
		$(".b-img").animate({"left":"-"+ctPosit+"%"},1000);
		$(".b-list span:eq("+n+")").addClass("spcss").siblings("span").removeClass("spcss");
		}
		}
	var slidesetInterval=setInterval(rollEnvent,4000);
	$(".slider-banner").hover(function(){clearInterval(slidesetInterval);},function(){slidesetInterval=setInterval(rollEnvent,4000);});
	$(".bar-left").mouseover(function(){
		$(this).css("background","url(images/arr-bg.png)");
		$(this).find("em").addClass("emcss");
		}).mouseleave(function(){
		$(this).css("background","none");
		$(this).find("em").removeClass("emcss");
			})
	$(".bar-right").mouseover(function(){
		$(this).css("background","url(images/arr-bg.png)");
		$(this).find("em").addClass("emcss");
		}).mouseleave(function(){
		$(this).css("background","none");
		$(this).find("em").removeClass("emcss");
			})
	})
