import { useState } from "react";
import { Link } from "react-router-dom";
import { BrainCircuit, MailCheck, Send } from "lucide-react";
import api from "../services/api";
import ErrorMessage from "../components/ErrorMessage";

export default function ForgotPassword() {
  const [email, setEmail] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const [sent, setSent] = useState(false);

  const submit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      await api.post("/auth/forgot-password", { email });
      // Backend always returns the same generic response whether or not
      // the email exists, so we always show the success state here too.
      setSent(true);
    } catch (err) {
      setError(err.response?.data?.message || "Something went wrong. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="flex min-h-screen items-center justify-center bg-slate-50 px-6">
      <div className="w-full max-w-md rounded-2xl border border-slate-200 bg-white p-8 shadow-xl shadow-slate-200/60">
        <div className="mb-8 text-center">
          <span className="mx-auto mb-4 flex h-12 w-12 items-center justify-center rounded-2xl bg-violet-600 text-white">
            <BrainCircuit size={26} />
          </span>
          <h1 className="text-3xl font-bold text-slate-900">Forgot Password?</h1>
          <p className="mt-2 text-slate-500">
            Enter your email and we'll send you a link to reset it.
          </p>
        </div>

        {sent ? (
          <div className="rounded-xl border border-emerald-200 bg-emerald-50 p-5 text-center">
            <MailCheck className="mx-auto text-emerald-600" size={32} />
            <p className="mt-3 text-sm text-emerald-700">
              If an account with that email exists, a reset link is on its way.
              Check your inbox (and spam folder).
            </p>
          </div>
        ) : (
          <>
            <ErrorMessage message={error} />

            <form onSubmit={submit} className="mt-5 space-y-5">
              <div>
                <label className="mb-2 block text-sm font-medium text-slate-700">Email</label>
                <input
                  type="email"
                  required
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="w-full rounded-xl border border-slate-300 bg-white px-4 py-3 text-slate-900 outline-none focus:border-violet-500 focus:ring-1 focus:ring-violet-500"
                  placeholder="Enter your email"
                />
              </div>

              <button disabled={loading} className="flex w-full items-center justify-center gap-2 rounded-xl bg-violet-600 py-3 font-semibold text-white hover:bg-violet-700 disabled:opacity-50">
                <Send size={18} />
                {loading ? "Sending..." : "Send Reset Link"}
              </button>
            </form>
          </>
        )}

        <p className="mt-6 text-center text-sm text-slate-500">
          Remembered it?{" "}
          <Link to="/login" className="font-medium text-violet-600 hover:text-violet-700">
            Back to login
          </Link>
        </p>
      </div>
    </main>
  );
}
