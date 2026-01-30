<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Registro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>

<h1>Gold Cinema</h1>
<h2>Crear cuenta</h2>

<c:if test="${not empty errorMessage}">
    <div class="error">${errorMessage}</div>
</c:if>

<c:if test="${not empty successMessage}">
    <div class="success">${successMessage}</div>
</c:if>

<form:form method="post"
           action="${pageContext.request.contextPath}/registro"
           modelAttribute="registro">

    <form:input path="username" placeholder="Usuario"/>
    <form:errors path="username" cssClass="error"/>

    <form:input path="email" placeholder="Email"/>
    <form:errors path="email" cssClass="error"/>

    <form:password path="password" placeholder="Contraseña"/>
    <form:errors path="password" cssClass="error"/>

    <form:password path="confirmPassword" placeholder="Confirmar contraseña"/>
    <form:errors path="confirmPassword" cssClass="error"/>

    <button type="submit">Registrarse</button>
</form:form>

<a href="${pageContext.request.contextPath}/login">Volver al login</a>

</body>
</html>
