<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>${titulo}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modal.css">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
<header>
    <h1><i class="fa-solid fa-film"></i> ${titulo}</h1>
    
    <nav>
        <a href="${pageContext.request.contextPath}/">Inicio</a>

        <c:choose>
            <c:when test="${empty usuario}">
                <a href="${pageContext.request.contextPath}/login">
                    <i class="fa-solid fa-right-to-bracket"></i> Iniciar sesión
                </a>
                <a href="${pageContext.request.contextPath}/registro">
                    <i class="fa-solid fa-user-plus"></i> Registrarse
                </a>
            </c:when>

            <c:otherwise>
                <a href="${pageContext.request.contextPath}/perfil">
                    <i class="fa-solid fa-user"></i> Perfil
                </a>
            <form id="logoutForm"
                action="${pageContext.request.contextPath}/logout"
                method="post">

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

    <a href="#" class="nav-link" onclick="this.closest('form').submit(); return false;">
        <i class="fa-solid fa-right-from-bracket"></i> Salir
    </a>
</form>

            </c:otherwise>
        </c:choose>
    </nav>
</header>

<h2 class="bienvenida">
    <c:choose>
        <c:when test="${not empty usuario and not empty usuario.username}">
            Bienvenido, <b>${usuario.username}</b> 🌌
        </c:when>
        <c:otherwise>
            Bienvenido invitado 🌌
        </c:otherwise>
    </c:choose>
</h2>

<form id="form-buscador">
    <input id="buscador" name="q" placeholder="Buscar por título o género…">
    <button type="submit"><i class="fa-solid fa-magnifying-glass"></i> Buscar</button>
</form>

<section class="buscador">
    <div id="resultados" class="grid">
        <c:if test="${not empty resultados}">
            <c:forEach var="p" items="${resultados}">
                <div class="card" data-id="${p.id}">
                    <img src="${p.imagen}" alt="${p.titulo}">
                    <h3>${p.titulo}</h3>
                    <p>${p.genero}</p>
                </div>
            </c:forEach>
        </c:if>
    </div>
</section>
<div id="movieModal" class="modal hidden">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2 id="modalTitulo"></h2>
        <img id="modalImg" src="" alt="" style="max-width:200px;">
        <p><strong>Año:</strong> <span id="modalAnio"></span></p>
        <p><strong>Actores:</strong> <span id="modalActores"></span></p>
        <p><b>Género:</b> <span id="modalGenero"></span></p>
        <p><b>Descripción:</b> <span id="modalDescripcion"></span></p>
        <p><b>Valoración IMDb:</b> <span id="modalValoracion"></span></p>

        <div class="votacion">
            <button id="btnLike">👍 Me gusta</button>
            <button id="btnDislike">👎 No me gusta</button>
            <p>Porcentaje de aprobación: <span id="modalPorcentajeLike">0</span></p>
        </div>

        <div class="acciones">
            <c:if test="${not empty usuario}">
                <button id="btnPorVer">Añadir a Por Ver</button>
                <button id="btnFavorito">❤️ Añadir a Favoritos</button>
                <button id="btnVistos">✅ Marcar como VISTA</button>
            </c:if>
        </div>

<div class="comentarios">
    <h3>Comentarios</h3>

    <c:if test="${not empty usuario}">
        <textarea id="nuevoComentario" placeholder="Escribe un comentario..."></textarea>
        <button id="btnAgregarComentario">Enviar</button>
    </c:if>

    <ul id="comentariosLista"></ul>
</div>

    </div>
</div>

<section id="recomendaciones" class="recomendaciones hidden">
    <h2> Tienes tan buen gusto, que quizas ame esto...</h2>
    <div class="grid-peliculas" id="recomendacionesGrid"></div>
</section>

<script>
    window.contextPath = '${pageContext.request.contextPath}';
</script>
<script src="${pageContext.request.contextPath}/js/peliculasModal.js" defer></script>
<script src="${pageContext.request.contextPath}/js/buscador.js" defer></script>

<footer>
    <p>Gold Cinema © 2026</p>
</footer>

</body>
</html>
