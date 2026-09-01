import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";
import ErrorMessage from "../components/ErrorMessage";
import Loading from "../components/Loading";
import AppLayout from "../components/AppLayout";

export default function StartInterview() {
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);
  const [categoryId, setCategoryId] = useState("");
  const [difficulty, setDifficulty] = useState("EASY");
  const [numberOfQuestions, setNumberOfQuestions] = useState(10);
  const [loading, setLoading] = useState(true);
  const [starting, setStarting] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    api.get("/categories")
      .then((res) => {
        const data = Array.isArray(res.data) ? res.data : [];
        setCategories(data);
        if (data.length) setCategoryId(data[0].id);
      })
      .catch((err) => setError(err.response?.data?.message || "Could not load categories"))
      .finally(() => setLoading(false));
  }, []);

  const start = async (e) => {
    e.preventDefault();
    setError("");
    setStarting(true);

    try {
      const res = await api.post("/interviews/start", {
        categoryId: Number(categoryId),
        difficulty,
        numberOfQuestions: Number(numberOfQuestions)
      });

      navigate(`/interview/${res.data.sessionId}`, {
        state: { firstQuestion: res.data.question, questionNumber: res.data.questionNumber, totalQuestions: res.data.totalQuestions }
      });
    } catch (err) {
      setError(err.response?.data?.message || "Could not start interview");
    } finally {
      setStarting(false);
    }
  };

  if (loading) return <Loading text="Loading categories..." />;

  return (
    <AppLayout>
    <main className="mx-auto max-w-2xl px-6 py-10">
      <div className="rounded-3xl border border-slate-200 bg-white p-8 shadow-sm">
        <h1 className="text-3xl font-bold text-slate-900">Start New Interview</h1>
        <p className="mt-2 text-slate-500">Customize your interview experience.</p>

        <ErrorMessage message={error} />

        <form onSubmit={start} className="mt-8 space-y-6">
          <div>
            <label className="mb-2 block text-sm font-medium text-slate-700">Role / Category</label>
            <select value={categoryId} onChange={(e) => setCategoryId(e.target.value)} required className="w-full rounded-xl border border-slate-300 bg-white px-4 py-3 text-slate-900 outline-none focus:border-violet-500 focus:ring-1 focus:ring-violet-500">
              {categories.map((c) => (
                <option key={c.id} value={c.id}>{c.name}</option>
              ))}
            </select>
          </div>

          <div>
            <label className="mb-2 block text-sm font-medium text-slate-700">Difficulty</label>
            <select value={difficulty} onChange={(e) => setDifficulty(e.target.value)} className="w-full rounded-xl border border-slate-300 bg-white px-4 py-3 text-slate-900 outline-none focus:border-violet-500 focus:ring-1 focus:ring-violet-500">
              <option value="EASY">Easy</option>
              <option value="MEDIUM">Medium</option>
              <option value="HARD">Hard</option>
            </select>
          </div>

          <div>
            <label className="mb-2 block text-sm font-medium text-slate-700">Number of questions</label>
            <select value={numberOfQuestions} onChange={(e) => setNumberOfQuestions(e.target.value)} className="w-full rounded-xl border border-slate-300 bg-white px-4 py-3 text-slate-900 outline-none focus:border-violet-500 focus:ring-1 focus:ring-violet-500">
              <option value="5">5</option>
              <option value="10">10</option>
              <option value="15">15</option>
              <option value="20">20</option>
            </select>
          </div>

          <button disabled={starting || !categoryId} className="w-full rounded-xl bg-violet-600 py-3 font-semibold text-white hover:bg-violet-700 disabled:opacity-50">
            {starting ? "Starting..." : "Start Interview"}
          </button>
        </form>
      </div>
    </main>
    </AppLayout>
  );
}
