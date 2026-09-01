export default function Loading({ text = "Loading..." }) {
  return (
    <div className="flex min-h-[50vh] items-center justify-center">
      <div className="text-center">
        <div className="mx-auto mb-4 h-10 w-10 animate-spin rounded-full border-4 border-slate-200 border-t-violet-600" />
        <p className="text-slate-500">{text}</p>
      </div>
    </div>
  );
}
