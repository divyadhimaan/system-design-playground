# Authentication and Global Cache
Here is the improved architecture diagram with better authentication.

![Alt text](/diagrams/email-service-2.png)

## Secure Authentication for API Calls

- `All API calls require authorization` — unauthorized access is strictly denied.
- Option: Store user `passwords in cookies` – [`Not recommended`] due to high security risks.
- Instead, we use `JWT tokens` or `auth secrets`, stored securely in the browser (e.g., `localStorage` or `HttpOnly cookies`).
- These tokens are sent with each API request for authentication.

---

## Auth Flow (One Session)

1. `User login` → request goes to `Auth Service`.
2. `Auth Service` generates and sends a `verification code`.
3. `User enters code` for `2-step authentication`.
4. On success, `Auth Service`:
   - Stores a new `auth secret/token`.
   - Returns the `token` to the client for use in future API calls.

---

## ❌ Problem: Gateway → Auth Service for Every Request

- Validating tokens on every request means `frequent network calls` from `Gateway` to `Auth Service`.
- This adds `latency`, `load on auth service`, and `network overhead`.

---

## ⚡ Solution: Cache Auth Secrets in Gateway

- `Gateway` maintains a `local cache` of user tokens/secrets for quick validation.
- Reduces dependency on `Auth Service` for every request.

---

## Keeping the Cache Updated

- `Message Queue (MQ)` can be used to:
  - Push `auth updates` (e.g., token revocations, refresh) to `Gateway`.
  - Ensure `real-time sync` across all gateways.

---

## ⚠️ Challenges with Local Cache at Gateway

- `Gateway` becomes responsible for:
  - `Memory allocation` & cleanup.
  - `Event handling` (e.g., token expiry, revocation).
  - `Update fanout` – syncing all gateway instances in real time.
- More complexity and higher resource consumption per gateway.

---

## Alternative: Use a Global Cache

- Use a `centralized (global) cache` like `Redis` or `Memcached`.
- Pros:
  - `Centralized control`, less per-gateway memory use.
- Cons:
  - Requires `network call` to global cache on every auth check.

---

## Best-of-Both: Use Sidecar Pattern

- Deploy a `sidecar proxy` with each `gateway` instance:
  - Handles `authentication`, `local caching`, and `syncing with global cache`.
  - Reduces `gateway` responsibilities.
  - Makes the system more `modular and scalable`.

- A `sidecar` is a design pattern in microservices architecture where a `helper component` (a separate process) runs alongside the main application/service, typically in the same `pod or host`, and adds `additional functionalities` without modifying the main service code.

---

## Summary of Authentication Strategy

| Step | Action |
|------|--------|
| 1 | User logs in via Auth Service |
| 2 | Code is generated and verified (2FA) |
| 3 | Auth token/secret is returned to user |
| 4 | Token is used for all subsequent API calls |
| 5 | Gateway caches the token for performance |
| 6 | Cache updates via MQ or global cache with sidecar |
