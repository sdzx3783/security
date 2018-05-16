<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />


<link rel="stylesheet" type="text/css" href="${ctx}/styles/default/css/Aqua/css/ligerui-all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/default/css/form.css" />

<!-- 这里可通过后台标签实现国际化  借鉴oa <f:js>标签-->
<script type="text/javascript" src="${ctx}/js/lang/common/zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/lang/js/zh_CN.js"></script>

<script type="text/javascript" src="${ctx}/js/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/js/util/util.js"></script>
<script type="text/javascript" src="${ctx}/js/form/rule.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/base.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerMenu.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerResizable.js" ></script>