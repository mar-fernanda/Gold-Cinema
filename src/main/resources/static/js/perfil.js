document.addEventListener("DOMContentLoaded", () => {

    const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
    const contextPath = window.contextPath || "";

    const perfilContainer = document.getElementById("perfilSection");
    const mainPerfil = document.querySelector("main.perfil");

    if (!perfilContainer || !mainPerfil) return;

    /* recarga */
    function recargarPerfil() {
        fetch(`${contextPath}/perfil/section`, {
            credentials: "same-origin"
        })
            .then(res => {
                if (!res.ok) throw new Error("Error recargando perfil");
                return res.text();
            })
            .then(html => {
                perfilContainer.innerHTML = html;
            })
            .catch(err => console.error("Perfil:", err));
    }

    document.addEventListener("perfilActualizado", recargarPerfil);

    function toggleMovie(url) {
        return fetch(url, {
            method: "POST",
            credentials: "same-origin",
            headers: {
                [csrfHeader]: csrfToken
            }
        }).then(res => {
            if (!res.ok) throw new Error("Error POST");
            return res;
        });
    }

    /* delegacion*/
    document.addEventListener("click", (e) => {

        const btn = e.target.closest(".btnFavorito, .btnPorVer, .btnVistos");
        if (!btn) return;

        const card = btn.closest(".card");
        if (!card?.dataset?.id) return;

        e.preventDefault();
        e.stopPropagation();

        const movieId = card.dataset.id;
        let url = null;

        if (btn.classList.contains("btnFavorito")) {
            url = `${contextPath}/usuario/favorito/toggle/${movieId}?accion=eliminar`;
        }
        else if (btn.classList.contains("btnPorVer")) {
            url = `${contextPath}/usuario/porver/toggle?movieId=${movieId}&accion=eliminar`;
        }
        else if (btn.classList.contains("btnVistos")) {
            url = `${contextPath}/usuario/vistos/toggle?movieId=${movieId}`;
        }

        if (!url) return;

        toggleMovie(url)
            .then(() => recargarPerfil())
            .catch(err => console.error(err));
    });

});
