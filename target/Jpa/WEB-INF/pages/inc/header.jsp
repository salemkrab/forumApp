<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<header class="">
	<!-- Navbar -->
	<nav class="navbar navbar-expand-lg navbar-light ">
		<!-- Container wrapper -->
		<div class="container-fluid">
			<!-- Toggle button -->
			<button class="navbar-toggler" type="button"
				data-mdb-toggle="collapse" data-mdb-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<i class="fas fa-bars"></i>
			</button>

			<!-- Collapsible wrapper -->
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<!-- Navbar brand -->
				<a class="navbar-brand mt-2 mt-lg-0"
					href="<c:url value="/welcome/home" />">ForumApp</a>
				<!-- Left links -->
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link"
						href="<c:url value="/welcome/category/JavaSE" />">Java SE</a></li>
					<li class="nav-item"><a class="nav-link"
						href="<c:url value="/welcome/category/JavaEE" />">Java EE</a></li>
					<li class="nav-item"><a class="nav-link"
						href="<c:url value="/welcome/category/JPA-Hibernate" />">JPA/Hibernate</a></li>
					<li class="nav-item"><a class="nav-link"
						href="<c:url value="/welcome/category/Spring" />">Spring</a></li>
					<li class="nav-item"><a class="nav-link"
						href="<c:url value="/welcome/category/Web" />">Html/Css/Js</a></li>
				</ul>
				<!-- Left links -->
			</div>
			<!-- Collapsible wrapper -->

			<!-- Right elements -->
			<div class="d-flex align-items-center">
				<!-- Icon -->
				<c:if test="${empty sessionScope.userId}">
					<a class="text-reset me-3"
						href='<c:url value="/welcome/auth/register" />'>Register</a>
				</c:if>
				<c:if test="${empty sessionScope.userId}">
					<a class="text-reset me-3"
						href='<c:url value="/welcome/auth/login" />'>Login</a>
				</c:if>

				<%-- 				<c:if test="${sessionScope.authen=='loggedIn'}"> --%>
				<!-- 					<a class="text-reset me-3" -->
				<%-- 						href='<c:url value="/welcome?logout=true" />'>Log out </a> --%>
				<%-- 				</c:if> --%>
				<c:if test="${!empty sessionScope.userId}">

					<div class="dropdown">

						<a class="dropdown-toggle d-flex align-items-center hidden-arrow"
							href="#" id="navbarDropdownMenuAvatar" role="button"
							data-mdb-toggle="dropdown" aria-expanded="false"> <img
							src="https://mdbcdn.b-cdn.net/img/new/avatars/2.webp"
							class="rounded-circle" height="25"
							alt="Black and White Portrait of a Man" loading="lazy" />
						</a>
						<ul class="dropdown-menu dropdown-menu-end"
							aria-labelledby="navbarDropdownMenuAvatar">
							<li><a class="dropdown-item"
								href="<c:url value="/welcome/user/${sessionScope.userId}" />">My
									profile</a></li>
							<li><a class="dropdown-item"
								href="<c:url value="/welcome/auth/logout" />">Logout</a></li>
						</ul>
					</div>
				</c:if>
			</div>
			<!-- Right elements -->
		</div>
		<!-- Container wrapper -->
	</nav>
	<!-- Navbar -->
</header>