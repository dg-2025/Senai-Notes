import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import './styles/global.css'

import Login from './pages/Login'
import Signup from './pages/Signup'
import ForgotPassword from './pages/ForgotPassword'
import ResetPassword from './pages/ResetPassword'
import TelaNotas from './pages/TelaNotas'
import TelaConfig from './pages/TelaConfig'
import NotFound from './pages/NotFound'
import RotaProtegida from './componentes/RotaProtegida'

function App() {

  // sempre limpa tudo ao abrir o site
  localStorage.removeItem("token");
  localStorage.removeItem("usuarioId");
  localStorage.removeItem("usuarioNome");
  localStorage.removeItem("usuarioEmail");

  return (
    <BrowserRouter>
      <Routes>

        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/reset-password" element={<ResetPassword />} />

        <Route path="/" element={<Navigate to="/login" />} />

        <Route element={<RotaProtegida />}>
          <Route path="/dashboard" element={<TelaNotas />} />
          <Route path="/settings" element={<TelaConfig />} />
        </Route>

        <Route path="*" element={<NotFound />} />

      </Routes>
    </BrowserRouter>
  )
}

export default App
