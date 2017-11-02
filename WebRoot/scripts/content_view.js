function ContentView() {
	this.cacheData = {
		ids : [],            // 所有id
		id : null,           // 选中的id
		index : 0,
		length : 0,
		tgNum : 0,           // 通过条数
		zfNum : 0,           // 作废条数 
		currentBean : null,   // 当前的bean
		pageAuditTypes : null
	};
}

ContentView.prototype = {
	init : function() {
		this.initData();
	},
	initData : function() {
		var me = this;
		var id = $("#_contentID").val();
		$.ajax({
			type : "post",
			url : $("#ctx").val() + "/info/content!ajaxContentId.action",
			data : {
				'contentID' : id
			},
			async : true,
			success : function(result) {
				if (result == "") {
					return false;
				}
				try{
					var jsonObj = jQuery.parseJSON(result)[0];
					$("#title").text(jsonObj.title);
				}catch(e){
					alert("[initData] "+e.message);
					return;
				}
			}
		});
	},
};

var contentview;
function contentview() {
	contentview = new ContentView();
	contentview.init();
}

$(document).ready(function(e) {
	contentview();
});