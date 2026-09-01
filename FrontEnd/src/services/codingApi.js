import api from "./api";

export const getCodingProblems = () => api.get("/coding/problems");
export const getCodingProblem = (id) => api.get(`/coding/problems/${id}`);
export const runCode = (id, code, language = "java") =>
  api.post(`/coding/problems/${id}/run`, { code, language });
export const submitCode = (id, code, language = "java") =>
  api.post(`/coding/problems/${id}/submit`, { code, language });
export const getSubmissions = () => api.get("/coding/submissions");
export const getCodingStats = () => api.get("/coding/stats");
