document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("movieModal");
    const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
    const contextPath = window.contextPath || "";
    let peliculaActual = null;
    let estadoPel = { favorito: false, porVer: false, visto: false };

    // ----------------- NOTIFICACIONES -----------------
    function mostrarNotificacion(mensaje, tipo = "success") {
        const container = document.getElementById("notification-container") || crearContenedorNotificaciones();
        const notification = document.createElement("div");
        notification.className = `toast toast-${tipo}`;
        notification.innerHTML = `
            <span class="toast-msg">${mensaje}</span>
            <i class="fa-solid fa-xmark toast-close"></i>
        `;
        container.appendChild(notification);

        notification.querySelector(".toast-close").addEventListener("click", () => {
            cerrarToast(notification);
        });

        setTimeout(() => cerrarToast(notification), 4000);
    }

    function cerrarToast(el) {
        if (el && el.parentElement) {
            el.classList.add("fade-out");
            setTimeout(() => el.remove(), 300);
        }
    }

    function crearContenedorNotificaciones() {
        const container = document.createElement("div");
        container.id = "notification-container";
        document.body.appendChild(container);
        return container;
    }

    // ----------------- MODAL -----------------
    window.abrirModal = async function (id) {
        peliculaActual = id;
        try {
            const res = await fetch(`${contextPath}/api/peliculas/${id}`, { credentials: "same-origin" });
            if (!res.ok) throw new Error("Error al obtener datos");
            const p = await res.json();

            document.getElementById("modalTitulo").innerText = p.titulo || "";
            document.getElementById("modalImg").src = p.imagen || "";
            document.getElementById("modalGenero").innerText = p.genero || "";
            document.getElementById("modalDescripcion").innerText = p.descripcion || "";
            document.getElementById("modalValoracion").innerText = p.valoracion || 0;

            await cargarEstado();
            actualizarBotones();
            cargarComentarios();
            cargarVotos();

            modal.classList.remove("hidden");
            modal.style.display = "flex";
        } catch (err) {
            console.error(err);
            mostrarNotificacion("Error al cargar la película", "error");
        }
    };

    window.cerrarModal = () => {
        modal.classList.add("hidden");
        modal.style.display = "none";
        peliculaActual = null;
        estadoPel = { favorito: false, porVer: false, visto: false };
    };

    window.addEventListener("click", (e) => {
        if (e.target === modal) cerrarModal();
    });

    // ----------------- POST GENERAL -----------------
    async function postAccion(url, mensaje, reload = false, paramsObj = {}) {
        try {
            const formData = new URLSearchParams();
            for (const key in paramsObj) {
                formData.append(key, paramsObj[key]);
            }

            const res = await fetch(url, {
                method: "POST",
                credentials: "same-origin",
                headers: {
                    [csrfHeader]: csrfToken,
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: formData.toString()
            });

            if (res.status === 401 || res.status === 403) {
                mostrarNotificacion("❌ Debes iniciar sesión", "error");
                return false;
            }

            if (!res.ok) throw new Error("Error " + res.status);

            if (mensaje) mostrarNotificacion(mensaje, "success");
            if (reload) setTimeout(() => location.reload(), 1000);

            return res;
        } catch (err) {
            console.error("Error en postAccion:", err);
            mostrarNotificacion("❌ Hubo un error al procesar la solicitud", "error");
            return false;
        }
    }

    // ----------------- ESTADO INICIAL -----------------
    async function cargarEstado() {
        try {
            const res = await fetch(`${contextPath}/usuario/estado?movieId=${peliculaActual}`, {
                credentials: "same-origin"
            });
            if (!res.ok) return;
            estadoPel = await res.json();
        } catch (err) {
            console.error("Error cargando estado:", err);
        }
    }

    function actualizarBotones() {
        const btnFav = document.getElementById("btnFavorito");
        const btnPorVer = document.getElementById("btnPorVer");
        const btnVistos = document.getElementById("btnVistos");

        if (btnFav) btnFav.innerText = estadoPel.favorito ? "❌ Eliminar de favoritos" : "❤️ Agregar a favoritos";
        if (btnPorVer) btnPorVer.innerText = estadoPel.porVer ? "❌ Eliminar de Por Ver" : "📌 Agregar a Por Ver";
        if (btnVistos) btnVistos.innerText = estadoPel.visto ? "🎬 Vista" : "🎬 Marcar como vista";
    }

    // ----------------- BOTONES -----------------
    document.getElementById("btnFavorito")?.addEventListener("click", async () => {
        const accion = estadoPel.favorito ? "eliminar" : "agregar";
        const res = await postAccion(`${contextPath}/usuario/favorito/toggle/${peliculaActual}?accion=${accion}`);
        if (res) {
            estadoPel.favorito = !estadoPel.favorito;
            actualizarBotones();
        }
    });

    document.getElementById("btnPorVer")?.addEventListener("click", async () => {
        const accion = estadoPel.porVer ? "eliminar" : "agregar";
        const res = await postAccion(`${contextPath}/usuario/porver/toggle?movieId=${peliculaActual}&accion=${accion}`);
        if (res) {
            estadoPel.porVer = !estadoPel.porVer;
            actualizarBotones();
        }
    });

    document.getElementById("btnVistos")?.addEventListener("click", async () => {
        const res = await postAccion(`${contextPath}/usuario/vistos/toggle?movieId=${peliculaActual}`);
        if (res) {
            estadoPel.visto = true;
            estadoPel.porVer = false; // Se elimina de Por Ver
            actualizarBotones();
        }
    });

    // ----------------- COMENTARIOS -----------------
    function cargarComentarios() {
        if (!peliculaActual) return;

        fetch(`${contextPath}/usuario/comentarios?movieId=${peliculaActual}`)
            .then(r => r.ok ? r.json() : [])
            .then(data => {
                const lista = document.getElementById("comentariosLista");
                if (!lista) return;

                lista.innerHTML = "";
                if (!Array.isArray(data) || data.length === 0) {
                    lista.innerHTML = "<li>Sin comentarios aún</li>";
                    return;
                }

                data.forEach(c => {
                    lista.innerHTML += `<li><b>${c.username}:</b> ${c.texto}</li>`;
                });
            })
            .catch(err => console.error("Error cargando comentarios:", err));
    }

    document.getElementById("btnAgregarComentario")?.addEventListener("click", async () => {
        const input = document.getElementById("nuevoComentario");
        const texto = input.value.trim();
        if (!texto) return;

        const ok = await postAccion(
            `${contextPath}/usuario/comentario`,
            "💬 Comentario añadido",
            false,
            { movieId: peliculaActual, texto }
        );

        if (ok) {
            input.value = "";
            cargarComentarios();
        }
    });

    // ----------------- VOTOS -----------------
    function cargarVotos() {
        if (!peliculaActual) return;
        fetch(`${contextPath}/usuario/votos?movieId=${peliculaActual}`)
            .then(r => r.json())
            .then(porcentaje => {
                const el = document.getElementById("modalPorcentajeLike");
                if (el) el.innerText = `${Math.round(porcentaje)}%`;
            })
            .catch(err => console.error("Error cargando votos:", err));
    }

    window.votar = (meGusta) =>
        postAccion(`${contextPath}/usuario/votar`, "👍 Voto registrado", false, { 
            movieId: peliculaActual,
            like: meGusta
        }).then(() => cargarVotos());

    document.getElementById("btnLike")?.addEventListener("click", () => votar(true));
    document.getElementById("btnDislike")?.addEventListener("click", () => votar(false));
});
