import urllib.request, urllib.error, json

body = {"username": "sara", "email": "sara@test.com", "password": "secret123"}
req = urllib.request.Request(
    "http://localhost:8080/api/auth/register",
    data=json.dumps(body).encode(),
    headers={"Content-Type": "application/json"},
    method="POST",
)
try:
    with urllib.request.urlopen(req) as res:
        print("STATUS:", res.status)
        print(res.read().decode())
except urllib.error.HTTPError as e:
    print("STATUS:", e.code)
    print(e.read().decode()[:500])
