$(document).ready(
	function(){
		$.getJSON({
			url: 'modify-user.action',
			data: { get : '1' },
			error: function(request) {
				layer.msg("connection error!", {icon:2, shift:6});
			},
			success: function(result){
				var type = result.type;
				if (type == "error") {
					layer.msg(result.content, {
						icon : 2
					});
					return;
				}
				var content = result.content;
				var id = content.id;
				var email = content.email;
				var username = content.username;
				var port = content.port;
				var transfer_enable = (content.transfer_enable/1024/1024).toFixed(2);
				var is_enabled = content.is_enabled;
				$('#id').val(id);
				$('#email').val(email);
				$('#username').val(username);
				$('#port').val(port);
				$('#transfer_enable').val(transfer_enable);
				if (is_enabled == 1)
					$('#enabled').attr("checked","checked");
				else
					$('#disabled').attr("checked","checked");
				
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
									} ,
									id : function() {
										return $("#id").val();
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
									},
									id : function() {
										return $("#id").val();
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
			}
	});
});

submitdata = function() {
	$('#transfer_enable').val($('#transfer_enable').val() * 1024 * 1024);
	$.ajax({
		type : "post",
		url : "modify-user.action",
		dataType : "json",
		data : "modify=1&" + $('#form-member-add').serialize(),
		success : function(msg) {
			if (msg.type == 'success') {
				// do things here
				parent.layer.msg('修改成功',
					{
						icon : 1 ,
						shade : 0.4,
						time: 1000
					}
				);
				parent.$('#init-table-data').click();
				parent.layer.close(parent.layer.getFrameIndex(window.name));
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
