<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="./inc/styles.jsp" />
<title>ForumApp - ${title}</title>
</head>
<body>

    
	<div class="container-fluid vh-100">

		<jsp:include page="./inc/header.jsp" />
		<jsp:include page="./views/${viewName}" />		
		<jsp:include page="./inc/footer.jsp" />

	</div>


				
</body>
</html>