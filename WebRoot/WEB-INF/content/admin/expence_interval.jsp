<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>报销审核</title>
	<%@ include file="/WEB-INF/content/common/include.jsp"%>
	<script type="text/javascript" src="${ctx}/scripts/expenceinterval.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/styles/order.css"/>
	<script type="text/javascript">
		window.onunload = function() {
			try{
				var explorer =navigator.userAgent ;
				if (explorer.indexOf("MSIE") >= 0) {
					window.dialogArguments.auditRefresh();
				}else if(explorer.indexOf("Chrome") >= 0){
					window.opener.auditRefresh();
				}
			}catch(e){
			}
		};
	</script>
</head>
<body class="list nobg" style="zoom: 1; padding:10px;" onload="self.focus(); expenceAuditInterval()" >
	<div class="input">
		<table class="orderTable orderTableAlign" id="orderTable">
			<thead><tr><th colspan="8" align="left">报销审核</th></tr></thead>
			<tbody>
				<tr>
				    <th width="80">编号：</th>
				    <td><span id="_id"></span></td>
				    <th width="80">报销类型：</th>
					<td width="150"><span id="_bxlx"></span></td>
					<th width="80">发生日期：</th>
					<td width="150"><span id="_fssj"></span></td>
				    <th width="80">金额：</th>
				    <td><span id="_bxje" style="color:red; font-weight: bold;"></span></td>
				</tr>
				<tr>
					<th width="80">报销部门：</th>
					<td width="150"><span id="_departmentName"></span><br /></td>
					<th width="80">报销人：</th>
					<td><span id="_bxr"></span><br /></td>
					<th width="80">报销日期：</th>
					<td><span id="_bxsj"></span><br /></td>
					<th width="80"><br /></th>
					<td width="150"><br /></td>
				</tr>
				<tr>
					<th width="80" class="tdRight">报销内容：</th>
				    <td class="tdLeft" colspan="7">
				        <span id="_bxnr"></span>
                    </td>
				</tr>
			</tbody>
        </table>
        
        <div class="listtab1" id="con_menu_1" style="display: block;">
	        <table class="orderTable" id="acctable" >
	        	<thead>
	               <tr><th >
						<div id="status_div">
							<span> 第 <b id="_intIndexNum">0</b>/<b id="_intTotal">0</b> 条 </span>&nbsp;
							<span>审核状态: <b id="_intStat"></b>&nbsp;&nbsp;</span>
						</div>
						<div id="oprt_buttons">&nbsp;
							<span class="opr_but"> 
								<input type="button" id="button_del" value="回退" class="btn_two"/>
							</span>
							<span class="opr_but">
								<input type="button" id="button_up" value="上一条" class="btn_two"/>
							</span>
							<span class="opr_but">
								<input type="button" id="button_next" value="下一条" class="btn_two"/>
							</span>
							<span class="opr_but"> 
								<input type="button" id="button_che" value="审核" class="btn_two" href="${ctx}/admin/expence!inputcheck.action"/>
							</span>
							<span class="opr_but">
								<input type="button" id="button_pay" value="支付" class="btn_two" href="${ctx}/admin/expence!inputpay.action"/>
							</span>
						</div>
	               </th></tr>
	            </thead>
	        </table>
        </div>
        
        <div class="listtab1" id="con_menu_2" style="display: none;">
	        <table class="orderTable" id="brtable" >
	        	<thead><tr>
	        		<th colspan="14">
						<span class="fl w200" >银行流水</span>
	               	</th>
	               </tr></thead>
				<tbody>
	            <tr class="listBg">
	            	<th width="80">编号</th>
	            	<th width="100">记账时间</th>
	                <th width="100">收支账户</th>
	                <th width="260">对方账户</th>
	                <th width="260">对方账户</th>
	                <th width="80">收入</th>
	                <th width="80">支出</th>
	            </tr>
	            </tbody>
	        </table>
	    </div>
        
        <div class="listtab1" id="con_menu_3" style="display: block;">
	        <table class="orderTable" id="eftable" >
	        	<thead><tr><th colspan="5">审核日志</th></tr></thead>
				<tbody>
	            <tr class="listBg">
	            	<th width="20">编号</th>
	            	<th width="70">操作人</th>
	            	<th width="70">操作时间</th>
	                <th width="70">操作内容</th>
	            </tr>
	            </tbody>
	        </table>
        </div>
	</div>
	<div id="popupDialog"></div>
	<div id="notice2" title="提示消息"></div>
	<s:hidden id="_initStatVal"/>
	<s:hidden name="bankRunning.bank.id"/>
	<s:hidden name="bankRunning.je"/>
	<s:hidden name="bankRunning.jzsj"/>
	<s:hidden name="bankRunning.bz"/>
	<s:hidden name="bankRunning.order_id"/>
	
	<!-- 页面数据显示基本参数 -->
	<input type="hidden" id="ctx" value="${ctx}"/>
	<input type="hidden" id="_indexNum" value="${idIndex}" />
	<input type="hidden" id="idArray" value="<s:property value="#request.idArray"/>"/>
</body>
</html>