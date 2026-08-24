// frontend-auth-snippet.js

// Example: call to login, store access token in memory and use refresh endpoint when 401
let accessToken = null;

async function login(username, password) {
  const res = await fetch('/auth/login', {
    method: 'POST',
    headers: {'Content-Type':'application/json'},
    body: JSON.stringify({ username, password })
  });
  const data = await res.json();
  accessToken = data.accessToken; // accessToken returned in body in this example
  // refreshToken is stored in HttpOnly cookie by the server
}

async function fetchWithAuth(url, opts = {}) {
  opts.headers = opts.headers || {};
  if (accessToken) opts.headers['Authorization'] = 'Bearer ' + accessToken;
  let res = await fetch(url, opts);
  if (res.status === 401) {
    // auto-refresh
    const r = await fetch('/auth/refresh', { method: 'POST' });
    if (r.ok) {
      const data = await r.json();
      accessToken = data.accessToken;
      opts.headers['Authorization'] = 'Bearer ' + accessToken;
      res = await fetch(url, opts);
    } else {
      // redirect to login
      throw new Error('Not authenticated');
    }
  }
  return res;
}
