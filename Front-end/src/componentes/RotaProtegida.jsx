import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';

const RotaProtegida = () => {
    // 1. Tenta pegar o token salvo
    const token = localStorage.getItem('token');

    // 2. Se NÃO tem token, manda pro login
    if (!token) {
        return <Navigate to="/login" replace />;
    }

    // 3. Se TEM token, deixa renderizar a página (Outlet)
    return <Outlet />;
};

export default RotaProtegida;