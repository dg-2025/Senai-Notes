import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import './styles/global.css'; 

import Login from './pages/Login';
import Signup from './pages/Signup';
import ForgotPassword from './pages/ForgotPassword';
import ResetPassword from './pages/ResetPassword';
import TelaNotas from './pages/TelaNotas';
import TelaConfig from './pages/TelaConfig';
import NotFound from './pages/NotFound';
import RotaProtegida from './componentes/RotaProtegida'; // <--- IMPORTA O PORTEIRO

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* === ROTAS PÚBLICAS (Qualquer um acessa) === */}
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/reset-password" element={<ResetPassword />} />
        
        {/* Redireciona a raiz para o dashboard (o porteiro vai barrar se não logar) */}
        <Route path="/" element={<Navigate to="/dashboard" />} />

        {/* === ROTAS TRANCADAS (Só com Token) === */}
        <Route element={<RotaProtegida />}>
            <Route path="/dashboard" element={<TelaNotas />} />
            <Route path="/settings" element={<TelaConfig />} />
        </Route>

        {/* Rota de erro 404 */}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;