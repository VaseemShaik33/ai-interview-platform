import { Link } from "react-router-dom";
import { BrainCircuit, Menu } from "lucide-react";
import { useState } from "react";

export default function SiteHeader() {
  const [menuOpen, setMenuOpen] = useState(false);

  const links = [
    { label: "Features", to: "/#features" },
    { label: "How it Works", to: "/how-it-works" }
  ];

  return (
    <header className="sticky top-0 z-50 border-b border-slate-200 bg-white/90 backdrop-blur">
      <div className="mx-auto flex max-w-7xl items-center justify-between px-6 py-4">
        <Link to="/" className="flex items-center gap-2 text-lg font-bold text-slate-900">
          <span className="flex h-9 w-9 items-center justify-center rounded-xl bg-violet-600 text-white">
            <BrainCircuit size={20} />
          </span>
          AI Interview
        </Link>

        <nav className="hidden items-center gap-8 md:flex">
          {links.map(({ label, to }) => (
            <Link key={label} to={to} className="text-sm font-medium text-slate-600 hover:text-slate-900">
              {label}
            </Link>
          ))}
        </nav>

        <div className="hidden items-center gap-3 md:flex">
          <Link to="/login" className="text-sm font-medium text-slate-600 hover:text-slate-900">
            Login
          </Link>
          <Link to="/register" className="rounded-lg bg-violet-600 px-4 py-2 text-sm font-semibold text-white hover:bg-violet-700">
            Get Started
          </Link>
        </div>

        <button className="p-2 text-slate-600 md:hidden" onClick={() => setMenuOpen((v) => !v)} aria-label="Menu">
          <Menu size={22} />
        </button>
      </div>

      {menuOpen && (
        <div className="border-t border-slate-200 bg-white px-6 py-4 md:hidden">
          <div className="flex flex-col gap-3">
            {links.map(({ label, to }) => (
              <Link key={label} to={to} className="text-sm font-medium text-slate-600" onClick={() => setMenuOpen(false)}>
                {label}
              </Link>
            ))}
            <Link to="/login" className="text-sm font-medium text-slate-600">Login</Link>
            <Link to="/register" className="rounded-lg bg-violet-600 px-4 py-2 text-center text-sm font-semibold text-white">
              Get Started
            </Link>
          </div>
        </div>
      )}
    </header>
  );
}
