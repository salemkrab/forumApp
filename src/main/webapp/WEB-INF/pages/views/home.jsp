<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="d-flex justify-content-between my-5 px-5">
	<div class="fs-3">| ${tableTitle}</div>
	<br>
	<div class="fs-3">
		| numb of topics :
		<c:out value="${topics.size()}" />
	</div>
</div>



<table
	class="table table-striped table-hover align-middle text-center mh-50 overflow-auto">
	<thead>
		<tr>
			<th scope="col">Title</th>
			<th scope="col">Author</th>
			<th scope="col"># Answers</th>
			<th scope="col">Date</th>
			<th scope="col">Enter</th>
		</tr>
	</thead>
	<tbody>

		<c:forEach items="${topics}" var="topic">
			<tr>
				<td><c:out value="${topic.title}" /></td>
				<td><c:out value="${topic.user.username}" /></td>
				<th scope="row"><c:out value="${topic.answers.size()}" /></th>
				<td><fmt:formatDate value="${topic.creationDate.getTime()}"
						pattern="dd-MM-yyyy à HH:mm:ss" /></td>
				<td><a href='<c:url value="/welcome/thread/id/${topic.id}"/>'
					class="btn btn-secondary btn-floating btn-lg"> <i
						class="fas fa-2x fa-arrow-circle-right" style="color: #f9f9f9"></i>
				</a></td>
			</tr>
		</c:forEach>
		<tr>
	</tbody>
</table>


<div class="col-6 offset-3 d-flex justify-content-center my-5">
	<a href="<c:url value="/welcome/thread/new" />"
		class="btn btn-secondary btn-lg">Create new post</a>
</div>
