$(document).ready(
		function(){
			getsysinfo();
			$.getJSON({
				url: 'get-login-info.action',
				error: function(request) {
					layer.msg("connection error!", {icon:2, shift:6});
				},
				success: function(result) {
					if (result.type == "success") {
						var content = result.content;
						parent.$('#username').html(content.username);
						$('#login-username').html(content.username);
						$('#login-count').html(content.loginCount);
						$('#login-ip-address').html(content.loginIpAddress);
						$('#lasttime-login-ip-address').html(content.lasttimeLoginIpAddress);
						$('#lasttime-login-time').html(content.lasttimeLoginTime);
					} else {
						layer.msg(result.content, {
							icon:2,shade:0.4
						});
					}
				}
			});
		}
);

function getsysinfo() {
	$.getJSON({
		url: 'get-sys-info.action',
		error: function(request) {
			layer.msg("connection error!", {icon:2, shift:6});
		},
		success: function(result){
			if (result.type == "success") {
				var content = result.content;
				$('#user-count').html(content.userCount);
				$('#admin-count').html(content.adminCount);
				$('#sys-name').html(content.sysName);
				$('#sys-version').html(content.sysVersion);
				$('#sys-arch').html(content.sysArch);
				if (content.sysCpuLoad < 0)
					$('#sys-cpu-load').html('系统不支持');
				else
					$('#sys-cpu-load').html((content.sysCpuLoad*100).toFixed(2) + '%');
				if (content.sysLoadAvg < 0)
					$('#sys-cpu-load-avg').html('系统不支持');
				else
					$('#sys-cpu-load-avg').html((content.sysLoadAvg*100).toFixed(2) + '%');
				if (content.sysProcCpuLoad < 0)
					$('#proc-cpu-load').html('系统不支持');
				else
					$('#proc-cpu-load').html((content.sysProcCpuLoad*100).toFixed(2) + '%');
				$('#sys-total-mem-size').html(content.sysTotalMemSize);
				$('#sys-free-mem-size').html(content.sysFreeMemSize);
				$('#sys-total-swap-mem-size').html(content.sysTotalSwapMemSize);
				$('#sys-free-swap-mem-size').html(content.sysFreeSwapMemSize);
				$('#disk-info').html(content.diskInfo);
				setTimeout(getsysinfo, 10000)
			} else {
				layer.msg(result.content, {
					icon:2,shade:0.4
				});
			}
		}
	});
}