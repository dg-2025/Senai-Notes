import axios from "axios";

const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL || "http://localhost:8080",
});

// adiciona token em todas as requisições
api.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

// se receber 401 → apaga tudo + manda para login
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.status === 401) {
            localStorage.removeItem("token");
            localStorage.removeItem("usuarioId");
            localStorage.removeItem("usuarioNome");
            localStorage.removeItem("usuarioEmail");
            window.location.href = "/login";
        }
        return Promise.reject(error);
    }
);

export default api;
