jQuery(document).ready(
		function() {
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
			$('#kanbuq').click(
					refreshcode=function() {
						$('#verifycodeimg').attr("src",
								"../get_verify_code.action?t" + Math.random());
					});
			$('#loginform').submit(function() {
				$('#span_msg').text('');
				var modulus = $('#moduls').val();
				var exponent = $('#exponent').val();
				var key = RSAUtils.getKeyPair(exponent, '', modulus);
				var email = $('#email').val();
				var pass = $('#pass').val();
				var verifycode = $('#verifycode').val();
				if (email == '') {
					$('#span_msg').text('邮箱不能为空');
					return false;
				}
				if (pass == '') {
					$('#span_msg').text('密码不能为空');
					return false;
				}
				if (verifycode == '') {
					$('#span_msg').html('验证码不能为空');
					return false;
				}
		        $('#pass').val(RSAUtils.encryptedString(key, pass));
		        $.ajax({ 
		        	type: "post",	//表单提交类型
		        	url: "login.action",	//表单提交目标
		        	dataType: "json",
		        	data: $(this).serialize(),	//表单数据
		        	success:function(msg){
		        		if(msg.type == 'success'){	//msg 是后台调用action时，你传过来的参数
		        			//do things here
		        			$('#span_msg').html(msg.content);
		        			setTimeout(function(){
		        				location.href="index";
		        			}, 1000);
		        		}else{
		        			//do things here
		        			$('#span_msg').html(msg.content);
		        			$('#pass').val('');
		        			refreshcode();
		        		}
		        	}
		        });
				return false;
			});

		});