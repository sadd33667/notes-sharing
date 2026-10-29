const API = "/api";
const TOKEN_KEY = "notes_token";

function getToken() { return localStorage.getItem(TOKEN_KEY); }
function setToken(t) { if (t) localStorage.setItem(TOKEN_KEY, t); else localStorage.removeItem(TOKEN_KEY); }

async function request(path, options = {}) {
  const headers = { "Content-Type": "application/json", ...(options.headers || {}) };
  const token = getToken();
  if (token) headers["Authorization"] = "Bearer " + token;
  const res = await fetch(API + path, { ...options, headers });
  let data = null;
  const ct = res.headers.get("content-type") || "";
  if (ct.includes("application/json")) {
    try { data = await res.json(); } catch (e) { data = null; }
  } else {
    try { data = await res.text(); } catch (e) { data = null; }
  }
  if (!res.ok) {
    const msg = (data && (data.error || data.message)) || ("HTTP " + res.status);
    const err = new Error(msg);
    err.status = res.status;
    err.data = data;
    throw err;
  }
  return data;
}

const api = {
  // auth
  register: (b) => request("/auth/register", { method: "POST", body: JSON.stringify(b) }),
  login: (b) => request("/auth/login", { method: "POST", body: JSON.stringify(b) }),
  changePassword: (b) => request("/auth/password", { method: "PUT", body: JSON.stringify(b) }),
  // notes
  listNotes: (type) => request("/notes" + (type ? "?type=" + encodeURIComponent(type) : "")),
  upcoming: (from, to) => request("/notes/upcoming?from=" + encodeURIComponent(from) + "&to=" + encodeURIComponent(to)),
  getNote: (id) => request("/notes/" + id),
  createNote: (b) => request("/notes", { method: "POST", body: JSON.stringify(b) }),
  updateNote: (id, b) => request("/notes/" + id, { method: "PUT", body: JSON.stringify(b) }),
  deleteNote: (id) => request("/notes/" + id, { method: "DELETE" }),
  pinNote: (id, pinned) => request("/notes/" + id + "/pin", { method: "PUT", body: JSON.stringify({ isPinned: pinned }) }),
  // sharing
  shareNote: (id, b) => request("/notes/" + id + "/share", { method: "POST", body: JSON.stringify(b) }),
  listShares: (id) => request("/notes/" + id + "/shares"),
  unshareNote: (id, userId) => request("/notes/" + id + "/share/" + userId, { method: "DELETE" }),
  sharedWithMe: () => request("/notes/shared-with-me"),
  favorite: (id, favorite) => request("/notes/" + id + "/favorite", { method: "PUT", body: JSON.stringify({ isFavorite: favorite }) }),
  // friends
  sendFriendRequest: (b) => request("/friends/request", { method: "POST", body: JSON.stringify(b) }),
  listFriends: () => request("/friends"),
  listPending: () => request("/friends/pending"),
  acceptFriend: (id) => request("/friends/" + id + "/accept", { method: "PUT" }),
  rejectFriend: (id) => request("/friends/" + id + "/reject", { method: "PUT" }),
  deleteFriend: (id) => request("/friends/" + id, { method: "DELETE" }),
  // notifications
  listNotifications: () => request("/notifications"),
  readNotification: (id) => request("/notifications/" + id + "/read", { method: "PUT" }),
  // user/profile
  me: () => request("/users/me"),
  searchUsers: (q) => request("/users/search?username=" + encodeURIComponent(q)),
  updateMe: (b) => request("/users/me", { method: "PUT", body: JSON.stringify(b) }),
  getSettings: () => request("/users/me/settings"),
  updateSettings: (b) => request("/users/me/settings", { method: "PUT", body: JSON.stringify(b) }),
  deleteMe: () => request("/users/me", { method: "DELETE" }),
  // dashboard
  dashboard: () => request("/dashboard"),
};