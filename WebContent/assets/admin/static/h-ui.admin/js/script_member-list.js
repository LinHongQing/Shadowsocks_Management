$(document).ready(
	init_tableData=function(){
		$.getJSON({
			url: 'get-all-user.action',
			error: function(request) {
				layer.msg("connection error!", {icon:2, shift:6});
			},
			success: function(result){
				var type = result.type
				if (type == "empty") {
					$('#table-content-area').empty();
					$('#total-count').html("0");
					layer.msg("数据库中无用户存在", {
						icon : 2
					});
					return;
				} else if (type == "error") {
					layer.msg(result.content, {
						icon : 2
					});
					return;
				}
				var content = result.content;
				$('#total-count').html(content.totalCount);
				$('#table-content-area').empty();
				$(content.tableContent).each(function(index, element){
						var table = 
							"<tr class=\"text-c\"><td>"+
							"<input type=\"checkbox\" value=\"" + element.id + "\" name=\"\" />"+
							"</td><td>"+element.id+
							"</td><td>"+element.username+
							"</td><td>"+element.email+
							"</td><td>"+element.t_registry+
							"</td><td>"+element.port+
							"</td><td>"+element.passwd+
							"</td><td>"+element.t+
							"</td><td>"+(element.u/1024/1024).toFixed(2)+
							"</td><td>"+(element.d/1024/1024).toFixed(2)+
							"</td><td>"+(element.transfer_enable/1024/1024).toFixed(2)+
							"</td>";
						if (element.is_switched_on == 1)
							table += "<td class=\"td-status_data\"><span class=\"label label-success radius\">已启用</td>";
						else
							table += "<td class=\"td-status_data\"><span class=\"label label-danger radius\">已停用</td>";
						if (element.is_enabled == 1)
							table += "</td><td class=\"td-status_admin\"><span class=\"label label-success radius\">已启用</td>";
						else
							table += "</td><td class=\"td-status_admin\"><span class=\"label label-danger radius\">已停用</td>";
						table += "<td class=\"td-manage\">";
						if (element.is_enabled == 1)
							table += "<a style=\"text-decoration:none\" onClick=\"member_stop(this,'" + element.id + "')\" href=\"javascript:;\" title=\"停用账户\"><i class=\"Hui-iconfont\">&#xe631;</i></a>";
						else
							table += "<a style=\"text-decoration:none\" onClick=\"member_start(this,'" + element.id + "')\" href=\"javascript:;\" title=\"启用账户\"><i class=\"Hui-iconfont\">&#xe6e1;</i></a>";
						table += "<a title=\"编辑账户\" href=\"javascript:;\" onclick=\"member_edit('编辑','" + element.id + "','510','400')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i></a>";
						table += "<a title=\"修改账户密码\" href=\"javascript:;\" onClick=\"change_password('修改账户密码','" + element.id + "','510','360')\" style=\"text-decoration:none\" class=\"ml-5\"><i class=\"Hui-iconfont\">&#xe63f;</i></a>";
						table += "<a title=\"删除账户\" href=\"javascript:;\" onclick=\"member_del(this,'" + element.id + "')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6e2;</i></a>";
						table += "</td>";
						$('#table-content-area').append(table);
				});
			}
		});
		$('.table-sort tbody').on( 'click', 'tr', function () {
			if ( $(this).hasClass('selected') ) {
				$(this).removeClass('selected');
			}
			else {
				table.$('tr.selected').removeClass('selected');
				$(this).addClass('selected');
			}
		});
});
$('#init-table-data').click(function(){
	init_tableData();
});
/*用户-添加*/
function member_add(title,w,h){
	layer_show(title,'add-user',w,h);
}
/*用户-停用*/
function member_stop(obj,id){
	layer.confirm('停用后该用户将无法登录和使用服务, 确认要停用吗?',
	{
		icon:3,
		shade:0.4,
		btn:['确定','取消']
	},
	function(index){
		$.ajax({
			type : "post",
			url : "modify-user.action",
			dataType : "json",
			data : { modify : '1' , id : id , is_enabled : '0'},
			success : function(msg) {
				if (msg.type == 'success') {
					// do things here
					$(obj).parents("tr").find(".td-manage").prepend("<a style=\"text-decoration:none\" onClick=\"member_start(this,'" + id + "')\" href=\"javascript:;\" title=\"启用账户\"><i class=\"Hui-iconfont\">&#xe6e1;</i></a>");
					$(obj).parents("tr").find(".td-status_admin").html("<span class=\"label label-danger radius\">已停用</td>");
					$(obj).remove();
					layer.msg('账户已停用',{icon:5,shade:0.4,time:1000});
				} else {
					// do things here
					layer.msg(msg.content, {
						icon:2,shade:0.4
					});
				}
			},
			error : function() {
				layer.msg("connection error", {
					icon:2,shade:0.4
				});
			}
		});
	});
}

/*用户-启用*/
function member_start(obj,id){
	layer.confirm('确认要启用吗?',
	{
		icon:3,
		shade:0.4,
		btn:['确定','取消']
	},
	function(index){
		$.ajax({
			type : "post",
			url : "modify-user.action",
			dataType : "json",
			data : { modify : '1' , id : id , is_enabled : '1'},
			success : function(msg) {
				if (msg.type == 'success') {
					// do things here
					$(obj).parents("tr").find(".td-manage").prepend("<a style=\"text-decoration:none\" onClick=\"member_stop(this,'" + id + "')\" href=\"javascript:;\" title=\"停用账户\"><i class=\"Hui-iconfont\">&#xe631;</i></a>");
					$(obj).parents("tr").find(".td-status_admin").html("<span class=\"label label-success radius\">已启用</td>");
					$(obj).remove();
					layer.msg('账户已启用',{icon:6,shade:0.4,time:1000});
				} else {
					// do things here
					layer.msg(msg.content, {
						icon:2,shade:0.4
					});
				}
			},
			error : function() {
				layer.msg("connection error", {
					icon:2,shade:0.4
				});
			}
		});
	});
}
/*用户-编辑*/
function member_edit(title,id,w,h){
	$.ajax({
		type : "post",
		url : "modify-user.action",
		dataType : "json",
		data : { prepare : '1' , id : id },
		success : function(msg) {
			if (msg.type == 'success') {
				// do things here
				layer_show(title,'modify-user',w,h);
			} else {
				// do things here
				layer.msg(msg.content, {
					icon:2,shade:0.4
				});
			}
		},
		error : function() {
			layer.msg("connection error", {
				icon:2,shade:0.4
			});
		}
	});
}
/*密码-修改*/
function change_password(title,id,w,h){
	$.ajax({
		type : "post",
		url : "change-password.action",
		dataType : "json",
		data : { prepare : '1' , id : id },
		success : function(msg) {
			if (msg.type == 'success') {
				// do things here
				layer_show(title,'change-password',w,h);
			} else {
				// do things here
				layer.msg(msg.content, {
					icon:2,shade:0.4
				});
			}
		},
		error : function() {
			layer.msg("connection error", {
				icon:2,shade:0.4
			});
		}
	});
}
/*用户-删除*/
function member_del(obj,id){
	layer.confirm('确认要删除吗？',
	{
		icon: 3,
		shade:0.4,
		btn:['确定','取消']
	},
	function(index){
		$.ajax({
			type : "post",
			url : "delete-user.action",
			dataType : "json",
			data : { id : id },
			success : function(msg) {
				if (msg.type == 'success') {
					// do things here
					$(obj).parents("tr").remove();
					$('#total-count').html($('#total-count').html()-1);
					layer.msg('已删除!',{icon:1,shade:0.4,time:1000});
				} else {
					// do things here
					layer.msg(msg.content, {
						icon:2,shade:0.4
					});
				}
			},
			error : function() {
				layer.msg("connection error", {
					icon:2,shade:0.4
				});
			}
		});
		$(obj).parents("tr").remove();
		layer.msg('已删除!',{icon:1,shade:0.4,time:1000});
	});
}