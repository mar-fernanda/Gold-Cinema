document.addEventListener("DOMContentLoaded", () => {

    const loginForm = document.getElementById("loginForm");
    const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;

    loginForm?.addEventListener("submit", async (e) => {
        e.preventDefault();

        const username = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value.trim();

        if (!username || !password) {
            alert("❌ Debes ingresar usuario y contraseña");
            return;
        }

        try {
            const res = await fetch(loginForm.action, {
                method: "POST",
                credentials: "same-origin",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    [csrfHeader]: csrfToken
                },
                body: new URLSearchParams({
                    username: username,
                    password: password
                })
            });

            if (res.ok) {
                window.location.href = "/";
            } else if (res.status === 401 || res.status === 403) {
                alert("❌ Usuario o contraseña incorrectos");
            } else {
                alert("❌ Error al iniciar sesión");
            }

        } catch (err) {
            console.error(err);
            alert("❌ Error de conexión");
        }
    });

});
