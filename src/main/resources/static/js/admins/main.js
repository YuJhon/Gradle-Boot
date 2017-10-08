/**
 * 博客管理员
 */

'use strict';

$(function(){
	
	/** 菜单事件 **/
	$(".blog-menu .list-group-item").click(function(){
		var url = $(this).attr('url');
		$(".blog-menu .list-group-item").removeClass("active");
		$(this).addClass("active");
		
		 $.ajax({ 
			 url: url, 
			 success: function(data){
				 $("#rightContainer").html(data);
		 },
		 error : function() {
		     alert("error");
		     }
		 });
	});
	
	/** 默认选择第一 **/
	$(".blog-menu .list-group-item:first").trigger("click");
});