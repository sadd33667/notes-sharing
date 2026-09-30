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

## Database

- H2 in-memory now so the app starts (`default` profile).
- MySQL profile ready: `application-mysql.properties` + `mysql-init.sql`.
- 6 tables, relations are OneToMany; NoteSharing breaks the User-Note ManyToMany.
- See `Student_ERD_Explanation.docx` and `My_ERD_final_arrows2.png` for the ERD.

## Git workflow

- `main` = stable base, `development` = work.
- Every feature: branch from `development` -> PR -> review -> merge.
