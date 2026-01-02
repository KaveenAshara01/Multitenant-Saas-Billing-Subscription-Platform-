import { defineStore } from 'pinia';
import { ref } from 'vue';
import { paymentApi } from '../services/api';
import { useAuthStore } from './auth';

export interface Payment {
    id: number;
    amount: number;
    status: string;
    date: string;
    method: string;
}

export const usePaymentStore = defineStore('payment', () => {
    const history = ref<Payment[]>([]);
    const isLoading = ref(false);
    const authStore = useAuthStore();

    async function processPayment(amount: number) {
        if (!authStore.user?.tenantId) return;
        isLoading.value = true;
        try {
            await paymentApi.post(`/process?tenantId=${authStore.user.tenantId}&amount=${amount}`);
            // Add mock record to history since backend doesn't return history yet
            history.value.unshift({
                id: Date.now(),
                amount,
                status: 'SUCCESS',
                date: new Date().toISOString().split('T')[0] as string,
                method: 'Credit Card'
            });
        } catch (e) {
            throw e;
        } finally {
            isLoading.value = false;
        }
    }

    async function fetchHistory() {
        // Mock implementation because backend endpoint is missing
        isLoading.value = true;
        setTimeout(() => {
            history.value = [
                { id: 1, amount: 100.00, status: 'SUCCESS', date: '2023-10-01', method: 'Credit Card' },
                { id: 2, amount: 100.00, status: 'SUCCESS', date: '2023-11-01', method: 'Credit Card' },
                { id: 3, amount: 100.00, status: 'SUCCESS', date: '2023-12-01', method: 'Credit Card' },
            ];
            isLoading.value = false;
        }, 500);
    }

    return { history, isLoading, processPayment, fetchHistory };
});
