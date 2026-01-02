# Pinia Architecture Guide

This project uses **Pinia** as its centralized state management library. Pinia allows different components (pages, layouts, widgets) to share data without having to pass "props" down manually through every layer.

We have organized the logic into **Three Mental Models (Stores)** based on the business domain:

---

## 1. Auth Store (`src/stores/auth.ts`)
**Purpose**: Manages the identity of the current user.

*   **State (Data)**:
    *   `user`: Contains `username`, `email`, `tenantId`, and `roles`.
    *   `token`: The JWT string received from the backend.
*   **Actions (Methods)**:
    *   `login(credentials)`: Sends request to Auth Service, saves the token to `localStorage`.
    *   `logout()`: Clears the token and redirects to login.
*   **Usage**:
    *   Used in `router/index.ts` to protect routes (if no token, redirect to login).
    *   Used in `DefaultLayout.vue` to show the user's name and email in the sidebar.

```typescript
// Example Usage in Component
const authStore = useAuthStore();
console.log(authStore.user.username); // Access data
authStore.logout(); // Perform action
```

---

## 2. Billing Store (`src/stores/billing.ts`)
**Purpose**: Manages subscription plans and the tenant's active status.

*   **State (Data)**:
    *   `plans`: List of available SaaS tiers (Basic, Pro, Enterprise).
    *   `subscriptions`: The current tenant's active subscription details.
*   **Actions (Methods)**:
    *   `fetchPlans()`: Gets data from the Billing Service.
    *   `subscribe(planName)`: Posts a request to subscribe the tenant to a new plan.
*   **Usage**:
    *   The `Subscription.vue` page purely renders the specific data found in `billingStore.plans`. It doesn't know *how* to fetch it, it just asks the store to do it.

---

## 3. Payment Store (`src/stores/payment.ts`)
**Purpose**: Manages financial transactions and history.

*   **State (Data)**:
    *   `history`: Array of past payment records.
*   **Actions (Methods)**:
    *   `processPayment(amount)`: Sends a request to the Payment Service.
    *   `fetchHistory()`: Loads valid past transactions (currently mocked for demo).
*   **Usage**:
    *   `Billing.vue` uses this to handle the "Pay Now" button click and to populate the "Payment History" table.

---

## Why This Implementation?
1.  **Separation of Concerns**: The View (`.vue` files) only cares about **how it looks**. The Store (`.ts` files) cares about **how it works**.
2.  **Reactivity**: If `billingStore.subscriptions` changes (e.g., after a successful payment), any component watching that data updates automatically.
3.  **Persistence**: The Auth store specifically handles `localStorage` logic inside itself, so the rest of the app just trusts `authStore.token` exists.
