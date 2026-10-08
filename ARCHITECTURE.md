# Notes Sharing - Backend Architecture

Prepared by: Sara

## Structure

```text
com.notes.sharing/
├── controller/   # HTTP only: validate, call service, return response
├── dto/          # Request/response shapes, never expose Entity
├── service/      # All business logic: sharing rules, friendship, notifications
├── repository/   # Spring Data JPA only, no SQL spread in code
├── entity/       # Tables: User, UserSettings, Friendship, Note, NoteSharing, Notification
├── mapper/       # Entity <-> DTO conversion in one place
├── exception/    # NotFoundException + GlobalExceptionHandler, one error shape
├── config/       # Security, CORS, JPA settings
└── security/     # JWT, password hash, 2FA later
```

## Rules

- Dependency goes down only: controller -> service -> repository -> entity -> DB.
- Controller never calls repository directly.
- Service holds sharing rules (view/edit) and friendship logic.
- DTOs never contain PasswordHash.
- Errors always return `{ "error": "..." }`.
- No `Map` request bodies: every endpoint has a typed DTO.

## Endpoints (30)

Auth: POST /api/auth/register (201), POST /api/auth/login (200), PUT /api/auth/password.
OAuth2: GET /oauth2/authorization/{google} -> provider login -> our JWT redirect (optional, active when credentials set).
Notes: POST /api/notes (201), GET /api/notes?type=, GET /api/notes/upcoming?from&to=, GET /api/notes/{id}, PUT /api/notes/{id}, DELETE /api/notes/{id} (204), PUT /api/notes/{id}/pin.
Sharing: POST /api/notes/{id}/share, DELETE /api/notes/{id}/share/{userId} (204), GET /api/notes/shared-with-me, PUT /api/notes/{id}/favorite.
Friends: POST /api/friends/request (201), GET /api/friends, GET /api/friends/pending, PUT /api/friends/{id}/accept, PUT /api/friends/{id}/reject, DELETE /api/friends/{id} (204).
Notifications: GET /api/notifications, PUT /api/notifications/{id}/read.
Profile: GET+PUT /api/users/me, GET+PUT /api/users/me/settings, DELETE /api/users/me (204).
Dashboard: GET /api/dashboard.
Permission rule: owner > shared(edit/view) > public > 404 (same message hides private notes).

## Database

- H2 in-memory now so the app starts (`default` profile).
- MySQL profile ready: `application-mysql.properties` + `mysql-init.sql`.
- 6 tables, relations are OneToMany; NoteSharing breaks the User-Note ManyToMany.
- See `Student_ERD_Explanation.docx` and `My_ERD_final_arrows2.png` for the ERD.

## Git workflow

- `main` = stable base, `development` = work.
- Every feature: branch from `development` -> PR -> review -> merge.
