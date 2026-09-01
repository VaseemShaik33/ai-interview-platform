import { useEffect, useState } from "react";
import CodingSidebar from "../components/CodingSidebar";
import { getSubmissions } from "../services/codingApi";

export default function CodingSubmissions() {
  const [items, setItems] = useState([]);

  useEffect(() => {
    getSubmissions().then((res) => setItems(res.data));
  }, []);

  return (
    <div className="flex min-h-screen bg-slate-50">
      <CodingSidebar />
      <main className="min-w-0 flex-1 p-6 lg:p-10">
        <div className="mx-auto max-w-6xl">
          <h1 className="text-3xl font-bold text-slate-900">Submission History</h1>
          <p className="mt-2 text-slate-500">Your latest coding submissions.</p>

          <div className="mt-8 overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm">
            <div className="grid grid-cols-[1fr_100px_150px_100px] border-b bg-slate-50 px-6 py-4 text-xs font-semibold uppercase tracking-wide text-slate-500">
              <span>Problem</span><span>Language</span><span>Status</span><span>Tests</span>
            </div>

            {items.length === 0 ? (
              <div className="p-8 text-center text-slate-500">No submissions yet.</div>
            ) : (
              items.map((item) => (
                <div key={item.id} className="grid grid-cols-[1fr_100px_150px_100px] border-b px-6 py-5 last:border-0">
                  <span className="font-semibold text-slate-800">{item.problemTitle}</span>
                  <span className="text-sm text-slate-600">{item.language}</span>
                  <span className={`text-sm font-semibold ${item.status === "ACCEPTED" ? "text-emerald-600" : "text-rose-600"}`}>
                    {item.status}
                  </span>
                  <span className="text-sm text-slate-600">{item.passedTests}/{item.totalTests}</span>
                </div>
              ))
            )}
          </div>
        </div>
      </main>
    </div>
  );
}
