$package('report.businessCustomer');
report.businessCustomer = function() {
	var _box = null;
	var _this = {
		sqlSaveAction : 'save.do',
		sqlSearchAction : 'list.do',
		sqlSaveForm : function() {
			return $("#sqlSaveForm");
		},
		sqlListForm : function() {
			return $("#sqlListForm");
		},
		sqlSaveWin : function() {
			return $("#sql-save-win");
		},
		sqlListWin : function() {
			return $("#sql-list-win");
		},
		sqlSearch : function() {
			jeecg.progress();// 缓冲条
			if (_this.sqlListForm().form('validate')) {
				_this.sqlListForm().attr('action', _this.sqlSearchAction);
				jeecg.saveForm(_this.sqlListForm(), function(data) {
					if (data) {
						jeecg.showAlert("提示", data.msg || '');
					}
					jeecg.closeProgress();// 关闭缓冲条
					_this.sqlListWin().dialog('close');
				});
			} else {
				jeecg.closeProgress();// 关闭缓冲条
			}
		},
		sqlSave : function() {
			jeecg.progress();// 缓冲条
			if (_this.sqlSaveForm().form('validate')) {
				_this.sqlSaveForm().attr('action', _this.sqlSaveAction);
				jeecg.saveForm(_this.sqlSaveForm(), function(data) {
					if (data) {
						jeecg.showAlert("提示", data.msg || '');
					}
					jeecg.closeProgress();// 关闭缓冲条
					_this.sqlSaveWin().dialog('close');
					$('#sql-list').datagrid('reload');
				});
			} else {
				jeecg.closeProgress();// 关闭缓冲条
			}
		},
		edtSqlBtn : function() {
			var row = $('#sql-list').datagrid('getSelected');
			if (row == null) {
				$.messager.alert("提示", "请选择要修改的行！", "info");
			}
			if (row) {
				$('#sql-save-win').dialog('open').dialog('setTitle', '修改SQL语句');
				$('#sqlSaveForm').form('load', row);
			}
		},

		// SQL保存弹出框
		initForm : function() {
			_this.sqlSaveWin().find("#btn-submit").click(function() {
				_this.sqlSave();
			});
			_this.sqlSaveWin().find("#btn-close").click(function() {
				$.messager.confirm('提示', '确定关闭吗?', function(r) {
					if (r) {
						_this.sqlSaveWin().dialog('close');
					}
				});
			});
			$('#btn-search').bind('click', sqltablegrid.reload);
			$('#object-data-list').datagrid({
				toolbar : null,
				fit : true,// 自动大小
				fitColumns : false,/* 自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动 */
				nowrap : true,
				autoRowHeight : false,
				striped : true,
				collapsible : false,
				remoteSort : false,
				pagination : true,// 显示分页
				rownumbers : true,
				singleSelect : false,
				pageList : [ 50, 100, 200 ]
			});
		},
		config : {},
		init : function() {
			_this.initForm();
			_box = new YDataGrid(_this.config);
			_box.init();

			$('#edtSqlBtn').click(_this.edtSqlBtn);
			$('#delSqlBtn').click(function() {
				jeecg.confirm('提示', '确定删除所有记录?', function(r) {
					_this.delSqlBtn(false);
				});
			});
		}
	}
	return _this;
}();

var sqltablegrid = (function() {

	var $grid = $('#sql-list');

	var initStyle = null;
	var toolbar = null;

	var config = {
		title : '<span>语句列表</span> <span class="detail">双击行使用保存的语句</span>',
		url : basePath + '/sqlZdyyj/dataList.do',
		border : false,
		fit : true,
		fitColumns : true,
		nowrap : true,
		autoRowHeight : false,
		striped : false,
		collapsible : false,
		remoteSort : false,
		pagination : true,
		rownumbers : true,
		singleSelect : false,
		method : 'post',
		pageList : [ 50, 100, 200 ],
		toolbar : "#toolbar",
		columns : [ [ 
 			{
 				field : 'sql_mc',
 				title : '语句名称',
 				width : 50,
 				formatter : function(value, row, index) {
 					return row.sql_mc;
 				}
 			}, {
 				field : 'user_name',
 				title : '创建人',
 				width : 50,
 				formatter : function(value, row, index) {
 					return row.user_name;
 				}
 			}, {
 				field : 'dept_name',
 				title : '所属单位名称',
 				align : 'center',
 				width : 50,
 				formatter : function(value, row, index) {
 					return row.dept_name;
 				}
 			}, {
 				field : 'fxzt',
 				title : '所属单位名称',
 				align : 'center',
 				width : 50,
 				formatter : function(value, row, index) {
 					if (row.fxzt == "1") {
 						return "专用";
 					} else if (row.fxzt == "2") {
 						return "部门";
 					} else if (row.fxzt == "3") {
 						return "所有";
 					}
 				}
 			}, {
 				field : 'cjsj',
 				title : '创建时间',
 				align : 'center',
 				width : 50,
 				formatter : function(value, row, index) {
 					return row.cjsj;
 				}
 			}, {
 				field : 'nvl',
 				title : '操作',
 				align : 'center',
 				width : 25,
 				formatter : function(value, row, index) {
 					if (row.user_id == $("#user_id").val()) { //当前登录用户ID
 						return  "<a href='javascript:void(0)' onclick=\"user_delSql('"+ row.id + "')\" >删除</a>"
                    }
 				}
 			}
 		] ],
		onDblClickRow : function(rowIndex, rowData) {
			var value = rowData.sql_show;
			$("#sqlText").val(value);
			$("#sql-list-win").dialog('close');
		}
	};

	return {
		init : function() {
			if (!initStyle) {
				$grid.datagrid(config);
				initStyle = true;
			}
		},
		reload : function() {
			var data = $('#sqlListForm').serializeObject();
			$grid.datagrid('reload', data);
		},
		getSelected : function() {
			return $grid.datagrid('getSelected');
		}
	}

})();

$(function() {
	report.businessCustomer.init();
});