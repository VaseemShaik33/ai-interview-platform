import { Link, useLocation, useNavigate } from "react-router-dom";
import { BarChart3, BookOpen, BrainCircuit, History, LayoutDashboard, LogOut, Settings, UserCircle, Code2, Users, FileText, ShieldCheck } from "lucide-react";
import { useAuth } from "../context/AuthContext";

export default function AppSidebar({ admin = false }) {
  const { user, logout } = useAuth();
  const location = useLocation();
  const navigate = useNavigate();

  const candidateLinks = [
    ["Dashboard", "/dashboard", LayoutDashboard],
    ["Interviews", "/history", History],
    ["Coding Practice", "/coding", Code2],
    ["Mock Interviews", "/interview/start", BookOpen],
    ["Performance", "/history", BarChart3],
    ["Bookmarks", "/bookmarks", BookOpen],
    ["Profile", "/profile", UserCircle],
    ["Settings", "/settings", Settings]
  ];

  const adminLinks = [
    ["Overview", "/admin", LayoutDashboard],
    ["Candidates", "/admin/candidates", Users],
    ["Interviews", "/admin/interviews", History],
    ["Questions", "/admin/questions", BookOpen],
    ["Analytics", "/admin/analytics", BarChart3],
    ["Reports", "/admin/reports", FileText],
    ["Settings", "/settings", Settings]
  ];

  const links = admin ? adminLinks : candidateLinks;

  const signOut = async () => {
    await logout();
    navigate("/login");
  };

  return (
    <aside className="hidden min-h-screen w-64 shrink-0 flex-col bg-[#111a2d] text-white lg:flex">
      <Link to={admin ? "/admin" : "/dashboard"} className="flex items-center gap-3 px-6 py-6 text-xl font-bold">
        <span className="flex h-9 w-9 items-center justify-center rounded-xl bg-violet-600"><BrainCircuit size={20} /></span>
        AI Interview
      </Link>

      <div className="px-4 py-2 text-[11px] font-semibold uppercase tracking-[0.18em] text-slate-500">
        {admin ? "Admin workspace" : "Candidate workspace"}
      </div>

      <nav className="mt-2 flex-1 space-y-1 px-3">
        {links.map(([label, href, Icon]) => {
          const active = location.pathname === href || (href !== "/dashboard" && href !== "/admin" && location.pathname.startsWith(href));
          return (
            <Link key={label} to={href} className={`flex items-center gap-3 rounded-xl px-3 py-2.5 text-sm font-medium transition ${active ? "bg-violet-600 text-white shadow-lg shadow-violet-900/20" : "text-slate-300 hover:bg-white/10 hover:text-white"}`}>
              <Icon size={17} /> {label}
            </Link>
          );
        })}
      </nav>

      <div className="border-t border-white/10 p-4">
        <div className="mb-3 flex items-center gap-3 rounded-xl bg-white/5 p-3">
          <span className="flex h-9 w-9 items-center justify-center rounded-full bg-violet-500/20 text-violet-200"><UserCircle size={21} /></span>
          <div className="min-w-0">
            <p className="truncate text-sm font-semibold">{user?.name || user?.email || "User"}</p>
            <p className="text-xs text-slate-400">{admin ? "Administrator" : "Candidate"}</p>
          </div>
        </div>
        <button onClick={signOut} className="flex w-full items-center gap-3 rounded-xl px-3 py-2 text-sm font-medium text-slate-300 hover:bg-red-500/10 hover:text-red-300">
          <LogOut size={17} /> Logout
        </button>
      </div>
    </aside>
  );
}
