var stats = {}, auditRecord = {};
var recheckArray;
var addressArray;
var idArray;
var listSize;

function setIdArray(array) {
	idArray = array.split(",");
	if (array == null || array == "") {
		listSize = 0;this.bank.getId()
	} else {
		listSize = idArray.length;
	}
	$("#_intTotal").text(listSize);
}

function initData() {
	getNextRecord();
}

function refreshBtn() {
	var type = $("#type").val();
	$(":button").removeAttr("disabled");
	$("#con_menu_3").hide();

	if(type == "viewData"){
		$("#cwhtButton").attr("disabled", true);
		$("#cwhtButton").removeClass("btn_two").addClass("pub_but_disable");
		$("#cwshButton").attr("disabled", true);
		$("#cwshButton").removeClass("btn_two").addClass("pub_but_disable");
	}else if(type == "payFinaneChecked" || type == "takePayment"){
		//2、状态：2经理；3已审；9回退；payFinaneChecked（ 转账支付）、takePayment（挂账支付）
		$("#payfinace").show();   //支付
		$('#cwshButton').hide();  //审核
		if(stats["stat"] == "3"){ //3已审
			$("#cwhtButton").attr("disabled", false);
			$("#cwhtButton").removeClass("pub_but_disable").addClass("btn_two");
			$("#payfinace").attr("disabled", true);
			$("#payfinace").removeClass("btn_two").addClass("pub_but_disable");
		}else if(stats["stat"] == "9"){
			$("#payfinace").attr("disabled", false);
			$("#payfinace").removeClass("pub_but_disable").addClass("btn_two");
			$("#cwhtButton").attr("disabled", true);
			$("#cwhtButton").removeClass("btn_two").addClass("pub_but_disable");
		}else{
			$("#payfinace").attr("disabled", false);
			$("#payfinace").removeClass("pub_but_disable").addClass("btn_two");
		}
	}else{
		//2、状态：2经理；3财务；9回退
		$("#payfinace").hide();
		$('#cwshButton').show();
		if(stats["stat"] == "2" || stats["stat"] == "3"){
			$("#cwhtButton").attr("disabled", false);
			$("#cwhtButton").removeClass("pub_but_disable").addClass("btn_two");
			$("#cwshButton").attr("disabled", true);
			$("#cwshButton").removeClass("btn_two").addClass("pub_but_disable");
		}else if(stats["stat"] == "9"){
			$("#cwshButton").attr("disabled", false);
			$("#cwshButton").removeClass("pub_but_disable").addClass("btn_two");
			$("#cwhtButton").attr("disabled", true);
			$("#cwhtButton").removeClass("btn_two").addClass("pub_but_disable");
		}else{
			$("#cwshButton").attr("disabled", false);
			$("#cwshButton").removeClass("pub_but_disable").addClass("btn_two");
		}
	}
}

function setWinSize() {
	var winHeight = window.screen.availHeight;
	var winWidth = window.screen.availWidth;
	try {
		window.moveTo(0, 0);
		window.resizeTo(winWidth, winHeight);
	} catch (e) {

	}
}

$(document).ready(function() {
	setIdArray($("#idArray").val());
	initData();
	
	$( "#popupDialog" ).dialog({
		dialogClass:"ppisDialog",
		autoOpen: false,
		resizable: false,
		width:470,
		maxHeight: window.screen.height-250,
		modal: true,
		close: function() {
			$("#dialog").dialog("close");
		}
	});
	
	$("#addButton").bind('click',function(){
		$.ajax({
			type: "POST",
			cache: false,
			url: $(this).attr("href"),
			success: function(msg){
				$("#popupDialog").html(msg).dialog("open");
				$(".ui-widget-overlay").css("height",document.body.scrollHeight);
			}
		});
		return false;
	});
	
	$("#payfinace").bind('click',function(){
		$.ajax({
			type: "POST",
			cache: false,
			url: $(this).attr("href"),
			success: function(msg){
				$("#popupDialog").html(msg).dialog("open");
				$(".ui-widget-overlay").css("height",document.body.scrollHeight);
			}
		});
		return false;
	});
	
});

//上一条
function getUpRecord() {
	addMask();
	$(":button").attr("disabled", "true");
	var indexNum = $("#_indexNum").val();
	if (indexNum == null || indexNum == "") {
		alert("请先选择违法数据");
		refreshBtn();
		removeMask();
		return;
	} else if (indexNum == "-1" || indexNum == "0") {
		alert("已经是第一条数据");
		refreshBtn();
		removeMask();
		return;
	}
	indexNum = Number(indexNum);
	var id = Number(idArray[indexNum - 1]);
	$.ajax({
		type : "post",
		url : $("#ctx").val() + "/admin/account_flow!getUpRecordJson.action",
		data : {'id' : id},
		async : false,
		success : function(msg) {
			if (msg != "") {
				$("#_intIndexNum").text(Number(indexNum));	// 设置显示当前条数
				$("#_indexNum").attr("value", Number(indexNum) - 1);	// 设置当前数据下标数值
				setBean(msg);
			} else {
				refreshBtn();
				alert("未查询到上一条数据");
			}
		},
		complete : function() {
			refreshBtn();
			loadAccout();
			removeMask();
		},
		error : function() {
			removeMask();
		}
	});
}

// 下一条
function getNextRecord() {

	// 初始化显示合成图片
	addMask();
	$(":button").attr("disabled", true);
	var indexNum = $("#_indexNum").val();
	if (listSize == 0) {
		alert("数据已经审核完毕,未查询到下一条数据");
		removeMask();
		window.close();
		return;
	}
	indexNum = Number(indexNum) + 1;
	if (indexNum >= listSize) {
		alert("已经是最后一条数据");
		refreshBtn();
		removeMask();
		return;
	}
	var id = Number(idArray[indexNum]);
	$.ajax({
		type : "post",
		url : $("#ctx").val() + "/admin/account_flow!getNextRecordJson.action",
		data : {
			'id' : id
		},
		async : false,
		success : function(msg) {
			if (msg != "") {
				$("#_intIndexNum").text(indexNum + 1);	// 设置显示当前条数
				$("#_indexNum").attr("value", Number(indexNum));	// 设置当前数据下标数值
				setBean(msg);
			}
		},
		complete : function() {
			refreshBtn();
			loadAccout();
			removeMask();
		},
		error : function() {
			removeMask();
		}
	});
}

function setBean(msg) {
	try{
		var jsonObj = jQuery.parseJSON(msg)[0];
		if(jsonObj.error == ""){
			//$("#_id").text(jsonObj.id);
			$("#_lsh").text(jsonObj.lsh);
			$("#_lrr").text(jsonObj.lrr);
			$("#_lrsj").text(jsonObj.lrsj);
			$("#_skje").text(jsonObj.skje);
			$("#_fkje").text(jsonObj.fkje);
			$("#_sksj").text(jsonObj.sksj);
			$("#_djm").text(jsonObj.djm);
			$("#_shr").text(jsonObj.shr);
			$("#_shsj").text(jsonObj.shsj);
			$("#_spr").text(jsonObj.spr);
			$("#_spsj").text(jsonObj.spsj);
			$("#_intStat").text(jsonObj.statZw);
			$("#_intSffs").text(jsonObj.sffsZw);
			$("#_intSflx").text(jsonObj.sflxZw);
			$("#_initStatVal").val(jsonObj.stat);
			
			$("#accountId").val(jsonObj.accountId);
			$("#bankRunningId").val(jsonObj.bankRunningId);
			$("#_id").val(jsonObj.id);
			$("#_bankId").val(jsonObj.bankId);
			$("#_ywlx").val(jsonObj.sflx);
			$("#_br_sksj").val(jsonObj.sksj);
			$("#bankRunning_je").val(jsonObj.je);
			stats["stat"] = jsonObj.stat;
			loadBankRunnig(jsonObj.bankRunningId, jsonObj.bankId, jsonObj.sflx, jsonObj.sksj, jsonObj.je);
		}else{
			alert("[setBean] error:"+jsonObj.error);
		}
	}catch(e){
		alert("[setBean] "+e.message);
		return;
	}
}

//根据ID加载操作日志 2\收付项目
function loadAccout() {
	var account_ids = $("#accountId").val();
	if (account_ids == "" || account_ids == null ) {
		return;
	}
	$.ajax({
		url : $("#ctx").val() + "/admin/account!loadAccount.action",
		data : {'account_ids' : account_ids},
		async : false,
		success : function(data) {
			setAccountTable(data);
		},
		error : function() {
		}
	});
}

function setAccountTable(msg){
	try{
		var jsonObj = jQuery.parseJSON(msg)[0];
		var data    = jsonObj.optionList;
		var sumskje = jsonObj.sumskje;
		var sumfkje = jsonObj.sumfkje;
	}catch(e){
		alert("setAccountTable --> " + e + " -- " + msg);
		return;
	}
	if (data.length > 0) {
		var datahtml = '';
		$.each(data, function(i) {
			var trclass  = '';
			if(i%2==1){
				trclass = "class=\"cor\"";
			}else{
				trclass = "class=\"listBg\"";
			}
			var checkboxinit = '';
			if(data[i].stat=="已审核" || data[i].sffs=="挂账"){
				checkboxinit = "disabled";
			}
			datahtml += '<tr ' + trclass + '>';
			if($('#type').val() == "payManageChecked"){
				datahtml += '<td class="payshoworder" onclick="accountOrder(\'' + data[i].orderId + '\')">' + data[i].orderId + '</td>';
			}else{
				datahtml += '<td>' + data[i].orderId + '</td>';
			}
			datahtml += '<td class="red tar">' + data[i].ygs + '</td>';
			datahtml += '<td class="red tar">' + data[i].yjs + '</td>';
			datahtml += '<td class="red tar">' + data[i].wsk + '</td>';
			datahtml += '<td class="tal">' + data[i].sfmc + '</td>';
			datahtml += '<td class="tal">' + data[i].travelLine + '</td>';
			datahtml += '<td class="red tar">' + data[i].skje + '</td>';
			datahtml += '<td class="green tar">' + data[i].fkje + '</td>';
			datahtml += '<td>' + data[i].lrr + '</td>';
			datahtml += '<td class="tdLeft" title="双击进行修改" id="account_bz_' + data[i].id + '" style="cursor:pointer;" ondblclick="on_click(\'account_bz\',\'' + data[i].id + '\',\'300\',\'textarea\')">' + data[i].bz + '<i class="onClickIco"></i></td>';
			datahtml += '</tr>';
			
			$("#bankRunning_dfzh").val(data[i].yhhm);
			$("#bankRunning_dfhm").val(data[i].yhzh);
			$("#_dwmc").val(data[i].sfmc);
			$("#_cpid").val(data[i].cpid);
		});
		$("#acctable tbody tr:not(:first)").remove();
		$("#acctable tbody ").append(datahtml);
	}
	$("#acctable tfoot td#sumskje").html(sumskje);
	$("#acctable tfoot td#sumfkje").html(sumfkje);
}

//收款 ------------------------------------------------
function accountOrder($orderId){
	$("#con_menu_3").show();
	$.ajax({
		url : $("#ctx").val() + "/admin/account!ajaxBusinessAccountTable.action",
		data: {"account.orderId": $orderId},
		async: false,
		success : function(data) {
			setAccountOrderTable(data);
		}
	});
}
function setAccountOrderTable(msg){
	try{
		if(msg.indexOf("Exception")<0){
			var jsonObj = jQuery.parseJSON(msg)[0];
			var data    = jsonObj.optionList;
			var sumskje = jsonObj.sumskje;
			var sumfkje = jsonObj.sumfkje;
			ddztBoolean = false;
		}else{
			alert("setAccountOrderTable --> " + msg);
			return false;
		}
	}catch(e){
		alert("setAccountOrderTable --> " + e);
		return false;
	}
	if (data.length > 0) {
		var datahtml = '';
		$.each(data, function(i) {
			var trclass  = '';
			if(i%2==1){
				trclass = "class=\"cor\"";
			}else{
				trclass = "class=\"listBg\"";
			}
			var statClass=''
			if(data[i].stat=="3"){
				statClass = "class=\"red\"";
			}else{
				statClass = "";
			}
			var checkboxinit = '';
			var sffsClass=''
			if(data[i].sffs=="3"){
				sffsClass = "class=\"red\"";
			}else{
				sffsClass = "";
			}
			if((data[i].stat == '0' || data[i].stat == '8') && data[i].sffs != '3'){
				ddztBoolean = true;
			}
			datahtml += '<tr ' + trclass + '>';
			datahtml += '<td>' + '<input type="checkbox" name="ids" id="acc'+data[i].id+'" value="'+data[i].id+'" '+checkboxinit+'/>'+ '</td>';
			datahtml += '<td '+ statClass +'>' + data[i].statZw + '</td>';
			datahtml += '<td>' + data[i].sflx + '</td>';
			datahtml += '<td '+ sffsClass +'>' + data[i].sffsZw + '</td>';
			datahtml += '<td class="tal">' + data[i].sfmc + '</td>';
			datahtml += '<td class="tar red">' + data[i].skje + '</td>';
			datahtml += '<td class="tar green">' + data[i].fkje + '</td>';
			datahtml += '<td>' + data[i].bankdjm + '</td>';
			datahtml += '<td>' + data[i].sfsj + '</td>';
			datahtml += '<td>' + data[i].lrr + '</td>';
			datahtml += '<td class="tal">' + data[i].bz + '</td>';
			datahtml += '<td>' + data[i].shsj + '</td>';
			datahtml += '<td>' + data[i].shr + '</td>';
			datahtml += '<td name="tdstat" style="display: none;">' + data[i].stat + '</td>';
			datahtml += '<td name="tdsffs" style="display: none;">' + data[i].sffs + '</td>';
			datahtml += '</tr>';
		});
		$("#accounttable tbody tr:not(:first)").remove();
		$("#accounttable tbody ").append(datahtml);
	}else{
		ddztBoolean = true;
	}
	$("#accounttable tfoot td#sumskje").html(sumskje);
	$("#accounttable tfoot td#sumfkje").html(sumfkje);
}
//收款 ------------------------------------------------

function refreshBankRunnig(){
	loadBankRunnig($("#bankRunningId").val(), $("#_bankId").val(), $("#_ywlx").val(), $("#_sksj").val(), $("#bankRunning_je").val());
}

//银行流水
function loadBankRunnig(id,bankId,sflx,jzsj,je) {

	var type = $('#type').val();
	if(type == "takeFinaneChecked" || type == "takeFinaneRechecked"){
		$("#con_menu_2").show();
	}else{
		$("#con_menu_2").hide();
		return false;
	}
	if((typeof(bankId) == "undefined" || bankId == "") && (typeof(jzsj) == "undefined" || jzsj == "")){
		return false;
	}
	$.ajax({
		url : $("#ctx").val() + "/admin/bank_running!loadBankRunning.action",
		data : {
			'bankRunning.id' : id,
			'bankRunning.bank.Id' : bankId,
			'bankRunning.sflx' : sflx,
			'bankRunning.jzsj' : jzsj,
			'bankRunning.je' : je
		},
		async : false,
		success : function(data) {
			setBankRunnigTable(data,id);
			selectBrTable();
		},
		error : function() {
		}
	});
}

//打开对应的流水
function setBankRunnigTable(msg,id){
	try{
		var jsonObj = jQuery.parseJSON(msg)[0];
		var data    = jsonObj.optionList;
		var sumskje = jsonObj.sumskje;
		var sumfkje = jsonObj.sumfkje;
	}catch(e){
		alert("setAccountTable --> "+e+msg);
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
			datahtml += '<td title=\"' + data[i].jzsj + '\">' + data[i].jzsj + '</td>';
			datahtml += '<td>' + data[i].djm + '</td>';
			datahtml += '<td>' + data[i].dfzh + '</td>';
			datahtml += '<td>' + data[i].dfhm + '</td>';
			datahtml += '<td class="red">' + data[i].srje + '</td>';
			datahtml += '<td class="green">' + data[i].zcje + '</td>';
			datahtml += '<td>' + data[i].bz + '</td>';
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
}

//通过
function collateRecord() {
	if ($("#_initStatVal").val() == 3) {
		alert("该数据财务已审核!");
		refreshBtn();
		return;
	}
	$(":button").attr("disabled", "true");
	var indexNum = $("#_indexNum").val();
	if (indexNum == null || indexNum == "") {
		alert("请先选择审核信息");
		refreshBtn();
		return;
	}
	indexNum = Number(indexNum);
	
	var bankRunningId = $('#bankRunningId').val();
	var type = $('#type').val();
	if (type == null || type == "") {
		alert("TYPE值为空，数据异常请联系管理员");
		refreshBtn();
		return;
	}else if(type == "takeFinaneChecked"){
		if (bankRunningId == null || bankRunningId == "") {
			alert("财务审核，请先选择银行流水数据");
			refreshBtn();
			return;
		}
	}
	
	var recheck = $("#_recheck").val();
	var id = Number(idArray[indexNum]);
	var nextId = Number(idArray[indexNum]);
	//var nextId = Number(idArray[indexNum + 1]);
	
	$.ajax({
		type : "post",
		url : $("#ctx").val() + "/admin/account_flow!collateRecordJson.action",
		data : {
			'id' : id,
			'bankRunningId' : bankRunningId,
			'nextId' : nextId,
			'recheck' : recheck,
			'type' : type,
			'bankRunning.bank.id' : $("#bankRunning_bank_id").val(),
			'bankRunning.fkje' : $("#bankRunning_je").val(),
			'bankRunning.jzsj' : $("#bankRunning_jzsj").val(),
			'bankRunning.dfzh' : $("#bankRunning_dfzh").val(),
			'bankRunning.dfhm' : $("#bankRunning_dfhm").val()
		},
		async : false,
		success : function(msg) {
			if (msg != "") {
				$("#_intIndexNum").text((indexNum + 1));	// 设置显示当前第几条
				$("#_indexNum").attr("value", (indexNum));	// 设置当前数据下标数值
				setBean(msg);
			} else {
				refreshBtn();
				alert("数据已经审核完毕,未查询到下一条数据");
			}
			var _intCheckValue = $("#_intCheckValue").val();	// 已校对总数
			var _intDeleteValue = $("#_intDeleteValue").val();	// 已作废总数
			if (typeof (auditRecord["_" + id]) == "undefined") {	// 未审核
				$("#_intDelete").text(Number(_intDeleteValue) + 1);
				$("#_intDeleteValue").attr("value", Number(_intDeleteValue) + 1);
			} else if (auditRecord["_" + id] == 1) {	// 已通过
				$("#_intCheck").text(Number(_intCheckValue) - 1);
				$("#_intCheckValue").attr("value", Number(_intCheckValue) - 1);
				$("#_intDelete").text(Number(_intDeleteValue) + 1);
				$("#_intDeleteValue").attr("value", Number(_intDeleteValue) + 1);
			}
			auditRecord["_" + id] = 0;// 标记为作废
		},
		complete : function() {
			refreshBtn();
			loadAccout();
			removeMask();
			osmsgiofo(' 操作成功！');
		},
		error : function() {
			removeMask();
		}
	});
}

//回退
function deleteRecord() {
	$(":button").attr("disabled", "true");
	var indexNum = $("#_indexNum").val();
	if (indexNum == null || indexNum == "") {
		alert("请先选择收付待审核数据");
		refreshBtn();
		return;
	}
	var recheck = $("#_recheck").val();
	var type    = $('#type').val();

	indexNum = Number(indexNum);
	var id   = Number(idArray[indexNum]);
	var nextId = Number(idArray[indexNum]);
	$.ajax({
		type : "post",
		url : $("#ctx").val() + "/admin/account_flow!deleteRecordJson.action",
		data : {
			'id' : id,
			'nextId' : nextId,
			'recheck' : recheck,
			'type' : type
		},
		async : false,
		success : function(msg) {
			if (msg != "") {
				$("#_intIndexNum").text((indexNum));	            // 设置显示当前第几条
				$("#_indexNum").attr("value", (indexNum));	        // 设置当前数据下标数值
				setBean(msg);
				osmsgiofo(' 回退操作成功！');
			} else {
				refreshBtn();
			}
			var _intCheckValue = $("#_intCheckValue").val();	    // 已校对总数
			var _intDeleteValue = $("#_intDeleteValue").val();	    // 已作废总数
			if (typeof (auditRecord["_" + id]) == "undefined") {	// 未审核
				$("#_intDelete").text(Number(_intDeleteValue) + 1);
				$("#_intDeleteValue").attr("value", Number(_intDeleteValue) + 1);
			} else if (auditRecord["_" + id] == 1) {	            // 已通过
				$("#_intCheck").text(Number(_intCheckValue) - 1);
				$("#_intCheckValue").attr("value", Number(_intCheckValue) - 1);
				$("#_intDelete").text(Number(_intDeleteValue) + 1);
				$("#_intDeleteValue").attr("value", Number(_intDeleteValue) + 1);
			}
			auditRecord["_" + id] = 0;                              // 标记为作废
			
		},
		complete : function() {
			refreshBtn();
			loadAccout();
			removeMask();
		},
		error : function(e) {
			alert(e);
		}
	});
}

function selectBrTable(){
	$("#brtable tbody tr").click(function(){
		$(this).toggleClass("selected").siblings("tr").removeClass("selected");
		var id = $(this).find("td").eq(7).html();
		if(id != ""){
			$("#cwshButton").attr("disabled", false);
			$("#cwshButton").removeClass("pub_but_disable").addClass("btn_two");
			$("#bankRunningId").val(id);
		}else{
			$("#cwshButton").attr("disabled", true);
			$("#cwshButton").removeClass("btn_two").addClass("pub_but_disable");
			$("#bankRunningId").val("");
		}
		//$(this).toggleClass("selected");
	});
}

//订单信息操作 192
function on_click(name,id,width,type,quantity){
	if(typeof(quantity) == 'undefined'){quantity=1;}
    if(typeof(width) == 'undefined')width=30;
    var val = $("#"+name+"_"+id).text();
	//$("#"+name+"_"+id).attr('onDblClick','');
	if(type=='textarea'){
		$("#"+name+"_"+id).html("<textarea onblur='on_blur(\""+name+"\",\""+id+"\","+width+",this,\""+type+"\",\""+quantity+"\")'>"+val+"</textarea>");
		setTimeout("$('#"+name+"_"+id+" textarea').select()",500);
		setTimeout("$('#"+name+"_"+id+" textarea').focus()",500);
		//$("#"+name+"_"+id+" textarea").position(4);
	}else{
		$("#"+name+"_"+id).html("<input type='text' style='width:"+width+"px;' value='"+val+"' onblur='on_blur(\""+name+"\",\""+id+"\","+width+",this,\""+type+"\",\""+quantity+"\")'>");
	    $("#"+name+"_"+id+" input").focus();
	    $("#"+name+"_"+id+" input").select();
	}
}

function on_blur(name,id,width,obj,type,quantity){
    $.ajax({
    	url:$("#ctx").val() + "/admin/account!ajaxUpdateAccountBz.action",
    	cache: false,
    	data:{'account.id':id,'account.bz':obj.value},
    	type:'POST',
    	dataType:"JSON",
    	timeout:20000, 
    	error:function(){         
			//alert("加载出错,请刷新！"); 
    	},
		success:function(text){
		    if(text!=''){
				//$("#"+name+"_"+id).attr('onDblClick',"on_click('"+name+"','"+id+"', '"+width+"', '"+type+"', '"+quantity+"')");
				if(text.status=='success'){
			    	$("#"+name+"_"+id).html(obj.value+'<i class="onClickIco"></i>');
			    	osmsgiofo(' 操作成功！');
		        }else{
		        	alert(text.message);
		        }
		    }
		}
	 });  
}
function osmsgiofo(msgtxt){
	if($('#OsMsgIofo')){$("#OsMsgIofo").remove();}
	$('body').append('<div id="OsMsgIofo"><div class="msgbj"></div><div class="msgtxt">' + msgtxt + '</div></div>');
	$("#OsMsgIofo").show();
	setTimeout('$("#OsMsgIofo").remove()',2000);
}