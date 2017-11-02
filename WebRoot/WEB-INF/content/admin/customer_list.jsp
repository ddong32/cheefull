<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>客户列表</title>
	<%@ include file="/WEB-INF/content/common/include.jsp"%>
	<style>
		.loading{cursor: pointer; background: url(${ctx}/images/loading_icon.gif) center left no-repeat;}
		.close{cursor: pointer; background: url(${ctx}/images/plus.gif) center left no-repeat;}
		.open{cursor: pointer; background: url(${ctx}/images/minus.gif) center left no-repeat;}
		.end{cursor: default;}
		.treeNode{padding-left: 20px;}
		tr[grade="2"]{background-color:#EAEEFF;}
		tr[grade="3"]{background-color:#FFFFFF;}
	</style>
	<script type="text/javascript">
	$(document).ready(function(){
		//
		$("#popupDialog").dialog({
			dialogClass:"ppisDialog",
			autoOpen: false,
			resizable: false,
			width:770,
			maxHeight: window.screen.height-200,
			modal: true,
			close: function() {
				$("#dialog").dialog("close");
				$( "#dialog_category_code" ).dialog("close");
				$( "#dialog_collect_code" ).dialog("close");
				$( "#dialog_address_parent_id ").dialog("close");
			}
		});
		
		//
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
		
	    //
	    $(".popup").live('click',function(){
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
		//
		$(".treeNode").live("click",function(){
			getChild($(this));
			$this = $(this);
			var id = $(this).attr("id");
			var $parent = $(this).parent();
			var grade = $("#tr_"+id).attr("grade");
			var minGrade = grade;
			$("#tr_"+id).nextAll("tr").each(function(){
				var tmpGrade = $(this).attr("grade");
				if (tmpGrade<grade){
					minGrade = tmpGrade;
				}
			});
			var isopen = $parent.hasClass("open");
			$("#tr_"+id).nextAll("tr").each(function(){
				var thisGrade = $(this).attr("grade");
				var node=$(this).find(".treeNode");
				if(thisGrade <=  grade) {
					return false;
				}
				if(!isopen){
					$(this).show();
					if (thisGrade!=minGrade){
						node.parent().removeClass("close").addClass("open");
						$parent.removeClass("close").addClass("open");
					}
				} else {
					$(this).hide();
					$parent.removeClass("open").addClass("close");
				}
			});
		});
	
	});
	//
	function getChild($o){
		var id = $o.attr("id");
		var $parent = $o.parent();
		var showTab = $parent.find("font").attr("showTab");
		showTab = parseInt(showTab)+1;
		if($o.data("contentLoaded")!= 1 && !$o.hasClass("end")){
			$o.data("contentLoaded", 1);
			$.ajax({
				url: 'customer!ajaxChildren.action',
				data: {'customer.id': id},
				dataType: 'json',
				async: true,
				beforeSend: function() {
					$parent.removeClass("close").addClass("loading");
				},
				success: function(data) {
					var datahtml = '';
					$.each(data, function(i) {
						datahtml += '<tr id="tr_'+data[i].id+'" grade="'+data[i].grade+'">';
						var gradeW = showTab * 30 - 30;
						var hasClass = "";
						var endClass = "";
						if(data[i].hasChildren == 1){
							hasClass = "close";
						}else{
							hasClass = "open";
							endClass = "end";
						}

						var tmpDatahtml="";
						tmpDatahtml = htmlEncode(data[i].areaName);
						datahtml += '<td class="tips" title="'+data[i].pathName+'" align="left"><span style="margin-left:'+gradeW+'px;" class="'+hasClass+'" ><font id="'+data[i].id+'" class="treeNode '+endClass+'" showTab="'+showTab+'">';
						datahtml += tmpDatahtml;
						datahtml += '</font></span></td>';
						
						datahtml += '<td class="tips" title="'+data[i].lxdh+'">'+data[i].lxdh+'</td>';
						datahtml += '<td class="tips" title="'+data[i].qq+'">'+data[i].qq+'</td>';
						datahtml += '<td class="tips" title="'+data[i].wxh+'">'+data[i].wxh+'</td>';
						datahtml += '<td class="tips" title="'+data[i].gsdh+'">'+data[i].gsdh+'</td>';
						datahtml += '<td>'+data[i].typeName+'</td>';
						datahtml += '<td>'+data[i].statName+'</td>';

						datahtml += '<td>';
						datahtml += '<a class="popup" href="customer!input.action?parent.id='+data[i].parentId+'&customer.id='+data[i].id+'" title="编辑">[编辑]</a> ';
						datahtml += '</td>';
						datahtml += '</tr>';
					});
					$("#tr_"+id).after(datahtml);
				},
				error:function()
				{
					alert('=error=');
				},
				complete:function(){
					$parent.removeClass("loading").addClass("open");
				}
			});
		}
	}	
	</script>
</head>
	<body class="list con_r" >
		<form id="listForm" action="${ctx}/admin/customer!list.action" target="_self" method="post">
        <div class="pub_inp_bg">
            <div class="pub_inp">
				<table class="con_table">
				    <tr>
						<td align="right" width="73">单位名称：</td>
						<td align="left" width="135"><s:textfield name="customer.areaName" cssClass="pub_input"/></td>
				    </tr>
				</table>
            </div>
        </div>
        <div class="buttonArea_l">
            <input type="button" id="searchButton" class="pub_but searchButton formButton" value="查找" />
            <input type="button" id="addButton" class="pub_but formButton" href="${ctx}/admin/customer!input.action" value="添加" />
        </div>
        <div class="datagrid">
			<table id="listTable" class="listTable" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
               	<colgroup>
                   	<col />
                       <col width="8%" />
                       <col width="8%" />
                       <col width="8%" />
                       <col width="8%" />
                       <col width="7%" />
                       <col width="5%" />
                       <col width="5%" />
                       <s:if test="#request.needViolationAddressAudit == 1">
                       	<col width="5%"/>
                       </s:if>
                   </colgroup>
				<tr>
					<th>
						<span class="sort" name="areaName">所在地/单位名称/联系人</span>
					</th>
					<th>
						<span class="sort" name="lxdh">手机号码</span>
					</th>
					<th>
						<span class="sort" name="qq">工作QQ</span>
					</th>
					<th>
						<span class="sort" name="wxh">微信号码</span>
					</th>
					<th>
						<span class="sort" name="gsdh">公司电话</span>
					</th>
					<th>
						<span class="sort" name="type">数据类型</span>
					</th>
					<th>
						<span class="sort" name="stat">启用状态</span>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<s:iterator value="page.result" var="list" status="status">
					<tr id="tr_<s:property value='#list.id'/>" grade="<s:property value='#list.grade'/>" class="cor">
						<td class="" title="<s:property value='#list.pathName'/>">
							<span id="sp_<s:property value='#list.id'/>" class="<s:if test="#list.children.size() > 0">close</s:if><s:else>open</s:else>" >
								<font id="<s:property value='#list.id'/>" class="treeNode <s:if test="#list.children.size() > 0"></s:if><s:else>end</s:else>" showTab="1">
									<s:property value="#list.areaName"/>
								</font>
							</span>
						</td>
						<td class="tips" />
			              	<s:property value="#list.lrdh"/>
						</td>
						<td class="tips" />
			              	<s:property value="#list.qq"/>
						</td>
						<td class="tips" />
			              	<s:property value="#list.wxh"/>
						</td>
						<td class="tips" />
			              	<s:property value="#list.gsdh"/>
						</td>
						<td class="tips" title="<s:if test="#list.type == 0">目录</s:if><s:if test="#list.type==1">区间</s:if><s:if test="#list.type==2">设备</s:if>">
							<s:if test="#list.type == 0">城市</s:if><s:if test="#list.type==1">客户</s:if><s:if test="#list.type==2">联系人</s:if>
						</td>
						<td class="tips" title="<s:if test="#list.stat == 1">启用</s:if><s:if test="#list.stat==0">禁用</s:if>">
							<s:if test="#list.stat == 1">启用</s:if><s:if test="#list.stat==0"><font color="red">禁用</font></s:if>
						</td>
						<td><a class="popup" href="customer!input.action?customer.id=<s:property value='#list.id'/>" title="编辑">[编辑]</a></td>
					</tr>
				</s:iterator>
			</table>
			<s:if test="page.result.size() > 0">
				<div class="pagerBar clear">
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
		<div id="popupDialog" title="添加/编辑客户信息"></div>
	</body>
</html>