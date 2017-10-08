
'use strict';

$(function(){
	/** 初始化 **/
	$.catalog('#category','post-content');
	
	/** 删除博客 **/
	$(".blog-content-container").on('click','.blog-delete-blog',function(){
		/** 获取 CSRF Token  **/
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		
		$.ajax({
			url:blogUrl,
			type:'DELETE',
			beforeSend:function(request){
				request.setRequestHeader(csrfHeader,csrfToken);
			},
			success:function(data){
				if(data.success){
					window.location = data.body;
				}else{
					toastr.error(data.message);
				}
			},
			error:function(err){
				toastr.error("error!");
			}
		});
	});
	
	/** 获取评论列表 **/
	function getComment(blogId){
		/** 获取 CSRF Token  **/
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		
		$.ajax({ 
			 url: '/comments', 
			 type: 'GET', 
			 data:{"blogId":blogId},
			 beforeSend: function(request) {
	             request.setRequestHeader(csrfHeader, csrfToken);
	         },
			 success: function(data){
				$("#mainContainer").html(data);
	
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	}
	
	/** 删除评论  **/
	$(".blog-content-container").on("click",".blog-delete-comment", function () { 
		/** 获取 CSRF Token  **/
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
 		
		$.ajax({ 
			 url: '/comments/'+$(this).attr("commentId")+'?blogId='+blogId, 
			 type: 'DELETE', 
			 beforeSend: function(request) {
                 request.setRequestHeader(csrfHeader, csrfToken); 
             },
			 success: function(data){
				 if (data.success) {
					 // 获取评论列表
					 getCommnet(blogId);
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
	
	
	/** 提交点赞 **/
	$(".blog-content-container").on("click","#submitStar", function () { 
		/** 获取 CSRF Token  **/
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
 		
		$.ajax({ 
			 url: '/stars', 
			 type: 'POST', 
			 data:{"blogId":blogId},
			 beforeSend: function(request) {
                 request.setRequestHeader(csrfHeader, csrfToken);
             },
			 success: function(data){
				 if (data.success) {
					 toastr.info(data.message);
						// 成功后，重定向
					 window.location = blogUrl;
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
	
	/** 提交点赞 **/
	$(".blog-content-container").on("click","#cancelStar", function () { 
		/** 获取 CSRF Token  **/
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
 		
		$.ajax({ 
			 url: '/stars/'+$(this).attr('starId')+'?blogId='+blogId, 
			 type: 'DELETE', 
			 beforeSend: function(request) {
                 request.setRequestHeader(csrfHeader, csrfToken); 
             },
			 success: function(data){
				 if (data.success) {
					 toastr.info(data.message);
					 // 成功后，重定向
					 window.location = blogUrl;
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
	
	/** 初始化 博客评论  **/
	getComment(blogId);
});