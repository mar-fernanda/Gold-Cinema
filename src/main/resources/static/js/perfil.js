document.addEventListener("DOMContentLoaded", () => {
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;

    function recargarPerfil() {
        fetch(`${window.contextPath}/perfil/section`)
            .then(res => res.text())
            .then(html => {
                document.getElementById("perfilSection").innerHTML = html;
            });
    }

    function toggleMovie(url) {
        fetch(url, {
            method: "POST",
            headers: { "X-CSRF-TOKEN": csrfToken }
        }).then(res => {
            if(res.ok){
                recargarPerfil();
            }
        });
    }

    document.querySelector("main.perfil").addEventListener("click", (e) => {
        const btn = e.target.closest(".btnFavorito, .btnPorVer, .btnVistos");
        if(!btn) return;

        const card = btn.closest(".card");
        const movieId = card.dataset.id;

        if(btn.classList.contains("btnFavorito")){
            toggleMovie(`${window.contextPath}/usuario/favorito/toggle/${movieId}?accion=eliminar`);
        } else if(btn.classList.contains("btnPorVer")){
            toggleMovie(`${window.contextPath}/usuario/porver/toggle?movieId=${movieId}&accion=eliminar`);
        } else if(btn.classList.contains("btnVistos")){
            toggleMovie(`${window.contextPath}/usuario/vistos/toggle?movieId=${movieId}`);
        }
    });
});
