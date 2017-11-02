<%@ page language="java" pageEncoding="utf-8"%>
<input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}" />
<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}" />
<input type="hidden" name="page.order" id="order" value="${page.order}" />
<div id="Pagination" class="page"></div>
<script type="text/javascript">

	function auditRefresh(){
		var $listForm = $("#listForm");
		var url = $listForm.attr("action");
		$listForm.attr("action", url + getActionAddParam("?"));
		$listForm.submit();
	    return false;
	}
	
	function pageselectCallback(page_index, jq){
		
		$("#pageNo").val(page_index+1);
		var $listForm = $("#listForm");
		var url = $listForm.attr("action");
		$listForm.attr("action", url + getActionAddParam("?"));
		$listForm.submit();
        return false;
	}
	$(document).ready(function(){
		$("#Pagination").pagination(${page.totalCount}, {
			callback: pageselectCallback,
			items_per_page:${page.pageSize}, 		// 显示条数
           	prev_text: '上一页',  	//上一页按钮里text
           	next_text: '下一页',   	//下一页按钮里text
           	num_display_entries: 5,	//连续分页主体部分分页条目数
           	current_page: ${page.pageNo-1},   		//当前页索引
           	num_edge_entries: 2	//两侧首尾分页条目数 
		});
	});
</script>

