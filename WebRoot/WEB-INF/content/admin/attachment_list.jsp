<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%;">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>选择图片</title>
		<meta name="”renderer”" content="”webkit”">
		<meta http-equiv="”X-UA-Compatible”" content="”IE=Edge,chrome=1″">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<%@ include file="/WEB-INF/content/common/includewechat.jsp"%>
		<style type="text/css">
		.list-inline>li {
		    display: inline-block;
		    padding: 3px;
		}
		</style>
		<link rel="stylesheet" href="${ctx}/scripts/wechat/layer/skin/layer.css" id="layui_layer_skinlayercss">
		<script>
			function input(){
				showDialog("${ctx}/admin/attachment!upload.action","","","");
			}

			var reload = false;
			function openlayer(id){
				reload = false;
			 	layer.open({
				    type: 2,
				    title: '附件详情',
				    shadeClose: true,
				    shade: 0.8,
				    area: ['70%', '90%'],
				    content: '${ctx}/admin/attachment/detail_layer?attachment.id='+id,
				    end:function(){
				    	if(reload){
				    		location.reload();
				    	}
				    }
				}); 
			 }
		</script>
	</head>
	<body class="skin-blue  pace-done">
		<div class="content-wrapper" style="min-height: 697px;">
		<!-- Main content -->
		<section class="content">
			<div class="row content-row">
				<form id="listForm" action="${ctx}/admin/attachment!list.action" method="POST">
					<div class="jp-left ">
						<s:select cssClass="form-control input-sm jp-width120" name="attachment.mime" list="#{'image':'图像','audio':'音频','video':'视频'}" headerKey="" headerValue="所有附件类型"></s:select>
					</div>
					<div class="jp-left ">
						<input type="text" class="WdateThumb form-control input-sm jp-width120" name="attachment.beginDate" id="bd" size="12" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'ed\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" style="text-indent:4px; background-color: #fff" value="${attachment.beginDate}" />
					</div>
					<div class="jp-left ">
						<input type="text" class="WdateThumb form-control input-sm jp-width120" name="attachment.endDate" id="ed" size="12" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'bd\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" style="text-indent:4px; background-color: #fff" value="${attachment.endDate}"/>
					</div>
					<div class="jp-left jp-margin-left-10">
						<s:textfield name="attachment.title" cssClass="form-control input-sm jp-inp-width280" placeholder="搜索"/>
					</div>
					<div class="jp-left">
						<button class="btn btn-sm btn-default" type="submit">筛选</button>
					</div>
					<div class="jp-left jp-margin-left-5">
						<button class="btn btn-block btn-sm btn-default" type="button" id="addButton" onclick="input()">上传</button>
					</div>
				</form>
			</div>
			<div class="col-xs-12  "><div class="row"><div class="box "><div class="box-body "><div class="col-xs-12 "><div class="row">
				<ul class="list-inline list-unstyled">
				<s:iterator value="page.result" var="list" status="status">
					<li><img src="${ctx}/<s:property value="#list[4]"/>" title="<s:property value="#list[1]"/>" alt="<s:property value="#list[1]"/>" class="jp-grids-photos img-responsive" onclick="openlayer('<s:property value="#list[0]"/>')"></li>
				</s:iterator>
				</ul>
			</div></div></div></div></div></div>
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
            <!-- 
            <div class="cf">
				<div class="pull-right ">
					<div id="Pagination" class="page">
						<div class="pub_l">
							<span class="cose prev">上一页</span>
							<span class="cose">1</span>
							<a href="javascript:void(0)">2</a>
							<a href="javascript:void(0)">3</a>
							<a href="javascript:void(0)">4</a>
							<a href="javascript:void(0)">5</a>
							<span style="margin-right:5px;">...</span>
							<a href="javascript:void(0)" class="ep">13</a>
							<a href="javascript:void(0)" class="ep">14</a>
				            <a href="javascript:void(0)" class="next">下一页</a>
				        </div>
				    </div>
		    	</div>
		    </div>
		    
		    <div class="cf">
				<div class="pull-right ">
					<ul class="pagination">
						<li class="paginate_button previous disabled">
				          <a href="#" data-dt-idx="0" tabindex="0">上页</a>
						</li>
						<li class="paginate_button active">
				           <a href="#" data-dt-idx="1" tabindex="0">1</a>
						</li>
						<li class="paginate_button next disabled">
							<a href="#" data-dt-idx="2" tabindex="0">下页</a>
						</li>
					</ul>
				</div>
			</div>
		    -->
			<!-- /.row -->
		</section>
		<!-- /.content -->
		</div>
	</body>
</html>