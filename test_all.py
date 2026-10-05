import urllib.request, urllib.error, json

BASE = "http://localhost:8080"
passed, failed = [], []

def call(method, path, token=None, body=None):
    req = urllib.request.Request(
        BASE + path,
        data=json.dumps(body).encode() if body is not None else None,
        headers={"Content-Type": "application/json", **({"Authorization": "Bearer " + token} if token else {})},
        method=method,
    )
    try:
        with urllib.request.urlopen(req) as res:
            raw = res.read().decode()
            return res.status, (json.loads(raw) if raw else None)
    except urllib.error.HTTPError as e:
        return e.code, e.read().decode()[:200]

def check(name, cond, extra=""):
    (passed if cond else failed).append(name)
    print(("PASS " if cond else "FAIL ") + name, extra)

t = str(__import__("time").time()).replace(".", "")
r, owner = call("POST", "/api/auth/register", body={"username": "u1", "email": f"u1{t}@t.com", "password": "pw12345"})
check("register", r == 201, str(r))
tok1 = owner["token"] if isinstance(owner, dict) else None

r, u2 = call("POST", "/api/auth/register", body={"username": "u2", "email": f"u2{t}@t.com", "password": "pw12345"})
check("register-2", r == 201, str(r))
tok2 = u2["token"]

r, me = call("GET", "/api/users/me", token=tok1)
check("profile", r == 200 and me["username"] == "u1", str(r))

r, note = call("POST", "/api/notes", token=tok1, body={"ownerId": owner["userId"], "title": "N1", "contents": "hi"})
check("create-note", r == 201 and note["noteID"], str(r))
nid = note["noteID"]

r, g = call("GET", f"/api/notes/{nid}", token=tok1)
check("get-note-owner", r == 200, str(r))

r, _ = call("GET", f"/api/notes/{nid}", token=tok2)
check("get-note-stranger-blocked", r == 404, str(r))

r, _ = call("PUT", f"/api/notes/{nid}", token=tok1, body={"title": "N1-edited"})
check("update-note", r == 200, str(r))

r, _ = call("POST", f"/api/notes/{nid}/share", token=tok1, body={"userId": u2["userId"], "permission": "view"})
check("share", r == 200, str(r))

r, g = call("GET", f"/api/notes/{nid}", token=tok2)
check("get-note-shared", r == 200, str(r))

r, lst = call("GET", "/api/notes/shared-with-me", token=tok2)
check("shared-with-me", r == 200 and len(lst) == 1, str(r))

r, _ = call("POST", "/api/friends/request", token=tok1, body={"username": "u2"})
check("friend-request", r == 201, str(r))

r, pend = call("GET", "/api/friends/pending", token=tok2)
check("friend-pending", r == 200 and len(pend) == 1, str(r))
rid = pend[0]["relationshipID"]

r, _ = call("PUT", f"/api/friends/{rid}/accept", token=tok2)
check("friend-accept", r == 200, str(r))

r, fr = call("GET", "/api/friends", token=tok1)
check("friend-list", r == 200 and len(fr) == 1, str(r))

r, _ = call("DELETE", f"/api/notes/{nid}/share/{u2['userId']}", token=tok1)
check("unshare", r == 204, str(r))

r, _ = call("GET", "/api/notifications", token=tok1)
check("notifications", r == 200, str(r))

r, _ = call("GET", "/api/notes/upcoming?from=2000-01-01T00:00:00&to=2100-01-01T00:00:00", token=tok1)
check("upcoming", r == 200, str(r))

print(f"\nTOTAL: {len(passed)} passed, {len(failed)} failed: {failed}")
