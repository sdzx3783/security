<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
   	<head>
      	<title id="testtitle">TEST 列表页</title>
	  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	  	<link href="/styles/blue/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	  	<link href="/js/pagination/pagination.css" rel="stylesheet">
	  	<link href="/styles/blue/css/Aqua/css/ligerui-all.css" rel="stylesheet">
	  	
	  	<script type="text/javascript" src="/js/jquery/jquery-3.3.1.min.js"></script>
  		<script src="/js/jquery/bootstrap.min.js"></script>
  		<script type="text/javascript" src="/js/calendar/My97DatePicker/newWdatePicker.js"></script>
  		<script type="text/javascript" src="/js/pagination/jquery.pagination.js"></script>
  		
  		<!-- 表格数据导出 -->
  		<script src="/js/jquery/bootstrap-table.js"></script>
		<script src="/js/jquery/bootstrap-table-zh-CN.js"></script>
  		<script src="/js/jquery/bootstrap-table-export.js"></script>
  		<script src="/js/jquery/tableExport.js"></script>
  		
  		<!-- 弹框 -->
  		<script type="text/javascript" src="/js/lang/common/zh_CN.js" ></script>
  		<script type="text/javascript" src="/js/lg/ligerui.min.js"></script>
  		<script type="text/javascript" src="/js/lg/plugins/ligerDialog.js" ></script>
  		
  		<%@include file="/WEB-INF/view/msg.jsp"%>
  		
  		<style type="text/css">
			.form-body {
			    margin-bottom: 20px;
			    margin-left: 6px;
			  }
		    .form-group{margin-right: 20px;}
		    .control-label{width:64px;}
		    .form-control{width:180px !important;}
		    .control-label label{min-width:23px;}
		    table tr th {
			    font-size: 14px;
			    color: #000;    
			    font-weight: 600;
		    }
		    .executivetitle{
			   	font-size: 20px !important;
			    color: #000;
			    font-weight: 600 !important;
			    text-align: center;
			    margin: 10px;
		    }
		    .center{text-align: center !important;}
		    
		    a.btn.edit{
		    	color:#337ab7;
		    	background-color: #ddd;
		    }
		    a.btn.del{
		    	color:#e51814;
		    	background-color: #f2dede;
		    }
		    button {
				font-size: 18px;
			}
		</style>
	</head>
<body>
	<div id="oa_list_title" class="oa-mgb20 oa-project-title">
	    	<h2 class='executivetitle'>Test汇总表</h2>
	</div>
	<div  >
        <div class="col-md-12">
			<form class="form-inline" role="form" method="post" action="list.ht" id="search-form">
		         <div class="form-body">
		        	 <div class="form-group">
		               	<label class="control-label">名<label>&nbsp;</label>字</label>
		               	<input name="Q_name_SL"  class="form-control" id="name" placeholder="请输入名称" type="text" value="${param['Q_name_SL']}"/>         
		             </div>
		         	<div class="form-group">
		               	<label class="control-label">日期</label>
		               	<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'endbirthday\')}'})" type="text" readonly="readonly" name="beginbirthday" id="beginbirthday" value="${param['beginbirthday']}" class="form-control Wdate" />
		               		至
		               	<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'%y-%M'})" type="text" name="endbirthday" id="endbirthday" readonly="readonly" value="${param['endbirthday']}" class="form-control Wdate" />
		            </div>
		            <!--  href="javascript:loadInitPageData();" -->
		            <input type="hidden" id="page" name="page" value="${pageBean.currentPage }"/>
		            <input type="hidden" id="pageSize" name="pageSize" value="${pageBean.pageSize }"/>
		            <div class="btn-group">
		            	<button type="button"  onclick="submitForm()" class="btn-primary">查询</button>
		            	<button type="button" class="btn-primary"  id="btnExport">导出</button>
		            	<button type="button" class="btn-primary" onclick="window.location.href='/platform/sz/edit'">新增</button>
		            </div>
		            
		         </div>
		     </form>
		 </div>
    </div>

	<div class="">
	  <table id="tb_common_show" class="table">
	    <thead>
	      <tr>
	        <th>序号</th>
	        <th>姓名</th>
	        <th>年龄</th>
	        <th>生日</th>
	        <th>创建日期</th>
	        <th>操作</th>
	       </tr>
	       </tr>
	    </thead>
	    <tbody>
		    <c:choose>
				<c:when test="${empty list }">
					<tr>
							<td colspan="6" style="text-align: center;">未查询到记录</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="item" varStatus="vs" items="${list}">
				    	<tr><!--pageBean是查询baseDao里的setForWeb()方法将对象放进request域中的  -->
					        <td>${(pageBean.currentPage-1)*pageBean.pageSize+vs.index+1}</td>
					        <td>${item.name}</td>
					        <td>${item.age}</td>
					        <td><fmt:formatDate value="${item.birthday}" pattern="yyyy年MM月dd日" /> </td>
					        <td><fmt:formatDate value="${item.ctime}" pattern="yyyy年MM月dd日HH点mm分ss秒" /> </td>
					        <th><a href="edit.ht?id=${item.id }" class="btn edit">编辑</a><a href="#" onclick="javascript:return del(${item.id});" class="btn del">删除</a></th>
					        
				        </tr>
				    </c:forEach>
				</c:otherwise>
			</c:choose>
	    </tbody>
	  </table>
	
	</div>
	<div id="pagination">
 	</div>
 	
 	<script type="text/javascript">
 	$(function () {
 	　　　　var pageCount=${pageBean.totalCount};  //分页总数量
		 	$("#pagination").pagination(pageCount, {
		 	    num_edge_entries: 2,
		 	    num_display_entries: 4,
		 	    current_page:${pageBean.currentPage-1},
		 	    callbackMain : fb,
		 	    callback: pageselectCallback,
		 	    items_per_page:${pageBean.pageSize},
		 	    prev_text : "",
				next_text : ""
		 	});
 	
		 	$("#btnExport").click(function(){
		        $('#tb_common_show').tableExport({
			        type:'excel',
			        escape:'false',
			        ignoreColumn: [5],  //忽略某一列的索引
			        excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
			        htmlContent :true,
			        fileName: $("#testtitle").text()
		        });
		    });
 	});
 	function pageselectCallback(page_index,jq){
 	   var currentPage=${pageBean.currentPage};
 	   $("#page").val(page_index+1);
 	   
 	   if(currentPage!=page_index+1){
 		  $("#search-form").submit();
 	   }
 	}
 	function fb(){
  	   $("#pageSize").val($(".eachPageSize").val());
  	   $("#search-form").submit();
 	}
 	function submitForm(){
 		$("#page").val(1);
 		$("#pageSize").val(10);
 		$("#search-form").submit();
 	}
 	function del(id){
 		$.ligerDialog.confirm("确认删除吗？", "提示",function(yes){
 			if(yes){
 				window.location.href="del.ht?id="+id;
 			}
 		});
 		
 	}
 	
 	</script>
	</body>
</html>