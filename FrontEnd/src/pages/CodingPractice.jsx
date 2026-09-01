import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Code2, Search, ArrowRight } from "lucide-react";
import CodingSidebar from "../components/CodingSidebar";
import { getCodingProblems } from "../services/codingApi";

const difficultyStyle = {
  EASY: "bg-emerald-50 text-emerald-700",
  MEDIUM: "bg-amber-50 text-amber-700",
  HARD: "bg-rose-50 text-rose-700",
};

export default function CodingPractice() {
  const [problems, setProblems] = useState([]);
  const [search, setSearch] = useState("");
  const [difficulty, setDifficulty] = useState("ALL");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getCodingProblems()
      .then((res) => setProblems(res.data))
      .finally(() => setLoading(false));
  }, []);

  const filtered = problems.filter((p) => {
    const textMatch = p.title.toLowerCase().includes(search.toLowerCase());
    const difficultyMatch = difficulty === "ALL" || p.difficulty === difficulty;
    return textMatch && difficultyMatch;
  });

  return (
    <div className="flex min-h-screen bg-slate-50">
      <CodingSidebar />

      <main className="min-w-0 flex-1 p-6 lg:p-10">
        <div className="mx-auto max-w-6xl">
          <div className="mb-8 flex flex-col justify-between gap-4 md:flex-row md:items-end">
            <div>
              <div className="mb-3 inline-flex items-center gap-2 rounded-full bg-violet-50 px-4 py-2 text-sm font-semibold text-violet-700">
                <Code2 size={16} /> Coding Practice
              </div>
              <h1 className="text-3xl font-bold text-slate-900">Practice Coding Problems</h1>
              <p className="mt-2 text-slate-500">
                Improve your problem-solving skills before your next interview.
              </p>
            </div>

            <Link
              to="/coding/submissions"
              className="rounded-xl border border-slate-200 bg-white px-5 py-3 text-sm font-semibold text-slate-700 hover:bg-slate-50"
            >
              View submissions
            </Link>
          </div>

          <div className="mb-6 flex flex-col gap-3 md:flex-row">
            <div className="relative flex-1">
              <Search className="absolute left-4 top-3.5 text-slate-400" size={18} />
              <input
                value={search}
                onChange={(e) => setSearch(e.target.value)}
                placeholder="Search problems..."
                className="w-full rounded-xl border border-slate-200 bg-white py-3 pl-11 pr-4 outline-none focus:border-violet-500"
              />
            </div>

            <select
              value={difficulty}
              onChange={(e) => setDifficulty(e.target.value)}
              className="rounded-xl border border-slate-200 bg-white px-4 py-3 outline-none focus:border-violet-500"
            >
              <option value="ALL">All difficulties</option>
              <option value="EASY">Easy</option>
              <option value="MEDIUM">Medium</option>
              <option value="HARD">Hard</option>
            </select>
          </div>

          <div className="overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm">
            <div className="grid grid-cols-[1fr_140px_100px] border-b border-slate-100 bg-slate-50 px-6 py-4 text-xs font-semibold uppercase tracking-wide text-slate-500">
              <span>Problem</span>
              <span>Difficulty</span>
              <span />
            </div>

            {loading ? (
              <div className="p-8 text-center text-slate-500">Loading problems...</div>
            ) : filtered.length === 0 ? (
              <div className="p-8 text-center text-slate-500">No problems found.</div>
            ) : (
              filtered.map((problem) => (
                <Link
                  key={problem.id}
                  to={`/coding/${problem.id}`}
                  className="grid grid-cols-[1fr_140px_100px] items-center border-b border-slate-100 px-6 py-5 last:border-0 hover:bg-slate-50"
                >
                  <div className="flex items-center gap-3">
                    <div className="flex h-9 w-9 items-center justify-center rounded-lg bg-violet-50 text-violet-600">
                      <Code2 size={18} />
                    </div>
                    <span className="font-semibold text-slate-800">{problem.title}</span>
                  </div>
                  <span className={`w-fit rounded-full px-3 py-1 text-xs font-semibold ${difficultyStyle[problem.difficulty]}`}>
                    {problem.difficulty}
                  </span>
                  <span className="flex items-center gap-1 text-sm font-semibold text-violet-600">
                    Solve <ArrowRight size={15} />
                  </span>
                </Link>
              ))
            )}
          </div>
        </div>
      </main>
    </div>
  );
}
