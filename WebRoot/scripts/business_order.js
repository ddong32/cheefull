/**
 * 订单界面
 * 覃东梁 19611 2015年10月28日 下午3:03:32
 * 1、按钮控制；2、订单信息显示；3、收支信息显示
 * 
 */

var isSuccess=true;
var isCurrentUser=true;
var ygs = 0, yjf = 0, ygf=0, yjf=0;
var _current = "";
var _trSeq = 0; 
var $orderId = "";
var ddztBoolean = false;

function CheeBusinessOrderInterval() {
	this.cacheData = {
		queryParam : null,
		ids : [],            // 所有id
		id : null,           // 选中的id
		index : 0,
		length : 0,
		tgNum : 0,           // 通过条数
		zfNum : 0,           // 作废条数 
		sysconfig : {},      // 系统配置
		vioBehavours : {},   // 违法行为
		currentBean : null,  // 当前的bean
		auditRecord : [],    // 1 已通过、0作废 undefined 未审核
		pageAuditTypes : null
	};
}

$(function() {
	//收付款列表
    $("#notice").dialog({
        dialogClass:"ppisDialog",
        autoOpen: false,
        resizable:false,
        modal: true,
        buttons: {
            "确定" : function(){
                $(this).dialog('close');
                if (isSuccess){
                	orderTable();
                    accountTable();
                    customerTable();
                    popupBind();
                    doclear();
                }
            }
        }
    });
    //组团社列表
    $("#notice1").dialog({
        dialogClass:"ppisDialog",
        autoOpen: false,
        resizable:false,
        modal: true,
        buttons: {
            "确定" : function(){
                $(this).dialog('close');
                if (isSuccess){
                	$( "#popupDialog" ).dialog('close');
                	orderTable();
                    customerTable();
					popupBind();
                }
            }
        }
    });
	$( "#lineDialog" ).dialog({
		dialogClass:"ppisDialog",
		autoOpen: false,
		width: 600,
		height: "auto",
		minHeight: 0,
		resizable: true,
		modal: true
	});
	
	//提示信息
    $("#notice0").dialog({
        dialogClass:"ppisDialog",
        autoOpen: false,
        resizable:false,
        modal: true,
        buttons: {
            "确定" : function(){
                $(this).dialog('close');
            }
        }
    });
	
	//添加剂
	$( "#popupDialog" ).dialog({
		dialogClass:"ppisDialog",
		autoOpen: false,
		resizable: false,
		width:500,
		maxHeight: window.screen.height-250,
		modal: true,
		close: function() {
			$("#popupDialog").dialog("close");
		}
	});
	$( "#cooperatorDialog" ).dialog({
		dialogClass:"ppisDialog",
		autoOpen: false,
		resizable: false,
		width:500,
		maxHeight: window.screen.height-250,
		modal: true,
		close: function() {
			$("#popupDialog").dialog("close");
		}
	});
	$( "#accountDialog" ).dialog({
		dialogClass:"ppisDialog",
		autoOpen: false,
		resizable: false,
		width:600,
		maxHeight: window.screen.height-250,
		modal: true,
		close: function() {
			$("#dialog").dialog("close");
		}
	});
	$("#travelLine").autocomplete($("#ctx").val() +"/admin/travel!ajaxAutoTravel.action", {
		width: $("#travelLine").width(),
        matchContains: true,  
		scrollHeight: 280,
		dataType: "json",
		delay:500,
		minChars: 1,
		max: 10,
		extraParams: {
		    travelLine: function() { return $("#travelLine").val(); }
		},
		parse: function(data) {
			return $.map(data, function(row) {
				return {
					data: row,
					value: row.line,
					result: row.line
				}
			});
		},
		formatItem: function(row, i, max) {
			return row.line;
		},
		formatMatch: function(row, i, max) {
            return row.line;
        },
		formatResult: function(row) {
			return row.line;
		}
	});
	
	//供应商，搜索
	$( "#cooperator_name_span" ).click(function() {
		$.ajax({
			type: "POST",
			cache: false,
			url: $("#ctx").val() +"/admin/cooperator!dialog.action",
			success: function(msg){
				$("#cooperatorDialog").html(msg).dialog("open");
				$(".ui-widget-overlay").css("height",document.body.scrollHeight);
				cooperatorTrClick();
			}
		});
		return false;
	});
});

$(document).ready(function(e) {
	
    $("#cancel").bind('click',function (){
		$( "#popupDialog" ).dialog('close');
    });
    $("#inputForm").bind("submit", function(){
        var isIllegal = false;
        $("[rule]").each(function(index, element) {
            isIllegal |= testIllegal($(this));
        });
        if (isIllegal){
            $("#errInfo").show();
            return false;
        } else {
            $("#errInfo").hide();
        }
        var param=getParam("inputForm");
        $.ajax({
            url : $("#ctx").val() + "/admin/business!update.action",
            type: "POST",
            data: param,
            success: function(msg){
                isSuccess= (trim(msg)=="您的操作已成功!")?true :false;
                $("#notice").html(msg);
                $("#notice").dialog('open');
                $(".ui-widget-overlay").css("height", document.body.scrollHeight);
            }
        });
        return false;
    });
    
    $("#account_sffs").bind('click',function (){
    	if($("#account_sflx").val() == 2){
    		return false;
    	}
		if($("#account_sffs").val() == 3) {
			$( "#f #account_bank_id" ).val("").attr("disabled",true);
			$( "#f #account_sfsj" ).val("").attr("disabled",true);
		}else{
			$( "#f #account_bank_id" ).attr("disabled",false);
			$( "#f #account_sfsj" ).attr("disabled",false);
		}
    });
    orderTable();
	$orderId = $("#inputForm #business_orderId").val();
	customerTable();
	accountTable();
	popupBind();
	refreshOrderButton();
});

//订单信息
function orderTable() {
	$("#orderTable .alert-tip").hide();
	var id = $("#_buessinID").val();
	var username=$("#username").val();
	var password="$("#password").val();
	// 初始化显示合成图片
	$.ajax({
		type : "post",
		url : $("#ctx").val() + "/admin/business!ajaxBusinessId.action",
		data : {'buessinID' : id},
		async : false,
		success : function(msg) {
			try{
				if (msg == "") {
					alert(id+"订单信息为空！");
					return false;
				}
				var jsonObj = jQuery.parseJSON(msg)[0];
				$("#orderTable #_orderId").text(jsonObj.orderId);
				$("#orderTable #business_travelLine").val(jsonObj.travelLine);
				$("#orderTable #travelLine").val(jsonObj.travelLine);
				$("#orderTable #_ddzt_zw").text(jsonObj.ddzt_zw);
				$("#orderTable #_lrr").text(jsonObj.lrr);
				$("#orderTable #business_ywlx").val(jsonObj.ywlx);
				$("#orderTable #business_ddlx").val(jsonObj.ddlx);
				$("#orderTable #business_cfsj").val(jsonObj.cfsj);
				$("#orderTable #_lrsj").text(jsonObj.lrsj);
				$("#orderTable #_sumAdultNo").text(jsonObj.adultNo);
				$("#orderTable #_sumChildNo").text(jsonObj.childNo);
				$("#orderTable #_sumEscortNo").text(jsonObj.escortNo);
				$("#orderTable #_ygs").text(jsonObj.ygs);
				$("#orderTable #_ygf").text(jsonObj.ygf);
				$("#orderTable #_yjs").text(jsonObj.yjs);
				$("#orderTable #_yjf").text(jsonObj.yjf);
				$("#orderTable #_wsk").text(jsonObj.wsk);

				$("#orderTable #_lr").text(jsonObj.lr);

		    	$("#inputForm #business_id").val(jsonObj.id);
		    	$("#inputForm #business_orderId").val(jsonObj.orderId);
		    	$("#inputForm #business_travelLine").val(jsonObj.travelLine);
		    	$("#inputForm #business_ywlx").val(jsonObj.ywlx);
		    	$("#inputForm #business_cfsj").val(jsonObj.cfsj);
		    	$("#inputForm #business_sdate").val(jsonObj.cfsj);
		    	$("#inputForm #business_bz").val(jsonObj.bz);
		    	$("#inputForm #business_ddzt").val(jsonObj.ddzt);
		    	isCurrentUser = jsonObj.isCurrentUser;
		    	$orderId      = jsonObj.orderId;
			}catch(e){
				alert(e);
				return false;
			}
		}
	});	

}

function popupBind(){
	//组团社编辑
	$(".popup").bind('click',function(){
		var $idsCheckedCheck = $(".list #buctable input[name='ids']:checked");
		$.ajax({
			type: "POST",
			cache: false,
			url: $(this).attr("href")+"?orderId="+$orderId,
			data: $idsCheckedCheck.serialize(),
			success: function(msg){
				$("#popupDialog").dialog({dialogClass:"ppisDialog",width:815});
				//$("#popupDialog").dialog({position:[100, 250]});
				$("#popupDialog").html(msg).dialog("open");
				$(".ui-widget-overlay").css("height",document.body.scrollHeight);
			}
		});
		return false;
	});
	
	//组团社
	var $bucAllCheck = $(".list #buctable input.bucAllCheck");// 全选复选框
	var $bucIdsCheck = $(".list #buctable input[name='ids']:enabled");
	$bucAllCheck.attr("checked",false);
	// 无复选框被选中时,删除按钮不可用
	$(".list #buctable input[name='ids']").click( function() {
		var $bucIdsChecked = $(".list #buctable input[name='ids']:checked");
		if ($bucAllCheck.attr("checked") == "checked"){
			$bucAllCheck.attr("checked",false);
		}
		if($("#inputForm #business_ddzt").val() == 0){
			if ($bucIdsChecked.size() > 0) {
				if ($bucIdsChecked.size() == $bucIdsCheck.size()){
					$bucAllCheck.attr("checked",true);
				}
				if ($bucIdsChecked.size() == 1){
			    	$("#customerEdit").attr("disabled", false).removeClass("disableCss");
				}else{
					$("#customerEdit").attr("disabled", true).addClass("disableCss");
				}
				$("#customerDel").attr("disabled", false).removeClass("disableCss");
			} else {
				$("#customerDel").attr("disabled", true).addClass("disableCss");
				$("#customerEdit").attr("disabled", true).addClass("disableCss");
			}
		}else{
			$("#customerAdd").attr("disabled", true).addClass("disableCss");
			$("#customerEdit").attr("disabled", true).addClass("disableCss");
			$("#customerDel").attr("disabled", true).addClass("disableCss");
		}
	});
	// 全选
	$bucAllCheck.click( function() {
		if($("#inputForm #business_ddzt").val() == 0){
			if ($(this).attr("checked") == "checked") {
				$bucIdsCheck.attr("checked", true);
				$("#customerDel").attr("disabled", false).removeClass("disableCss");
			} else {
				$bucIdsCheck.attr("checked", false);
				$("#customerDel").attr("disabled", true).addClass("disableCss");
			}
		} else {
			$("#customerAdd").attr("disabled", true).addClass("disableCss");
			$("#customerEdit").attr("disabled", true).addClass("disableCss");
			$("#customerDel").attr("disabled", true).addClass("disableCss");
		}
	});

	var $accAllCheck = $(".list #acctable input.accAllCheck");// 全选复选框
	var $accIdsCheck = $(".list #acctable input[name='ids']:enabled");
	$accAllCheck.attr("checked",false);
	// 无复选框被选中时,删除按钮不可用
	$(".list #acctable input[name='ids']").click( function() {

		var $accIdsChecked  = $(".list #acctable input[name='ids']:checked");
		if ($accAllCheck.attr("checked") == "checked"){
			$accAllCheck.attr("checked",false);
		}

		if ($accIdsChecked.size() > 0 && $("#inputForm #business_ddzt").val() == 0) {
			//account.stat: 0：新增；1：提交；2：经理；3：财务；8：招回；9：回退
			//account.sffs: 1:;2:;3:挂账
			var $stat = $(this).parents("tr").find("td[name='tdstat']").text();
			var $sffs = $(this).parents("tr").find("td[name='tdsffs']").text();
			if ($accIdsChecked.size() == $accIdsCheck.size()){
				$accAllCheck.attr("checked",true);
			}
			
			//-----------------------------------------
			//编辑
			if (($stat == 0 || $stat == 8 || $stat == 9) && $accIdsChecked.size() == 1){
				$("#accountEdit").attr("disabled", false).removeClass("disableCss");
			}else{
				$("#accountEdit").attr("disabled", true).addClass("disableCss");
			}
			//提交
			//if(($stat == 0 || $stat == 8 || $stat == 9) && $sffs != 3){
			if($stat == 0 || $stat == 8 || $stat == 9){
				$("#jdtjButton").attr("disabled", false).removeClass("disableCss");
			}else{
				$("#jdtjButton").attr("disabled", true).addClass("disableCss");
			}
			//删除
			if (($stat == 0 || $stat == 8 || $stat == 9) ){
				$("#accountDel").attr("disabled", false).removeClass("disableCss");
			}else{
				$("#accountDel").attr("disabled", true).addClass("disableCss");
			}
			//招回
			//if($stat == 1 && $sffs != 2 && $sffs != 3){
			if($stat == 1){
				$("#jdzhButton").attr("disabled", false).removeClass("disableCss");
			}else{
				$("#jdzhButton").attr("disabled", true).addClass("disableCss");
			}
			if($sffs == 3){
				$accIdsCheck.attr("checked", false);
				$accIdsCheck.attr("disabled", true);
				$(this).attr("disabled", false);
				$(this).attr("checked", true);
			}else{
				$accIdsCheck.attr("disabled", false);
			}
			//-----------------------------------------
			
		} else {
			$("#accountDel").attr("disabled", true).addClass("disableCss");
			$("#accountEdit").attr("disabled", true).addClass("disableCss");
			$("#jdtjButton").attr("disabled", true).addClass("disableCss");
			$("#jdzhButton").attr("disabled", true).addClass("disableCss");       //回退
			
			$accIdsCheck.attr("disabled", false);
		}
	});
	// 全选
	$accAllCheck.click( function() {
		if ($(this).attr("checked") == "checked" && $("#inputForm #business_ddzt").val() == 0) {
			$accIdsCheck.attr("checked", true);
			$("#accountDel").attr("disabled", false).removeClass("disableCss");
			$("#jdtjButton").attr("disabled", false).removeClass("disableCss");
			$("#jdzhButton").attr("disabled", false).removeClass("disableCss");  //回退
		} else {
			$accIdsCheck.attr("checked", false);
			$("#accountDel").attr("disabled", true).addClass("disableCss");
			$("#jdtjButton").attr("disabled", true).addClass("disableCss");
			$("#jdzhButton").attr("disabled", true).addClass("disableCss");      //回退
		}
	});
}

function setTab(name,cursel,n){
	for(i=1;i<=n;i++){
		var menu=document.getElementById(name+i);
		var con=document.getElementById("con_"+name+"_"+i);
		//menu.className=i==cursel?"act":"";
		$("#"+name+i).find("a").removeClass();
		if(i==cursel){
			$("#"+name+i).find("a").addClass("act");
		}
		con.style.display=i==cursel?"block":"none";
	}
}
//提示框方法
function visibleMessage(str,obj)
{
	var width = document.body.clientWidth-event.clientX;
	if(width < 200)
	{
		document.all.meg.style.left = -175;
		document.all.megTip.style.left = 175;
	}
	else
	{
		document.all.meg.style.left = 0;
		document.all.megTip.style.left = 0;
	}
	document.all.tip.style.visibility='visible';
	document.all.tip.style.top = getTop(obj)+20;
	document.all.tip.style.left = getLeft(obj)+5;
	document.all.message.innerText=str;
}

//隐藏提示框
function hiddenMessage()
{
	tip.style.visibility='hidden';
}

//获取元素的纵坐标 
function getTop(e){ 
	var offset=e.offsetTop; 
	if(e.offsetParent!=null) offset+=getTop(e.offsetParent); 
	return offset; 
} 
//获取元素的横坐标 
function getLeft(e){ 
	var offset=e.offsetLeft; 
	if(e.offsetParent!=null) offset+=getLeft(e.offsetParent); 
	return offset; 
}

//------------------------------------------------------------------------
function business(){
	$("#inputForm #business_travelLine").val($("#orderTable #travelLine").val());
	$("#inputForm #business_ywlx").val($("#orderTable #business_ywlx").val());
	$("#inputForm #business_ddlx").val($("#orderTable #business_ddlx").val());
	$("#inputForm #business_cfsj").val($("#orderTable #business_cfsj").val());
	$("#inputForm #business_bz").val($("orderTable #business_bz").val());
    var param=getParam("inputForm");
    $.ajax({
    	url : $("#ctx").val() + "/admin/business!update.action",
        type: "POST",
        data: param,
        success: function(msg){
            isSuccess= (trim(msg)=="您的操作已成功!")?true :false;
            $("#notice").html(msg);
            $("#notice").dialog('open');
            $(".ui-widget-overlay").css("height", document.body.scrollHeight);
        }
    });
}

function businessDdzt(ddzt){
	var customer_wsk_boolean = $("#buctable tfoot td#hjwsk").html();
	if(ddzt == 1){
		if(ddztBoolean){
	        $("#notice").html("收付项目为空，或有未审核的收付款，请联系财务尽快审核后再确认订单！");
	        $("#notice").dialog('open');
			return;
		}
		if(customer_wsk_boolean != "0.00"){
			$("#notice").html("该订单还有未收款：‘"+customer_wsk_boolean+"’，请尽快催款完成订单！");
	        $("#notice").dialog('open');
			return;
		}
	}
	var id = $("#inputForm #business_id").val();
	$.ajax({
		url : $("#ctx").val() + "/admin/business!ajaxBusinessStat.action",
        type: "POST",
        data: {"business.id":id, "business.ddzt":ddzt},
        success: function(msg){
            isSuccess= (trim(msg)=="您的操作已成功!")?true :false;
            if(isSuccess){
            	orderTable();
            	refreshOrderButton();
            }
            $("#notice").html(msg);
            $("#notice").dialog('open');
            $(".ui-widget-overlay").css("height", document.body.scrollHeight);
        }
    });
}

//收支信息保存
function account(){
	var flag=true;
	var sflx=document.f.account_sflx.value;
	var fklx=document.f.account_fklx.value;
	var sffs=document.f.account_sffs.value;
	var je=document.f.account_je.value;
	var bz=document.f.account_bz.value;
	var id=document.f.account_id.value;
	var sfsj=document.f.account_sfsj.value;
	var buc_id=document.f.customerDwmcSelect.value;
	var bank_id=document.f.account_bank_id.value;
	var coop_id=document.f.cooperator_id.value;
	
	if(sflx==''){
		flag=false;
		document.f.account_sflx.onfocus;
		alert("请选择收付类型");
		return false;
	}
	if(sflx!=''&&sffs==''){
		flag=false;
		document.f.account_sffs.onfocus;
		alert("请选择支付方式");
		return false;
	}
	if(sflx!=''&&sflx!=''&&je==''){
		flag=false;
		document.f.account_je.onfocus;
		alert("金额不能为空");
		return false;
	}else if(sflx!=''&&sffs==''&&je!=''&&parseFloat(je)<=0){
		flag=false;
		document.f.account_je.onfocus;
		alert("输入金额应大于0");
		return false;
	}
	if(sflx == '1' && sffs == '1'){
		if(buc_id == ""){
			flag=false;
			document.f.customerDwmcSelect.onfocus;
			alert("请选择订购单位！");
			return false;
		}
		if(bank_id == ""){
			flag=false;
			document.f.account_bank_id.onfocus;
			alert("请选择转帐银行！");
			return false;
		}
		if(sfsj == ""){
			flag=false;
			document.f.account_sfsj.onfocus;
			alert("收款时间不能为空！");
			return false;
		}
	}else{
		if(coop_id == ""){
			flag=false;
			document.f.cooperator_dwmc.onfocus;
			alert("付款单位不能为空！");
			return false;
		}
	}
	$("#accbutton").attr("disabled", true); //收付款保存
	if(flag){
		$("#accForm #account_id").val(id)
		$("#accForm #account_sfsj").val(sfsj)
		$("#accForm #account_sflx").val(sflx);
		$("#accForm #account_sffs").val(sffs);
		if(sflx == 1){
			$("#accForm #account_skje").val(je);
			$("#accForm #account_fkje").val("");
			$("#accForm #account_fklx").val("");
			$("#accForm #account_businessCustomer_id").val(buc_id);
			$("#accForm #account_cooperator_id").val("");
		}else if(sflx == 2){
			$("#accForm #account_skje").val("");
			$("#accForm #account_fkje").val(je);
			$("#accForm #account_fklx").val(fklx);
			$("#accForm #account_businessCustomer_id").val("");
			$("#accForm #account_cooperator_id").val(coop_id);
		}
		$("#accForm #account_bank_id").val(bank_id)
		$("#accForm #account_bz").val(bz)
		$("#accForm #account_orderId").val($("#inputForm #business_orderId").val());
	
		var param=getParam("accForm");
        $.ajax({
            url : $("#ctx").val() + "/admin/account!save.action",
            type: "POST",
            data: param,
            success: function(msg){
                isSuccess= (trim(msg)=="您的操作已成功!")?true :false;
                $("#notice").html(msg);
                $("#notice").dialog('open');
                $(".ui-widget-overlay").css("height", document.body.scrollHeight);
                doclear();
            }
        });
    }
	$("#accbutton").attr("disabled", false); //收付款保存
}

function accountAdd(){
	$.ajax({
		type: "POST",
		cache: false,
		url : $("#ctx").val() + "/admin/account!lookinput.action",
		success: function(msg){
			$("#accountDialog").html(msg).dialog("open");
			$(".ui-widget-overlay").css("height",document.body.scrollHeight);
		}
	});
	return false;
}

//组团社
function customerTable(){
	$.ajax({
		url : $("#ctx").val() + "/admin/business_customer!ajaxFindOrderID.action",
		data: {"businessCustomer.orderId": $orderId},
		async: false,
		success : function(data) {
			setCustomerTable(data);
		}
	});
}
function setCustomerTable(msg){
	try{
		var jsonObj = jQuery.parseJSON(msg)[0];
		var data    = jsonObj.optionList;
		var hjygs   = jsonObj.hjygs;
		var hjyjs   = jsonObj.hjyjs;
		var hjwsk   = jsonObj.hjwsk;
		var reshu   = jsonObj.sumAdultNo + "'" + jsonObj.sumChildNo + "'" + jsonObj.sumEscortNo ;
		var ztsmap  = jsonObj.ztsmap;
	}catch(e){
		alert("setCustomerTable --> "+e);
		return false;
	}
	if (data.length > 0) {
		$("#customerDwmcSelect option").remove();
		$("#customerDwmcSelect").append("<option value=''>请选择</option>");
		var datahtml = '';
		$.each(data, function(i) {
			var trclass  = '';
			if(i%2==1){
				trclass = "class=\"cor\"";
			}else{
				trclass = "class=\"listBg\"";
			}

			datahtml += '<tr ' + trclass + '>';
			datahtml += '<td>' + '<input type="checkbox" name="ids" id="buc'+data[i].id+'" value="'+data[i].id+'" />'+ '</td>';
			datahtml += '<td class="tal">' + data[i].customerDwmc + '</td>';
			datahtml += '<td>' + data[i].crj + '</td>';
			datahtml += '<td>' + data[i].etj + '</td>';
			datahtml += '<td>' + data[i].adultNo + '\'' + data[i].childNo + '\'' + data[i].escortNo +'</td>';
			datahtml += '<td class="tar red">' + data[i].ygs + '</td>';
			datahtml += '<td class="tar red">' + data[i].yjs + '</td>';
			datahtml += '<td class="tar red">' + data[i].wsk + '</td>';
			datahtml += '<td class="tal">' + data[i].cbcbz + '</td>';
			datahtml += '<td>' + data[i].lrsj + '</td>';
			datahtml += '<td>' + data[i].lrr + '</td>';
			$("#customerDwmcSelect").append("<option value='"+data[i].id+"'>"+data[i].customerDwmc+"</option>");
		});
		$("#buctable tbody tr:not(:first)").remove();
		$("#buctable tbody ").append(datahtml);
		//clickCustomerTable(); 客户信息
	}
	//alert(ztsmap);
	if (ztsmap.length > 0) {
		$.each(ztsmap,function(name,value) {
           alert(name+","+value);
		});
	}
	$("#buctable tfoot td#hjygs").html(hjygs);
	$("#buctable tfoot td#hjyjs").html(hjyjs);
	$("#buctable tfoot td#hjwsk").html(hjwsk);
	$("#buctable tfoot td#reshu").html(reshu);
	
	$("#orderTable tbody #sumAdultNo").html(jsonObj.sumAdultNo);
	$("#orderTable tbody #sumChildNo").html(jsonObj.sumChildNo);
	$("#orderTable tbody #sumEscortNo").html(jsonObj.sumEscortNo);
	$("#orderTable tbody #business_ygs").html(jsonObj.hjygs);
}

//点击行显示
function clickCustomerTable(){
	$("#buctable tbody tr:odd").addClass("odd");
	$("#buctable tbody tr:first-child").addClass("odd");
	$("#buctable tbody tr:not(.odd)").hide();
	$("#buctable tbody td:not(:last-child):not(:first-child)").click(function(){
		var _trObj = $(this).parent();
		_trSeq = _trObj.parent().find("tr").index($(this).parent()[0]);
		var tdSeq = _trObj.find("td").index($(this)[0]); 
		if(_trObj.attr("class").indexOf("odd") > 0 && tdSeq != 2){
			if(_current != _trSeq){
				$("#buctable tbody tr:not(.odd)").hide();
			}
			_trObj.next("tr").toggle();
			_trObj.find(".arrow").toggleClass("up");
			_current = _trSeq;
		}
	});
}

//组团社删除
function customerDel(){
	var url = $("#customerDel").attr("url")+"?orderId="+$orderId;
	var $idsCheckedCheck = $(".list #buctable input[name='ids']:checked");
	if (confirm("您确定吗？") == true) {
		$.ajax({
			type: "post",
			url: url,
			data: $idsCheckedCheck.serialize(),
			dataType: "json",
			async: false,
			beforeSend: function(data) {
				$("#customerDel").attr("disabled", true).addClass("disableCss");
				//$('#BusyMsg').show();
			},
			success: function(data) {
				if(data){
					if (data.status == "success") {
						alert(data.message);
						$idsCheckedCheck.parent().parent().remove();
						orderTable();
						customerTable();
						popupBind();
					}
				}
			},
			complete: function() {
		        
			},
            error:function(){
            	alert("批量操作错误");
            }
		});
	}
}

//收款
function accountTable(){
	$.ajax({
		url : $("#ctx").val() + "/admin/account!ajaxBusinessAccountTable.action",
		data: {"account.orderId": $orderId},
		async: false,
		success : function(data) {
			setAccountTable(data);
		}
	});
}

function setAccountTable(msg){
	try{
		if(msg.indexOf("Exception")<0){
			var jsonObj = jQuery.parseJSON(msg)[0];
			var data    = jsonObj.optionList;
			var sumskje = jsonObj.sumskje;
			var sumfkje = jsonObj.sumfkje;
			ddztBoolean = false;
		}else{
			alert("setAccountTable --> " + msg);
			return false;
		}
	}catch(e){
		alert("setAccountTable --> " + e);
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
			}else if(data[i].stat=="9"){
				statClass = "class=\"green\"";
			}else{
				statClass = "";
			}
			var checkboxinit = '';
			var sffsClass=''
			if(data[i].sffs=="3"){
				//checkboxinit = "disabled";
				sffsClass = "class=\"red\"";
			}else{
				sffsClass = "";
			}
			//if((data[i].stat == '0' || data[i].stat == '8') && data[i].sffs != '3'){
			if(data[i].stat != '3' && data[i].sffs != '3'){
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
		$("#acctable tbody tr:not(:first)").remove();
		$("#acctable tbody ").append(datahtml);
		//clickCustomerTable();
	}else{
		ddztBoolean = true;
	}
	$("#acctable tfoot td#sumskje").html(sumskje);
	$("#acctable tfoot td#sumfkje").html(sumfkje);
}

////收付类型     1：收款；2：付款；
function changsflx(sflx){
	if(sflx == 2){
		document.f.customerDwmcSelect.style.display="none";
		$("#_accountfklx").css('display','block');
		$("#account_bank_id").attr('value',"")
		$("#account_bank_id").attr('disabled',true).addClass("disableCss");
		$("#account_sfsj").attr('disabled',true).addClass("disableCss");
	}else{
		document.f.customerDwmcSelect.style.display="block";
		$("#_accountfklx").css('display','none');
		$("#account_bank_id").attr('value',"")
		$("#account_bank_id").attr('disabled',false).removeClass("disableCss");
		$("#account_sfsj").attr('disabled',false).removeClass("disableCss");
	}
}

function accountEdit(){
	$("#clrbutton").attr("disabled", false);
	var $idsCheckedCheck = $(".list #acctable input[name='ids']:checked");
	$.ajax({
		type: "post",
		url : $("#ctx").val() + "/admin/account!ajaxLoadAccountId.action",
		data: $idsCheckedCheck.serialize(),
		success : function(msg) {
			if (msg != '') {
				setAccountEdit(msg);
			}
		},
		before: function() {
			doclear();
		},
        error:function(){
        	alert("批量操作错误");
        }
	});
	return false;
}

function setAccountEdit(msg){
	var currentJsonObj = "";
	try{
		var jsonObj = jQuery.parseJSON(msg)[0];
		currentJsonObj = jsonObj;
	}catch(e){
		alert(e);
		return;
	}
	changsflx(currentJsonObj.sflx);
	document.f.account_id.value=currentJsonObj.id;
	document.f.account_sflx.value=currentJsonObj.sflx;
	document.f.account_sffs.value=currentJsonObj.sffs;
	document.f.account_bank_id.value=currentJsonObj.bankId;
	document.f.cooperator_id.value=currentJsonObj.coopId;
	document.f.account_sfsj.value=currentJsonObj.sfsj;
	if(currentJsonObj.sflx == 1){
		document.f.customerDwmcSelect.value=currentJsonObj.bucId;
		document.f.account_je.value=currentJsonObj.skje;
	}else if(currentJsonObj.sflx == 2){
		document.f.account_fklx.value=currentJsonObj.fklx;
		document.f.cooperator_dwmc.value=currentJsonObj.coopDwmc;
		document.f.account_je.value=currentJsonObj.fkje;
	}
	document.f.account_bz.value=currentJsonObj.bz;
}

//删除收付记录
function accountDel(){
	var $idsCheckedCheck = $(".list #acctable input[name='ids']:checked");
	if (confirm("您确定吗？") == true) {
		$.ajax({
			type: "post",
			url : $("#ctx").val() + "/admin/account!ajaxBatchDelete.action?orderId="+$orderId,
			data: $idsCheckedCheck.serialize(),
			dataType: "json",
			async: false,
			beforeSend: function(data) {
				$("#accountDel").attr("disabled", true).addClass("disableCss");
			},
			success: function(data) {
				if(data){
					if (data.status == "success") {
						alert(data.message);
						$idsCheckedCheck.parent().parent().remove();
						orderTable();
						accountTable();
						popupBind();
					}
				}
			},
			complete: function() {
		        
			},
            error:function(){
            	alert("批量操作错误");
            }
		});
	}
}

//订单管理 - 提交
function accountSTAT(){
	$idsCheckedCheck = $(".list #acctable input[name='ids']:checked");
	$.ajax({
		url : $("#ctx").val() + "/admin/account!ajaxAccountStat.action",
		type: "POST",
		data: $idsCheckedCheck.serialize(),
		dataType: "json",
		async: false,
		success: function(data){
			if(data && data.status == "success"){
				alert(data.message);
				$idsCheckedCheck.parent().parent().remove();
				accountTable();
				popupBind();
				refreshOrderButton();
			}
		}
	});
}

//招回
function accountStatRecall(){
	$idsCheckedCheck = $(".list #acctable input[name='ids']:checked");
	$.ajax({
		url : $("#ctx").val() + "/admin/account!ajaxAccountStatRecall.action",
		type: "POST",
		data: $idsCheckedCheck.serialize(),
		dataType: "json",
		async: false,
		success: function(data){
			if(data && data.status == "success"){
				alert(data.message);
				$idsCheckedCheck.parent().parent().remove();
				accountTable();
				popupBind();
				refreshOrderButton();
			}
		}
	});
}

function cooperatorTrClick(){
	$("#cooperatorDialog table:eq(1) tr:gt(0)").click(function () {
		$("#cooperator_id").val($(this).find("td").eq(0).html());
		$("#cooperator_dwmc").val($(this).find("td").eq(2).html());
		$("#cooperatorDialog").dialog("close");
	})
}

function refreshOrderButton(){
	var ddzt = $("#inputForm #business_ddzt").val();
	if(ddzt == 0 && isCurrentUser == "true"){
	//if(ddzt == 0){
		$("#modButton").attr("disabled", false);
    	$("#dooButton").attr("disabled", false);
    	$("#canButton").attr("disabled", true);
    	$("#accbutton").attr("disabled", false); //收付款保存
    	$("#customerAdd").attr("disabled", false).removeClass("disableCss");
    	$("#customerLxrAdd").attr("disabled", false).removeClass("disableCss");

    	$("#modButton").removeClass("pub_but_disable").addClass("pub_but");
    	$("#dooButton").removeClass("pub_but_disable").addClass("pub_but red");
    	$("#canButton").removeClass("pub_but blue").addClass("pub_but_disable");
    	$("#accbutton, #clrbutton").removeClass("pub_but_disable").addClass("pub_but");
    	
    	$("#orderTable #business_travelLine").attr("disabled", false);
    	$("#orderTable #travel_name_span").css("display", "block");
		$("#orderTable #business_cfsj").attr("disabled", false); 
		$("#orderTable #business_ywlx").attr("disabled", false); 
		$("#orderTable #business_ddlx").attr("disabled", false); 
		
		$(".list #acctable input[name='ids']:enabled").attr("disabled", false);
		$(".list #f").css("display", "block");
	} else {
		$("#modButton").attr("disabled", true);
    	$("#dooButton").attr("disabled", true);
    	$("#canButton").attr("disabled", false);
    	
        $("#customerAdd").attr("disabled", true).addClass("disableCss");
        $("#customerLxrAdd").attr("disabled", true).addClass("disableCss");
		$("#accbutton").attr("disabled", true);        //添加保存
		
		$("#modButton").removeClass("pub_but").addClass("pub_but_disable");
		$("#dooButton").removeClass("pub_but red").addClass("pub_but_disable");
		$("#canButton").removeClass("pub_but_disable").addClass("pub_but blue");
		$("#accbutton, #clrbutton").removeClass("pub_but").addClass("pub_but_disable");
		
		$("#orderTable #business_travelLine").attr("disabled", true);
		$("#orderTable #travel_name_span").css("display", "none");
		$("#orderTable #business_cfsj").attr("disabled", true);
		$("#orderTable #business_ywlx").attr("disabled", true);
		$("#orderTable #business_ddlx").attr("disabled", true);
		
		$(".list #acctable input[name='ids']:enabled").attr("disabled", true);
		$(".list #f").css("display", "none");
	}
	$("#customerEdit").attr("disabled", true).addClass("disableCss");      //组团社编辑 
	$("#customerDel").attr("disabled", true).addClass("disableCss");	   //组团社删除
	$("#accountDel").attr("disabled", true).addClass("disableCss");        //收付款删除
	$("#accountEdit").attr("disabled", true).addClass("disableCss");       //收付款编辑 
	$("#jdtjButton").attr("disabled", true).addClass("disableCss");        //收付款提交
	$("#jdzhButton").attr("disabled", true).addClass("disableCss");        //收付款招回
	$("#clrbutton").attr("disabled", true);
	
	if(isCurrentUser == "false"){
		$("#canButton").attr("disabled", true);
		$("#canButton").removeClass("pub_but blue").addClass("pub_but_disable");
	}
}

function doclear(){
	$("#f :input").not(':button, :submit, :reset').val("").removeAttr('checked').removeAttr('selected');
}

function travelTrClick(){
	$("#lineDialog table:eq(1) tr:gt(0)").click(function () {
		$("#orderTable #business_travelLine").val($(this).find("td").eq(0).html());
		$("#lineDialog").dialog("close");
	})
}
function business_cfsjchange(_v){
	var _current_fsj = $("#inputForm #business_sdate").val();
	if(_v != _current_fsj){
		$("#orderTable .alert-tip").text("提示：你已修改了订单的出发时间，订单编号也会被系统重新编排！");
		$("#orderTable .alert-tip").show(600);
	}else{
		$("#orderTable .alert-tip").text("");
		$("#orderTable .alert-tip").hide();
	}
}

function printEvent(){
	if($orderId != ""){
		//url = "${ctx}/admin/business!order.action?buessinID=" + buessinID;
		//$("#priButton").attr("disabled", true);
		//$("#priButton").removeClass("pub_but blue").addClass("pub_but_disable");
		var url = $("#ctx").val() +"/admin/business!print.action?business.orderId=" + $orderId;
		//var url = "http://"+window.location.host+"/WebReport/ReportServer?reportlet=businessPrint.cpt&op=view&orderId=" + $orderId;
	    showDialog(url,"","","","printDialog");
  	}else{
  		alert("打印失败，订单编号为空！")
  	}
}

