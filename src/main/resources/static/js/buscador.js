// buscador.js
document.addEventListener("DOMContentLoaded", () => {
    const input = document.getElementById("buscador");
    const form = document.getElementById("form-buscador");

    // Crear un contenedor de resultados si no existe
    let contenedor = document.getElementById("resultados");
    if (!contenedor) {
        contenedor = document.createElement("div");
        contenedor.id = "resultados";
        contenedor.className = "grid";
        form.parentNode.insertBefore(contenedor, form.nextSibling);
    }

    // Evitar que el formulario recargue la página
    form.addEventListener("submit", e => {
        e.preventDefault();
        buscarPeliculas(input.value.trim());
    });

    // Buscar mientras se escribe
    input.addEventListener("keyup", e => {
        const q = e.target.value.trim();
        buscarPeliculas(q);
    });

    function buscarPeliculas(q) {
        if (q.length < 2) {
            contenedor.innerHTML = "";
            return;
        }

        fetch(`${window.contextPath}/api/buscar?q=${encodeURIComponent(q)}`)
            .then(res => {
                if (!res.ok) throw new Error("Error al buscar películas");
                return res.json();
            })
            .then(data => {
                contenedor.innerHTML = "";
                data.forEach(p => {
                    if (!p.imagen || p.imagen === "N/A") return;

                    contenedor.innerHTML += `
                        <div class="card" onclick="abrirModal('${p.id}')">
                            <img src="${p.imagen}" alt="${p.titulo}">
                            <h3>${p.titulo}</h3>
                            <p>${p.genero}</p>
                        </div>
                    `;
                });
            })
            .catch(err => console.error(err));
    }
});
