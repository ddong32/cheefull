function ExpenceAudit() {
	this.cacheData = {};
}

ExpenceAudit.prototype = {
	init : function() {
		var me = this;
		
		this.cacheData.grid = $("#ppisVioAudit1Grid");
		this.cacheData.window = null;
		this.cacheData.queryParam = null; // 查询参数，供弹窗调用
		
		this.bindEvent();
		this.initSelect();
		this.initMainGrid();
		this.searchGrid();
		
		$(window).bind("unload.PpisVioAudit1", function() {
			if (me.cacheData.window) {
				me.cacheData.window.close();
			}
		});
	},
	
	bindEvent : function() {
		var me = this;
		$("#search_ppisvioaudit1").bind("click", function() {
			me.searchGrid();
		});
		
		$("#addressNames_ppisvioaudit1_search").bind("click", function() {
			ppisVioPlaceTree.open({
				callerId : "addressIds_ppisvioaudit1_search",
				callerName : "addressNames_ppisvioaudit1_search",
				isFilter : 1
			});
		});
		
		$("#audit_ppisvioaudit1").bind("click", function() {
			if ($(this).hasClass("disabled")) {
				return;
			}
			me.openWindow();
		});
		
		$("#cancel_ppisvioaudit1").bind("click", function() {
			me.cancel(me.cacheData.grid.jqGrid("getGridParam", "selarrrow"));
		});
		
	},
	
	openWindow : function(id) {
		var winHeight = window.screen.availHeight - 60;
	  	var winWidth  = window.screen.availWidth;
		this.cacheData.window = window.open("../html/ppisvioauditinterval.html?" + guid.gname + "=" + guid.gvalue 
				+ "&type=" + PageAuditTypesEnum.WAITAUDIT + "&id=" + id, "违法审核", "height=" + winHeight + ",width=" + 
				winWidth + ",top=0,left=0,toolbar=no,location=no," + "menubar=no,scrollbars=no,resizable=yes,status=no");
		this.cacheData.window.focus();
	},
	
	initDateWidget : function() {
		
		// 开始时间-结束时间
		dateWidget("beginDate_ppisvioaudit1_search", "endDate_ppisvioaudit1_search");
		dateWidget("endDate_ppisvioaudit1_search", "", "beginDate_ppisvioaudit1_search", "end");	
	},
	
	initSelect : function() {
		
		$("#carNumTypes_ppisvioaudit1_search").multiselect({
			header: true,
			checkAllText: "全选",
		    uncheckAllText: "取消全选",
			classes: "",
			height: 400,
			noneSelectedText: "",
			selectedList: 2,
			selectedText: "# 个选中"
		});
		
		$("#collectionTypes_ppisvioaudit1_search").multiselect({
			header: true,
			checkAllText: "全选",
		    uncheckAllText: "取消全选",
			classes: "",
			height: 200,
			noneSelectedText: "",
			selectedList: 2,
			selectedText: "# 个选中"
		});
		
		$("#behaviourIds_ppisvioaudit1_search").multiselect({
			header: true,
			checkAllText: "全选",
		    uncheckAllText: "取消全选",
			classes: "",
			height: 400,
			buttonWidth: 150, 
			minWidth:300,
			noneSelectedText: "",
			selectedList: 2,
			selectedText: "# 个选中"
		}).multiselectfilter();
	},
	
	initPageData : function() {
		
		$("#beginDate_ppisvioaudit1_search").val(moment().add(-g_ppisSysConfig.AuditShowIntervalDate + 1, "day").format("YYYY-MM-DD 00:00:00"));
		$("#endDate_ppisvioaudit1_search").val(moment().format("YYYY-MM-DD 23:59:59"));
		
		// ppis号牌种类
		getPpisSelect(1, "carNumTypes_ppisvioaudit1_search", true);
		getPpisSelect(7, "collectionTypes_ppisvioaudit1_search", true);
		
		// 违法行为
		var params1 = {
			funcName : PpisViolationBehaviorController.getViolationBehaviour,
			callback : function(result) {
				var records = result.value.records;
				var html = $.map(records, function(n) {
					var value = n.id;
					var code = n.behaviourCode;
					var title = "(" + code + ")" + htmlTitleEncode(n.behaviourMemo);
					var text = "(" + code + ")" + htmlEncode(n.behaviourMemo);
					return "<option title='"+ title +"' value='" + value +"'>" + text + "</option>";
				});
				$("#behaviourIds_ppisvioaudit1_search").html(html.join(""));
			}
		};
		dsstActionClass.doAction(params1);
		getPpisSelect(11, "carNumPlace_ppisvioaudit1_search", false, true, "", "全部");
		$("#audit_ppisvioaudit1").addClass("disabled");
		$("#carNumPlace_ppisvioaudit1_search").val(g_ppisSysConfig.LocalPlateShort);
		$("#carNum_ppisvioaudit1_search").val(g_ppisSysConfig.LocalPlateChar);
	},
	
	initMainGrid : function() {
		var me = this;
		this.cacheData.grid.jqGrid({
			url: "../rest/recordsearch/getRecordByPage",
			datatype: "local",
			jsonReader: {
				root: "value.rows",
				page: "value.page",
				records: "value.records",
				total: "value.total"
			},
			colNames: ["id", "号牌号码","号牌种类", "车辆速度", "限制速度", "违法行为", "违法地点",
			              "违法时间", "采集类型", "审核状态"],
			colModel: [
			{name: "id", hidden:true},
			{name: "carNum", index: "carNum", sortable: false, align: "center"},
			{name: "carNumTypeStr", index: "carNumTypeStr", sortable: false, align: "center"},
			{name: "carSpeed", index: "carSpeed", sortable: false, align: "center"},
			{name: "carSpeedLimit", index: "carSpeedLimit", sortable: false, align: "center"},
			{name: "behaviourName", index: "behaviourName", width: 170, fixed: true,  sortable: false, align: "center",
				formatter : function(cellValue, options, rowObject) {
					if ($.trim(cellValue) == "") {
						return "";
					} else {
						return "(" + rowObject.behaviourCode + ")" + $.trim(cellValue);
					}
			}},
			{name: "addressName", index: "addressName", width: 220, fixed: true, sortable: false, align: "center",
				formatter : function(cellValue, options, rowObject) {
					  var km = rowObject.violationAddressKM || rowObject.addressKM;
					  var m = rowObject.violationAddressM || rowObject.addressM;
					  if (cellValue) {
						  return cellValue + "(" + km + "KM+" + m + "M)";
					  } else {
						  return "";
					  }
				}
			},
			{name: "createDate", index: "createDate", width: 150, fixed: true, sortable: false, align: "center",
				formatter : function(cellValue, options, rowObject) {
					   return moment(cellValue).format("YYYY-MM-DD HH:mm:ss");
			}},
			{name: "categoryName", index: "categoryName", hidden: true},
			{name: "proStat", index: "proStat", width: 100, fixed: true, sortable: false, align: "center",
				   formatter : function(cellValue, options, rowObject) {
					   return ppisAuditStatEnum[cellValue];
			}}
			],
			ondblClickRow : function(rowIndex) {
				var id = me.cacheData.grid.jqGrid("getRowData", rowIndex)["id"];
				me.openWindow(id);
			},
			pager: "#ppisVioAudit1GridPager",
			multiselect: true,
			gridComplete: function() {
				tabResize.commonPage();
			}
		}); 
	},
	
	getQueryParam : function() {
		var beginDate = $("#beginDate_ppisvioaudit1_search").val();
		var endDate = $("#endDate_ppisvioaudit1_search").val();
		var carNumTypes = getMultiselect("value text", "carNumTypes_ppisvioaudit1_search");
		carNumTypes = $.map(carNumTypes, function(n) {
			return n.value;
		});
		var carNumPlace = $("#carNumPlace_ppisvioaudit1_search").val();
		var carNum = $("#carNum_ppisvioaudit1_search").val();
		var addressIdsStr = $.trim($("#addressIds_ppisvioaudit1_search").val());
		var addressIds = [];
		if (addressIdsStr != "") {
			addressIds = addressIdsStr.split(",");
		}
		var behaviourIds = getMultiselect("value text", "behaviourIds_ppisvioaudit1_search");
		behaviourIds = $.map(behaviourIds, function(n) {
			return n.value;
		});
		var collectionTypes = getMultiselect("value text", "collectionTypes_ppisvioaudit1_search");
		collectionTypes = $.map(collectionTypes, function(n) {
			return n.value;
		});
		var ret = validCarSpeed("minSpeed_ppisvioaudit1_search", "maxSpeed_ppisvioaudit1_search");
		if (!ret.flag) {
			return;
		}
		return {
			beginDate : beginDate,
			endDate : endDate,
			carNumTypes : carNumTypes,
			carNum : carNumPlace + carNum,
			addressIds : addressIds,
			behaviourIds : behaviourIds,
			collectionTypes : collectionTypes,
			minSpeed : ret.minSpeed,
			maxSpeed : ret.maxSpeed,
			pageAuditType : PageAuditTypesEnum.WAITAUDIT
		};
	},
	
	searchGrid : function() {
		var msg = this.getQueryParam();
		this.cacheData.queryParam = msg;
		if (msg) {
			this.cacheData.grid.jqGrid("setGridParam", {
				mtype: "POST",
				datatype: "json",
				postData: JSON.stringify(msg),
				page: 1
			}).trigger("reloadGrid");
		}
		$("#audit_ppisvioaudit1").removeClass("disabled");
	},
	
	cancel : function(rowIndexs) {
		var me = this;
		var params = {
				selRows	: rowIndexs,
				actionName : "作废",
				msgName : "违法记录",
				funcName : PpisViolationRecordController.batchInvalidRecord,
				grid : this.cacheData.grid,
				id : "id",
				extRestParams : { pageAuditType : PageAuditTypesEnum.WAITAUDIT },
				callback : function(result) {
					var success = result.value.succeedCount;
					var failed = result.value.failureCount;
					if (failed == 0) {
						me.cacheData.grid.trigger("reloadGrid");
					} else {
						alertDIV("成功作废" + success + "条，" + "失败" + failed + "条。");
						me.cacheData.grid.trigger("reloadGrid");
					}
				}
			};
		dsstActionClass.confirmAction(params);
	},
	
	pass : function(rowIndexs) {
		var me = this;
		var params = {
			selRows	: rowIndexs,
			actionName : "通过",
			msgName : "违法记录",
			funcName : PpisViolationRecordController.batchCollateRecord,
			grid : this.cacheData.grid,
			id : "id",
			extRestParams : { pageAuditType : PageAuditTypesEnum.WAITAUDIT },
			callback : function(result) {
				var success = result.value.succeedCount;
				var failed = result.value.failureCount;
				if (failed == 0) {
					me.cacheData.grid.trigger("reloadGrid");
				} else {
					alertDIV("成功通过" + success + "条，" + "失败" + failed + "条。");
					me.cacheData.grid.trigger("reloadGrid");
				}
			}
		};
		dsstActionClass.confirmAction(params);
	}
};

var expenceAudit;
function expenceaudit() {
	expenceAudit = new ExpenceAudit();
	expenceAudit.init();
}

