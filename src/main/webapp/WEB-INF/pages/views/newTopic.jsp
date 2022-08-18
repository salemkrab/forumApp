<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<section
	class="pb-4 d-flex justify-content-center mt-5">
	<div class="bg-white border rounded-5 w-75">

		<section class="w-100 p-4 d-flex align-items-center pb-4 flex-column">

			<h3 class="display-5 fw-bold">Create new topic</h3>

			<form style="width: 26rem;" class="d-block"
				action="<c:url value="/welcome/thread/new" />" method="post">
				<!-- Name input -->
				<div class="form-outline mb-4">
					<input type="text" id="form4Example1" class="form-control"
						name="topicTitle"> <label class="form-label"
						for="form4Example1" style="margin-left: 0px;">Title</label>
					<div class="form-notch">
						<div class="form-notch-leading" style="width: 9px;"></div>
						<div class="form-notch-middle" style="width: 42.4px;"></div>
						<div class="form-notch-trailing"></div>
					</div>
				</div>

				<select class="form-select mb-4" aria-label="Default select example"
					name="category">
					<option value="" selected>Category</option>
					<option value="JavaSE">Java SE</option>
					<option value="JavaEE">Java EE</option>
					<option value="JPA-Hibernate">JPA/Hibernate</option>
					<option value="Spring">Spring</option>
					<option value="Web">Html/Css/Js</option>
				</select>



				<!-- Message input -->
				<div class="form-outline mb-4">
					<textarea class="form-control" id="form4Example3" rows="4"
						name="TopicContent"></textarea>
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

			<c:if test="${requestScope.newTopicFail=='empties'}">
				<div class="alert alert-danger" role="alert">Missing title and/or category.</div>
			</c:if>

			<c:if test="${requestScope.newTopicFail=='notLogged'}">
				<div class="alert alert-danger" role="alert">You must be
					logged in to create a new topic.</div>
			</c:if>


		</section>



	</div>
</section>