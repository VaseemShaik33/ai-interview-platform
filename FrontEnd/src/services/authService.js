const API_URL = "http://localhost:8080/api";

export async function login(email, password) {

    const response = await fetch(`${API_URL}/auth/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    });

    const data = await response.json();

    if (!response.ok) {
        throw new Error(data.message || "Login failed");
    }

    // Save authentication information
    localStorage.setItem("accessToken", data.token);
    localStorage.setItem("userId", data.userId);

    return data;
}

export function getToken() {
    return localStorage.getItem("accessToken");
}

export function logout() {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("userId");
}