import { Navigate, Route, Routes } from "react-router-dom";
import { useAuth } from "./context/AuthContext";
import ProtectedRoute from "./components/ProtectedRoute";
import Landing from "./pages/Landing";
import HowItWorks from "./pages/HowItWorks";
import Login from "./pages/Login";
import Register from "./pages/Register";
import ForgotPassword from "./pages/ForgotPassword";
import ResetPassword from "./pages/ResetPassword";
import Dashboard from "./pages/Dashboard";
import StartInterview from "./pages/StartInterview";
import Interview from "./pages/Interview";
import Result from "./pages/Result";
import History from "./pages/History";
import AdminDashboard from "./pages/AdminDashboard";
import WorkspacePlaceholder from "./pages/WorkspacePlaceholder";
import CodingPractice from "./pages/CodingPractice";
import CodingProblem from "./pages/CodingProblem";
import CodingSubmissions from "./pages/CodingSubmissions";

function AdminRoute() {
  const { user } = useAuth();
  return user?.role === "ADMIN" ? <AdminDashboard /> : <Navigate to="/dashboard" replace />;
}

function HomeRedirect() {
  const { user } = useAuth();
  if (!user) return <Landing />;
  return <Navigate to={user.role === "ADMIN" ? "/admin" : "/dashboard"} replace />;
}

export default function App() {
  const { user } = useAuth();
  return <Routes>
    <Route path="/" element={<HomeRedirect />} />
    <Route path="/how-it-works" element={<HowItWorks />} />
    <Route path="/login" element={user ? <Navigate to={user.role === "ADMIN" ? "/admin" : "/dashboard"} replace /> : <Login />} />
    <Route path="/register" element={user ? <Navigate to="/dashboard" replace /> : <Register />} />
    <Route path="/forgot-password" element={user ? <Navigate to="/dashboard" replace /> : <ForgotPassword />} />
    <Route path="/reset-password" element={user ? <Navigate to="/dashboard" replace /> : <ResetPassword />} />

    <Route element={<ProtectedRoute />}>
      <Route path="/dashboard" element={<Dashboard />} />

      <Route path="/interview/start" element={<StartInterview />} />
      <Route path="/interview/:sessionId" element={<Interview />} />
      <Route path="/interview/:sessionId/result" element={<Result />} />

      <Route path="/history" element={<History />} />

      {/* Coding Platform */}
      <Route path="/coding" element={<CodingPractice />} />
      <Route path="/coding/:id" element={<CodingProblem />} />
      <Route path="/coding/submissions" element={<CodingSubmissions />} />

      <Route path="/bookmarks" element={<WorkspacePlaceholder />} />
      <Route path="/profile" element={<WorkspacePlaceholder />} />
      <Route path="/settings" element={<WorkspacePlaceholder />} />

      <Route path="/admin" element={<AdminRoute />} />
      <Route path="/admin/candidates" element={<AdminRoute />} />
      <Route path="/admin/interviews" element={<AdminRoute />} />
      <Route path="/admin/questions" element={<AdminRoute />} />
      <Route path="/admin/analytics" element={<AdminRoute />} />
      <Route path="/admin/reports" element={<AdminRoute />} />
    </Route>
    <Route path="*" element={<Navigate to="/" replace />} />
  </Routes>;
}
