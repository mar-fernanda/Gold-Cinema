<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${titulo}</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/modal.css">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script>window.contextPath = '${pageContext.request.contextPath}';</script>
</head>
<body>
<header>
    <h1><i class="fa-solid fa-film"></i> ${titulo}</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/">Inicio</a>
        <c:choose>
            <c:when test="${empty usuario}">
                <a href="${pageContext.request.contextPath}/login"><i class="fa-solid fa-right-to-bracket"></i> Iniciar sesión</a>
                <a href="${pageContext.request.contextPath}/registro"><i class="fa-solid fa-user-plus"></i> Registrarse</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/perfil"><i class="fa-solid fa-user"></i> Perfil</a>
                <form id="logoutForm" action="${pageContext.request.contextPath}/logout" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <a href="#" class="nav-link" onclick="this.closest('form').submit(); return false;">
                        <i class="fa-solid fa-right-from-bracket"></i> Salir
                    </a>
                </form>
            </c:otherwise>
        </c:choose>
    </nav>
</header>

<main class="perfil">
<div id="perfilSection">
    <section class="perfil-bloque">
        <h2>Favoritos ❤️</h2>
        <c:choose>
            <c:when test="${empty favoritos}">
                <p>No tienes favoritos todavía.</p>
            </c:when>
            <c:otherwise>
                <div class="grid-peliculas">
                    <c:forEach items="${favoritos}" var="f">
                        <div class="card" data-id="${f.id}" onclick="abrirModal('${f.id}')">
                            <img src="${f.imagen}" alt="${f.titulo}">
                            <h3>${f.titulo}</h3>
                            <p>${f.genero}</p>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </section>

    <section class="perfil-bloque">
        <h2>Por ver ⏳</h2>
        <c:choose>
            <c:when test="${empty porVer}">
                <p>No tienes películas pendientes.</p>
            </c:when>
            <c:otherwise>
                <div class="grid-peliculas">
                    <c:forEach items="${porVer}" var="p">
                        <div class="card" data-id="${p.id}" onclick="abrirModal('${p.id}')">
                            <img src="${p.imagen}" alt="${p.titulo}">
                            <h3>${p.titulo}</h3>
                            <p>${p.genero}</p>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </section>

    <section class="perfil-bloque">
        <h2>Vistos ✅</h2>
        <c:choose>
            <c:when test="${empty vistos}">
                <p>Aún no has marcado películas como vistas.</p>
            </c:when>
            <c:otherwise>
                <div class="grid-peliculas">
                    <c:forEach items="${vistos}" var="p">
                        <div class="card" data-id="${p.id}" onclick="abrirModal('${p.id}')">
                            <img src="${p.imagen}" alt="${p.titulo}">
                            <h3>${p.titulo}</h3>
                            <p>${p.genero}</p>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </section>

    <section class="perfil-bloque">
        <h2>Géneros que más veo 🎬</h2>
        <c:choose>
            <c:when test="${empty generosFavoritos}">
                <p>Aún no has marcado favoritos.</p>
            </c:when>
            <c:otherwise>
                <div class="generos-container">
                    <c:forEach items="${generosFavoritos}" var="entry">
                        <span class="genero-pill">${entry.key} (${entry.value})</span>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </section>
</div>
</main>

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

<script src="${pageContext.request.contextPath}/js/peliculasModal.js" defer></script>
<script src="${pageContext.request.contextPath}/js/perfil.js" defer></script>

<footer>
    <p>Gold Cinema © 2026</p>
</footer>
</body>
</html>
