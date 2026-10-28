const $ = (s) => document.querySelector(s);
const $$ = (s) => document.querySelectorAll(s);

let currentUser = null;
let isRegister = false;

function toast(msg, type) {
  const t = $("#toast");
  t.textContent = msg;
  t.className = "toast" + (type ? " " + type : "");
  t.classList.remove("hidden");
  setTimeout(() => t.classList.add("hidden"), 2500);
}

function showMsg(el, msg, type) {
  el.textContent = msg;
  el.className = "msg" + (type ? " " + type : "");
}

function setView(name) {
  $$(".view").forEach(v => v.classList.add("hidden"));
  $("#view-" + name).classList.remove("hidden");
  $$(".nav-btn").forEach(b => b.classList.toggle("active", b.dataset.view === name));
}

function showAuth(show) {
  if (show) {
    $("#nav").classList.add("hidden");
    $("#userBadge").classList.add("hidden");
    setView("auth");
  } else {
    $("#nav").classList.remove("hidden");
    $("#userBadge").classList.remove("hidden");
    setView("dashboard");
  }
}

// ---------- AUTH ----------
function updateAuthUI() {
  $("#authTitle").textContent = isRegister ? "Register" : "Login";
  $("#authSubmit").textContent = isRegister ? "Register" : "Login";
  $("#registerFields").classList.toggle("hidden", !isRegister);
  $("#authSwitchText").textContent = isRegister ? "Already have an account?" : "No account?";
  $("#authSwitch").textContent = isRegister ? "Login here" : "Register here";
}

$("#authSwitch").addEventListener("click", (e) => {
  e.preventDefault();
  isRegister = !isRegister;
  updateAuthUI();
});

$("#authForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const msg = $("#authMsg");
  const email = $("#authEmail").value.trim();
  const password = $("#authPassword").value;
  try {
    let res;
    if (isRegister) {
      const username = $("#regUsername").value.trim();
      res = await api.register({ email, password, username });
    } else {
      res = await api.login({ email, password });
    }
    setToken(res.token);
    await loadUser();
    showAuth(false);
    showMsg(msg, "", "");
    toast("Welcome!", "ok");
    await refreshDashboard();
  } catch (err) {
    showMsg(msg, err.message, "error");
  }
});

async function loadUser() {
  try {
    currentUser = await api.me();
    $("#userBadge").textContent = currentUser.username || currentUser.email;
  } catch (e) {
    currentUser = null;
  }
}

// ---------- NAVIGATION ----------
$$(".nav-btn[data-view]").forEach(btn => {
  btn.addEventListener("click", async () => {
    const v = btn.dataset.view;
    setView(v);
    await loadView(v);
  });
});

async function loadView(v) {
  if (v === "dashboard") await refreshDashboard();
  else if (v === "notes") await refreshNotes();
  else if (v === "upcoming") {
    if (!$("#upFrom").value) {
      const now = new Date();
      $("#upFrom").value = toLocalInput(now);
      const later = new Date(now.getTime() + 7*24*60*60*1000);
      $("#upTo").value = toLocalInput(later);
    }
    await refreshUpcoming();
  }
  else if (v === "shared") await refreshShared();
  else if (v === "friends") await refreshFriends();
  else if (v === "notifications") await refreshNotifs();
  else if (v === "profile") await refreshProfile();
}

$("#logoutBtn").addEventListener("click", () => {
  setToken(null);
  currentUser = null;
  showAuth(true);
  toast("Logged out");
});

// ---------- DASHBOARD ----------
async function refreshDashboard() {
  try {
    const d = await api.dashboard();
    $("#statMyNotes").textContent = d.myNotesCount ?? d.myNotes ?? "-";
    $("#statShared").textContent = d.sharedWithMeCount ?? d.sharedWithMe ?? "-";
    $("#statFriends").textContent = d.friendsCount ?? d.friends ?? "-";
  } catch (e) { toast(e.message, "error"); }
}

$("#quickNoteForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const msg = $("#qnMsg");
  try {
    await api.createNote({
      title: $("#qnTitle").value.trim(),
      contents: $("#qnContent").value.trim(),
      noteType: $("#qnType").value,
      visibility: $("#qnVisibility").value,
    });
    showMsg(msg, "Created!", "ok");
    $("#qnTitle").value = "";
    $("#qnContent").value = "";
    await refreshDashboard();
  } catch (err) {
    showMsg(msg, err.message, "error");
  }
});

// ---------- NOTES ----------
async function refreshNotes() {
  const type = $("#noteTypeFilter").value;
  const list = $("#notesList");
  list.innerHTML = "<p class='muted'>Loading...</p>";
  try {
    const notes = await api.listNotes(type || undefined);
    if (!notes || !notes.length) { list.innerHTML = "<p class='muted'>No notes yet.</p>"; return; }
    list.innerHTML = notes.map(n => `
      <div class="item">
        <h4>${escapeHtml(n.title)} ${n.isPinned ? "📌" : ""}</h4>
        <p>${escapeHtml(n.contents || "")}</p>
        <div class="meta">Type: ${n.noteType || "-"} · Visibility: ${n.visibility || "-"} · #${n.userNoteNumber ?? n.noteID ?? n.id} · Updated: ${fmtDate(n.updatedAt || n.createdAt)}</div>
        <div class="actions">
          <button class="btn" onclick="viewNote(${n.noteID})">View</button>
          <button class="btn" onclick="editNote(${n.noteID})">Edit</button>
          <button class="btn" onclick="togglePin(${n.noteID}, ${!n.isPinned})">${n.isPinned ? "Unpin" : "Pin"}</button>
          <button class="btn" onclick="shareNotePrompt(${n.noteID})">Share</button>
          <button class="btn danger" onclick="removeNote(${n.noteID})">Delete</button>
        </div>
      </div>
    `).join("");
  } catch (e) {
    list.innerHTML = "<p class='msg error'>" + escapeHtml(e.message) + "</p>";
  }
}

$("#refreshNotes").addEventListener("click", refreshNotes);
$("#noteTypeFilter").addEventListener("change", refreshNotes);

async function viewNote(id) {
  try {
    const n = await api.getNote(id);
    const body = $("#modalBody");
    body.innerHTML = `
      <h3>${escapeHtml(n.title)}</h3>
      <p>${escapeHtml(n.contents || "")}</p>
      <div class="meta">Type: ${n.noteType} · Visibility: ${n.visibility} · #${n.userNoteNumber ?? n.noteID ?? n.id}</div>
      <div class="meta">Created: ${fmtDate(n.createdAt)} · Updated: ${fmtDate(n.updatedAt)}</div>
      ${n.owner ? `<div class="meta">Owner: ${escapeHtml(n.owner.fullName || n.owner.username || n.owner.email)}</div>` : ""}
    `;
    $("#modal").classList.remove("hidden");
  } catch (e) { toast(e.message, "error"); }
}

async function editNote(id) {
  try {
    const n = await api.getNote(id);
    const newTitle = prompt("New title:", n.title);
    if (newTitle === null) return;
    const newContent = prompt("New content:", n.contents || "");
    if (newContent === null) return;
    await api.updateNote(id, { title: newTitle, contents: newContent, noteType: n.noteType, visibility: n.visibility });
    toast("Updated", "ok");
    await refreshNotes();
  } catch (e) { toast(e.message, "error"); }
}

async function togglePin(id, pinned) {
  try {
    await api.pinNote(id, pinned);
    toast(pinned ? "Pinned" : "Unpinned", "ok");
    await refreshNotes();
  } catch (e) { toast(e.message, "error"); }
}

let shareNoteId = null;
async function shareNotePrompt(id) {
  shareNoteId = id;
  const sel = $("#shareFriend");
  sel.innerHTML = "<option>Loading...</option>";
  $("#shareModal").classList.remove("hidden");
  try {
    const friends = await api.listFriends();
    if (!friends || !friends.length) {
      sel.innerHTML = "<option value=''>No friends yet</option>";
      return;
    }
    sel.innerHTML = friends.map(f =>
      `<option value='${f.friendUserId}'>${escapeHtml(f.friendUsername)}</option>`).join("");
  } catch (e) {
    sel.innerHTML = "<option value=''>Error loading friends</option>";
    toast(e.message, "error");
  }
}

$("#shareForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const msg = $("#shareMsg");
  const userId = Number($("#shareFriend").value);
  if (!userId) { showMsg(msg, "Pick a friend", "error"); return; }
  const perm = $("#sharePerm").value;
  try {
    await api.shareNote(shareNoteId, { userId, permission: perm });
    showMsg(msg, "Shared!", "ok");
    toast("Shared!", "ok");
    $("#shareModal").classList.add("hidden");
  } catch (err) { showMsg(msg, err.message, "error"); }
});
$("#shareModalClose").addEventListener("click", () => $("#shareModal").classList.add("hidden"));
$("#shareModal").addEventListener("click", (e) => { if (e.target.id === "shareModal") $("#shareModal").classList.add("hidden"); });

async function removeNote(id) {
  if (!confirm("Delete this note?")) return;
  try {
    await api.deleteNote(id);
    toast("Deleted", "ok");
    await refreshNotes();
    await refreshDashboard();
  } catch (e) { toast(e.message, "error"); }
}

// ---------- UPCOMING ----------
async function refreshUpcoming() {
  const list = $("#upcomingList");
  list.innerHTML = "<p class='muted'>Loading...</p>";
  const from = $("#upFrom").value;
  const to = $("#upTo").value;
  if (!from || !to) {
    list.innerHTML = "<p class='muted'>Pick a date range above.</p>";
    return;
  }
  try {
    const notes = await api.upcoming(from, to);
    if (!notes || !notes.length) { list.innerHTML = "<p class='muted'>No upcoming notes.</p>"; return; }
    list.innerHTML = notes.map(n => `
      <div class="item">
        <h4>${escapeHtml(n.title)} ${n.isPinned ? "📌" : ""}</h4>
        <p>${escapeHtml(n.contents || "")}</p>
        <div class="meta">${fmtDate(n.createdAt)} → ${fmtDate(n.updatedAt)} · ${n.visibility || "-"}</div>
        <div class="actions"><button class="btn" onclick="viewNote(${n.noteID})">View</button></div>
      </div>
    `).join("");
  } catch (e) {
    list.innerHTML = "<p class='msg error'>" + escapeHtml(e.message) + "</p>";
  }
}
$("#refreshUpcoming").addEventListener("click", refreshUpcoming);

// ---------- SHARED ----------
async function refreshShared() {
  const list = $("#sharedList");
  list.innerHTML = "<p class='muted'>Loading...</p>";
  try {
    const notes = await api.sharedWithMe();
    if (!notes || !notes.length) { list.innerHTML = "<p class='muted'>Nothing shared with you.</p>"; return; }
    list.innerHTML = notes.map(n => `
      <div class="item">
        <h4>${escapeHtml(n.title)} ${n.isPinned ? "📌" : ""}</h4>
        <p>${escapeHtml(n.contents || "")}</p>
        <div class="meta">Visibility: ${n.visibility || "-"} · #${n.userNoteNumber ?? n.noteID}</div>
        <div class="actions">
          <button class="btn" onclick="viewNote(${n.noteID})">View</button>
          <button class="btn" onclick="toggleFavorite(${n.noteID}, true)">Favorite</button>
        </div>
      </div>
    `).join("");
  } catch (e) {
    list.innerHTML = "<p class='msg error'>" + escapeHtml(e.message) + "</p>";
  }
}
$("#refreshShared").addEventListener("click", refreshShared);

async function toggleFavorite(id, fav) {
  try {
    await api.favorite(id, fav);
    toast("Favorited", "ok");
  } catch (e) { toast(e.message, "error"); }
}

// ---------- FRIENDS ----------
async function refreshFriends() {
  const msg = $("#friendMsg");
  // pending
  const pending = $("#pendingList");
  pending.innerHTML = "<p class='muted'>Loading...</p>";
  try {
    const p = await api.listPending();
    if (!p || !p.length) pending.innerHTML = "<p class='muted'>No pending requests.</p>";
    else pending.innerHTML = p.map(f => `
      <div class="item">
        <h4>Request from ${escapeHtml(f.friendUsername || "?")}</h4>
        <div class="meta">Status: ${f.status}</div>
        <div class="actions">
          <button class="btn primary" onclick="acceptFriend(${f.relationshipID})">Accept</button>
          <button class="btn" onclick="rejectFriend(${f.relationshipID})">Reject</button>
        </div>
      </div>
    `).join("");
  } catch (e) { pending.innerHTML = "<p class='msg error'>" + escapeHtml(e.message) + "</p>"; }

  const friends = $("#friendsList");
  friends.innerHTML = "<p class='muted'>Loading...</p>";
  try {
    const fr = await api.listFriends();
    if (!fr || !fr.length) friends.innerHTML = "<p class='muted'>No friends yet.</p>";
    else friends.innerHTML = fr.map(f => `
      <div class="item">
        <h4>${escapeHtml(f.friendUsername || "Friend")}</h4>
        <div class="meta">Status: ${f.status}</div>
        <div class="actions">
          <button class="btn danger" onclick="removeFriend(${f.relationshipID})">Remove</button>
        </div>
      </div>
    `).join("");
  } catch (e) { friends.innerHTML = "<p class='msg error'>" + escapeHtml(e.message) + "</p>"; }
}

$("#friendReqForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const msg = $("#friendMsg");
  try {
    await api.sendFriendRequest({ username: $("#friendUsername").value.trim() });
    showMsg(msg, "Request sent!", "ok");
    $("#friendUsername").value = "";
    await refreshFriends();
  } catch (err) { showMsg(msg, err.message, "error"); }
});

async function acceptFriend(id) {
  try { await api.acceptFriend(id); toast("Accepted", "ok"); await refreshFriends(); await refreshDashboard(); }
  catch (e) { toast(e.message, "error"); }
}
async function rejectFriend(id) {
  try { await api.rejectFriend(id); toast("Rejected", "ok"); await refreshFriends(); }
  catch (e) { toast(e.message, "error"); }
}
async function removeFriend(id) {
  if (!confirm("Remove friend?")) return;
  try { await api.deleteFriend(id); toast("Removed", "ok"); await refreshFriends(); await refreshDashboard(); }
  catch (e) { toast(e.message, "error"); }
}

// ---------- NOTIFICATIONS ----------
async function refreshNotifs() {
  const list = $("#notifList");
  list.innerHTML = "<p class='muted'>Loading...</p>";
  try {
    const ns = await api.listNotifications();
    const unread = (ns || []).filter(n => !n.isRead).length;
    const badge = $("#notifBadge");
    if (unread > 0) { badge.textContent = unread; badge.classList.remove("hidden"); }
    else badge.classList.add("hidden");
    if (!ns || !ns.length) { list.innerHTML = "<p class='muted'>No notifications.</p>"; return; }
    list.innerHTML = ns.map(n => `
      <div class="item">
        <h4>${escapeHtml((n.notifierUsername ? n.notifierUsername + " " : "") + (n.type || "Notification"))}</h4>
        <div class="meta">${fmtDate(n.issueDate)} · ${n.isRead ? "Read" : "Unread"}${n.noteId ? " · note #" + n.noteId : ""}</div>
        <div class="actions">
          ${n.isRead ? "" : `<button class="btn" onclick="readNotif(${n.notificationID})">Mark read</button>`}
        </div>
      </div>
    `).join("");
  } catch (e) { list.innerHTML = "<p class='msg error'>" + escapeHtml(e.message) + "</p>"; }
}
$("#refreshNotifs").addEventListener("click", refreshNotifs);

async function readNotif(id) {
  try { await api.readNotification(id); toast("Marked read", "ok"); await refreshNotifs(); }
  catch (e) { toast(e.message, "error"); }
}

// ---------- PROFILE ----------
async function refreshProfile() {
  try {
    const u = await api.me();
    $("#profUsernameLabel").textContent = u.username || "";
    $("#profEmailLabel").textContent = u.email || "";
    $("#profBio").value = u.bio || "";
    $("#profPhone").value = u.phoneNumber || "";
    $("#profImage").value = u.image || "";
    $("#profSecondaryEmail").value = u.secondaryEmail || "";
    const s = await api.getSettings();
    $("#setTheme").value = s.theme || "light";
    $("#setLang").value = s.language || "en";
    $("#setNotif").checked = !!s.notificationsEnabled;
    $("#set2fa").checked = !!s.twoFactorEnabled;
  } catch (e) { toast(e.message, "error"); }
}

$("#profileForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const msg = $("#profMsg");
  try {
    await api.updateMe({
      bio: $("#profBio").value.trim(),
      phoneNumber: $("#profPhone").value.trim(),
      image: $("#profImage").value.trim(),
      secondaryEmail: $("#profSecondaryEmail").value.trim(),
    });
    showMsg(msg, "Saved!", "ok");
    await loadUser();
  } catch (err) { showMsg(msg, err.message, "error"); }
});

$("#settingsForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const msg = $("#setMsg");
  try {
    await api.updateSettings({
      theme: $("#setTheme").value,
      language: $("#setLang").value,
      notificationsEnabled: $("#setNotif").checked,
      twoFactorEnabled: $("#set2fa").checked,
    });
    showMsg(msg, "Saved!", "ok");
  } catch (err) { showMsg(msg, err.message, "error"); }
});

$("#pwdForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const msg = $("#pwdMsg");
  try {
    await api.changePassword({ oldPassword: $("#oldPwd").value, newPassword: $("#newPwd").value });
    showMsg(msg, "Password changed!", "ok");
    $("#oldPwd").value = ""; $("#newPwd").value = "";
  } catch (err) { showMsg(msg, err.message, "error"); }
});

// ---------- MODAL ----------
$("#modalClose").addEventListener("click", () => $("#modal").classList.add("hidden"));
$("#modal").addEventListener("click", (e) => { if (e.target.id === "modal") $("#modal").classList.add("hidden"); });

// ---------- HELPERS ----------
function escapeHtml(s) {
  if (s == null) return "";
  return String(s).replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;");
}
function fmtDate(d) {
  if (!d) return "-";
  try { return new Date(d).toLocaleString(); } catch (e) { return d; }
}
function toLocalInput(d) {
  const pad = (n) => String(n).padStart(2, "0");
  return d.getFullYear() + "-" + pad(d.getMonth()+1) + "-" + pad(d.getDate()) + "T" + pad(d.getHours()) + ":" + pad(d.getMinutes());
}

// ---------- BOOT ----------
updateAuthUI();
(async () => {
  if (getToken()) {
    try {
      await loadUser();
      showAuth(false);
      await refreshDashboard();
    } catch (e) {
      setToken(null);
      showAuth(true);
    }
  } else {
    showAuth(true);
  }
})();