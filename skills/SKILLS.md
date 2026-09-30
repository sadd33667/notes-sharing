# Skills - Project Brief

Owner: sadd33667 | Project: notes-sharing

## Skills structure (convention)

```text
skills/
  <skill-name>/
    SKILL.md   # name + when to use + steps + one example + tools
```

Rules for every SKILL.md:
- Max 10 lines of instructions. 4 clear lines beat 20 vague ones.
- Always: name, when to use, steps 1-2-3, one example from this project.
- One skill = one job only.

## Planned skills

### 1. commit-msg (do first)
- When: every commit.
- Steps: `git diff --stat` -> title under 50 chars -> body (what + why).
- Example: `Add User entity with relations`.
- Why: all commits same style.

### 2. create-note-tdd (first feature)
- When: building Create Note API.
- Steps: write failing test -> implement -> green -> refactor.
- Example: `POST /notes` returns 201 with note id.
- Why: matches trainer's TDD requirement.

### 3. code-review (before every merge)
- When: any PR to `development`.
- Checks: naming, thin controller / business in service, no PasswordHash in DTOs, no duplicated code, security, errors same shape, tests exist.
- Why: protects the architecture in ARCHITECTURE.md.

## Coding conventions (team)

- Packages: `controller / service / repository / entity / dto / mapper / exception / config`.
- Classes: `CreateNoteRequest`, `NoteService`, `NoteController` (PascalCase).
- Methods: `createNote`, `findByEmail` (camelCase).
- REST: `POST /notes` (201), `GET /notes` (200), errors `{ "error": "..." }`.
- Never return `PasswordHash` in any DTO.
- Controller calls service only, never repository.
- Branches: `feature/<name>` from `development` -> PR -> review -> merge.
