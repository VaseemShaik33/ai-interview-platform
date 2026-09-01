import AppSidebar from "./AppSidebar";
import { Link } from "react-router-dom";
import { BrainCircuit, Play } from "lucide-react";

export default function AppLayout({ children, admin = false }) {
  return (
    <div className="min-h-screen bg-slate-50">
      <div className="flex min-h-screen">
        <AppSidebar admin={admin} />
        <div className="min-w-0 flex-1">
          <header className="sticky top-0 z-40 flex items-center justify-between border-b border-slate-200 bg-white/90 px-5 py-3 backdrop-blur lg:hidden">
            <Link to={admin ? "/admin" : "/dashboard"} className="flex items-center gap-2 font-bold"><span className="flex h-8 w-8 items-center justify-center rounded-lg bg-violet-600 text-white"><BrainCircuit size={18}/></span>AI Interview</Link>
            {!admin && <Link to="/interview/start" className="rounded-lg bg-violet-600 px-3 py-2 text-sm font-semibold text-white"><Play size={15} className="inline mr-1"/>Start</Link>}
          </header>
          {children}
        </div>
      </div>
    </div>
  );
}
