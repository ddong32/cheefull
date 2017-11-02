<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>收付款审核</title>
	<%@ include file="/WEB-INF/content/common/include.jsp"%>
	<script type="text/javascript" src="${ctx}/scripts/account_flow_interval.js"></script>
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
<body class="list nobg" style="zoom: 1; padding:10px; min-width:1100px;" onload="self.focus()">
	<div class="input">
		<table class="orderTable orderTableAlign" id="orderTable">
			<thead><tr><th colspan="10" align="left">审核信息</th></tr></thead>
			<tbody>
				<tr>
				    <th width="80">业务流水：</th>
				    <td><span id="_lsh"></span></td>
				    <th width="80">收款方式：</th>
					<td width="150"><span id="_sffs"></span></td>
					<th width="80">收款银行：</th>
					<td width="150"><span id="_djm"></span></td>
				    <th width="80">收入：</th>
				    <td><span id="_skje" style="color:red; font-weight: bold;"></span></td>
				    <th width="80">支出：</th>
				    <td><span id="_fkje" style="color:green; font-weight: bold;"></span></td>
				</tr>
				<tr>
					<th width="80">收付时间：</th>
					<td width="150"><span id="_sksj"></span></td>
					<th width="80">提交人：</th>
					<td><span id="_lrr"></span></td>
					<th width="80">提交时间：</th>
					<td><span id="_lrsj"></span></td>
					<th width="80">经理审核：</th>
					<td width="150"><span id="_shr"></span></td>
					<th width="80">审核时间：</th>
					<td width="150"><span id="_shsj"></span></td>
				</tr>
				<tr>
					<th width="80" class="tdRight">备注：</th>
				    <td class="tdLeft" colspan="5">
				        <span id="_bz"></span>
				    </td>
					<th width="80">财务审批：</th>
					<td width="150"><span id="_spr"></span></td>
					<th width="80">审批时间：</th>
					<td width="150"><span id="_spsj"></span></td>
				</tr>
			</tbody>
        </table>
        
        <div class="listtab1" id="con_menu_1" style="display: block;">
	        <table class="orderTable" id="acctable" >
	        	<thead>
                <tr>
                    <th colspan="14">
						<div>收付项目</div>
						<div id="status_div">
							<span>
								第 <b id="_intIndexNum">0</b>/<b id="_intTotal">0</b> 条
							</span>&nbsp; 
							<span> 
								审核条数:<b id="_intCheck">0</b>&nbsp;&nbsp; 
								回退条数:<b id="_intDelete">0</b>&nbsp;&nbsp; 
								<input type="hidden" id="_intCheckValue" value="0" /> 
								<input type="hidden" id="_intDeleteValue" value="0" />
							</span>
							<span>类型: <b id="_intSflx"></b>&nbsp;&nbsp;</span>
							<span>方式: <b id="_intSffs"></b>&nbsp;&nbsp;</span> 
							<span>审核状态: <b id="_intStat"></b>&nbsp;&nbsp;</span> 
						</div>
						<div class=""></div>
						<div id="oprt_buttons">
							<span class="opr_but"> 
								<input type="button" id="cwhtButton" value="回退" class="btn_two" onclick="deleteRecord();" />
							</span> 
							<span>
								<input type="button" id="button_up" value="上一条" class="btn_two" onclick="getUpRecord();" />
							</span> 
							<span> 
								<input type="button" id="button_next" value="下一条" class="btn_two" onclick="getNextRecord();" />
							</span> 
							<span class="opr_but"> 
								<input type="button" id="cwshButton" value="审核" class="btn_two" onclick="collateRecord();"/>
							</span>
							<span class="opr_but">
								<input type="button" id="payfinace" class="pub_but formButton" href="${ctx}/admin/account_flow!inputpay.action" value="支付" />
							</span>
						</div>
                    </th>
                </tr>
                </thead>
				<tbody>
	            <tr class="listBg">
	            	<th width="100">订单号</th>
	            	<th width="70" class="tar">应收</th>
	                <th width="70" class="tar">已收</th>
	                <th width="70" class="tar">未收</th>
	                <th width="180" class="tal">订购单位</th>
	                <th width="430">旅行线路</th>
	                <th width="70" class="tar">收入</th>
	                <th width="70" class="tar">支出</th>
	                <th width="80">操作人</th>
	                <th >备注</th>
	            </tr>
	            </tbody>
	            <tfoot>
	            <tr>
	                <td class="tips">小计</td>
	                <td>&nbsp;</td>
	                <td>&nbsp;</td>
	                <td>&nbsp;</td>
	                <td>&nbsp;</td>
	                <td>&nbsp;</td>
	                <td class="tips red fwb tar" id="sumskje">&nbsp;</td>
	                <td class="tips green fwb tar" id="sumfkje">&nbsp;</td>
	                <td>&nbsp;</td>
	                <td>&nbsp;</td>
	            </tr>
	            </tfoot>
	        </table>
	    </div>
	    
	    <div class="listtab1" id="con_menu_2" style="display: block;">
	        <table class="orderTable" id="brtable" >
	        	<thead><tr>
	        		<th colspan="14">
						<span class="fl w200" >银行流水</span>
		             	<span class="fr w220 tar mr10">
							<input type="button" id="addButton" class="pub_but formButton" href="${ctx}/admin/bank_running!inputrec.action" value="添加" />
						</span>
                	</th>
                </tr></thead>
				<tbody>
	            <tr class="listBg">
	            	<th width="100">记账时间</th>
	                <th width="100">收支账户</th>
	                <th width="260">对方账户</th>
	                <th width="260">对方账户</th>
	                <th width="80">收入</th>
	                <th width="80">支出</th>
	                <th width="">备注</th>
	            </tr>
	            </tbody>
	        </table>
	    </div>
	    
	    <div class="listtab1" id="con_menu_3" style="display: none;">
	        <table class="orderTable" id="accounttable" >
	        	<thead>
                <tr>
                    <th colspan="13">
						收付项目
                    </th>
                </tr>
                </thead>
				<tbody>
	            <tr class="listBg">
	            	<th width="20" class="check"><!--input type="checkbox" class="accAllCheck" /--></th>
	            	<th width="60">状态</th>
	                <th width="60">类型</th>
	                <th width="60">支付方式</th>
	                <th width="260">收付名称</th>
	                <th width="80">收入</th>
	                <th width="80">支出</th>
	                <th width="80">收支银行</th>
	                <th width="100">收付时间</th> 
	                <th width="70">操作人</th>
	                <th>备注</th>
	                <th width="100">审核时间</th> 
	                <th width="70">审核人</th>
	            </tr>
	            </tbody>
	            <tfoot>
	            <tr>
	                <td>&nbsp;</td>
	                <td class="tips">小计</td>
	                <td>&nbsp;</td>
	                <td>&nbsp;</td>
	                <td>&nbsp;</td>
	                <td class="tips tar fwb red" id="sumskje">&nbsp;</td>
	                <td class="tips tar fwb green" id="sumfkje">&nbsp;</td>
	                <td>&nbsp;</td>
	                <td>&nbsp;</td>
	                <td>&nbsp;</td>
	                <td>&nbsp;</td>
	                <td>&nbsp;</td>
	                <td>&nbsp;</td>
	            </tr>
	            </tfoot>
	        </table>
	    </div>
	</div>
	<div id="popupDialog" title="新增流水"></div>
	<div id="notice2" title="提示消息"></div>
	<s:hidden id="accountId"/>
	<s:hidden id="bankRunningId"/>
	<s:hidden id="_bankId"/>
	<s:hidden id="_ywlx"/>
	<s:hidden id="_br_sksj"/>
	<s:hidden id="_initStatVal"/>
	<s:hidden name="_dwmc"/>
	<s:hidden name="_cpid"/>
	<s:hidden name="bankRunning.bank.id"/>
	<s:hidden name="bankRunning.je"/>
	<s:hidden name="bankRunning.jzsj"/>
	<s:hidden name="bankRunning.dfzh"/>
	<s:hidden name="bankRunning.dfhm"/>
	
	<!-- 页面数据显示基本参数 -->
	<input type="hidden" id="ctx" value="${ctx}"/>
	<input type="hidden" id="type" value="${type}" />
	<input type="hidden" id="_indexNum" value="${idIndex}" />
	<input type="hidden" id="idArray" value="<s:property value="#request.idArray"/>"/>
</body>
</html>