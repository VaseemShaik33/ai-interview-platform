import { Link } from "react-router-dom";
import { ArrowRight, ListChecks, MessageCircleQuestion, Rocket, Trophy } from "lucide-react";
import SiteHeader from "../components/SiteHeader";
import SiteFooter from "../components/SiteFooter";

const STEPS = [
  {
    icon: ListChecks,
    title: "1. Pick a role and difficulty",
    text: "Choose a category like Java, Spring Boot, or SQL, set a difficulty, and decide how many questions you want."
  },
  {
    icon: MessageCircleQuestion,
    title: "2. Answer AI-generated questions",
    text: "Work through your session one question at a time, typing answers the way you would in a real interview."
  },
  {
    icon: Rocket,
    title: "3. Get real-time AI evaluation",
    text: "Each answer is scored for relevance, correctness, and depth as soon as you submit it — no waiting for a human reviewer."
  },
  {
    icon: Trophy,
    title: "4. Review your scorecard",
    text: "At the end of the session you get an overall score, a breakdown by topic, and notes on what to work on next."
  }
];

export default function HowItWorks() {
  return (
    <div className="min-h-screen bg-white">
      <SiteHeader />

      <main>
        <section className="mx-auto max-w-4xl px-6 py-16 text-center">
          <h1 className="text-4xl font-extrabold tracking-tight text-slate-900">How it Works</h1>
          <p className="mt-4 text-lg text-slate-600">
            Four simple steps between you and interview-ready confidence.
          </p>
        </section>

        <section className="mx-auto max-w-4xl px-6 pb-20">
          <div className="space-y-6">
            {STEPS.map(({ icon: Icon, title, text }) => (
              <div key={title} className="flex gap-5 rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
                <span className="flex h-12 w-12 shrink-0 items-center justify-center rounded-xl bg-violet-100 text-violet-600">
                  <Icon size={24} />
                </span>
                <div>
                  <h2 className="font-semibold text-slate-900">{title}</h2>
                  <p className="mt-1 text-sm leading-6 text-slate-600">{text}</p>
                </div>
              </div>
            ))}
          </div>

          <div className="mt-12 text-center">
            <Link to="/register" className="inline-flex items-center gap-2 rounded-xl bg-violet-600 px-6 py-3 font-semibold text-white shadow-lg shadow-violet-600/25 hover:bg-violet-700">
              Get Started <ArrowRight size={18} />
            </Link>
          </div>
        </section>
      </main>

      <SiteFooter />
    </div>
  );
}
