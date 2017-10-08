
'use strict';

$(function(){
	// 初始化 md 编辑器
    $("#md").markdown({
        language: 'zh',
        fullscreen: {
            enable: true
        },
        resize:'vertical',
        localStorage:'md',
        imgurl: 'http://localhost:8089',
        base64url: 'http://localhost:8089'
    });
    
	/** 初始化下拉 **/
    $('.form-control-chosen').chosen();
    
    
    /** 初始化标签 **/
    $('.form-control-tag').tagsInput({
    	'defaultText':'输入标签'
    });
 
    $("#uploadImage").click(function() {
		$.ajax({
		    url: 'http://localhost:8089/upload',
		    type: 'POST',
		    cache: false,
		    data: new FormData($('#uploadformid')[0]),
		    processData: false,
		    contentType: false,
		    success: function(data){
		    	var mdcontent=$("#md").val();
		    	 $("#md").val(mdcontent + "\n![]("+data +") \n");
 
	         }
		}).done(function(res) {
			$('#file').val('');
		}).fail(function(res) {});
 	})
 
 	// 发布博客
 	$("#submitBlog").click(function() {
		// 获取 CSRF Token 
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");

		$.ajax({
		    url: '/u/'+ $(this).attr("userName") + '/blogs/edit',
		    type: 'POST',
			contentType: "application/json; charset=utf-8",
		    data:JSON.stringify(
		    	{	"id":$('#blogId').val(), 
			    	"title": $('#title').val(), 
			    	"summary": $('#summary').val() , 
			    	"content": $('#md').val(), 
			    	"category":{"id":$('#categorySelect').val()},
			    	"tags":$('.form-control-tag').val()
		    	}),
			beforeSend: function(request) {
			    request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token 
			},
			success: function(data){
				if (data.success) {
					// 成功后，重定向
					window.location = data.body;
				} else {
					toastr.error("error!"+data.message);
				}
		    },
		    error : function() {
		    	toastr.error("error!");
		    }
		})
 	});
    
});