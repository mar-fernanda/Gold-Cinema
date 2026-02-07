<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Gold Cinema</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">

    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
</head>

<body>

<h1>Gold Cinema</h1>

<c:if test="${param.error != null}">
    <p style="color:red">Usuario o contraseña incorrectos</p>
</c:if>

<c:if test="${not empty successMessage}">
    <p style="color:green">${successMessage}</p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/perform_login">

    <input type="text" name="username" placeholder="Usuario" required />
    <br>

    <input type="password" name="password" placeholder="Contraseña" required />
    <br><br>

    <input type="hidden" name="_csrf" value="${_csrf.token}" />

    <button type="submit">Entrar</button>
</form>

<p>
    ¿No tienes cuenta?
    <a href="${pageContext.request.contextPath}/registro">Regístrate aquí</a>
</p>

</body>
</html>
