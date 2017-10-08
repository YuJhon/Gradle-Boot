/**
 * 用户管理
 */

'use strict';
/** DOM 加载完再执行 **/
$(function(){
	var _pageSize;
	
	/** 获取用户列表 **/
	function getUsersByName(pageIndex,pageSize){
		$.ajax({
			url:"/users",
			content-Type:"application/json",
			data:{
				"async":true,
				"pageIndex":pageIndex,
				"pageSize":pageSize,
				"name":$("#searchName").val()
			},
			success:function(data){
				$("#mainContainer").html(data);
			},
			error:function(err){
				toastr.error("error!");
			}
		});
	}
	
	/** 分页 **/
	$.tabpage("#mainContainer",function(pageIndex,pageSize){
		getUsersByName(pageIdex,pageSize);
		_pageSize = pageSize;
	});
	
	/** 搜索 **/
	$("#searchNameBtn").click(function(){
		getUsersByName(0,_pageSize);
	});
	
	/** 获取添加用户的界面 **/
	$("#addUser").click(function(){
		$.ajax({
			url:"/users/add",
			success:function(data){
				$("#userFormContainer").html(data);
			},
			error:function(err){
				toastr.error("error");
			}
		});
	});
	
	/** 获取编辑用户的界面 **/
	$("#rightContainer").on('click',".blog-edit-user",function(){
		$.ajax({
			url:"/users/edit/"+$(this).attr("userId"),
			success:function(data){
				$("#userFormContainer").html(data);
			},
			error:function(err){
				toastr.error("error!");
			}
		});
	});
	
	/** 提交变更后，清空列表 **/
	$("#submitEdit").click(function(){
		$.ajax({
			url:"/users",
			type:"POST",
			data:$('#userForm').serialize(),
			success:function(data){
				$('#userForm')[0].reset();
				if(data.success){
					/** 刷新页面 **/
					getUsersByName(0,_pageSize);
				}else{
					toastr.error(data.message);
				}
			},
			error:function(err){
				toastr.error(err);
			}
		});
	});
	
	/** 删除用户 **/
	$("#rightContainer").on('click','.blog-delete-user',function(){
		/** 获取CSRF Token **/
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		
		$.ajax({
			url:"/users/"+$(this).attr("userId"),
			type:'DELETE',
			beforeSend:function(request){
				request.setRequestHeader(csrfHeader,csrfToken);// 添加CSRF TOKEN
			},
			success:function(data){
				if(data.success){
					getUsersByName(0,_pageSize);
				}else{
					toastr.error(data.message);
				}
			},
			error:function(err){
				toastr.error('error');
			}
		});
	});
});
