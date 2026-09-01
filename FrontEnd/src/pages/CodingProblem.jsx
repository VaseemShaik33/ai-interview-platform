import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { ArrowLeft, Play, Send, Code2 } from "lucide-react";
import CodingSidebar from "../components/CodingSidebar";
import { getCodingProblem, runCode, submitCode } from "../services/codingApi";

const badge = {
  EASY: "bg-emerald-50 text-emerald-700",
  MEDIUM: "bg-amber-50 text-amber-700",
  HARD: "bg-rose-50 text-rose-700",
};

export default function CodingProblem() {
  const { id } = useParams();
  const [problem, setProblem] = useState(null);
  const [code, setCode] = useState("");
  const [result, setResult] = useState(null);
  const [running, setRunning] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    getCodingProblem(id).then((res) => {
      setProblem(res.data);
      setCode(res.data.starterCode || "");
    });
  }, [id]);

  const execute = async (submit = false) => {
    setResult(null);
    submit ? setSubmitting(true) : setRunning(true);
    try {
      const res = submit
        ? await submitCode(id, code)
        : await runCode(id, code);
      setResult(res.data);
    } catch (err) {
      setResult({
        status: "RUNTIME_ERROR",
        passedTests: 0,
        totalTests: 0,
        message: err.response?.data?.message || "Request failed",
      });
    } finally {
      setRunning(false);
      setSubmitting(false);
    }
  };

  if (!problem) {
    return <div className="p-10 text-center text-slate-500">Loading problem...</div>;
  }

  return (
    <div className="flex min-h-screen bg-slate-50">
      <CodingSidebar />

      <main className="min-w-0 flex-1 p-4 lg:p-6">
        <div className="mx-auto max-w-[1500px]">
          <Link to="/coding" className="mb-4 inline-flex items-center gap-2 text-sm font-semibold text-slate-600 hover:text-violet-600">
            <ArrowLeft size={16} /> Back to problems
          </Link>

          <div className="grid min-h-[calc(100vh-100px)] gap-4 lg:grid-cols-[42%_58%]">
            <section className="overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm">
              <div className="border-b border-slate-100 p-6">
                <div className="flex items-center justify-between gap-3">
                  <h1 className="text-2xl font-bold text-slate-900">{problem.title}</h1>
                  <span className={`rounded-full px-3 py-1 text-xs font-bold ${badge[problem.difficulty]}`}>
                    {problem.difficulty}
                  </span>
                </div>
              </div>

              <div className="space-y-6 overflow-y-auto p-6">
                <div>
                  <h2 className="mb-2 font-bold text-slate-900">Problem</h2>
                  <p className="whitespace-pre-line leading-7 text-slate-600">{problem.description}</p>
                </div>

                <div>
                  <h2 className="mb-2 font-bold text-slate-900">Examples</h2>
                  <pre className="whitespace-pre-wrap rounded-xl bg-slate-950 p-4 text-sm text-slate-200">
                    {problem.examples}
                  </pre>
                </div>

                <div>
                  <h2 className="mb-2 font-bold text-slate-900">Constraints</h2>
                  <pre className="whitespace-pre-wrap rounded-xl bg-slate-50 p-4 text-sm text-slate-600">
                    {problem.constraints}
                  </pre>
                </div>
              </div>
            </section>

            <section className="flex min-h-[700px] flex-col overflow-hidden rounded-2xl border border-slate-200 bg-slate-950 shadow-sm">
              <div className="flex items-center justify-between border-b border-slate-800 px-4 py-3 text-white">
                <div className="flex items-center gap-2">
                  <Code2 size={18} className="text-violet-400" />
                  <span className="font-semibold">Solution</span>
                </div>
                <span className="rounded-lg border border-slate-700 px-3 py-1 text-xs text-slate-300">
                  Java
                </span>
              </div>

              <textarea
                value={code}
                onChange={(e) => setCode(e.target.value)}
                spellCheck={false}
                className="min-h-[500px] flex-1 resize-none bg-[#0f172a] p-5 font-mono text-sm leading-6 text-slate-100 outline-none"
              />

              {result && (
                <div className={`border-t p-4 ${
                  result.status === "ACCEPTED"
                    ? "border-emerald-900 bg-emerald-950/40"
                    : "border-rose-900 bg-rose-950/40"
                }`}>
                  <div className="flex items-center justify-between">
                    <strong className={result.status === "ACCEPTED" ? "text-emerald-400" : "text-rose-400"}>
                      {result.status}
                    </strong>
                    <span className="text-sm text-slate-300">
                      {result.passedTests}/{result.totalTests} tests passed
                    </span>
                  </div>
                  <pre className="mt-2 max-h-32 overflow-auto whitespace-pre-wrap text-xs text-slate-300">
                    {result.message}
                  </pre>
                </div>
              )}

              <div className="flex justify-end gap-3 border-t border-slate-800 p-4">
                <button
                  onClick={() => execute(false)}
                  disabled={running || submitting}
                  className="inline-flex items-center gap-2 rounded-xl border border-slate-700 px-5 py-2.5 text-sm font-semibold text-white hover:bg-slate-800 disabled:opacity-50"
                >
                  <Play size={16} /> {running ? "Running..." : "Run Code"}
                </button>

                <button
                  onClick={() => execute(true)}
                  disabled={running || submitting}
                  className="inline-flex items-center gap-2 rounded-xl bg-violet-600 px-5 py-2.5 text-sm font-semibold text-white hover:bg-violet-500 disabled:opacity-50"
                >
                  <Send size={16} /> {submitting ? "Submitting..." : "Submit Code"}
                </button>
              </div>
            </section>
          </div>
        </div>
      </main>
    </div>
  );
}
