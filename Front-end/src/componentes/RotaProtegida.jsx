import React from 'react';
import { Outlet } from 'react-router-dom';
import NotFound from '../pages/NotFound';

const RotaProtegida = () => {
    const token = localStorage.getItem('token');

    // Se NÃO tem token, finge que a página não existe (404)
    if (!token) {
        return <NotFound />;
    }

    // Se tem token, libera o acesso
    return <Outlet />;
};

export default RotaProtegida;