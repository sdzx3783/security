<%@page language="java" pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<!DOCTYPE html>
<html>
<head>
	<title>编辑 规则管理</title>
	<%@include file="/WEB-INF/view/common.jsp" %>
	<script type="text/javascript" src="${ctx}/js/form/formdata.js"></script>
	<script type="text/javascript" src="${ctx}/js/form/CustomValid.js"></script>
	<script type="text/javascript" src="${ctx}/js/form/subform.js"></script>
	<script type="text/javascript">
		var frm;	
		$(function() {
			var options={};
			if(showResponse){
				options.success=showResponse;
			}
			frm=$('#ruleManagerForm').form();
			$("a.save").click(function() {
				frm.ajaxForm(options);
				$("#saveData").val(1);
				if(frm.valid()){
					//如果有编辑器的情况下
					$("textarea[name^='m:'].myeditor").each(function(num) {
						var name=$(this).attr("name");
						var data=getEditorData(editor[num]);
						$("textarea[name^='"+name+"']").val(data);
					});
					frm.handleFieldName();
					frm.sortList();
					$('#ruleManagerForm').submit();
				}
			});
		});
		
		function showResponse(responseText) {
			var obj = responseText;
			if (obj.status=="success") {
				$.ligerDialog.confirm("保存成功，是否继续操作","提示信息", function(rtn) {
					if(rtn){
						window.location.href = window.location.href;
					}else{
						window.location.href = "${ctx}/makshi/doc/rule/list.ht";
					}
				});
			} else {
				$.ligerDialog.error(obj.msg.toString(),"提示信息");
				
			}
			frm.handleFieldNameReduction("s:simpleSub");
			frm.sortListReduction();
		}
	</script>
    <style>
    .panel-head{padding:10px 0;}
    .panel-head h2{font-size:14px;}
    .panel-wrap{margin:0 16px;}
    .panel-main{margin:0 16px;padding:10px 0;}
    .default-table{width:100%;}
    .default-table td,.default-table th{padding:0 8px;height:50px;border:1px solid #dadfed;}
    .default-table input[type=text]{box-sizing:border-box;padding:0 10px;width:100%;height:36px;line-height:36px;}
    span[name=editable-input]{display:block!important;}
    .btn{display:inline-block;padding:0 15px;border:0;border-radius:3px 3px 3px 3px;background:#478de4;-webkit-box-shadow:0 1px 1px rgba(0,0,0,.15);-moz-box-shadow:0 1px 1px rgba(0,0,0,.15);box-shadow:0 1px 1px rgba(0,0,0,.15);color:#fff;text-align:left;text-decoration:none;line-height:26px;cursor:pointer;}
    .btns-wrap{margin-bottom:15px;}
    .sm-input{box-sizing:border-box;width:200px;height:26px;border:1px solid #dadfed;line-height:26px;}
  	 .l-menu-item{
    	display:block !important;
    }
    </style>
   
</head>
<body>
<div class="panel" style="height:100%;overflow:auto;">
        <div class="panel-head">
            <div class="panel-wrap">
                <h2>规则详情</h2>
            </div>
        </div>

	<form id="ruleManagerForm" method="post" action="save.ht">
		<div type="custform">
			<div class="">
                <div class="panel-main">
                    <span>规则</span>
                    <input type="text" name="m:SimpleModel:name" lablename="规则" class="inputText sm-input" value="${SimpleModel.name}" validate="{maxlength:50}" isflag="tableflag" />
                   <input type="text" name="m:SimpleModel:applicateTime" lablename="规则" class="inputText sm-input" value="${SimpleModel.applicateTime}" validate="{maxlength:50}" isflag="tableflag" />
                             <a class="btn save" id="dataFormSave" href="javascript:;">保存</a>
                <a class="btn " href="list.ht">返回</a>
                </div>

                <div type="subtable" class="panel-main" tablename="simpleSub" tabledesc="规则管理" show="true" parser="rowmodeedit" id="simpleSub" formtype="edit"> 
                    <div class="btns-wrap">
                        <a class="add btn" href="javascript:;">添加</a>
                        
                    </div>
                            
                     <table class="default-table" border="0" cellpadding="2" cellspacing="0"> 
                      <tbody> 

                       <tr class=""> 
                        <th style="word-break: break-all;">书签名</th> 
                        <th style="word-break: break-all;">书签值</th> 
                       </tr> 
                       <c:forEach items="${simpleSub}" var="ruleBookmark" varStatus="status">
                        <tr class="" type="subdata"> 
                         <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:simpleSub:susbName" lablename="书签名" class="inputText" value="" validate="{required:true,maxlength:50}" isflag="tableflag" /> </span> </td> 
                         <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:simpleSub:subApplicateTime" lablename="书签值" class="inputText" value="" isflag="tableflag" /> </span> </td> 
                        	<input name="s:simpleSub:id" type="hidden" value="" />
                        </tr>
                       </c:forEach> 
                       <tr class="" type="append" style="display:none;"> 
                          <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text"  name="s:simpleSub:susbName" lablename="书签名" class="inputText" value="" validate="{required:true,maxlength:50}" isflag="tableflag" /> </span> </td> 
                          <td> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> <input type="text" name="s:simpleSub:subApplicateTime" lablename="书签值" class="inputText" value=""  isflag="tableflag" /> </span> </td> 
                      		<input name="s:simpleSub:id" type="hidden" value="" />
                       </tr>
                     
                      </tbody> 
                     </table> 
                </div>
			</div>
		</div>
		<input type="hidden" name="id" value="${ruleManager.id}"/>
	</form>
</body>
</html>
