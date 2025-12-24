import axios from 'axios';

const api = axios.create({
    headers: {
        'Content-Type': 'application/json',
    },
});

api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.status === 401) {
            localStorage.removeItem('token');
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

export const authApi = axios.create({ baseURL: import.meta.env.VITE_API_AUTH_URL });
export const tenantApi = axios.create({ baseURL: import.meta.env.VITE_API_TENANT_URL });
export const paymentApi = axios.create({ baseURL: import.meta.env.VITE_API_PAYMENT_URL });
export const billingApi = axios.create({ baseURL: import.meta.env.VITE_API_BILLING_URL });

// Apply interceptors to all instances
[authApi, tenantApi, paymentApi, billingApi].forEach(instance => {
    instance.interceptors.request.use(
        (config) => {
            const token = localStorage.getItem('token');
            if (token) {
                config.headers.Authorization = `Bearer ${token}`;
            }
            return config;
        },
        (error) => Promise.reject(error)
    );
});

export default api;
