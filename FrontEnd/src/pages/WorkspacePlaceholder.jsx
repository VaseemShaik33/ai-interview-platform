import { Link, useLocation } from "react-router-dom";
import { ArrowLeft, Sparkles } from "lucide-react";
import AppLayout from "../components/AppLayout";

export default function WorkspacePlaceholder(){
 const {pathname}=useLocation(); const label=pathname.split("/").filter(Boolean).pop()?.replace(/-/g," ")||"workspace";
 return <AppLayout><main className="mx-auto max-w-4xl px-6 py-12"><div className="rounded-3xl border border-slate-200 bg-white p-10 text-center shadow-sm"><span className="mx-auto flex h-14 w-14 items-center justify-center rounded-2xl bg-violet-100 text-violet-600"><Sparkles/></span><h1 className="mt-5 text-3xl font-bold capitalize text-slate-900">{label}</h1><p className="mx-auto mt-3 max-w-xl text-slate-500">This workspace is ready for the next feature module. Your interview history, dashboard and AI interview flow are already connected.</p><Link to="/dashboard" className="mt-7 inline-flex items-center gap-2 rounded-xl bg-violet-600 px-5 py-3 font-semibold text-white"><ArrowLeft size={17}/> Back to Dashboard</Link></div></main></AppLayout>
}
