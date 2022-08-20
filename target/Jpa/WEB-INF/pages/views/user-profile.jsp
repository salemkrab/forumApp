<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<div class="col d-flex justify-content-around my-5">
	<div class="col-4">
		<table class="table col-4 align-middle">
			<tr>
				<th colspan="2" class="text-center">Personal data</th>
			</tr>
			<tr>
				<td>Nickname :</td>
				<td class="text-end">${user.username}</td>
			</tr>
			<tr>
				<td>registred since:</td>
				<td class="text-end"><fmt:formatDate
						value="${user.registerDate.getTime()}"
						pattern="dd-MM-yyyy à HH:mm:ss" /></td>
			</tr>
			<tr>
				<td>last answer :</td>
				<td class="text-end"><c:choose>
						<c:when
							test="${userDates['last-answer'].getTime().getYear() > 70}">
							<fmt:formatDate value="${userDates['last-answer'].getTime()}"
								pattern="dd-MM-yyyy à HH:mm:ss" />
						</c:when>
						<c:otherwise>
							<c:out default="None" value="No answers yet." />
						</c:otherwise>
					</c:choose></td>
			</tr>
			<tr>
				<td>last topic :</td>
				<td class="text-end"><c:choose>
						<c:when
							test="${userDates['last-topic'].getTime().getYear() > 70}">
							<fmt:formatDate value="${userDates['last-topic'].getTime()}"
								pattern="dd-MM-yyyy à HH:mm:ss" />
						</c:when>
						<c:otherwise>
							<c:out default="None" value="No posts yet." />
						</c:otherwise>
					</c:choose></td>
			</tr>
		</table>
	</div>

	<div class="col-4">
		<table class="table align-middle">
			<tr>
				<th colspan="2" class="text-center">Activity</th>
			</tr>
			<tr>
				<td>Topics :</td>
				<td class="text-end">${user.topics.size()}</td>
			</tr>
			<tr>
				<td>Answers :</td>
				<td class="text-end"><c:out value="${user.answers.size()}" />
				</td>
			</tr>
		</table>
	</div>

</div>

<h2 class="text-center mt-5">Topic List</h2>

<table
	class="table table-striped table-hover align-middle text-center mh-50 overflow-auto">
	<thead>
		<tr>
			<th scope="col">Title</th>
			<th scope="col"># Answers</th>
			<th scope="col">Date</th>
			<th scope="col">Enter</th>
			<th scope="col">Delete</th>

		</tr>
	</thead>
	<tbody>

		<c:forEach items="${user.topics}" var="topic">
			<tr>
				<td><c:out value="${topic.title}" /></td>
				<th scope="row"><c:out value="${topic.answers.size()}" /></th>
				<td><fmt:formatDate value="${topic.creationDate.getTime()}"
						pattern="dd-MM-yyyy à HH:mm:ss" /></td>
				<td><a href='<c:url value="/welcome/thread/id/${topic.id}"/>'
					class="btn btn-secondary btn-floating btn-lg"> <i
						class="fas fa-2x fa-arrow-circle-right" style="color: #f9f9f9"></i>
				</a></td>
				<td><a
					href='<c:url value="/welcome/thread/delete/id/${topic.id}"/>'
					class="btn btn-secondary btn-floating btn-lg"> <i
						class="fas fa-2x fa-trash-alt" style="color: #f9f9f9"></i>
				</a></td>
			</tr>
		</c:forEach>
		<tr>
	</tbody>
</table>