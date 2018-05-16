<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title id="testtitle">Test<c:if test="${empty test.id }">新增</c:if><c:if test="${not empty test.id }">编辑</c:if></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="/styles/blue/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<link href="/styles/blue/css/Aqua/css/ligerui-all.css" rel="stylesheet">

<script type="text/javascript" src="/js/jquery/jquery-3.3.1.min.js"></script>
<script src="/js/jquery/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/calendar/My97DatePicker/newWdatePicker.js"></script>

<!-- 表单提交 -->
<script type="text/javascript" src="/js/form/jquery.validate.min.js"></script>
<script type="text/javascript" src="/js/form/jquery.form.js"></script>
<!-- 弹框 -->
<script type="text/javascript" src="/js/lang/common/zh_CN.js"></script>
<script type="text/javascript" src="/js/lg/ligerui.min.js"></script>
<script type="text/javascript" src="/js/lg/plugins/ligerDialog.js"></script>
<style type="text/css">
	#oa_list_title h2{
		text-align: center;
	}
	div.panel{
		padding-left: 15px;
		padding-right: 15px;
		border: 0px;
	}
	.control-label{
		width: 30%;
	}
	div.form-group{
		margin-bottom: 10px;
		margin-top: 10px;
	}
	.help-block{
		display: inline;
	}
</style>



</head>
<body>
	<div id="oa_list_title" class="">
		<h2 class='test'>Test表单</h2>
	</div>
	<div class="panel toolbar">
		<div class="form-group">
                <button type="submit"  class="save btn btn-primary">保存</button>
                <button type="submit" onclick="window.location.href='/platform/sz/list.ht'"  class="back btn btn-primary">返回</button>
         </div>
	</div>
	<div class="panel main">
		<div class="col-md-12">
			<form class="form-inline" id="testform" role="form" method="post" action="save.ht">
				<div>
					<div class="form-group col-md-4">
						<label class="control-label">姓 名</label> <input type="text" class="form-control" value="${test.name }"  name="name" id="name"/>
					</div>
					<div class="form-group col-md-4">
						<label class="control-label">年 龄</label> <input type="text" class="form-control" value="${test.age }"  name="age" id="age"/>
					</div>
					<div class="form-group col-md-4">
		               	<label class="control-label">生 日</label>
		               	<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'%y-%M'})" type="text" readonly="readonly" name="birthday" id="birthday" value="<fmt:formatDate value="${test.birthday}" pattern="yyyy-MM-dd" />" class="form-control Wdate" />
		            </div>
				</div>
				<div>
					<div class="form-group col-md-4">
		               	<label class="control-label">创建日期</label>
		               	<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,maxDate:'%y-%M'})" type="text" readonly="readonly" name="ctime" id="ctime" value="<fmt:formatDate value="${test.ctime}" pattern="yyyy-MM-dd HH:mm:ss" />" class="form-control Wdate" />
		         	</div>
				</div>
				<input type="hidden" name="id" value="${test.id }"/>
			</form>
		</div>
	</div>
<script type="text/javascript">
	$(function(){
		var options={};
		if(showResponse){
			options.success=showResponse;
		}
		if(showError){
			options.error=showError;
		}
		 $('#testform').validate({
	            errorElement : 'span',  
	            errorClass : 'help-block',  
	            focusInvalid : false,  
	            rules : {  
	                name : {  
	                    required : true  
	                },  
	                age : {  
	                    required : true,  
	                    digits:true 
	                },  
	                birthday : {  
	                    required : true ,
	                    dateISO:true
	                }  
	            },  
	            messages : {  
	                name : {  
	                    required : "姓名必填"  
	                },  
	                age : {  
	                    required : "年龄必填",  
	                   	digits:"请输入正整数"
	                },  
	                birthday : {  
	                    required : "生日必填",
	                    dateISO:"日期格式错误"
	                }  
	            },  
	            highlight : function(element) {  
	                $(element).closest('.form-group').addClass('has-error');  
	            },  
	            success : function(label) {  
	                label.closest('.form-group').removeClass('has-error');  
	                label.remove();  
	            },  
	            errorPlacement : function(error, element) { 
	                element.parent('div').append(error);  
	            },  
	            submitHandler : function(form) {  
	    			$("#testform").ajaxSubmit(options);
	            }  
	        });  
		 $(".save").click(function(){
			 $("#testform").submit();
		 });
	})
	
	function showResponse(responseText) {
		var obj= eval('(' + responseText + ')');
		if (obj.result==1) {
			$.ligerDialog.confirm(obj.message+",是否继续操作","提示信息", function(rtn) {
				if(rtn){
					window.location.href = window.location.href;
				}else{
					window.location.href = "list.ht";
				}
			});
		} else {
			$.ligerDialog.error(obj.message,"提示信息");
		}
	}
	function showError(responseText, statusText){
		console.log(statusText);
		console.log(responseText);
		if(statusText=='timeout'){
			$.ligerDialog.error("服务器繁忙，请稍后再试！","提示信息");
			return;
		}
		if(statusText=='error'){
			if(responseText.status=='404'){
				$.ligerDialog.error("服务请求错误：404！","提示信息");
			}else{
				$.ligerDialog.error("服务器出错！","提示信息");
			}
		}
	}
	
</script>

</body>
</html>