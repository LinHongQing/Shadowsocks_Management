$(function() {
	$('.skin-minimal input').iCheck({
		checkboxClass : 'icheckbox-blue',
		radioClass : 'iradio-blue',
		increaseArea : '20%'
	});

	$("#form-member-add").validate({
		rules : {
			username : {
				required : true,
				minlength : 2,
				maxlength : 16
			},
			email : {
				required : true,
				email : true,
				remote : {
					url : "../validate.action",
					type : "post",
					dataType : "json",
					data : {
						email : function() {
							return $("#email").val();
						}
					},
					dataFilter : function(data) { // 判断控制器返回的内容
						if (data == "true") {
							return true;
						} else {
							return false;
						}
					}
				}
			},
			port : {
				required : true,
				number : true,
				range : [ 1024, 65535 ],
				min : 0,
				remote : {
					url : "../validate.action",
					type : "post",
					dataType : "json",
					data : {
						port : function() {
							return $("#port").val();
						}
					},
					dataFilter : function(data) { // 判断控制器返回的内容
						if (data == "true") {
							return true;
						} else {
							return false;
						}
					}
				}
			},
			transfer_enable : {
				required : true,
				number : true
			}
		},
		messages : {
			email : {
				remote : "邮箱已存在"
			},
			port : {
				remote : "端口已被占用"
			}
		},
		onfocus : true,
		onkeyup : false,
		focusCleanup : true,
		success : "valid",
		submitHandler : function(form) {
			submitdata();
		}
	});
});
submitdata = function() {
	$('#transfer_enable').val($('#transfer_enable').val() * 1024 * 1024);
	$.ajax({
		type : "post",
		url : "add-user.action",
		dataType : "json",
		data : $('#form-member-add').serialize(),
		success : function(msg) {
			if (msg.type == 'success') {
				// do things here
				$('#btn-reset').click();
				layer.msg('用户添加成功', {
					time : 2000,
					icon : 1
				}, function(){
					parent.$('#init-table-data').click();
				});
			} else {
				// do things here
				layer.msg(msg.content, {
					icon : 2
				});
			}
		},
		error : function() {
			layer.msg("connection error", {
				icon : 2
			});
		}
	});
	return false;
}
