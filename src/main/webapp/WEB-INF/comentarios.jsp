<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Comentarios - Gold Cinema</title>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>

<header>
    <h1> <i class="fa-solid fa-film"></i> Gold Cinema</h1>
<nav>
        <a href="${pageContext.request.contextPath}/">Inicio</a>

        <c:if test="${pageContext.request.userPrincipal != null}">
            <a href="${pageContext.request.contextPath}/perfil">
                <i class="fa-solid fa-user"></i> Perfil
            </a>
            <a href="${pageContext.request.contextPath}/favoritos">
                <i class="fa-solid fa-heart"></i> Favoritos
            </a>
            <a href="${pageContext.request.contextPath}/logout">
                <i class="fa-solid fa-right-from-bracket"></i> Salir
            </a>
        </c:if>
    </nav>
</header>

<h2><i class="fa-solid fa-comment"></i> Comentarios de la comunidad</h2>

<c:if test="${empty comentarios}">
    <p>No hay comentarios todavía.</p>
</c:if>

<c:forEach items="${comentarios}" var="c">
    <div class="comentario" onclick="abrirModal(${c.peliculaId})">
        <h4>${c.tituloPelicula}</h4>
        <p>
            <b>${c.usuario}</b>:
            <c:out value="${c.texto}"/>
        </p>
        <span>
            <i class="fa-regular fa-clock"></i>
            <fmt:formatDate value="${c.fecha}" pattern="dd/MM/yyyy HH:mm"/>
        </span>
    </div>
</c:forEach>

<jsp:include page="fragments/modal.jsp"/>

<script src="${pageContext.request.contextPath}/js/modal.js"></script>
<script src="${pageContext.request.contextPath}/js/reacciones.js"></script>

</body>
</html>
