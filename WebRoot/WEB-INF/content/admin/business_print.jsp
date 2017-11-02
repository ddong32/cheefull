<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>订单打印</title>
    <%@ include file="/WEB-INF/content/common/include.jsp"%>
    <!-- 
    <script type="text/javascript" src="/WebReport/ReportServer?op=emb&resource=jquery.js"></script>
    <script type="text/javascript" src="/WebReport/ReportServer?op=emb&resource=finereport.js"></script>
    -->
    <script type="text/javascript">
    	var curType = "1";

		function getParams(){
			var params    = "&orderId="+$("#business_orderId").val();
			return params;
		}
		//http://127.0.0.1
		function search(){
			var params = getParams();
			var hostname = window.location.host;
			var reportAdd_a = "http://"+hostname+"/WebReport/ReportServer?reportlet=businessPrint.cpt&op=view"+params;
			reportAdd_1 = encodeURI(reportAdd_a);
			switch(curType){
				case "1":
					$("#printFrame").attr("src",reportAdd_1);
					break;
				case "2":
					break;
				default:;
			}
			
			//FR.doURLAppletPrint(reportAdd_1, false);
			//FR.doURLFlashPrint(reportAdd_1, false);
			//FR.doNativePrint();
		    //setInterval("myInterval()",5000);//1000为1秒钟
		}

		function myInterval() {
			$("#priButton", opener.document).attr("disabled", false);
			$("#priButton", opener.document).removeClass("pub_but_disable").addClass("pub_but");
			window.close();
	    }
		
		$(document).ready(function() {
			var windowHeight = document.documentElement.clientHeight;
			var iframeHeight = windowHeight-$("#printFrame").offset().top-20;
			$("#printFrame").height(iframeHeight);
			search();
		});
    </script>
</head>
<body class="con_r list" >
	<div class="listtab" style="margin:0px;padding:0px">
		<iframe id="printFrame" src=""  style="width:100%; border: 0; background:#fff"></iframe>
	</div>
	<!-- 订单打印开始... ...-->
    <s:hidden name="business.orderId"></s:hidden>
</body>
</html>
