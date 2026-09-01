# AI Interview Platform Frontend

React + Vite + Tailwind CSS frontend for the Spring Boot AI Interview Platform.

## 1. Create/run

```bash
npm install
npm run dev
```

Frontend:
http://localhost:5173

Backend:
http://localhost:8080

## 2. Tailwind CSS

This project uses Tailwind CSS v4 through the Vite plugin:

- tailwindcss
- @tailwindcss/vite

There is no `tailwind.config.js` or `postcss.config.js` required for this setup.

Tailwind is loaded from:

```css
@import "tailwindcss";
```

in `src/index.css`.

## 3. Backend endpoints used

- POST /api/auth/register
- POST /api/auth/login
- GET /api/categories
- POST /api/interviews/start
- POST /api/interviews/{sessionId}/answer
- GET /api/interviews/{sessionId}/result
- GET /api/interviews/history

The Axios interceptor automatically sends:

```text
Authorization: Bearer <accessToken>
```

for authenticated requests.

## Important

Your Spring Boot backend must allow CORS from:

```text
http://localhost:5173
```

If CORS is not configured yet, add it before testing the browser frontend.
