import { Link } from "react-router-dom";
import { Sparkles, Gauge, ClipboardCheck, ArrowRight, MessageSquare, Code2 } from "lucide-react";
import SiteHeader from "../components/SiteHeader";
import SiteFooter from "../components/SiteFooter";

const FEATURES = [
  { icon: Sparkles, title: "AI Generated Questions", text: "Every session adapts to your role and skill level, no two interviews are the same." },
  { icon: Gauge, title: "Real-time Evaluation", text: "Answers are scored the moment you submit them, with reasoning you can actually learn from." },
  { icon: ClipboardCheck, title: "Detailed Feedback", text: "Get a breakdown of strengths and gaps after every interview, not just a final number." }
];

export default function Landing() {
  return (
    <div className="min-h-screen bg-white">
      <SiteHeader />

      <main>
        <section className="mx-auto grid max-w-7xl items-center gap-12 px-6 py-16 md:grid-cols-2 md:py-24">
          <div>
            <h1 className="text-4xl font-extrabold leading-tight tracking-tight text-slate-900 md:text-5xl">
              Practice Smarter.
              <br />
              Get Hired Faster.
              <br />
              <span className="bg-gradient-to-r from-violet-600 to-indigo-500 bg-clip-text text-transparent">
                AI Interview Platform
              </span>
            </h1>

            <p className="mt-6 max-w-lg text-lg leading-8 text-slate-600">
              AI-powered interviews, adaptive questions, real-time evaluation and smart
              feedback to help you crack your dream job.
            </p>

            <div className="mt-8 flex flex-wrap gap-4">
              <Link to="/register" className="flex items-center gap-2 rounded-xl bg-violet-600 px-6 py-3 font-semibold text-white shadow-lg shadow-violet-600/25 hover:bg-violet-700">
                Start Interview <ArrowRight size={18} />
              </Link>
              <a href="#features" className="flex items-center gap-2 rounded-xl border border-slate-300 px-6 py-3 font-semibold text-slate-700 hover:bg-slate-50">
                View Demo
              </a>
            </div>

            <dl className="mt-12 grid grid-cols-1 gap-6 sm:grid-cols-3">
              {FEATURES.map(({ icon: Icon, title }) => (
                <div key={title} className="flex items-center gap-2">
                  <Icon size={18} className="shrink-0 text-violet-600" />
                  <dt className="text-sm font-medium text-slate-700">{title}</dt>
                </div>
              ))}
            </dl>
          </div>

          <div className="relative">
            <div className="absolute -top-10 -right-10 h-56 w-56 rounded-full bg-violet-200/60 blur-3xl" />
            <div className="absolute -bottom-10 -left-10 h-56 w-56 rounded-full bg-indigo-200/60 blur-3xl" />

            <div className="relative rounded-3xl border border-slate-200 bg-white p-6 shadow-2xl shadow-slate-200">
              <div className="flex items-center gap-2 border-b border-slate-100 pb-4">
                <span className="h-3 w-3 rounded-full bg-red-300" />
                <span className="h-3 w-3 rounded-full bg-amber-300" />
                <span className="h-3 w-3 rounded-full bg-emerald-300" />
                <span className="ml-3 text-xs font-medium text-slate-400">Java Backend Developer Interview</span>
              </div>

              <div className="mt-5 flex items-start gap-3">
                <span className="flex h-9 w-9 shrink-0 items-center justify-center rounded-full bg-violet-100 text-violet-600">
                  <MessageSquare size={16} />
                </span>
                <div className="rounded-2xl rounded-tl-sm bg-slate-100 px-4 py-3 text-sm text-slate-700">
                  What is dependency injection in Spring Framework? Explain with an example.
                </div>
              </div>

              <div className="mt-4 flex items-start justify-end gap-3">
                <div className="rounded-2xl rounded-tr-sm bg-violet-600 px-4 py-3 text-sm text-white">
                  It's a design pattern where objects receive their dependencies from an external source...
                </div>
                <span className="flex h-9 w-9 shrink-0 items-center justify-center rounded-full bg-slate-200 text-slate-500">
                  <Code2 size={16} />
                </span>
              </div>

              <div className="mt-5 rounded-xl bg-emerald-50 px-4 py-3 text-xs font-medium text-emerald-700">
                ✓ Relevance · ✓ Correctness · ✓ Depth — Confidence Score 92%
              </div>
            </div>
          </div>
        </section>

        <section id="features" className="border-t border-slate-100 bg-slate-50">
          <div className="mx-auto max-w-7xl px-6 py-16">
            <div className="mx-auto max-w-2xl text-center">
              <h2 className="text-3xl font-bold tracking-tight text-slate-900">Everything you need to prep with confidence</h2>
              <p className="mt-3 text-slate-600">From your first mock question to a full scorecard, every step is built to mirror a real interview.</p>
            </div>

            <div className="mt-12 grid gap-6 md:grid-cols-3">
              {FEATURES.map(({ icon: Icon, title, text }) => (
                <div key={title} className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
                  <span className="flex h-11 w-11 items-center justify-center rounded-xl bg-violet-100 text-violet-600">
                    <Icon size={22} />
                  </span>
                  <h3 className="mt-4 font-semibold text-slate-900">{title}</h3>
                  <p className="mt-2 text-sm leading-6 text-slate-600">{text}</p>
                </div>
              ))}
            </div>
          </div>
        </section>

        <section className="mx-auto max-w-7xl px-6 py-16 text-center">
          <h2 className="text-3xl font-bold tracking-tight text-slate-900">Ready to practice your next interview?</h2>
          <p className="mt-3 text-slate-600">Create a free account and start your first AI-evaluated mock interview today.</p>
          <Link to="/register" className="mt-8 inline-flex items-center gap-2 rounded-xl bg-violet-600 px-6 py-3 font-semibold text-white shadow-lg shadow-violet-600/25 hover:bg-violet-700">
            Get Started <ArrowRight size={18} />
          </Link>
        </section>
      </main>

      <SiteFooter />
    </div>
  );
}
