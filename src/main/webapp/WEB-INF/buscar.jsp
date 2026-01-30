<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>${titulo}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modal.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

</head><meta name="_csrf" th:content="${_csrf.token}"/>
<meta name="_csrf_header" th:content="${_csrf.headerName}"/>
<body>

<header>
    <h1><i class="fa-solid fa-film"></i> ${titulo}</h1>

<nav>
    <a href="${pageContext.request.contextPath}/">Inicio</a>>
    <c:if test="${empty usuario}">
        <a href="${pageContext.request.contextPath}/login">
            <i class="fa-solid fa-right-to-bracket"></i> Iniciar sesión
        </a>
        <a href="${pageContext.request.contextPath}/registro">
            <i class="fa-solid fa-user-plus"></i> Registrarse
        </a>
    </c:if>
    <c:if test="${not empty usuario}">
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

<section class="buscador">
    <input id="buscador" placeholder="Buscar por título o género…">
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
        <p><b>Género:</b> <span id="modalGenero"></span></p>
        <p><b>Descripción:</b> <span id="modalDescripcion"></span></p>
        <p><b>Valoración IMDb:</b> <span id="modalValoracion"></span></p>

        <div class="votacion">
            <button id="btnLike">👍 Me gusta</button>
            <button id="btnDislike">👎 No me gusta</button>
            <p>Porcentaje de aprobación: <span id="modalPorcentajeLike">0</span>%</p>
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
            <ul id="comentariosLista"></ul>
            <c:if test="${not empty usuario}">
                <textarea id="nuevoComentario" placeholder="Escribe un comentario..."></textarea>
                <button id="btnAgregarComentario">Enviar</button>
            </c:if>
        </div>
    </div>
</div>

<script>
    window.contextPath = '${pageContext.request.contextPath}';

    // Abrir modal al hacer click en cualquier card
    document.addEventListener("DOMContentLoaded", () => {
        const cards = document.querySelectorAll(".card");
        cards.forEach(card => {
            card.addEventListener("click", () => {
                const id = card.getAttribute("data-id");
                if (window.abrirModal) window.abrirModal(id);
            });
        });

        // Cerrar modal al hacer click en la X o fuera del contenido
        const modal = document.getElementById("movieModal");
        modal.addEventListener("click", e => {
            if (e.target === modal || e.target.classList.contains("close")) {
                if (window.cerrarModal) window.cerrarModal();
            }
        });
    });
</script>

<script src="${pageContext.request.contextPath}/js/peliculasModal.js" defer></script>
<script src="${pageContext.request.contextPath}/js/buscador.js" defer></script>

<footer>
    <p>Gold Cinema © 2026</p>
</footer>

</body>
</html>
