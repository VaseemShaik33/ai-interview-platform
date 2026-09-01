import { createContext, useContext, useEffect, useState } from "react";
import api from "../services/api";

const AuthContext = createContext(null);

function readUser() {
  try {
    const saved = localStorage.getItem("user");
    return saved ? JSON.parse(saved) : null;
  } catch {
    return null;
  }
}

export function AuthProvider({ children }) {
  const [user, setUser] = useState(readUser);

  const login = async (email, password) => {
    const response = await api.post("/auth/login", { email, password });
    const data = response.data;

    localStorage.setItem("accessToken", data.accessToken);
    localStorage.setItem("refreshToken", data.refreshToken);

    const loggedUser = {
      userId: data.userId,
      name: data.name,
      email: data.email || email,
      role: data.role || "CANDIDATE"
    };

    localStorage.setItem("user", JSON.stringify(loggedUser));
    setUser(loggedUser);
    return data;
  };

  const register = async (name, email, password) => {
    return api.post("/auth/register", { name, email, password });
  };

  const logout = async () => {
    const refreshToken = localStorage.getItem("refreshToken");
    try {
      if (refreshToken) await api.post("/auth/logout", { refreshToken });
    } catch (_) {
      // Local logout must still succeed if the server token is already invalid.
    }
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("user");
    setUser(null);
  };

  useEffect(() => {
    if (!localStorage.getItem("accessToken")) setUser(null);
  }, []);

  return (
    <AuthContext.Provider value={{ user, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
