$().ready( function() {
	var $allCheck = $(".list input.allCheck");// 全选复选框
	var $idsCheck = $(".list input[name='ids']");// ID复选框
	var $deleteButton = $("#deleteButton");// 删除按钮
	
	var $listForm = $("#listForm");// 列表表单
	var $searchButton =  $("#searchButton");// 查询按钮
	var $pageNo = $("#pageNo");// 当前页码
	var $pageSize = $("#page_pageSize");// 每页显示数
	var $sort = $(".list .sort");// 排序
	var $orderBy = $("#orderBy");// 排序方式
	var $order = $("#order");// 排序字段
	
	listCheckbox();
	
	// 无复选框被选中时,删除按钮不可用
	$idsCheck.click( function() {
		var $idsChecked = $(".list input[name='ids']:checked");
		if ($allCheck.attr("checked") == "checked"){
			$allCheck.attr("checked",false);
		}
		if ($idsChecked.size() > 0) {
			if ($idsChecked.size() == $idsCheck.size()){
				$allCheck.attr("checked",true);
			}
			$deleteButton.attr("disabled", false);
		} else {
			$deleteButton.attr("disabled", true);
		}
	});
	
	// 批量操作
	$deleteButton.click( function() {
		var url = $(this).attr("url")+getActionAddParam("?");
		var $idsCheckedCheck = $(".list input[name='ids']:checked");
		if (confirm("您确定吗？") == true) {
			$.ajax({
				type: "post",
				url: url,
				data: $idsCheckedCheck.serialize(),
				dataType: "json",
				async: true,
				beforeSend: function(data) {
					$deleteButton.attr("disabled", true);
					$('#BusyMsg').show();
				},
				success: function(data) {
					//$deleteButton.attr("disabled", true);
					$('#BusyMsg').hide();
					if(data){
						if (data.status == "success") {
							alert(data.message);
							$idsCheckedCheck.parent().parent().remove();
							listForm.submit();
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
	});

	// 查找
	$searchButton.click( function() {
		var url = $listForm.attr("action");
		var username = $("#username").val();
		var password = $("#password").val();
		if (username != "" && password != "") {
			$listForm.attr("action", url + "?username=" + username + "&password=" + password);
		}
		$pageNo.val("1");
		$listForm.submit();
	});

	// 每页显示数
	$pageSize.change( function() {
		$pageNo.val("1");
		$listForm.submit();
	});

	function sortStyle() {
		var orderByValue = $orderBy.val();
		var orderValue = $order.val();
		if (orderByValue != "" && orderValue != "") {
			$(".sort[name='" + orderByValue + "']").after('<span class="' + orderValue + 'Sort">&nbsp;</span>');
		}
	}
	
	// 页码跳转
	$.gotoPage = function(id) {
		$pageNo.val(id);
		$listForm.submit();
	}
});

function listCheckbox(){
	
	$allCheck = $(".list input.allCheck");// 全选复选框
	$idsCheck = $(".list input[name='ids']");// ID复选框
	$deleteButton = $("#deleteButton");// 删除按钮

	if ($idsCheck.size()<=0){
		$allCheck.attr("disabled", true);
	}else{
		$allCheck.attr("disabled", false);
	}
	// 全选
	$allCheck.click( function() {
		if ($(this).attr("checked") == "checked") {
			$idsCheck.attr("checked", true);
			$deleteButton.attr("disabled", false);
		} else {
			$idsCheck.attr("checked", false);
			$deleteButton.attr("disabled", true);
		}
	});
}