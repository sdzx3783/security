<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sz.web.ResultMessage"%>

<%
ResultMessage _obj_=(ResultMessage)session.getAttribute("message");
if(_obj_!=null){
	session.removeAttribute("message");
%>
<script type="text/javascript">
$(function(){
	<%
	  if(_obj_.getResult()==ResultMessage.Success){
	%>
		$.ligerDialog.success('<p><font color="green"><%=_obj_.getMessage()%></font></p>');
	
	<%}
	  else{
		if(!"".equals(_obj_.getCause())){
	%>
		$.ligerDialog.err('','<%=_obj_.getMessage()%>','<%=_obj_.getCause()%>');
		<%}else{%>
		$.ligerDialog.warn('<p><font color="red"><%=_obj_.getMessage()%></font></p>');
	<%   }
	   }%>
});
</script>
<%
} %>



