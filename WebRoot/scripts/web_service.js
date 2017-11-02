var isSuccess = false;	// 提交页面是否成功
$(document).ready(function (e) {
	
	//初始化弹出框
	$("#submitNotice").dialog({
		dialogClass:"ppisDialog",
		autoOpen: false,
		resizable:false,
		modal: true,
		buttons: {
			"确定" : function () {
				
				// 提交成功
				if (isSuccess) {
					location.reload();
				} else {	// 提交失败（后台返回）
					$(this).dialog("close");
				}
			}
		}
	});
	
	//	六合一验证方式
	var WebServiceAccess = $("#WebServiceAccess").val();
	$("#fieldWebServiceAccess option[value=" + WebServiceAccess + "]").attr("selected", "selected");
	
	//	六合一用户名
	var WebServiceYhdh = $("#WebServiceYhdh").val();
	$("#fieldWebServiceYhdh").val(WebServiceYhdh);
	
	//	六合一密码
	var WebServiceMm = $("#WebServiceMm").val();
	$("#fieldWebServiceMm").val(WebServiceMm);
	
	//	六合一序列号
	var WebServiceXlh = $("#WebServiceXlh").val();
	$("#fieldWebServiceXlh").val(WebServiceXlh);
	
	//	六合一终端标识
	var WebServiceZdbs = $("#WebServiceZdbs").val();
	$("#fieldWebServiceZdbs").val(WebServiceZdbs);
	
	//	六合一单位机关名称
	var WebServiceDwmc = $("#WebServiceDwmc").val();
	$("#fieldWebServiceDwmc").val(WebServiceDwmc);
	
	//	六合一单位机关代码
	var WebServiceDwjgdm = $("#WebServiceDwjgdm").val();
	$("#fieldWebServiceDwjgdm").val(WebServiceDwjgdm);
	
	//	六合一用户姓名
	var WebServiceYhxm = $("#WebServiceYhxm").val();
	$("#fieldWebServiceYhxm").val(WebServiceYhxm);
	
	//	六合一用户标识
	var WebServiceYhbz = $("#WebServiceYhbz").val();
	$("#fieldWebServiceYhbz").val(WebServiceYhbz);
	
	
	//	六合一接口地址
	var WebServiceWsdl = $("#WebServiceWsdl").val();
	$("#fieldWebServiceWsdl").val(WebServiceWsdl);
	
	//	六合一查询接口方法名
	var WebServiceQueryMethod = $("#WebServiceQueryMethod").val();
	$("#fieldWebServiceQueryMethod").val(WebServiceQueryMethod);
	
	//	六合一写入接口方法名
	var WebServiceWriteMethod = $("#WebServiceWriteMethod").val(); 
	$("#fieldWebServiceWriteMethod").val(WebServiceWriteMethod);
	
	//	六合一已审核接口
	var WebServiceWriteYesJkid = $("#WebServiceWriteYesJkid").val();
	$("#fieldWebServiceWriteYesJkid").val(WebServiceWriteYesJkid);
	
	//	六合一未审核接口
	var WebServiceWriteNoJkid = $("#WebServiceWriteNoJkid").val();
	$("#fieldWebServiceWriteNoJkid").val(WebServiceWriteNoJkid);
	
	//	违法数据上传模式
	var WebServiceUploadMode = $("#WebServiceUploadMode").val();
	
	//	如果选择手动模式,隐藏间隔时间
	if (WebServiceUploadMode == 0) {
		$("#interval_time").hide();
	} else {
		$("#time_time").hide();
	}
	
	for (var i = 1; i <= 24; i++) {
		$("#hour").append("<option value='" + i + "'>" +i + "</option>");
	}
	$("#fieldWebServiceUploadMode option[value=" + WebServiceUploadMode + "]").attr("selected", "selected");
	
	//	违法数据上传间隔时间
	var WebServiceIntervalTime = $("#WebServiceIntervalTime").val();
	
	//  如果是定时上传
	if (WebServiceUploadMode == 0) {
		$("#hour option[value='" + WebServiceIntervalTime + "']").attr("selected", "selected");
	} else {	//如果是间隔上传
		$("#fieldWebServiceIntervalTime").val(WebServiceIntervalTime);
	}
	
	// 绑定事件
	$("#fieldWebServiceUploadMode").bind("change", function () {
		var WebServiceUploadMode = $(this).val();
		
		//	如果选择间隔上传
		if (WebServiceUploadMode == 1) {
			$("#interval_time").show();
			$("#time_time").hide();
		} else { // 如果选择手动模式
			$("#interval_time").hide();
			$("#time_time").show();
		}
	});
	
	// 提交页面
	$("#confirm").bind("click", function () {
		var paramList = [];
		
		//	六合一验证方式
		var WebServiceAccess = $("#fieldWebServiceAccess option:checked").val();
		paramList.push({"key" : "WebServiceAccess" , "value" : WebServiceAccess});
		
		//	六合一用户名
		var WebServiceYhdh = $("#fieldWebServiceYhdh").val();
		paramList.push({"key" : "WebServiceYhdh" , "value" : WebServiceYhdh});
		
		//	六合一密码
		var WebServiceMm = $("#fieldWebServiceMm").val();
		paramList.push({"key" : "WebServiceMm" , "value" : WebServiceMm});
		
		//	六合一序列号
		var WebServiceXlh = $("#fieldWebServiceXlh").val();
		paramList.push({"key" : "WebServiceXlh" , "value" : WebServiceXlh});
		
		//	六合一终端标识
		var WebServiceZdbs = $("#fieldWebServiceZdbs").val();
		paramList.push({"key" : "WebServiceZdbs" , "value" : WebServiceZdbs});
		
		//	六合一单位机关名称
		var WebServiceDwmc = $("#fieldWebServiceDwmc").val();
		paramList.push({"key" : "WebServiceDwmc" , "value" : WebServiceDwmc});
		
		
		//	六合一单位机关代码
		var WebServiceDwjgdm = $("#fieldWebServiceDwjgdm").val();
		paramList.push({"key" : "WebServiceDwjgdm" , "value" : WebServiceDwjgdm});
		
		//	六合一用户姓名
		var WebServiceYhxm = $("#fieldWebServiceYhxm").val();
		paramList.push({"key" : "WebServiceYhxm" , "value" : WebServiceYhxm});
		
		//	六合一用户标识
		var WebServiceYhbz = $("#fieldWebServiceYhbz").val();
		paramList.push({"key" : "WebServiceYhbz" , "value" : WebServiceYhbz});
		
		
		//	六合一接口地址
		var WebServiceWsdl = $("#fieldWebServiceWsdl").val();
		paramList.push({"key" : "WebServiceWsdl" , "value" : WebServiceWsdl});
		
		//	六合一已审核接口
		var WebServiceWriteYesJkid = $("#fieldWebServiceWriteYesJkid").val();
		paramList.push({"key" : "WebServiceWriteYesJkid" , "value" : WebServiceWriteYesJkid});
		
		//	六合一未审核接口
		var WebServiceWriteNoJkid = $("#fieldWebServiceWriteNoJkid").val();
		paramList.push({"key" : "WebServiceWriteNoJkid" , "value" : WebServiceWriteNoJkid});
		
		//	违法数据上传模式
		var WebServiceUploadMode = $("#fieldWebServiceUploadMode option:checked").val();
		paramList.push({"key" : "WebServiceUploadMode" , "value" : WebServiceUploadMode});
		
		//	违法数据上传间隔时间
		var WebServiceIntervalTime = $("#fieldWebServiceIntervalTime").val();
		
		//	选择间隔上传
		if (WebServiceUploadMode == 1) {
			paramList.push({"key" : "WebServiceIntervalTime" , "value" : WebServiceIntervalTime});
			$("#fieldWebServiceIntervalTime").attr("rule", "noempty num");
		} else { //	选择非间隔上传
			paramList.push({"key" : "WebServiceIntervalTime" , "value" : $("#hour option:selected").val()});
		}
		
		// 数据校验
		var isIllegal = false;
		$("#web_service [rule]").each(function (index, element) {
			isIllegal |= testIllegal($(this));
		});
		if (isIllegal) {
			$("#errInfo").show();
			return false;
		} else {
			$("#errInfo").hide();
		}
		
		if (paramList.length>0) {
			var paramObject = new Object();
			paramObject.paramJson = JSON.stringify(paramList);
			$.ajax({
				url: $("#ctx").val() + "/admin/web_service!saveByJson.action",
				type: "POST",
				data: paramObject,
				success: function (msg) {
					isSuccess = true;
					$("#submitNotice").html(msg).dialog("open");
					return;
				}
			});
		}
		return false;
	});
});