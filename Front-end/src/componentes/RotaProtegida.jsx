import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';

const RotaProtegida = () => {
    const token = localStorage.getItem("token");

    // se não tem token → login
    if (!token) {
        return <Navigate to="/login" replace />;
    }

    // token existe → libera acesso
    return <Outlet />;
};

export default RotaProtegida;
