import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { ArrowRight, BarChart3, BookOpen, Clock3, History, Play, Sparkles, Target, Trophy } from "lucide-react";
import { useAuth } from "../context/AuthContext";
import api from "../services/api";
import Loading from "../components/Loading";
import ErrorMessage from "../components/ErrorMessage";
import AppLayout from "../components/AppLayout";

export default function Dashboard() {
  const { user } = useAuth();
  const [data, setData] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    api.get("/dashboard/candidate")
      .then((res) => setData(res.data))
      .catch((err) => setError(err.response?.data?.message || "Could not load dashboard"));
  }, []);

  if (!data && !error) return <Loading text="Loading your dashboard..." />;

  return (
    <AppLayout>
      <main className="mx-auto max-w-[1400px] px-5 py-7 sm:px-8">
        {error && <ErrorMessage message={error} />}

        <section className="flex flex-wrap items-center justify-between gap-5">
          <div>
            <p className="text-sm font-medium text-violet-600">Candidate Dashboard</p>
            <h1 className="mt-1 text-3xl font-bold tracking-tight text-slate-900">Welcome back, {user?.name || "Vaseem"} 👋</h1>
            <p className="mt-2 text-slate-500">Ready to take your skills to the next level?</p>
          </div>
          <Link to="/interview/start" className="flex items-center gap-2 rounded-xl bg-violet-600 px-5 py-3 font-semibold text-white shadow-lg shadow-violet-600/20 hover:bg-violet-700">
            <Play size={18}/> Start New Interview
          </Link>
        </section>

        <section className="mt-7 grid gap-4 sm:grid-cols-2 xl:grid-cols-4">
          <StatCard icon={History} label="Total Interviews" value={data?.totalInterviews ?? 0}/>
          <StatCard icon={BarChart3} label="Average Score" value={`${Math.round(data?.averageScore ?? 0)}%`}/>
          <StatCard icon={Trophy} label="Best Score" value={`${Math.round(data?.bestScore ?? 0)}%`}/>
          <StatCard icon={Clock3} label="Total Time" value={formatMinutes(data?.totalMinutes ?? 0)}/>
        </section>

        <section className="mt-7 grid gap-6 xl:grid-cols-[1fr_340px]">
          <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
            <div className="flex items-center justify-between">
              <div><h2 className="text-lg font-bold text-slate-900">Recent Interviews</h2><p className="mt-1 text-sm text-slate-500">Your latest interview attempts and scores.</p></div>
              <Link to="/history" className="text-sm font-semibold text-violet-600">View All</Link>
            </div>
            <div className="mt-5 overflow-x-auto">
              <table className="w-full min-w-[650px] text-left text-sm">
                <thead><tr className="border-b border-slate-100 text-xs uppercase tracking-wide text-slate-400"><th className="px-3 py-3">Role</th><th>Difficulty</th><th>Score</th><th>Status</th><th>Date</th><th/></tr></thead>
                <tbody>{(data?.recentInterviews || []).map((item) => <tr key={item.sessionId} className="border-b border-slate-50 last:border-0"><td className="px-3 py-4 font-semibold text-slate-800">{item.role}</td><td><Badge text={item.difficulty}/></td><td className="font-bold text-slate-800">{Math.round(item.percentage)}%</td><td><span className={`rounded-full px-2.5 py-1 text-xs font-medium ${item.status === "COMPLETED" ? "bg-emerald-50 text-emerald-700" : "bg-amber-50 text-amber-700"}`}>{item.status === "COMPLETED" ? "Completed" : "In Progress"}</span></td><td className="text-slate-500">{formatDate(item.startedAt)}</td><td><Link className="font-semibold text-violet-600" to={`/interview/${item.sessionId}/result`}>View</Link></td></tr>)}</tbody>
              </table>
              {!data?.recentInterviews?.length && <EmptyState/>}
            </div>
          </div>

          <div className="rounded-2xl border border-violet-100 bg-gradient-to-br from-violet-600 to-indigo-600 p-6 text-white shadow-lg shadow-violet-600/15">
            <span className="flex h-11 w-11 items-center justify-center rounded-xl bg-white/15"><Sparkles size={22}/></span>
            <h2 className="mt-5 text-xl font-bold">Improve with AI feedback</h2>
            <p className="mt-2 text-sm leading-6 text-violet-100">Practice realistic questions and get instant evaluation on correctness, depth, relevance and clarity.</p>
            <Link to="/interview/start" className="mt-6 inline-flex items-center gap-2 rounded-xl bg-white px-4 py-2.5 text-sm font-bold text-violet-700">Practice now <ArrowRight size={16}/></Link>
          </div>
        </section>

        <section className="mt-7 grid gap-4 md:grid-cols-3">
          <QuickCard icon={BookOpen} title="Mock Interviews" text="Choose a role, difficulty and question count." href="/interview/start"/>
          <QuickCard icon={Target} title="Performance" text="Review scores and identify areas to improve." href="/history"/>
          <QuickCard icon={Trophy} title="Keep improving" text="Complete interviews consistently to build confidence." href="/interview/start"/>
        </section>
      </main>
    </AppLayout>
  );
}

function StatCard({icon: Icon,label,value}) { return <div className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm"><div className="flex items-center justify-between"><span className="flex h-10 w-10 items-center justify-center rounded-xl bg-violet-50 text-violet-600"><Icon size={19}/></span><span className="text-xs font-medium text-slate-400">Updated now</span></div><p className="mt-5 text-sm text-slate-500">{label}</p><p className="mt-1 text-3xl font-bold text-slate-900">{value}</p></div> }
function QuickCard({icon:Icon,title,text,href}) { return <Link to={href} className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm transition hover:-translate-y-0.5 hover:border-violet-200 hover:shadow-md"><span className="flex h-10 w-10 items-center justify-center rounded-xl bg-violet-50 text-violet-600"><Icon size={19}/></span><h3 className="mt-4 font-bold text-slate-900">{title}</h3><p className="mt-1 text-sm leading-6 text-slate-500">{text}</p></Link> }
function Badge({text}) { const c=text === "HARD" ? "bg-red-50 text-red-600" : text === "MEDIUM" ? "bg-amber-50 text-amber-700" : "bg-emerald-50 text-emerald-700"; return <span className={`rounded-full px-2.5 py-1 text-xs font-semibold ${c}`}>{text}</span> }
function EmptyState(){return <div className="py-12 text-center text-sm text-slate-400">No interviews yet. Start your first interview.</div>}
function formatDate(v){return v ? new Date(v).toLocaleDateString(undefined,{day:"2-digit",month:"short",year:"numeric"}) : "-"}
function formatMinutes(m){if(!m)return "0m"; const h=Math.floor(m/60),min=m%60; return h?`${h}h ${min}m`:`${min}m`}
