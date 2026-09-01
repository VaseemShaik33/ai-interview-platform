import { useEffect, useMemo, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { Bot, Check, CircleHelp, Clock3, Send, Sparkles, X } from "lucide-react";
import api from "../services/api";
import ErrorMessage from "../components/ErrorMessage";
import Loading from "../components/Loading";

export default function Interview() {
  const { sessionId } = useParams();
  const location = useLocation();
  const navigate = useNavigate();
  const [question, setQuestion] = useState(location.state?.firstQuestion || null);
  const [questionNumber, setQuestionNumber] = useState(location.state?.questionNumber || 1);
  const [totalQuestions, setTotalQuestions] = useState(location.state?.totalQuestions || null);
  const [answer, setAnswer] = useState("");
  const [evaluation, setEvaluation] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(!question);
  const [submitting, setSubmitting] = useState(false);
  const [seconds, setSeconds] = useState(30 * 60);

  useEffect(() => { const id=setInterval(()=>setSeconds(s=>Math.max(0,s-1)),1000); return()=>clearInterval(id); },[]);
  useEffect(() => { if(question) return; api.get(`/interviews/${sessionId}/result`).finally(()=>setLoading(false)); },[sessionId,question]);

  const time = useMemo(()=>`${String(Math.floor(seconds/60)).padStart(2,"0")}:${String(seconds%60).padStart(2,"0")}`,[seconds]);
  const submit = async (e) => {
    e.preventDefault(); if(!answer.trim()){setError("Please enter an answer.");return;} setError("");setSubmitting(true);
    try { const res=await api.post(`/interviews/${sessionId}/answer`,{userAnswer:answer}); const d=res.data; setEvaluation(d.evaluation||null); setAnswer(""); setQuestionNumber(d.questionNumber); setTotalQuestions(d.totalQuestions); if(!d.question){navigate(`/interview/${sessionId}/result`);return;} setQuestion(d.question); }
    catch(err){setError(err.response?.data?.message||"Could not submit answer");} finally{setSubmitting(false);}
  };
  if(loading)return <Loading text="Loading interview..."/>;

  return <main className="min-h-screen bg-slate-50 px-4 py-4 sm:px-6">
    <div className="mx-auto max-w-[1380px]">
      <header className="flex flex-wrap items-center justify-between gap-3 rounded-2xl border border-slate-200 bg-white px-5 py-3 shadow-sm"><div><p className="text-sm font-bold text-slate-900">{question?.categoryInformation || "Technical"} Interview</p><p className="text-xs text-slate-400">AI-powered interview practice</p></div><div className="flex items-center gap-4"><span className="text-sm font-semibold text-slate-600">Question {questionNumber} / {totalQuestions || "-"}</span><span className="flex items-center gap-1.5 rounded-lg bg-slate-100 px-3 py-2 text-sm font-semibold text-slate-700"><Clock3 size={15}/>{time}</span><button onClick={()=>navigate(`/interview/${sessionId}/result`)} className="rounded-lg border border-red-200 px-3 py-2 text-xs font-semibold text-red-600 hover:bg-red-50">End Interview</button></div></header>
      {error&&<div className="mt-4"><ErrorMessage message={error}/></div>}
      <div className="mt-5 grid gap-5 lg:grid-cols-[1fr_320px]">
        <section className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm sm:p-8"><div className="flex items-center justify-between"><div className="flex items-center gap-2 text-xs font-semibold text-slate-400"><CircleHelp size={15}/> Interview question</div><span className="rounded-full bg-amber-50 px-3 py-1 text-xs font-semibold text-amber-700">{question?.difficulty || "MEDIUM"}</span></div>
          <h1 className="mt-5 text-2xl font-bold leading-9 text-slate-900">{question?.questionText}</h1><p className="mt-2 text-sm text-slate-500">Explain the concept clearly and include an example where relevant.</p>
          <form onSubmit={submit} className="mt-7"><textarea rows="11" value={answer} onChange={e=>setAnswer(e.target.value)} placeholder="Type your answer here..." className="w-full resize-none rounded-2xl border border-slate-200 bg-white p-5 text-slate-800 outline-none transition focus:border-violet-500 focus:ring-4 focus:ring-violet-50"/>
          <div className="mt-4 flex flex-wrap justify-between gap-3"><button type="button" onClick={()=>setAnswer("")} className="rounded-xl border border-slate-200 px-5 py-3 text-sm font-semibold text-slate-600 hover:bg-slate-50"><X size={16} className="mr-1 inline"/>Clear</button><button disabled={submitting} className="flex items-center gap-2 rounded-xl bg-violet-600 px-7 py-3 text-sm font-bold text-white shadow-lg shadow-violet-600/20 hover:bg-violet-700 disabled:opacity-50">{submitting?<><Sparkles size={16}/> Evaluating...</>:<><Send size={16}/> Submit Answer</>}</button></div></form>
        </section>
        <aside className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm"><div className="flex items-center gap-3"><span className="flex h-11 w-11 items-center justify-center rounded-xl bg-violet-100 text-violet-700"><Bot size={24}/></span><div><h2 className="font-bold text-slate-900">AI Assistant</h2><p className="text-xs text-slate-400">Answer evaluation</p></div></div>
          <p className="mt-5 text-sm leading-6 text-slate-500">Your answer is evaluated using four interview dimensions.</p><Criteria label="Relevance"/><Criteria label="Correctness"/><Criteria label="Depth"/><Criteria label="Clarity"/>
          {evaluation&&<div className="mt-5 rounded-xl bg-violet-50 p-4"><div className="flex items-center justify-between"><span className="text-xs font-semibold text-violet-700">Latest feedback</span><span className="font-bold text-violet-700">{evaluation.score}/10</span></div><p className="mt-2 text-xs leading-5 text-slate-600">{evaluation.feedback}</p></div>}
        </aside>
      </div>
    </div>
  </main>
}
function Criteria({label}){return <div className="mt-4 flex items-center justify-between border-b border-slate-100 pb-3 text-sm"><span className="text-slate-600">{label}</span><span className="flex h-5 w-5 items-center justify-center rounded-full bg-emerald-50 text-emerald-600"><Check size={13}/></span></div>}
