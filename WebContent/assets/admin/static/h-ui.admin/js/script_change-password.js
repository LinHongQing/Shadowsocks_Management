$(document).ready(function(){
	$.getJSON({
		url: 'change-password.action',
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
			$('#username').html(content.username);
			$('#id').html(content.id);
		}
	});
	$.getJSON({
		url: '../getpubkey.action',
		error: function(request) {
			layer.msg("connection error!", {icon:2, shift:6});
		},
		success: function(result){
			$('#moduls').val(result.moduls);
			$('#exponent').val(result.exponent);
		}
	});
	$("#form-change-password").validate({
		rules:{
			pass:{
				minlength:6,
				maxlength:16
			},
			pass2:{
				minlength:6,
				maxlength:16,
				equalTo: "#pass"
			},
			passwd:{
				minlength:6,
				maxlength:16,
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			submitdata();
		}
	});
});

submitdata = function(){
	var modulus = $('#moduls').val();
	var exponent = $('#exponent').val();
	var id = $('#id').html().trim();
	var pass = $('#pass').val();
	var passwd = $('#passwd').val();
	var key = RSAUtils.getKeyPair(exponent, '', modulus);
	if (pass == '' && passwd == '') {
		parent.layer.msg('密码未修改',
				{
					icon : 3 ,
					shade : 0.4,
					time: 1000
				}
			);
		parent.$('#init-table-data').click();
		parent.layer.close(parent.layer.getFrameIndex(window.name));
	} else {
	    $.ajax({ 
	    	type: "post",	//表单提交类型
	    	url: "change-password.action",	//表单提交目标
	    	dataType: "json",
	    	data: {
	    		modify : '1',
	    		id : id ,
	    		pass : RSAUtils.encryptedString(key, pass) ,
	    		passwd : passwd
	    	},	//表单数据
	    	success:function(msg){
	    		if(msg.type == 'success'){	//msg 是后台调用action时，你传过来的参数
	    			//do things here
					parent.layer.msg('修改成功',
							{
								icon : 1 ,
								shade : 0.4,
								time: 1000
							}
						);
					parent.$('#init-table-data').click();
					parent.layer.close(parent.layer.getFrameIndex(window.name));
	    		}else{
	    			//do things here
					layer.msg(msg.content, {
						icon : 2
					});
	    			$('#pass').val('');
	    			$('#pass2').val('');
	    			$('#passwd').val('');
	    		}
	    	}
	    });
	}
}