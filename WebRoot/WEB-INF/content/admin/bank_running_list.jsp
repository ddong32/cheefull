<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>帐号流水</title>
		<%@ include file="/WEB-INF/content/common/include.jsp"%>
		<script type="text/javascript">
		
		$(document).ready(function() {
			$("#notice").dialog({
				dialogClass:"ppisDialog",
				autoOpen: false,
				resizable:false,
				modal: true,
				buttons: {
					"确定" : function(){
						$(this).dialog('close');
						if (isSuccess){
							$("#listForm").submit();
						}
					}
				}
			});
			
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
		
			$(".popup,#addButton").bind('click',function(){
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
			
			$(".popupDelete").bind('click',function(){
				if(window.confirm('你确定要删除信息吗？')){
	                $.ajax({
						type: "POST",
						cache: false,
						url: $(this).attr("href"),
						success: function(msg){
							isSuccess= (trim(msg)=="您的操作已成功!")?true :false;
							$("#notice").html(msg);
							$("#notice").dialog('open');
							$(".ui-widget-overlay").css("height", document.body.scrollHeight);
						}
					});
				}
	            return false;
			});
			
			$("#bankRunning_sflx").change(function(){
				var value = $(this).children('option:selected').val();  //弹出select的值
				if(value == 2){
					$("#bankRunning_shbj").val("1");
				}else{
					$("#bankRunning_shbj").val("0");
				}
		    });
			
			$("#bankRunning_business_ddlx").change(function(){
				var value = $(this).children('option:selected').val();  //弹出select的值
				if(value != ""){
					$("#bankRunning_shbj").val("1");
				}else{
					$("#bankRunning_shbj").val("0");
				}
		    });
			
			$("#exportButton").click(function(){
				var oldUrl = document.forms[0].action;
       		  	document.forms[0].action = "${ctx}/admin/bank_running!exportXls.action";
			    document.forms[0].submit();
			  	document.forms[0].action = oldUrl;
			});
		});
		
	</script>
	</head>
	<body class="con_r">
		<form id="listForm" action="${ctx}/admin/bank_running!list.action" method="post">
    	<div class="pub_inp_bg"><div class="pub_inp">
			<table class="con_table">
				<tr>
					<td align="right" width="90">审核状态:</td>
					<td align="left" width="80">
						<s:select name="bankRunning.shbj" list="#{'0':'未审核','1':'已审核'}" style="width:80px;" headerKey="" headerValue="未选择"/>
					</td>
					<td align="right" width="90">记账日期：</td>
					<td align="left" width="260">
						<input type="text" class="Wdate" name="bankRunning.beginDate" id="bd" size="12" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'ed\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" class="Wdate" style="width:90px;text-indent:4px" value="${bankRunning.beginDate}" />
						至
						<input type="text" class="Wdate" name="bankRunning.endDate" id="ed" size="12" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'bd\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" class="Wdate" style="width:90px;text-indent:4px"  value="${bankRunning.endDate}"/>
					</td>
					<td align="right" width="80">线路分类：</td>
					<td align="left" width="60">
				      	<s:select name="bankRunning.business.ddlx" list="ddlxMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" ></s:select>
					</td>
					<td align="right" width="90">收支银行：</td>
					<td align="left" width="120">
						<s:select name="bankRunning.bank.id" list="bankMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择"></s:select>
					</td>
					<td align="right" width="90">收支类型:</td>
					<td align="left" width="100">
						<s:select name="bankRunning.sflx" list="sflxMap" listKey="key" listValue="%{value}" style="width:80px;" headerKey="" headerValue="未选择" ></s:select>
					</td>
					<td align="right" width="90">金额：</td>
					<td align="left" width="200">
						<input type="text" name="bankRunning.beginJe" value="${bankRunning.beginJe}" class="w60"/> - <input type="text" name="bankRunning.endJe" value="${bankRunning.endJe}" class="w60"/> 
					</td>
				</tr>
			</table>
        </div></div>
        <div class="buttonArea_l">
            <input type="button" id="searchButton" class="pub_but searchButton formButton" value="查找" />
            <input type="button" id="addButton" class="pub_but formButton" href="${ctx}/admin/bank_running!input.action" value="添加" />
        	<input type="button" id="exportButton" class="pub_but exportButton formButton" value="Excel导出" />&nbsp;
        </div>
        <div class="datagrid">
			<table id="listTable" class="listTable" cellpadding="0" cellspacing="0">
               	<colgroup>
               		<col width="5%"/>
               		<col width="10%"/>
               		<col width="7%"/>
					<col width="7%"/>
					<col width="7%"/>
					<col width="7%"/>
					<col width="11%"/>
					<col width="11%"/>
					<col width="5%"/>
					<col width="7%"/>
					<col width=""/>
					<col width="7%"/>
				</colgroup>
				<tr>
					<th><span>状态</span></th>
					<th><span>订单号</span></th>
					<th><span>收支账户</span></th>
					<th><span>记帐日期</span></th>
					<th><span class="fr">收入</span></th>
					<th><span class="fr">支出</span></th>
					<th><span>对方账户</span></th>
					<th><span>对方户名</span></th>
					<th><span>操作人</span></th>
					<th><span>录入时间</span></th>
					<th><span>备注</span></th>
					<th><span>操作</span></th>
				</tr>
				<s:iterator value="page.result" var="list" status="status">
				<tr <s:if test="#status.count%2==1">class="cor"</s:if>>
					<td>
						<s:if test="#list[1] == \"1\""><label class="red">已审核</label></s:if>
						<s:else>未审核</s:else>
					</td>
					<td class="red"><s:property value="#list[12]"/></td>
					<td><s:property value="#list[2]"/></td>
					<td><s:date name="#list[3]" format="yyyy-MM-dd"/></td>
					<!-- td class="blue tar"><s:property value="getFormatMoney(#list[4])"/></td> -->
					<td class="red tar"><s:property value="getFormatMoney(#list[5])"/></td>
					<td class="green tar"><s:property value="getFormatMoney(#list[6])"/></td>
					<td><s:property value="#list[7]"/></td>
					<td><s:property value="#list[8]"/></td>
					<td><s:property value="#list[9]"/></td>
					<td><s:date name="#list[10]" format="yyyy-MM-dd"/></td>
					<td nowrap title="<s:property value="#list[11]"/>"><s:property value="#list[11]"/></td>
					<td>
						<s:if test="#list[1]==\"1\"">
							<label style="color: #666"></label>
						</s:if>
						<s:else>
							<a class="popup" href="${ctx}/admin/bank_running!input.action?bankRunning.id=<s:property value='#list[0]'/>" shbj="<s:property value='#list[1]'/>" title="编辑">[编辑]</a>
							<a class="popupDelete" href="${ctx}/admin/bank_running!delete.action?bankRunning.id=<s:property value='#list[0]'/>" shbj="<s:property value='#list[1]'/>" title="删除">[删除]</a>
						</s:else>
					</td>
				</tr>
				</s:iterator>
				<tr>
					<td colspan="3" class="tips"></td>
					<td class="tips">总计：</td>
					<td class="tips red tar"><s:property value="page.sumSrje"/></td>
					<td class="tips green tar"><s:property value="page.sumZcje"/></td>
					<td class="tips">&nbsp;</td>
					<td class="tips">&nbsp;</td>
					<td class="tips">&nbsp;</td>
					<td class="tips">&nbsp;</td>
					<td class="tips">&nbsp;</td>
					<td class="tips">&nbsp;</td>
				</tr>
			</table>
			<s:if test="page.result.size() > 0">
				<div class="pagerBar clear">
                       <div class="pager pub_l">
                           <%@ include file="/WEB-INF/content/common/page.jsp"%>
                       </div>
                       <div class="bar">
                          	 总数<s:property value="page.totalCount"/>条/第<s:property value="page.pageNo"/>页/共<s:property value="page.totalPages"/>页
                       </div>
                   </div>
			</s:if>
			<s:else>
				<div class="noRecord">没有找到任何记录!</div>
			</s:else>
		</div>
        </form>
        <div id="popupDialog" title="新增流水"></div>
        <div id="notice" title="提示消息"></div>
	</body>
    <form id="totalForm" action="" method="post">
		<s:hidden name="business.sdate" value="1"/>
    	<s:hidden name="business.beginDate"/>
    	<s:hidden name="business.endDate" />
    	<s:hidden name="business.ddzt" />
    	<s:hidden name="business.orderId" />
    	<s:hidden name="business.wskchecked" />
    	<s:hidden name="business.userids" />
    	<s:hidden name="business.ddlx" />
    </form>
</html>