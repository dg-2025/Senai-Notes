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
  return (
    <BrowserRouter>
      <Routes>
        {/* Rotas públicas acessíveis sem login */}
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/reset-password" element={<ResetPassword />} />

        {/* Redireciona a rota raiz para o dashboard */}
        <Route path="/" element={<Navigate to="/login" />} />

        {/* Rotas protegidas que só funcionam com token válido */}
        <Route element={<RotaProtegida />}>
          <Route path="/dashboard" element={<TelaNotas />} />
          <Route path="/settings" element={<TelaConfig />} />
        </Route>

        {/* Rota de erro para caminhos inexistentes */}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
