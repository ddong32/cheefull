function ExpenceAuditInterval() {
	this.cacheData = {
		ids : [],            // 所有id
		id : null,           // 选中的id
		index : 0,
		length : 0,
		tgNum : 0,           // 通过条数
		zfNum : 0,           // 作废条数 
		currentBean : null,   // 当前的bean
		pageAuditTypes : null
	};
}

ExpenceAuditInterval.prototype = {
	init : function() {
		this.cacheData.paydialog = $("#popupDialog");
		this.bindEvent();
		this.initData();
		//this.initMainGrid();
		this.initDialog();
		
		if (queryStringArgs.type == "verAudit") { 
			document.title = "报销经理审批";
			this.showOnlyMangeBtn();
		} else if (queryStringArgs.type == "payAudit") {
			document.title = "报销财务审批";
			this.showOnlyFanceBtn();
		}
	},
	
	/**
	 * 绑定事件
	 */
	bindEvent : function() {
		var me = this;
		
		// 上一条
		$("#button_up").bind("click", function() {
			if ($(this).hasClass("disabled")) {
				return;
			}
			if (me.cacheData.index == 0) {
				alertDIV("已是第一条。");
			} else {
				me.cacheData.index--;
				me.setBean(me.cacheData.index);
			}
		});
		
		// 下一条
		$("#button_next").bind("click", function() {
			if ($(this).hasClass("disabled")) {
				return;
			}
			if (me.cacheData.index + 1 == me.cacheData.length) {
				alertDIV("已是最后一条。");
			} else {
				me.cacheData.index++;
				me.setBean(me.cacheData.index);
			}
		});
		
		// 作废
		$("#button_del").bind("click", function() {
			if ($(this).hasClass("disabled")) {
				return;
			}
			me.cancel();
		});
		
		//弹出银行流水框
		$("#button_pay").bind('click',function(){
			$.ajax({
				type: "POST",
				cache: false,
				url: $(this).attr("href"),
				success: function(msg){
					me.cacheData.paydialog.dialog({ title : "加入银行流水"}).html(msg).dialog("open");
					$(".ui-widget-overlay").css("height",document.body.scrollHeight);
				}
			});
			return false;
		});
		
		$("#button_che").bind('click',function(){
			if ($(this).hasClass("disabled")) {
				return;
			}
			me.pass();
		});
	},
	
	initData : function() {
		var me = this;
		me.cacheData.ids = $("#idArray").val().split(",");
		me.cacheData.length = me.cacheData.ids.length;
		
		if (me.cacheData.length == 0) { // 如果没有需要审核的数据，则禁用所有按钮
			me.setDisabledAll();
			return;
		}
		
		var index = Number($("#_indexNum").val()) + 1; // 当前id所在位置
		if (me.cacheData.id) {
			index = $.inArray(me.cacheData.id, me.cacheData.ids);
			if (index == -1) {
				alertDIV("该数据已审核完毕，自动跳到第一条。");
				index = 0;
			}
		} else {
			me.cacheData.id = me.cacheData.ids[0];
		}
		me.cacheData.index = index;
		
		$("#_intTotal").text(me.cacheData.length);
		
		me.setBean(me.cacheData.index);
	},
	
	initDialog : function() {
		var me = this;
		this.cacheData.paydialog.dialog({
			dialogClass:"ppisDialog",
			autoOpen: false,
			resizable: false,
			width:470,
			maxHeight: window.screen.height-250,
			modal: true,
			buttons : {
				"确定": function() { //支付
					me.payy();
				},
				"关闭": function() {
					$(this).dialog("close");
				}
			}
		});
	},
	
	/**
	 * 
	 * @param index
	 * 2015年1月26日
	 * 上午10:53:27
	 */
	setBean : function(index) {
		var me = this;
		$("#_intIndexNum").text(this.cacheData.index + 1);
		var id = this.cacheData.ids[index];

		$.ajax({
			type : "post",
			url : $("#ctx").val() + "/admin/expence!getExpenceIdsJson.action",
			data : {
				'id' : id
			},
			async : true,
			success : function(result) {
				if (result == "") {
					return false;
				}
				try{
					var jsonObj = jQuery.parseJSON(result)[0];
					if(jsonObj.error == ""){
						$("#_id").text(jsonObj.id);
						$("#_bxlx").text(jsonObj.bxlx);
						$("#_fssj").text(jsonObj.fssj);
						$("#_bxje").text(jsonObj.bxje);
						$("#_bxr").text(jsonObj.bxr);
						$("#_bxsj").text(jsonObj.bxsj);
						$("#_bxnr").text(jsonObj.bxnr);
						$("#_departmentName").text(jsonObj.departmentName);
						$("#_intStat").text(jsonObj.statZw);
						me.loadexpenceflow(jsonObj.id);
						me.loadbankrunnig(jsonObj.stat, jsonObj.bankrunningid);
						me.setExpenceButton(jsonObj.stat);
					}else{
						alert("[setBean] error:"+jsonObj.error);
					}
				}catch(e){
					alert("[setBean] "+e.message);
					return;
				}
			}
		});
	},
	
	/**
	 * 回退；删除银行流水、增加银行账户金额
	 * 
	 * 覃东梁 19622
	 * 2015年1月25日
	 * 下午7:53:41
	 */
	cancel : function() {
		var me = this;
		var id = this.cacheData.ids[this.cacheData.index];
		$.ajax({
			type : "post",
			url : $("#ctx").val() + "/admin/expence!cancel.action",
			data : {
				'expence.id' : id,
			},
			async : true,
			success : function(result) {
				if (result == "") {
					return false;
				}
				try{
					var jsonObj = jQuery.parseJSON(result)[0];
					if(jsonObj.error == ""){
						me.setBean(me.cacheData.index);
						me.osmsgiofo(' 操作成功！');
					}else{
						alert("[setBean] error:"+jsonObj.error);
					}
				}catch(e){
					alert("[setBean] "+e.message);
					return;
				}
			}
		})
	},
	
	/**********************************************************************************
	 * 通过
	 * @returns {Boolean}
	 * 覃东梁 19622
	 * 2015年1月25日
	 * 下午8:00:36
	 */
	pass : function() {
		var me = this;
		var id = this.cacheData.ids[this.cacheData.index];
		$.ajax({
			type : "post",
			url : $("#ctx").val() + "/admin/expence!pass.action",
			data : {
				'expence.id' : id
			},
			async : true,
			success : function(result) {
				if (result == "") {
					return false;
				}
				try{
					var jsonObj = jQuery.parseJSON(result)[0];
					if(jsonObj.error == ""){
						me.cacheData.paydialog.dialog("close");
						me.osmsgiofo(' 操作成功！');
						me.setBean(me.cacheData.index);
					}else{
						alert("[setBean] error:"+jsonObj.error);
					}
				}catch(e){
					alert("[setBean] "+e.message);
					return;
				}
			}
		})
	},
	
	/**********************************************************************************
	 * 
	 * 支付
	 * @returns {Boolean}
	 * 覃东梁 19622
	 * 2015年1月25日
	 * 下午8:00:36
	 */
	payy : function() {
		var me = this;
		var id = this.cacheData.ids[this.cacheData.index];
		$.ajax({
			type : "post",
			url : $("#ctx").val() + "/admin/expence!payy.action",
			data : {
				'expence.id' : id,
				'bankRunning.orderId' : id,
				'bankRunning.bank.id' : $("#bankRunningAddFrom #_selectBankId").val(),
				'bankRunning.fkje' : $("#bankRunningAddFrom #br_je").val(),
				'bankRunning.jzsj' : $("#bankRunningAddFrom #br_jzsj").val()
			},
			async : true,
			success : function(result) {
				if (result == "") {
					return false;
				}
				try{
					var jsonObj = jQuery.parseJSON(result)[0];
					if(jsonObj.error == ""){
						me.cacheData.paydialog.dialog("close");
						me.osmsgiofo(' 操作成功！');
						me.setBean(me.cacheData.index);
					}else{
						alert("[setBean] error:"+jsonObj.error);
					}
				}catch(e){
					alert("[setBean] "+e.message);
					return;
				}
			}
		})
	},
	
	/**
	 * 设置操作日志
	 * @param id
	 * 覃东梁 19622
	 * 2015年1月26日
	 * 上午11:34:39
	 */
	loadexpenceflow : function(id) {
		var me = this;
		if (id == "" || id == null ) {
			return;
		}
		$.ajax({
			url : $("#ctx").val() + "/admin/expence!loadExpenceFlow.action",
			data : {'expence.id' : id},
			async : true,
			success : function(data) {
				me.setexpenceflowTable(data);
			},
			error : function() {
			}
		});
	},
	
	setexpenceflowTable : function(msg){
		try{
			var jsonObj = jQuery.parseJSON(msg)[0];
			var data    = jsonObj.optionList;
		}catch(e){
			alert("setexpenceflowTable --> " + e + " -- " + msg);
			return;
		}
		$("#eftable tbody tr:not(:first)").remove();
		if (data.length > 0) {
			var datahtml = '';
			$.each(data, function(i) {
				var trclass  = '';
				if(i%2==1){
					trclass = "class=\"cor\"";
				}else{
					trclass = "class=\"listBg\"";
				}

				datahtml += '<tr ' + trclass + '>';
				datahtml += '<td>' + (i+1) + '</td>';
				datahtml += '<td>' + data[i].userName + '</td>';
				datahtml += '<td>' + data[i].gxsj + '</td>';
				datahtml += '<td>' + data[i].opt + '</td>';
				datahtml += '</tr>';
			});
			$("#eftable tbody ").append(datahtml);
		}
	},
	
	//银行流水
	loadbankrunnig : function(stat, id){
		var me = this;
		if(stat == "3" && id != ""){
			$("#con_menu_2").show();
		}else{
			$("#con_menu_2").hide();
			return false;
		}
		$.ajax({
			url : $("#ctx").val() + "/admin/bank_running!loadBankRunning.action",
			data : {
				'bankRunning.id' : id
			},
			async : true,
			success : function(data) {
				me.setbankrunnigTable(data,id);
			},
			error : function() {
			}
		});
	},
	
	//打开对应的流水
	setbankrunnigTable : function(msg, id){
		try{
			var jsonObj = jQuery.parseJSON(msg)[0];
			var data    = jsonObj.optionList;
		}catch(e){
			alert("setbankrunnigTable --> "+e+data);
			return;
		}
		if (data.length > 0) {
			var datahtml = '';
			$.each(data, function(i) {
				var trclass  = '';
				var trselect = '';
				if(data[i].id == id){
					trselect = 'selected';
				}
				if(i%2==1){
					trclass = "class=\"cor "+trselect+"\"";
				}else{
					trclass = "class=\"listBg "+trselect+"\"";
				}
				datahtml += '<tr ' + trclass + '>';
				datahtml += '<td>' + data[i].id + '</td>';
				datahtml += '<td>' + data[i].jzsj + '</td>';
				datahtml += '<td>' + data[i].djm + '</td>';
				datahtml += '<td>' + data[i].dfzh + '</td>';
				datahtml += '<td>' + data[i].dfhm + '</td>';
				datahtml += '<td class="red">' + data[i].srje + '</td>';
				datahtml += '<td class="green">' + data[i].zcje + '</td>';
				datahtml += '<td style="display:none">' + data[i].id + '</td>';
				datahtml += '</tr>';
			});
			$("#brtable tbody tr:not(:first)").remove();
			$("#brtable tbody ").append(datahtml);
		}else{
			var datahtml = '';
			datahtml += '<tr>';
			datahtml += '<td colspan="10" class="red fwb">请先录入银行流水信息中心！</td>';
			datahtml += '</tr>';
			$("#brtable tbody tr:not(:first)").remove();
			$("#brtable tbody ").append(datahtml);
		}
	},
	
	/**
	 * 禁用所有按钮
	 * 
	 * 覃东梁 19622
	 * 2015年1月26日
	 * 上午10:57:17
	 */
	setDisabledAll : function() {
		$(".operationButton").addClass("disabled");
	},
	
	/**
	 * 启用所有按钮
	 * 
	 * 覃东梁 19622
	 * 2015年1月26日
	 * 上午10:57:27
	 */
	setEnabledAll : function() {
		$(".operationButton").removeClass("disabled");
	},
	
	/**
	 * 业务状态控制按钮
	 * 
	 * 覃东梁 19622
	 * 2015年1月26日
	 * 上午10:57:27
	 */
	setExpenceButton : function(stat) {
		if (stat == "0") {           //回退
			$("#button_che").attr("disabled", false);
			$("#button_che").removeClass("pub_but_disable").addClass("btn_two");
			$("#button_del").attr("disabled", true);
			$("#button_del").removeClass("btn_two").addClass("pub_but_disable");
			$("#button_pay").attr("disabled", true);
			$("#button_pay").removeClass("btn_two").addClass("pub_but_disable");
		} else if (stat == "2") {    //已审
			$("#button_del").attr("disabled", false);
			$("#button_del").removeClass("pub_but_disable").addClass("btn_two");
			$("#button_pay").attr("disabled", false);
			$("#button_pay").removeClass("pub_but_disable").addClass("btn_two");
			$("#button_che").attr("disabled", true);
			$("#button_che").removeClass("btn_two").addClass("pub_but_disable");
		} else if (stat == "3") {   //支付
			$("#button_del").attr("disabled", false);
			$("#button_del").removeClass("pub_but_disable").addClass("btn_two");
			$("#button_che").attr("disabled", true);
			$("#button_che").removeClass("btn_two").addClass("pub_but_disable");
			$("#button_pay").attr("disabled", true);
			$("#button_pay").removeClass("btn_two").addClass("pub_but_disable");
		}else{
			$("#button_che").attr("disabled", false);
			$("#button_che").removeClass("pub_but_disable").addClass("btn_two");
			$("#button_del").attr("disabled", false);
			$("#button_del").removeClass("pub_but_disable").addClass("btn_two");
		}
	},
	
	showOnlyMangeBtn : function() {
		$("#button_pay").hide();
	},
	showOnlyFanceBtn : function() {
		$("#button_che").hide();
	},
	
	osmsgiofo : function(msgtxt){
		if($('#OsMsgIofo')){$("#OsMsgIofo").remove();}
		$('body').append('<div id="OsMsgIofo"><div class="msgbj"></div><div class="msgtxt">' + msgtxt + '</div></div>');
		$("#OsMsgIofo").show();
		setTimeout('$("#OsMsgIofo").remove()',2000);
	}
};

var expenceAuditInterval;
function expenceAuditInterval() {
	expenceAuditInterval = new ExpenceAuditInterval();
	expenceAuditInterval.init();
}
