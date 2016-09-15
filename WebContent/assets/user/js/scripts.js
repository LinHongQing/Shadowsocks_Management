jQuery(document).ready(function() {
	
    $('.page-container form').submit(function(){
        var email = $(this).find('.email').val();
        var password = $(this).find('.password').val();
        var verifycode = $(this).find('.verifycode').val();
        if(email == '') {
            $('.error').fadeOut('fast');
            $('.error span').html('邮箱为空');
            $('.error').fadeIn('fast', function(){
                $('.email').focus();
                setTimeout(function(){
                	$('.error').fadeOut('fast');
                }, 3000);
            });
            return false;
        }
        if(password == '') {
            $('.error').fadeOut('fast');
            $('.error span').html('密码为空');
            $('.error').fadeIn('fast', function(){
                $('.password').focus();
                setTimeout(function(){
                	$('.error').fadeOut('fast');
                }, 3000);
            });
            return false;
        }
        if(verifycode == '') {
            $('.error').fadeOut('fast');
            $('.error span').html('验证码为空');
            $('.error').fadeIn('fast', function(){
                $('.verifycode').focus();
                setTimeout(function(){
                	$('.error').fadeOut('fast');
                }, 3000);
            });
            return false;
        }
        document.getElementById("password").value = CryptoJS.MD5(password);
        $.ajax({ 
        	type: "post",	//表单提交类型
        	url: "login",	//表单提交目标
        	dataType: "json",
        	data: $(this).serialize(),	//表单数据
        	success:function(msg){
        		if(msg.type == 'success'){	//msg 是后台调用action时，你传过来的参数
        			//do things here
        			
        		}else{
        			//do things here
        			$('.error').fadeOut('fast');
        			$('.error span').html(msg.content);
                    $('.error').fadeIn('fast', function(){
                        setTimeout(function(){
                        	$('.error').fadeOut('fast');
                        }, 3000);
                    });
                    refreshcode();
        		}
        	}
        });
        return false;
    });
    
    $('.verifycode').focus(function(){
    	$('.verifycodediv').fadeIn('fast');
    });
    
//    $('.verifycode').blur(function(){
//    	$('.verifycodediv').fadeOut('fast');
//    });

    $('.page-container form .email, .page-container form .password, .page-container form .verifycode').keypress(function(){
        $(this).parent().find('.error').fadeOut('fast');
    });
    
    $('#verifycodeimg').click(function(){
    	refreshcode();
    });

});

function refreshcode() {
	$('#verifycodeimg').attr("src", "get_verify_code.action?t" + Math.random());
	document.getElementById("verifycode").value="";
}
