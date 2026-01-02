# API Testing Guide

This guide outlines the steps to test the microservices for the Multitenant SaaS Billing & Subscription Platform.

## Prerequisites
Ensure all services are running on their respective ports:
- **Auth Service**: `8081`
- **Tenant Service**: `8082`
- **Payment Service**: `8083`
- **Billing Service**: `8084`

## Testing Order
It is strictly recommended to follow this order to handle data dependencies (e.g., `tenantId` is required for almost all other operations).

1. **Tenant Service**: Create a tenant.
2. **Auth Service**: Register and Login.
3. **Billing Service**: Subscribe to a plan.
4. **Payment Service**: Process a payment.

---

## 1. Tenant Service
**Base URL**: `http://localhost:8082`

### Create a Tenant
*   **Method**: `POST`
*   **Endpoint**: `/api/tenants`
*   **Body (JSON)**:
    ```json
    {
      "name": "Acme Corp",
      "email": "admin@acmecorp.com"
    }
    ```
*   **Important**: Copy the `id` from the response. This is your `tenantId`.

### Get Tenant Details
*   **Method**: `GET`
*   **Endpoint**: `/api/tenants/{id}`
*   **Example**: `/api/tenants/1`

---

## 2. Auth Service
**Base URL**: `http://localhost:8081`

### Register User
*   **Method**: `POST`
*   **Endpoint**: `/api/auth/signup`
*   **Body (JSON)**:
    ```json
    {
      "username": "jdoe",
      "email": "jdoe@acmecorp.com",
      "password": "password123",
      "tenantId": 1, 
      "role": ["admin"]
    }
    ```
    *(Replace `tenantId` with the ID from Step 1)*

### Login (Generate Token)
*   **Method**: `POST`
*   **Endpoint**: `/api/auth/signin`
*   **Body (JSON)**:
    ```json
    {
      "username": "jdoe",
      "password": "password123"
    }
    ```
*   **Note**: The response contains an `accessToken`. Use this token in the `Authorization` header (`Bearer <token>`) for secured endpoints if security is enabled.

---

## 3. Billing Service
**Base URL**: `http://localhost:8084`

### Get All Plans
*   **Method**: `GET`
*   **Endpoint**: `/api/billing/plans`

### Subscribe Tenant to a Plan
*   **Method**: `POST`
*   **Endpoint**: `/api/billing/subscribe`
*   **Query Parameters**:
    - `tenantId`: `1` (From Step 1)
    - `planName`: `Pro` (Or any valid plan name)
*   **Example URL**: `http://localhost:8084/api/billing/subscribe?tenantId=1&planName=Pro`
*   **Note**: This endpoint creates a subscription record for the tenant.

### Get Subscriptions for Tenant
*   **Method**: `GET`
*   **Endpoint**: `/api/billing/subscriptions/{tenantId}`
*   **Example**: `/api/billing/subscriptions/1`

---

## 4. Payment Service
**Base URL**: `http://localhost:8083`

### Process Payment
*   **Method**: `POST`
*   **Endpoint**: `/api/payments/process`
*   **Query Parameters**:
    - `tenantId`: `1`
    - `amount`: `100.00`
*   **Example URL**: `http://localhost:8083/api/payments/process?tenantId=1&amount=100.00`
