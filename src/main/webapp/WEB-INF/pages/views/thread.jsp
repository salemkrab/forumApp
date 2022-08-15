<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="page-container mb-5 pb-5">



<blockquote class="fs-3 my-4">
	| Question :
	<c:out value="${topic.title}" />
</blockquote>
<div class="topic-container mx-auto mb-5">
	<div class="container-fluid mt-100">
		<div class="row">
			<div class="col-md-12">
				<div class="card">
					<div class="card-header">
						<div
							class="media flex-wrap w-100 align-items-center d-flex flex-column text-center">
							<!-- 							<img src="https://i.imgur.com/iNmBizf.jpg" -->
							<!-- 								class="d-block ui-w-40 rounded-circle" alt=""> -->
							<div class="media-body ml-3">
								<a href="javascript:void(0)" data-abc="true"><c:out
										value="${topic.user.username}" /></a>
								<div class="text-muted small">
									posted on <strong><fmt:formatDate
											value="${topic.creationDate.getTime()}"
											pattern="dd-MM-yyyy @ HH:mm:ss" /></strong>
								</div>
							</div>
							<div class="text-muted small ml-3">
								<div>
									Member since
									<fmt:formatDate value="${topic.user.registerDate.getTime()}"
										pattern="dd-MM-yyyy" />
								</div>
								<div>
									<strong><c:out value="${topic.user.topics.size()}" /></strong>
									posts
								</div>
							</div>
						</div>
					</div>
					<div class="card-body">
						<p class="mb-0">
							<c:out value="${topic.content}" />
						</p>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>
<blockquote class="fs-3 my-4">
	| Answers :
	<c:out value="${topic.answers.size()}" />
</blockquote>

<c:forEach items="${topic.answers}" var="answer">
<div class="topic-container mb-3 w-75 mx-auto">
	<div class="container-fluid mt-100" >
		<div class="row">
			<div class="col-md-12">
				<div class="card" style="
                        background: linear-gradient(to right, #e7e4e4, #f3f2f2);
                        background: -webkit-linear-gradient(left, #e7e4e4, #f3f2f2);
                        background: -o-linear-gradient(right, #e7e4e4, #f3f2f2);
                        background: -moz-linear-gradient(right, #e7e4e4, #f3f2f2);">
					<div class="card-header">
						<div
							class="media flex-wrap w-100 align-items-center d-flex flex-column text-center">
								<div class="media-body ml-3">
								<a href="javascript:void(0)" data-abc="true"><c:out
										value="${answer.user.username}" /></a>
								<div class="text-muted small">
									answered on <strong><fmt:formatDate
											value="${answer.creationDate.getTime()}"
											pattern="dd-MM-yyyy @ HH:mm:ss" /></strong>
								</div>
							</div>
							<div class="text-muted small ml-3">
								<div>
									Member since
									<fmt:formatDate value="${answer.user.registerDate.getTime()}"
										pattern="dd-MM-yyyy" />
								</div>
								<div>
									<strong><c:out value="${answer.user.topics.size()}" /></strong>
									posts
								</div>
							</div>
						</div>
					</div>
					<div class="card-body">
						<p class="mb-0">
							<c:out value="${answer.content}" />
						</p>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>
</c:forEach>
		<section class="w-100 p-4 d-flex align-items-center pb-4 flex-column">

			<h3 class="display-6 fw-bold">Reply topic</h3>
			<div class="alert alert-info" role="alert">You must be logged
				in to post a reply.</div>



			<form style="width: 26rem;" class="d-block"
				action="<c:url value="/welcome/thread/id/${topic.id}" />" method="post">
				<!-- Name input -->

				<!-- Message input -->
				<div class="form-outline mb-4">
					<textarea class="form-control" id="form4Example3" rows="4"
						name="AnswerContent"></textarea>
					<label class="form-label" for="form4Example3"
						style="margin-left: 0px;">Content</label>
					<div class="form-notch">
						<div class="form-notch-leading" style="width: 9px;"></div>
						<div class="form-notch-middle" style="width: 60px;"></div>
						<div class="form-notch-trailing"></div>
					</div>
				</div>

				<!-- Submit button -->
				<button type="submit" class="btn btn-secondary btn-block mb-4">Send</button>
			</form>

			<c:if test="${requestScope.newAnswerFail=='empties'}">
				<div class="alert alert-danger" role="alert">Missing content.</div>
			</c:if>

			<c:if test="${requestScope.newAnswerFail=='notLogged'}">
				<div class="alert alert-danger" role="alert">You must be
					logged in to create a new topic.</div>
			</c:if>


		</section>
		
</div>