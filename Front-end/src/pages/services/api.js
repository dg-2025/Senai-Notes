import axios from 'axios';

// Confirme se o seu Java roda na porta 8080
const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080', 
});

// Esse trecho envia o Token automaticamente em todo pedido
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default api;