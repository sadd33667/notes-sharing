# commit-msg skill

Use when: every commit in notes-sharing.

Steps:
1. Run `git diff --stat` and `git status --short`.
2. Title: under 50 chars, verb first (Add/Fix/Update).
3. Body: one line WHAT, one line WHY.
4. Never commit secrets or target/ files.

Example:
```
Add User entity with relations

WHAT: User with UserTag, Bio, BirthDate.
WHY: needed for profile and friend search.
```
