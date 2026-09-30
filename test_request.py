import urllib.request, json

data = {"ownerId": 1, "title": "First note", "contents": "Hello world"}
req = urllib.request.Request(
    "http://localhost:8080/api/notes",
    data=json.dumps(data).encode(),
    headers={"Content-Type": "application/json"},
    method="POST",
)
try:
    with urllib.request.urlopen(req) as res:
        print("STATUS:", res.status)
        print(res.read().decode())
except Exception as e:
    print("FAILED:", type(e).__name__, e)
