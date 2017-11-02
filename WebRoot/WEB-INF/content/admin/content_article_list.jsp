<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%;">
<HEAD>
	<title>文章列表 </title>
	<%@ include file="/WEB-INF/content/common/includewechat.jsp"%>
	<style type="text/css">.pub_input{width:150px;}</style>
	<script>
	
	// increase the default animation speed to exaggerate the effect
	$.fx.speeds._default = 1000;
	jQuery.ajaxSettings.traditional = true;
	var isSuccess=true;
	$(document).ready(function(e) {
		onmouse();
	});
	
	function onmouse(){
		$(".jp-onmouse").mouseover(function() {
			$(this).find(".row-actions").show();
		}).mouseout(function() {
			$(".row-actions").hide()
		})
	}
	
	function input(){
		//url = "${ctx}/admin/content!article_input.action?id=" + id;
		url = "${ctx}/admin/content!article_input.action";
		showDialog(url,"","","");
	}
	
	//信息内容统计
	function stat(){
    	//var param=getParam("totalForm");
		$.ajax({
			url : "${ctx}/admin/content!listStat.action",
			type : "POST",
			data: param,
			success : function(result) {
				setBean(result);
			}
		});
		return false;
    }
	
	//设置
    function setBean(msg) {
    	if(msg == "") return false;
    	try{
			var jsonObj = jQuery.parseJSON(msg)[0];
			if(jsonObj.error == ""){
				$("#normal").text( "("+jsonObj.normal+")" );
				$("#draft").text( "("+jsonObj.draft+")" );
				$("#delete").text( "("+jsonObj.delete+")" );
			}else{
				alert("[setBean] error:"+jsonObj.error);
			}
		}catch(e){
			alert("[setBean] "+e.message);
			return;
		}
    }
	</script>
</HEAD>
<body class="con_r">

    <div class="content-wrapper" style="min-height: 698px;">
		<!-- Main content -->
		<div class="content">
			<div class="row content-row">
				<ul class="list-inline" style="float: left">
					<li class="publish">
						<a href="/jpress/admin/content?m=article&amp;p=article&amp;c=list&amp;s=normal">已发布 <span class="count" id="normal"></span></a>|
					</li>
					<li class="publish">
						<a href="/jpress/admin/content?m=article&amp;p=article&amp;c=list&amp;s=draft">草稿 <span class="count" id="draft"></span></a>|
					</li>
					<li class="trash">
						<a href="/jpress/admin/content?m=article&amp;p=article&amp;c=list&amp;s=delete">垃圾箱 <span class="count" id="delete"></span></a>
					</li>
				</ul>
		
				<div class="jp-right ">
					<button class="btn btn-block btn-sm btn-default" type="button" id="addButton" onclick="input()">添加</button>
				</div>
				<form class="form-horizontal" method="POST" style="float: right" action="${ctx}/admin/content?arch_list.action">
					<div class="input-group input-group-sm">
						<input id="post-search-input" class="form-control" type="search" value="" name="k" placeholder="请输入关键词">&nbsp;&nbsp;
						<input id="search-submit" class="btn btn-default btn-sm" type="submit" value="搜索">
					</div>
				</form>
			</div>
		
			<div class="row content-row">
				<div class="jp-left">
					<select class="form-control input-sm jp-width120" name="action">
						<option value="">==批量操作==</option>
						<option value="delete">删除</option>
					</select>
				</div>
				<div class="jp-left  ">
					<button class="btn btn-block btn-sm btn-default" type="button" onclick="batchAction()">应用</button>
				</div>
				
				<div class="jp-left">
					<select class="form-control input-sm jp-width120 jp-selected">
						<option value="">全部专题</option>
					</select>
				</div>
				<div class="jp-left">
					<select class="form-control input-sm jp-width120 jp-selected">
						<option value="">全部分类</option>
					</select>
				</div>
				
				<div class="jp-left  ">
					<form action="/jpress/admin/content?m=article&amp;p=article&amp;c=list&amp;s=&amp;k=" method="POST">
						<input type="hidden" class="tids" name="tids">
						<input type="submit" class="btn btn-block btn-sm btn-default" value="筛选">
					</form>
				</div>
			</div>
		
			<div class="box">
				<!-- /.box-header -->
				<div class="box-body">
					<form action="" method="POST" id="form">
						<input type="hidden" name="ucode" value="c12d1f6e0aaed1151aeda1704b85e12a">
						<table class="table table-striped" style="word-break:break-all">
							<thead>
								<tr>
									<th style="width:20px;"><input name="dataItem" onclick="checkAll(this)" type="checkbox" value="0"></th>
									<th>标题</th>
									<th style="width:10%">作者</th> 
									<th style="width:15%">分类</th> 
									<th style="width:100px;">发布日期</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="page.result" var="list" status="status">
								<tr class="jp-onmouse">
									<td><input name="dataItem" type="checkbox" value="1"></td>
									<td><strong><a href="${ctx}/info/content!view.action?contentID=<s:property value="#list[0]"/>" target="_bank">
										<span class="article-title"><s:property value="#list[1]"/></span></a> </strong>
										<div class="jp-flash-comment">
											<p class="row-actions jp-cancel-pad" style="display: none;">
												<span class="approve"> <a class="vim-a" href="${ctx}/admin/content!edit.action?contentID=<s:property value="#list[0]"/>">编辑</a></span> 
													<span class="approve">|<a class="vim-a" href="javascript:draft('<s:property value="#list[0]"/>')">草稿</a></span> 
												<span class="spam">|<a class="vim-s vim-destructive" href="javascript:trash('<s:property value="#list[0]"/>')">垃圾箱</a></span> 
												<span class="trash">|<a class="delete vim-d vim-destructive" href="/jpress/c/11111111111中仍__" target="_blank">查看</a>
												</span> 
											</p>
										</div>
									</td>
									<td><s:property value="#list[2]"/></td>
									<td>
										&nbsp;
									</td>
									<td><s:date name="#list[3]" format="yyyy-MM-dd H:mm:SS"/></td>
								</tr>
								</s:iterator>
							</tbody>
							<tfoot>
								<tr>
									<th style="width:20px;"><input name="dataItem" onclick="checkAll(this)" type="checkbox" value="0"></th>
									<th>标题</th>
									<th style="width:10%">作者</th> 
									<th style="width:15%">分类</th> 
									<th style="width:90px;">发布日期</th>
								</tr>
							</tfoot>
						</table>
					</form>
				</div>
				<!-- /.box-body -->
			</div>
			<s:if test="page.result.size() > 0">
				<div class="cf">
					<div class="pagerBar clear">
						<div class="pub_l" >
	                        <%@include file="/WEB-INF/content/common/jpage.jsp"%>
	                    </div>
						<div class="bar">
							总数<s:property value="page.totalCount"/>条/第<s:property value="page.pageNo"/>页/共<s:property value="page.totalPages"/>页
	                    </div>
					</div>
				</div>
			</s:if>
			<s:else>
				<div class="noRecord">没有找到任何记录!</div>
			</s:else>
			<!-- /.row -->
		</div>
		<!-- /.content -->
	</div>
	<div id="popupDialog" title="添加/编辑银行账户"></div>
	<div id="tranDialog" title="添加/编辑账内划转"></div>
    <div id="notice2" title="提示消息"></div>
</body>
</html>