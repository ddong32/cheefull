<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="X-UA-Compatible" content="IE=7,IE=9" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	<meta http-equiv="Content-Language" content="utf-8"/> 
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<title>喜洋洋国际旅行社有限公司</title>
    <!-- 公共样式表 -->
    <link rel="stylesheet" href="${ctx}/styles/m/common.css">
    <link rel="stylesheet" href="${ctx}/styles/m/color.css">
	<link rel="stylesheet" href="${ctx}/styles/m/t_index.css">
	
    <!-- 页面样式表 -->
    <script type="text/javascript" src="${ctx}/scripts/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/m/common.js"></script>
    <script type="text/javascript">
        document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
            WeixinJSBridge.call('hideToolbar');
        });
    </script>
</head>
<body>
	<div id="loader" style="display: none;">
        <div id="wait" style="display: none;"></div>
    </div>
	<header class="header"> 
		<h1 class="es">喜洋洋国际旅行社有限公司</h1>
		<div class="le-head">
			<a id="goBack" href="javascript:history.go(-1);" class="iconfont icon-left col-1"></a>
		</div>
		<div class="rg-head">
			<a href="javascript:;" class="iconfont icon-more-2 col-1" id="goHome"></a>
		</div>
	</header>

    <!-- 页面内容块 -->
    <div class="content">
    
        <div id="main">
            <div class="tab-nav"><div class="tab-screen">
                <ul class="tab-ul cf">
                    <li data-flag="sort-1"><span><b>南宁</b><i class="iconfont icon-down"></i></span><em></em></li>
                    <li data-flag="sort-2"><span><b>北海</b><i class="iconfont icon-down"></i></span><em></em></li>
                    <li data-flag="sort-3"><span><b>排序</b><i class="iconfont icon-down"></i></span><em></em></li>
                    <li data-flag="sort-4"><span><b>筛选</b><i class="iconfont icon-down"></i></span><em></em></li>
                </ul>
                <div id="sort-bg"></div>
                
                <div id="sort-1" class="sort-ani none"><div class="city-wrap"><div class="city-item">
                    <ul>
                        <li data-id="1" class="col-1">南宁</li>
                    </ul>
                </div></div></div>
                
                <div id="sort-2" class="sort-ani none">
                    <div class="filter-panel">
                        <div class="panel-um">
                            <ul>
                                <li class="penel-m " data-rel="" data-name="目的地">不限</li>
                                <li class="" data-rel="558">周边旅游</li>
                                <li class="" data-rel="589">国内旅游</li>
                                <li class="" data-rel="677">出境旅游</li>
                                <li class="" data-rel="729">自由行</li>
                                <li class="" data-rel="758">团队旅游</li>
                                <li class="" data-rel="890">嗨森地球</li>
                                <li class="" data-rel="892">豪华邮轮</li>
                                <li class="" data-rel="894">游学体验</li>
                            </ul>
                        </div>
                        <div class="panel-item"></div>
                        <div class="panel-item none">
                            <ul>
                                <li data-rel="558" data-name="周边旅游">不限</li> 
                                <li data-rel="559" data-name="桂林">桂林</li> 
                                <li data-rel="566" data-name="北海">北海</li> 
                                <li data-rel="575" data-name="巴马">巴马</li> 
                                <li data-rel="577" data-name="大新德天">大新德天</li> 
                                <li data-rel="579" data-name="周边特色游">周边特色游</li> 
                            </ul>  
                        </div>
                        <div class="panel-item none">
                            <ul>
                                <li data-rel="589" data-name="国内旅游">不限</li> 
                                <li data-rel="590" data-name="海南">海南</li> 
                                <li data-rel="596" data-name="云南">云南</li> 
                                <li data-rel="605" data-name="北京">北京</li> 
                                <li data-rel="607" data-name="四川">四川</li> 
                                <li data-rel="617" data-name="华东">华东</li> 
                                <li data-rel="632" data-name="湖南">湖南</li> 
                                <li data-rel="639" data-name="东北">东北</li> 
                                <li data-rel="647" data-name="河南">河南</li> 
                                <li data-rel="653" data-name="西藏">西藏</li> 
                                <li data-rel="658" data-name="福建">福建</li> 
                                <li data-rel="663" data-name="陕西">陕西</li> 
                                <li data-rel="669" data-name="江西">江西</li> 
                                <li data-rel="674" data-name="安徽">安徽</li> 
                                <li data-rel="834" data-name="山东">山东</li> 
                                <li data-rel="835" data-name="西北">西北</li> 
                                <li data-rel="836" data-name="广东">广东</li> 
                                <li data-rel="837" data-name="贵州">贵州</li> 
                                <li data-rel="838" data-name="山西内蒙">山西内蒙</li> 
                                <li data-rel="848" data-name="新疆">新疆</li> 
                                <li data-rel="851" data-name="甘肃">甘肃</li> 
                            </ul>  
                        </div>
                        <div class="panel-item none">
                            <ul>
                            <li data-rel="677" data-name="出境旅游">不限</li> 
                                <li data-rel="678" data-name="东南亚">东南亚</li> 
                                <li data-rel="691" data-name="日本">日本</li> 
                                <li data-rel="695" data-name="韩国">韩国</li> 
                                <li data-rel="698" data-name="港澳台">港澳台</li> 
                                <li data-rel="702" data-name="澳洲">澳洲</li> 
                                <li data-rel="706" data-name="欧洲">欧洲</li> 
                                <li data-rel="716" data-name="美洲">美洲</li> 
                                <li data-rel="721" data-name="非洲中东">非洲中东</li> 
                                <li data-rel="874" data-name="朝鲜">朝鲜</li> 
                            </ul> 
                        </div>
                        <div class="panel-item none">
                            <ul>
                            <li data-rel="729" data-name="自由行">不限</li> 
                                <li data-rel="730" data-name="国内">国内</li> 
                                <li data-rel="738" data-name="出境">出境</li> 
                            </ul>  
                        </div>
                        <div class="panel-item none">
                            <ul>
                                <li data-rel="758" data-name="团队旅游">不限</li> 
                                <li data-rel="764" data-name="云南">云南</li> 
                                <li data-rel="773" data-name="海南">海南</li> 
                                <li data-rel="797" data-name="福建">福建</li> 
                                <li data-rel="802" data-name="河南">河南</li> 
                                <li data-rel="806" data-name="西藏">西藏</li> 
                                <li data-rel="810" data-name="广西">广西</li> 
                                <li data-rel="814" data-name="贵州">贵州</li> 
                                <li data-rel="816" data-name="东北">东北</li> 
                                <li data-rel="820" data-name="草原游">草原游</li> 
                                <li data-rel="823" data-name="海滨海岛">海滨海岛</li> 
                            </ul>  
                        </div>
                        <div class="panel-item none">
                            <ul>
                            <li data-rel="890" data-name="嗨森地球">不限</li> 
                            <li data-rel="891" data-name="最新特价">最新特价</li> 
                            </ul>
                        </div>
                        <div class="panel-item none">
                            <ul>
                            <li data-rel="892" data-name="豪华邮轮">不限</li> 
                            <li data-rel="893" data-name="邮轮特供">邮轮特供</li> 
                            </ul>  
                        </div>
                        <div class="panel-item none">
                            <ul>
                            <li data-rel="894" data-name="游学体验">不限</li> 
                            <li data-rel="895" data-name="游学体验">游学体验</li> 
                            </ul>
                        </div>
                    </div>
                </div>
                
                <div id="sort-3" class="none sort-ani">
                    <ul class="dia-um">
                        <li data-rel="">不限</li>
                        <li data-rel="1">精品热销</li>
                        <li data-rel="2">价格最低</li>
                        <li data-rel="3">价格最高</li>
                    </ul>
                </div>
                    
                <div id="sort-4" class="none sort-ani">
                    <div class="flag">
                        <div class="flag-box">
                            <div class="flag-um">
                                <ul>
                                    <li class="col-1">出游天数</li>
                                    <li>价格区间</li>
                                </ul>
                            </div>
                            <div class="flag-item flag-day">
                                <ul>
                                    <li data-rel="" class="c-ba"><span>不限</span></li>
                                    <li data-rel="1"><span>1日游</span></li>
                                    <li data-rel="2"><span>2日游</span></li>
                                    <li data-rel="3"><span>3日游</span></li>
                                    <li data-rel="4"><span>4日游</span></li>
                                    <li data-rel="5"><span>5日游</span></li>
                                    <li data-rel="6"><span>6日游</span></li>
                                    <li data-rel="7"><span>7日游</span></li>
                                    <li data-rel="8"><span>7日游以上</span></li>
                                </ul>
                            </div>
                            <div class="flag-item none flag-pri">
                                <ul>
                                    <li data-base="0" data-sell="0" class="c-ba"><span>不限</span></li>
                                    <li data-base="500" data-sell="0"><span>500以下</span></li>
                                    <li data-base="1000" data-sell="501"><span>501-1000</span></li>
                                    <li data-base="2000" data-sell="1001"><span>1001-2000</span></li>
                                    <li data-base="3000" data-sell="2001"><span>2001-3000</span></li>
                                    <li data-base="4000" data-sell="3001"><span>3001-4000</span></li>
                                    <li data-base="8000" data-sell="4001"><span>4001-8000</span></li>
                                    <li data-base="0" data-sell="8000"><span>8000以上</span></li>
                                </ul>
                            </div>
                        </div>
                        <div class="flag-btn">
                            <a class="confirm" href="javascript:;">确定</a>
                        </div>
                    </div>
                </div>
            </div></div><!-- end tab-nav tab-screen--><!-- end tab-screen-->
            
            <div class="box-search box-search2">
                <div class="search-con">
                    <div class="search-dest search-dest2" id="dest-name" data-page="dest" data-name="搜索">
                        <i class="iconfont icon-search"></i><span>目的地/关键字</span>
                    </div>
                </div>
            </div>
            
            <div id="t-list">
                <div class="list-item">
                    <a href="http://www.gxbestone.com/mobile/Lvyou/info-4008.html">
                        <div class="pic">
                            <img src="${ctx}/images/m/201693102943171.jpg" alt="">
                        </div>
                        <div class="name">
                            <h4>【碧海丝路】北海、涠洲岛3日游（不进店可加点）</h4>
                            <p>
                                <em class="fr" style="display:none">同行：<span class="price">730</span>起</em> <span class="price">880</span>起
                            </p>
                        </div>
                    </a>
                </div>
                <div class="list-item">
                    <a href="http://www.gxbestone.com/mobile/Lvyou/info-4006.html">
                        <div class="pic"><img src="${ctx}/images/m/201693101950968.jpg" alt=""></div>
                        <div class="name">
                            <h4>【碧海丝路】北海、涠洲岛2日游（不进店可加点）</h4>
                            <p>
                                <em class="fr" style="display:none">同行：<span class="price">730</span>起</em> <span class="price">840</span>起
                            </p>
                        </div>
                    </a>
                </div>
                <div class="list-item">
                    <a href="http://www.gxbestone.com/mobile/Lvyou/info-4005.html">
                        <div class="pic">
                            <img src="${ctx}/images/m/20169310644531.jpg" alt="">
                        </div>
                        <div class="name">
                            <h4>【碧海丝路】北海市内2日游（不进店可加点）</h4>
                            <p>
                                <em class="fr" style="display:none">同行：<span class="price">190</span>起</em> <span class="price">270</span>起
                            </p>
                        </div>
                    </a>
                </div>
                <div class="list-item">
                    <a href="http://www.gxbestone.com/mobile/Lvyou/info-4004.html">
                        <div class="pic">
                            <img src="${ctx}/images/m/20169395216140.jpg" alt="">
                        </div>
                        <div class="name">
                            <h4>【碧海丝路】北海1日游（不进店可加点）</h4>
                            <p>
                                <em class="fr" style="display:none">同行：<span class="price">80</span>起</em> <span class="price">160</span>起
                            </p>
                        </div>
                    </a>
                </div>
                <div class="list-item">
                    <a href="http://www.gxbestone.com/mobile/Lvyou/info-3998.html">
                        <div class="pic">
                            <img src="${ctx}/images/m/201692111259890.jpg" alt="">
                        </div>
                        <div class="name">
                            <h4>【碧海丝路】北海、涠洲岛3日游（可进店可加点）</h4>
                            <p>
                                <em class="fr" style="display:none">同行：<span class="price">650</span>起</em> <span class="price">810</span>起
                            </p>
                        </div>
                    </a>
                </div>
                <div class="list-item">
                    <a href="http://www.gxbestone.com/mobile/Lvyou/info-3997.html">
                        <div class="pic">
                            <img src="${ctx}/images/m/201692105552187.jpg" alt="">
                        </div>
                        <div class="name">
                            <h4>【碧海丝路】北海、涠洲岛2日游（可进店可加点）</h4>
                            <p>
                                <em class="fr" style="display:none">同行：<span class="price">580</span>起</em> <span class="price">680</span>起
                            </p>
                        </div>
                    </a>
                </div>
                <div class="list-item">
                    <a href="http://www.gxbestone.com/mobile/Lvyou/info-3996.html">
                        <div class="pic">
                            <img src="${ctx}/images/m/20169210391615.jpg" alt="">
                        </div>
                        <div class="name">
                            <h4>【碧海丝路】北海市内2日游（可进店可加点）</h4>
                            <p>
                                <em class="fr" style="display:none">同行：<span class="price">110</span>起</em> <span class="price">190</span>起
                            </p>
                        </div>
                    </a>
                </div>
                <div class="list-item">
                    <a href="http://www.gxbestone.com/mobile/Lvyou/info-3995.html">
                        <div class="pic">
                            <img src="${ctx}/images/m/20169210324328.jpg" alt="">
                        </div>
                        <div class="name">
                            <h4>【碧海丝路】北海1日游（可进店可加点）</h4>
                            <p>
                                <em class="fr" style="display:none">同行：<span class="price">50</span>起</em> <span class="price">120</span>起
                            </p>
                        </div>
                    </a>
                </div>
                <div class="list-item">
                    <a href="http://www.gxbestone.com/mobile/Lvyou/info-3457.html">
                        <div class="pic">
                            <img src="${ctx}/images/m/2016712135219140.jpg" alt="">
                        </div>
                        <div class="name">
                            <h4>北海市内、涠洲岛精华三日游（不进可加）</h4>
                            <p>
                                <em class="fr" style="display:none">同行：<span class="price">0</span>起</em> <span class="price">870</span>起
                            </p>
                        </div>
                    </a>
                </div>
                <div class="list-item">
                    <a href="http://www.gxbestone.com/mobile/Lvyou/info-3455.html">
                        <div class="pic">
                            <img src="${ctx}/images/m/20168310279156.jpg" alt="">
                        </div>
                        <div class="name">
                            <h4>北海市内精华二日游（不进可加）</h4>
                            <p>
                                <em class="fr" style="display:none">同行：<span class="price">0</span>起</em> <span class="price">290</span>起
                            </p>
                        </div>
                    </a>
                </div>
            </div>
            <div class="list-more">正在加载更多</div>
        </div> <!-- end main -->
    
        <div id="page-dest" class="m-page">
            <!--  搜索 模块 -->
            <div class="dest-search dest-search2">
                <h2 class="cf">
                    <form action="http://www.gxbestone.com/mobile/lvyou/list.html" id="LinesSearch" method="get">
                        <a class="iconfont icon-left col-1" href="javascript:;" id="dest-close"></a>
                        <input type="text" name="Name" value="" placeholder="目的地、主题或关键字" class="dest-sea-name" id="s_cName">
                        <span id="dest-click">搜索</span> <i class="iconfont icon-search"></i>
                    </form>
                </h2>
            </div>
            <div class="dest-list">
                <h3>热搜词</h3>
                <ul class="cf">
                    <li><a href="http://www.gxbestone.com/mobile/lvyou/list.html?LCid=593">蜈支洲岛</a></li>
                    <li><a href="http://www.gxbestone.com/mobile/lvyou/list.html?LCid=592">海口</a></li>
                    <li><a href="http://www.gxbestone.com/mobile/lvyou/list.html?LCid=591">三亚</a></li>
                    <li><a href="http://www.gxbestone.com/mobile/lvyou/list.html?LCid=590">海南</a></li>
                    <li><a href="http://www.gxbestone.com/mobile/lvyou/list.html?LCid=579">周边特色游</a></li>
                    <li><a href="http://www.gxbestone.com/mobile/lvyou/list.html?LCid=577">大新德天</a></li>
                    <li><a href="http://www.gxbestone.com/mobile/lvyou/list.html?LCid=575">巴马</a></li>
                    <li><a href="http://www.gxbestone.com/mobile/lvyou/list.html?LCid=575">北海</a></li>
                </ul>
            </div>
        </div>
    
        <style type="text/css">
            #footer
            {
                display: none !important;
            }
            .topinner
            {
                display: none;
            }
        </style>
        <input type="hidden" id="keyword" name="keyword"><!--  关键词 -->
        <input type="hidden" id="city-hid" name="City" value="1"><!--  出发城市 -->
        <input type="hidden" name="LCid" id="LCid" value="566"><!-- 目的地 -->
        <input type="hidden" name="SchedulingDayNumber" id="SchedulingDayNumber" value=""><!-- 排序 -->
        <input type="hidden" name="SchedulingDay" id="SchedulingDay" value="0"><!-- 出游天数 -->
        <input type="hidden" name="BasePrice" id="BasePrice" value="0"><!-- 价格区间 -->
        <input type="hidden" name="SellPrice" id="SellPrice" value="0"><!-- 价格区间 -->
        <script type="text/javascript" src="${ctx}/scripts/m/t_list.js"></script>

    </div>
    <!-- 公共页脚 -->
    
    <footer id="footer">
		<div class="foot-nav cf"> 
			<a class="fr iconfont icon-top c6" id="goTop" href="javascript:;"></a> 
			<p>
			<a href="http://www.gxbestone.com/mobile/member/login.html" class="c6">登录</a>|<a href="http://www.gxbestone.com/mobile/member/reg.html" class="c6">注册</a>|              <a href="http://www.gxbestone.com/mobile/usercenter/home/index.html" class="c6">会员中心</a> |
            </p> 
       </div> 
       <div class="foot-link"> 
            <p class="foot_com">
                <a class="c9" href="http://www.gxbestone.com/mobile/help/index.html">帮助中心</a> |
                <a class="c9" href="http://www.gxbestone.com/mobile/about/index.html">关于我们</a> | 
                <a class="c9" href="http://www.gxbestone.com/mobile/FeedBack.html">意见反馈</a> 
            </p> 
            <p>24小时服务热线：<a href="tel:0771--- 5603033 4803033 4863033 3943033" class="c9">0771--- 5603033 4803033 4863033 3943033</a></p> 
            <p>Copyright ?2014 广西百事通国际旅行社电子商务中心</p> 
            <p><a href="http://www.miitbeian.gov.cn/" target="_blank" class="c9">桂ICP备15009141号</a></p>
		</div> 
    </footer>
    <script type="text/javascript">
        $("#goTop").on("click touch", function () {
            $(window).scrollTop(0);
        });
    </script>
    <!-- 移动端第三方代码 -->
    
    <!-- End 移动端第三方代码 -->
    <div class="header-menu">
        <a href="http://www.gxbestone.com/mobile/index.html"><i class="iconfont icon-home-2"></i>首页1</a> 
        <a href="http://www.gxbestone.com/mobile/usercenter/home/index.html"><i class="iconfont icon-me"></i>会员中心</a>
        <a href="http://www.gxbestone.com/mobile/member/login.html"><i class="iconfont icon-pass-2"></i>登录/注册</a>
        <a href="javascript:location.reload()"><i class="iconfont icon-map-2"></i>刷新</a>
        <a href="tel:0771--- 5603033 4803033 4863033 3943033" class="col-1"><i class="iconfont icon-tel-2 col-1"></i>联系客服</a>
    </div>

	<a href="javascript:;" id="return-top" style="display: none;"></a>
</body>
</html>