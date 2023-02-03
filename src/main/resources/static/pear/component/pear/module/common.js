layui.define(['jquery', 'element','table'], function(exports) {
	"use strict";

	/**
	 * 常用封装类
	 * */
	var MOD_NAME = 'common',
		$ = layui.jquery,
		table = layui.table,
		element = layui.element;

	var common = new function() {
		
		/**
		 * 获取当前表格选中字段
		 * @param obj 表格回调参数
		 * @param field 要获取的字段
		 * */
		this.checkField = function(obj, field) {
			let data = table.checkStatus(obj.config.id).data;
			if (data.length === 0) {
				return "";
			}
			let ids = "";
			for (let i = 0; i < data.length; i++) {
				ids += data[i][field] + ",";
			}
			ids = ids.substr(0, ids.length - 1);
			return ids;
		}
		
		/**
		 * 当前是否为与移动端
		 * */
		this.isModile = function(){
			if ($(window).width() <= 768) {
				return true;
			}
			return false;
		}
		
		
		/**
		 * 提交 json 数据 
		 * @param data 提交数据
		 * @param href 提交接口
		 * @param ajaxtype 提交方式
		 * @param table 刷新父级表
		 * @param callback 自定义回调函数
		 * */
		this.submit = function(href,data,ajaxtype,table,callback){
			if(ajaxtype==''){ ajaxtype='post';}
			$.ajax({
			    url:href,
			    data:JSON.stringify(data),
			    dataType:'json',
			    contentType:'application/json',
			    type:ajaxtype,
				success:callback !=null?callback:function(result){
			        if(result.code==1){
			            layer.msg(result.msg,{icon:1,time:1000},function(){
			                parent.layer.close(parent.layer.getFrameIndex(window.name));//关闭当前页
							if(table!=null){parent.layui.table.reload(table);}
			            });
			        }else{
			            layer.msg(result.msg,{icon:2,time:1000});
			        }
			    },
                //异步访问出错的时候会用到error，可以扩充
				error:function(XMLHttpRequest){
					if(XMLHttpRequest.status==419)
					{
						layer.msg('长时间未操作，自动刷新后重试！',{icon: 5});
						setTimeout(function () { window.location.reload();}, 2000);
					}
					if(XMLHttpRequest.status==429)
					{
						layer.msg('尝试次数太多，请一分钟后再试',{icon: 5});
					}
					if(XMLHttpRequest.status==500)
					{
						layer.msg(XMLHttpRequest.responseJSON.message,{icon: 5});
					}
				},
                //完成的时候会用到，例如更新token
				complete:function (xhr,status){
					console.log(xhr);
					console.log(status);
				}
			})
		}
	}
	exports(MOD_NAME, common);
});
