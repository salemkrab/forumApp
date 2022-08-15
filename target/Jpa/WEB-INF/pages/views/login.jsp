<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<section
	class="h-100 p-4 d-flex justify-content-center align-items-center pb-4"
	style="background-color: #eee; border-radius: .5rem .5rem 0 0;">
	<div class="card text-black" style="border-radius: 25px;">
		<div class="card-body p-md-5">
			<div class="row justify-content-center">

				<p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Sign in</p>

				<form action="<c:url value="/welcome/auth/login" />" method="post"
					style="width: 22rem;">
					<!-- Email input -->
					<div class="form-outline mb-4">
						<input type="text" id="form2Example1" class="form-control"
							name="username"> <label class="form-label"
							for="form2Example1" style="margin-left: 0px;">Username</label>
						<div class="form-notch">
							<div class="form-notch-leading" style="width: 9px;"></div>
							<div class="form-notch-middle" style="width: 88.8px;"></div>
							<div class="form-notch-trailing"></div>
						</div>
					</div>

					<!-- Password input -->
					<div class="form-outline mb-4">
						<input type="password" id="form2Example2" class="form-control"
							name="password"> <label class="form-label"
							for="form2Example2" style="margin-left: 0px;">Password</label>
						<div class="form-notch">
							<div class="form-notch-leading" style="width: 9px;"></div>
							<div class="form-notch-middle" style="width: 64.8px;"></div>
							<div class="form-notch-trailing"></div>
						</div>
					</div>

					<!-- 2 column grid layout for inline styling -->
					<!-- 					<div class="row mb-4"> -->
					<!-- 						<div class="col d-flex justify-content-center"> -->
					<!-- 							Checkbox -->
					<!-- 							<div class="form-check"> -->
					<!-- 								<input class="form-check-input" type="checkbox" value="" -->
					<!-- 									id="form2Example31" checked=""> <label -->
					<!-- 									class="form-check-label" for="form2Example31"> Remember -->
					<!-- 									me </label> -->
					<!-- 							</div> -->
					<!-- 						</div> -->

					<!-- 						<div class="col"> -->
					<!-- 							Simple link -->
					<!-- 							<a href="#!">Forgot password?</a> -->
					<!-- 						</div> -->
					<!-- 					</div> -->

					<!-- Submit button -->
					<button type="submit" class="btn btn-secondary btn-block mb-4">Sign
						in</button>

					<!-- Register buttons -->
					<div class="text-center">
						<p>
							Not a member? <a href='<c:url value="/welcome/auth/register" />'>Register</a>
						</p>
					</div>
				</form>
			</div>
			<c:if test="${requestScope.loginFail=='empties'}">
				<div class="alert alert-danger" role="alert">Your username
					and/or password is/are empty.</div>
			</c:if>

			<c:if test="${requestScope.loginFail=='not_found'}">
				<div class="alert alert-danger" role="alert">Wrong username
					and/or password.</div>
			</c:if>
		</div>
	</div>
</section>



