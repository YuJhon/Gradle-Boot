
'use strict';

$(function(){
	var _pageSize;
	
	/** 通过用户名获取博客列表 **/
	function getBlogsByName(pageIndex,pageSize){
		$.ajax({
			url:'/u' + username + '/blogs',
			contentType: 'application/json',
			data:{
				"async":true,
				"pageIndex":pageIndex,
				"pageSize":pageSize,
				"category":categoryId,
				"keyword":$("#keyword").val()
			},
			success:function(data){
				$("#mainContainer").html(data);
				
				/** 如果是分类查询，则取消最新，最热选中样式 **/
				if(categoryId){
					$(".nav-item .nav-link").removeClass('active');
				}
			},
			error:function(err){
				toastr.error("error");
			}
		});
	}
	
	
	/** 分页 **/
	$.tbpage("#mainContainer",function(pageIndex,pageSize){
		getBlogsByName(pageIndex,pageSize);
		_pageSize = pageSize;
	});
	
	/** 关键字搜索 **/
	$(".nav-item .nav-link").click(function(){
		var url = $(this).attr("url");
		
		/** 先移除其他的点击样式，再添加当前的点击样式 **/
		$(".nav-item .nav-link").removeClass('active');
		$(this).addClass('active');
		
		/** 加载其他的模块到右边区域 **/
		$.ajax({
			url:url+'&async=true',
			success:function(data){
				$("#mainController").html(data);
			},
			error:function(){
				toastr.error('error');
			}
		});
		
		/** 清空搜素框 **/
		$("#keyword").val('');
	});
	
	/** 获取分类列表 **/
	function getCategories(){
		$.ajax({
			url:"/categories",
			type:"GET",
			data:{
				"username":username
			},
			success:function(data){
				$("#categoryMain").html(data);
			},
			error:function(err){
				console.error(err);
				toastr('error');
			}
		});
	}
	
	/** 获取编辑分类的页面 **/
	$(".blog-content-container").on('click','.blog-add-category',function(){
		$.ajax({
			url:'/categories/edit',
			type:'GET',
			success:function(data){
				$("#categoryFormContainer").html(data);
			},
			error:function(err){
				console.error(err);
				toastr.error('error');
			}
		});
	});
	
	/** 编辑特定分类的页面 **/
	$('.blog-content-container').on('click','blog-edit-category',function(){
		$.ajax({
			url:'/categories/edit/'+$(this).attr('categoryId'),
			type:'GET',
			success:function(data){
				$("#categoryFormContainer").html(data);
			},
			error:function(err){
				toastr.error('error!');
			}
		});
	});
	
	/** 提交分类 **/
	$("#submitEditCategory").click(function(){
		/** 获取CSRF TOKEN **/
		var csrfToken = $("meta[name='_csrf']").attr('content');
		var csrfHeader = $("meta[nam='_csrf_header']").attr('content');
		
		$.ajax({
			url:'categories',
			type:'POST',
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify({
				"username":username,
				"category":{
					"id":$("#categoryId").val(),
					"name":$("#categoryName").val()
				}
			}),
			beforeSend: function(request){
				/** 添加  CSRF Token **/
				request.setRequestHeader(csrfHeader,csrfToken);
			},
			success:function(data){
				if(data.success){
					toastr.info(data.message);
					/** 成功后 **/
					getCategories(username);
				}else{
					toastr.error(data.message);
				}
			},
			error:function(err){
				console.error(err);
				toastr.error('error');
			}
		});
	});
	
	
	/** 删除分类 **/
	$(".blog-content-container").on('click','.blog-delete-category',function(){
		/** 获取CSRF TOKEN **/
		var csrfToken = $("meta[name='_csrf']").attr('content');
		var csrfHeader = $("meta[nam='_csrf_header']").attr('content');
		
		$.ajax({
			url:'categories/'+$(this).attr("categoryId")+'?username='+username,
			type:'POST',
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify({
				"username":username,
				"category":{
					"id":$("#categoryId").val(),
					"name":$("#categoryName").val()
				}
			}),
			beforeSend: function(request){
				/** 添加  CSRF Token **/
				request.setRequestHeader(csrfHeader,csrfToken);
			},
			success:function(data){
				if(data.success){
					toastr.info(data.message);
					/** 成功后 **/
					getCategories(username);
				}else{
					toastr.error(data.message);
				}
			},
			error:function(err){
				console.error(err);
				toastr.error('error');
			}
		});
	});
	
	
	/** 根据分类查询 **/
	$(".blog-content-container").on('click','.blog-query-by-category',function(){
		categoryId = $(this).attr("categoryId");
		getBlogsByName(0,_pageSize);
	});
	
	
	getCategories(username);
});