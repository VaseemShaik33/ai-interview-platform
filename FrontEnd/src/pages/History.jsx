import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { History as HistoryIcon, Play } from "lucide-react";
import api from "../services/api";
import Loading from "../components/Loading";
import ErrorMessage from "../components/ErrorMessage";
import AppLayout from "../components/AppLayout";

export default function History() {
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    api.get("/interviews/history")
      .then((res) => setHistory(Array.isArray(res.data) ? res.data : []))
      .catch((err) => setError(err.response?.data?.message || "Could not load interview history"))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <Loading text="Loading interview history..." />;

  return (
    <AppLayout>
    <main className="mx-auto max-w-7xl px-6 py-10">
      <div className="flex flex-wrap items-center justify-between gap-4">
        <div>
          <h1 className="text-3xl font-bold text-slate-900">Interview History</h1>
          <p className="mt-2 text-slate-500">Review your previous interview sessions.</p>
        </div>
        <Link to="/interview/start" className="flex items-center gap-2 rounded-xl bg-violet-600 px-4 py-3 font-semibold text-white hover:bg-violet-700">
          <Play size={18} /> New Interview
        </Link>
      </div>

      <div className="mt-8">
        <ErrorMessage message={error} />

        {history.length === 0 ? (
          <div className="rounded-2xl border border-dashed border-slate-300 bg-white p-12 text-center">
            <HistoryIcon className="mx-auto text-slate-400" size={42} />
            <h2 className="mt-4 text-lg font-semibold text-slate-900">No interviews yet</h2>
            <p className="mt-2 text-sm text-slate-500">Start your first interview to see it here.</p>
          </div>
        ) : (
          <div className="grid gap-4">
            {history.map((item) => (
              <div key={item.sessionId} className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
                <div className="flex flex-wrap items-start justify-between gap-4">
                  <div>
                    <h2 className="text-xl font-semibold text-slate-900">{item.category}</h2>
                    <p className="mt-1 text-sm text-slate-500">
                      {item.difficulty} · {item.totalQuestions} questions · {item.status}
                    </p>
                  </div>
                  <div className="text-right">
                    <p className="text-2xl font-bold text-violet-600">
                      {Number(item.percentage).toFixed(1)}%
                    </p>
                    <p className="text-sm text-slate-400">
                      {item.totalScore}/{item.maximumScore}
                    </p>
                  </div>
                </div>

                <div className="mt-5 grid gap-3 text-sm text-slate-600 sm:grid-cols-3">
                  <div className="rounded-xl bg-slate-50 p-3">
                    Answered: <b className="text-slate-900">{item.answeredQuestions}</b>
                  </div>
                  <div className="rounded-xl bg-slate-50 p-3">
                    Started: <b className="text-slate-900">{formatDate(item.startedAt)}</b>
                  </div>
                  <div className="rounded-xl bg-slate-50 p-3">
                    Completed: <b className="text-slate-900">{item.completedAt ? formatDate(item.completedAt) : "Not completed"}</b>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </main>
    </AppLayout>
  );
}

function formatDate(value) {
  if (!value) return "-";
  return new Date(value).toLocaleString();
}
