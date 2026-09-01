import { Link, useNavigate } from "react-router-dom";
import { BrainCircuit, History, Home, LogOut, Play } from "lucide-react";
import { useAuth } from "../context/AuthContext";

export default function Navbar() {
  const { logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <nav className="sticky top-0 z-50 border-b border-slate-200 bg-white/90 backdrop-blur">
      <div className="mx-auto flex max-w-7xl items-center justify-between px-6 py-4">
        <Link to="/dashboard" className="flex items-center gap-2 text-xl font-bold text-slate-900">
          <span className="flex h-9 w-9 items-center justify-center rounded-xl bg-violet-600 text-white">
            <BrainCircuit size={20} />
          </span>
          AI Interview
        </Link>

        <div className="flex items-center gap-2">
          <Link className="hidden items-center gap-2 rounded-lg px-3 py-2 text-sm font-medium text-slate-600 hover:bg-slate-100 hover:text-slate-900 sm:flex" to="/dashboard">
            <Home size={16} /> Dashboard
          </Link>
          <Link className="hidden items-center gap-2 rounded-lg px-3 py-2 text-sm font-medium text-slate-600 hover:bg-slate-100 hover:text-slate-900 sm:flex" to="/history">
            <History size={16} /> History
          </Link>
          <Link className="flex items-center gap-2 rounded-lg bg-violet-600 px-3 py-2 text-sm font-semibold text-white hover:bg-violet-700" to="/interview/start">
            <Play size={16} /> Start
          </Link>
          <button onClick={handleLogout} className="rounded-lg p-2 text-slate-500 hover:bg-red-50 hover:text-red-600" title="Logout">
            <LogOut size={18} />
          </button>
        </div>
      </div>
    </nav>
  );
}
