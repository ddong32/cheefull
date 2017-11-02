<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
	<title>订单管理</title>
	<%@ include file="/WEB-INF/content/common/include.jsp"%>
	<script type="text/javascript" src="${ctx}/scripts/business_order.js"></script>
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
	<style> 
		select{ border:1px solid #b4d0e5;line-height:30px;margin:-1px;padding:4px 3px;font-size:13px;} 
	</style> 
</head>
<body class="list nobg">
    <div class="input" style="zoom: 1; padding:10px; min-width:1200px;">
        <table class="orderTable orderTableAlign" id="orderTable">
          <thead>
            <tr>
              <th colspan="14" align="left">
              	<span class="fl w200" >订单线路</span>
             	<span class="fr w300 tar mr10">
		        	<input id="dooButton" type="button" onfocus="this.blur();" onclick="businessDdzt('1')" class="pub_but red" value="确认订单"/>
					<security:authorize ifAnyGranted="ROLE_ADMIN,ROLE_MANAGER">
						<input id="canButton" type="button" onfocus="this.blur();" onclick="businessDdzt('0')" class="pub_but blue" value="取消订单"/>
					</security:authorize>
					<input id="modButton" type="button" onfocus="this.blur();" onclick="business()" class="pub_but" value="修改订单"/>&nbsp;
					<input id="priButton" type="button" onfocus="this.blur();" onclick="printEvent()" class="pub_but" value="打印订单"/>&nbsp;
				</span>
				<span class="fr w500 alert-tip" style=""></span>
              </th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <th>订单号：</th>
              <td><div id="_orderId"></div></td>
              <th>业务类型：</th>
		      <td>
		      	<s:select name="business.ywlx" list="ywlxMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" rule="noempty"></s:select>
		      </td>
		      <th>线路分类：</th>
              <td>
		      	<s:select name="business.ddlx" list="ddlxMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" rule="noempty"></s:select>
		      </td>
              <th>成人：</th>
              <td><div id="_sumAdultNo"></div></td>
              <th>应收：</th>
              <td><div id="_ygs" class="fwb tar"></div></td>
              <th>应付：</th>
              <td><div id="_ygf" class="fwb tar"></div></td>
            </tr>
            <tr>
              <th>旅行线路：</th>
              <td colspan="3">
              	<s:textfield name="travelLine" id="travelLine" cssClass="formText" style="float:left; width:98%;"></s:textfield>
              </td>
              <th>出团日期：</th>
              <td>
              	<input type="text" class="Wdate" name="business.cfsj" id="business_cfsj" size="12" onchange="business_cfsjchange(this.value)" onfocus="WdatePicker({minDate:'%y-%M-{%d-90}',doubleCalendar:true,dateFmt:'yyyy-MM-dd'})" readonly="true" style="width:100px;" rule="noempty" value="<s:date name="business.cfsj" format="yyyy-MM-dd"/>"/>
              </td>
              <th>儿童：</th>
              <td><div id="_sumChildNo"></div></td>
              <th>已收：</th>
              <td><div id="_yjs" class="red tar"></div></td>
              <th>已付：</th>
              <td><div id="_yjf" class="green tar"></div></td>
            </tr>
            <tr>
              <th width="80">订单状态：</th>
              <td width="180"><div id="_ddzt_zw"></div></td>
              <th width="80">操作人：</th>
              <td width="180"><div id="_lrr"></div></td>
              <th width="80">录单日期：</th>
              <td width="150"><div id="_lrsj"></div></td>
              <th width="80">陪同：</th>
              <td width="100"><div id="_sumEscortNo"></div></td>
              <th width="80">未收：</th>
              <td width="100"><div id="_wsk" class="fwb red tar"></div></td>
              <th width="80">利润：</th>
              <td width="100"><div id="_lr" class="fwb blue tar"></div></td>
            </tr>
          </tbody>
        </table>
        <table class="orderTable">
			<tbody>
			<tr>
			    <th width="75" class="tdRight">操作说明：</th>
			    <td class="tdLeft">
			        <textarea name="business.bz" cols="" rows="" id="business_bz" class="textarea" maxlength="512"></textarea>
			        <div id="_bz"></div>
			    </td>
			</tr>
		    </tbody>
		</table>
        
        <div class="tagHeaderMenu" style="display: none;">
            <ul>
                <li id="menu1" onclick="setTab('menu',1,3)" ><a href="javascript:void(0)" class="act">组团社</a></li>
                <li id="menu2" onclick="setTab('menu',2,3)" ><a href="javascript:void(0)" class="">收付款</a></li>
                <li id="menu3" onclick="setTab('menu',3,3)" style="display: none"><a href="javascript:void(0)" class="">操作日志</a></li> 
            </ul>
        </div>
       
        <div class="listtab1" id="con_menu_1" style="display: block;">
        	<form id="bucForm" action="" method="post">
            <table class="orderTable" id="buctable">
                <thead>
                <tr>
                    <th colspan="11">
                    	<a id="customerLxrAdd" href="${ctx}/admin/customer!input.action" class="popup right mr10" hidefocus>添加客户</a>
                    	<a id="customerDel" onclick="customerDel()" href="#" url="${ctx}/admin/business_customer!ajaxBatchDelete.action" class="right mr20" hidefocus>删除</a>
                        <a id="customerEdit" href="${ctx}/admin/business_customer!input.action?orderId=<s:property value="business.orderId"/>" class="popup right mr10" hidefocus>编辑</a>
                        <a id="customerAdd" href="${ctx}/admin/business_customer!input.action?orderId=<s:property value="business.orderId"/>" class="popup right mr10" hidefocus>添加</a>
						订购单位
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr class="listBg">
                	<th width="20" class="check"><input type="checkbox" class="bucAllCheck" /></th>
                    <th width="300">订购单位</th>
                    <th width="50">成人价</th>
                    <th width="50">儿童价</th>
                    <th width="50">人数</th>
                    <th width="80" class="tar">应收</th>
                    <th width="80" class="tar">已收</th>
                    <th width="80" class="tar">未收</th>
                    <th class="tal">备注</th> 
                    <th width="90">录入时间</th> 
                    <th width="90">操作人</th>
                </tr>
                </tbody>
                <tfoot>
                <tr>
                    <td class="tips">&nbsp;</td>
                    <td class="tips">小计</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td class="tips" id="reshu">&nbsp;</td>
                    <td class="tips red fwb tar" id="hjygs"></td>
                    <td class="tips red fwb tar" id="hjyjs"></td>
                    <td class="tips red fwb tar" id="hjwsk"></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                </tfoot>
            </table>
        	</form>
        </div>
    
        <div class="listtab1" id="con_menu_2" style="display: block;">
	        <table class="orderTable" id="acctable" >
	        	<thead>
                <tr>
                    <th colspan="13">
                    	<a id="jdzhButton"  onclick="accountStatRecall()" href="javascript:void(0)" class="right" style="margin-right:10px">招回</a>
                    	<a id="accountDel"  onclick="accountDel()" href="javascript:void(0)" class="right" style="margin-right:10px" hidefocus>删除</a>
                    	<a id="jdtjButton"  onclick="accountSTAT()" href="javascript:void(0)" class="right" style="margin-right:20px">提交</a>
                        <a id="accountEdit" onclick="accountEdit()" href="javascript:void(0)" class="right" style="margin-right:10px">编辑</a>
                        <!-- a id="accountAdd" onclick="accountAdd()" href="javascript:void(0)" class="right" style="margin-right:10px">添加</a> -->
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
	
			<form name="f" id="f" method="post" action="${ctx}/admin/account!account.action">
	            <table class="orderTable">
	                <tbody>
	                <tr>
	                    <th width="70" class="tdRight">收支类型：</th>
	                    <td width="70" class="tdLeft">
	                    	<s:select id="account_sflx" list="sflxMap" listKey="key" listValue="%{value}" onchange="changsflx(this.value)"></s:select>
	                    </td>
	                    <th width="70" class="tdRight">收支名称：</th>
	                    <td class="tdLeft">
	                    	<select id="customerDwmcSelect" ></select>
	                    	<span id="_accountfklx" style="display:none">
		                    	<s:select id="account_fklx" list="fklxMap" listKey="key" listValue="%{value}" cssClass="formText left" style="display:none"></s:select>
		                    	<s:textfield id="cooperator_dwmc" maxlength="12" rule="" cssClass="formText left" style="width:68%; margin-left:5px; height:25px; line-height:25px"></s:textfield>
		                    	<span id="cooperator_name_span" style="margin-left:5px; line-height:25px; font-weight:bold; cursor:pointer; text-decoration:underline; font-size: 12px; color:red; "> 搜索 </span>
		                    	<s:hidden name="cooperator.id"/>
	                    	</span>
	                    </td>
	                    <th width="70" class="tdRight">金额：</th>
	                    <td width="110" class="tdLeft"><input type="text" name="account.je" id="account_je" value="" style="width:80px;"/> 元</td>
	                    <th width="70" class="tdRight">收支方式：</th>
	                    <td width="70" class="tdLeft">
	                    	<s:select id="account_sffs" list="sffsMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" value="1"></s:select>
	                    </td>
	                    <th width="70" class="tdRight">收支银行：</th>
	                    <td width="120" class="tdLeft">
	                    	<s:select id="account_bank_id" list="bankMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择"></s:select>
	                    </td>
	                    <th width="70" class="tdRight">收支时间：</th>
						<td width="120" class="tdLeft">
							<input type="text" class="Wdate" name="account.sfsj" id="account_sfsj" size="12" onfocus="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})" readonly="true" style="width:100px;" value=""/>
						</td>
	                </tr>
	                <tr>
	                    <th width="80" class="tdRight">备注：</th>
	                    <td class="tdLeft" colspan="11">
	                        <textarea class="combo-text validatebox-text textarea" name="account.bz" id="account_bz" maxlength="512"></textarea>
	                    </td>
	                </tr>
	                <tr>
	                    <th class="tdRight">可操作项：</th>
	                    <td class="tdLeft" colspan="11">
	                        <input type="button" id="accbutton" value="保存" class="pub_but" onfocus="this,blur()" onclick="account()"/>
	                        <input type="button" id="clrbutton" value="清空" class="pub_but" onfocus="this,blur()" onclick="doclear()"/>
	                    	<s:hidden name="account.id"></s:hidden>
	                    </td>
	                </tr>
	                </tbody>
	            </table>
	        </form>
	    </div>
        
        <div class="listtab1" id="con_menu_3" style="display: none;">
			<table class="orderTable">
                <thead>
                <tr>
                    <th colspan="5">操作日志</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th width="140">操作日期</th>
                    <th width="80">操作人</th>
                    <th width="60">操作类型</th>
                    <th width="60">订单状态</th>
                    <th>操作内容/操作备注</th>
                </tr>
                <tr>
                    <td class="tdLeft"><p>2015-04-11 11:26:58</p></td>
                    <td>李俊</td>
                    <td>其它</td>
                    <td>变更中</td>
                    <td class="tdLeft">
                        <p><strong>操作内容：</strong>新增=>变更中 [李俊 2015-04-11 11:26:58]</p>
                    </td>
                </tr>
                </tbody>
			</table>
        </div>
    </div>
    <div id="popupDialog" title="组团社信息" ></div>
    <div id="cooperatorDialog" title="供应商信息" ></div>
    <div id="lineDialog" title="旅行线路"></div>
    <div id="notice" title="提示信息"></div>
    <div id="notice1" title="提示信息"></div>
    <div id="accountDialog" title="收支信息" ></div>
    <form id="inputForm" class="validate" action="${ctx}/admin/user!save.action" method="post">
    	<s:hidden name="business.id"></s:hidden>
    	<s:hidden name="business.orderId"></s:hidden>
    	<s:hidden name="business.travelLine"></s:hidden>
    	<s:hidden name="business.ddlx"></s:hidden>
    	<s:hidden name="business.ywlx"></s:hidden>
    	<s:hidden name="business.cfsj"></s:hidden>
    	<s:hidden name="business.bz"></s:hidden>
    	<s:hidden name="business.ddzt"></s:hidden>
    	<s:hidden name="business.sdate"></s:hidden>
       	<input type="hidden" id="_buessinID" value="${buessinID}" />
       	<input type="hidden" id="ctx" value="${ctx}"/>
    </form>
    <form id="accForm" class="validate" action="${ctx}/admin/account!save.action" method="post">
    	<s:hidden name="account.id"></s:hidden>
		<s:hidden name="account.sflx"></s:hidden>
		<s:hidden name="account.fklx"></s:hidden>
		<s:hidden name="account.sffs"></s:hidden>
		<s:hidden name="account.fkje"></s:hidden>
		<s:hidden name="account.skje"></s:hidden>
		<s:hidden name="account.sfsj"></s:hidden>
		<s:hidden name="account.bz"></s:hidden>
		<s:hidden name="account.businessCustomer.id"></s:hidden>
		<s:hidden name="account.cooperator.id"></s:hidden>
		<s:hidden name="account.orderId"></s:hidden>
		<s:hidden name="account.bank.id"></s:hidden>
		<input type="hidden" name="account.business.id" value="${buessinID}" />
    </form>
    <!-- iframe id="reportFrame" src="" style="display:none; height:0"></iframe> -->
</body>
</html>