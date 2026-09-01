export default function SiteFooter() {
  return (
    <footer className="border-t border-slate-200 py-8">
      <div className="mx-auto max-w-7xl px-6 text-center text-sm text-slate-500">
        © {new Date().getFullYear()} AI Interview Platform. All rights reserved.
      </div>
    </footer>
  );
}
