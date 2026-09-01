import { Code2, LayoutDashboard, History, LogOut, BrainCircuit } from "lucide-react";
import { NavLink } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function CodingSidebar() {
  const { logout } = useAuth();

  const linkClass = ({ isActive }) =>
    `flex items-center gap-3 rounded-xl px-4 py-3 text-sm font-medium transition ${
      isActive
        ? "bg-violet-600 text-white shadow-sm"
        : "text-slate-600 hover:bg-slate-100"
    }`;

  return (
    <aside className="hidden min-h-screen w-64 shrink-0 flex-col bg-slate-950 p-5 text-white lg:flex">
      <div className="mb-8 flex items-center gap-3">
        <div className="flex h-10 w-10 items-center justify-center rounded-xl bg-violet-600">
          <BrainCircuit size={22} />
        </div>
        <div>
          <p className="font-bold">AI Interview</p>
          <p className="text-xs text-slate-400">Coding Practice</p>
        </div>
      </div>

      <nav className="space-y-2">
        <NavLink to="/dashboard" className={linkClass}>
          <LayoutDashboard size={18} /> Dashboard
        </NavLink>
        <NavLink to="/coding" end className={linkClass}>
          <Code2 size={18} /> Problems
        </NavLink>
        <NavLink to="/coding/submissions" className={linkClass}>
          <History size={18} /> Submissions
        </NavLink>
      </nav>

      <button
        onClick={logout}
        className="mt-auto flex items-center gap-3 rounded-xl px-4 py-3 text-sm text-slate-400 hover:bg-slate-900 hover:text-white"
      >
        <LogOut size={18} /> Logout
      </button>
    </aside>
  );
}
