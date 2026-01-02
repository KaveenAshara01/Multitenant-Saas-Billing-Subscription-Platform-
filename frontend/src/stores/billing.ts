import { defineStore } from 'pinia';
import { ref } from 'vue';
import { billingApi } from '../services/api';
import { useAuthStore } from './auth';

export interface Plan {
    id: number;
    name: string;
    price: number;
    description?: string;
}

export interface Subscription {
    id: number;
    plan: Plan;
    status: string;
    startDate: string;
    nextBillingDate: string;
}

export const useBillingStore = defineStore('billing', () => {
    const plans = ref<Plan[]>([]);
    const subscriptions = ref<Subscription[]>([]);
    const isLoading = ref(false);
    const error = ref<string | null>(null);

    const authStore = useAuthStore();

    async function fetchPlans() {
        isLoading.value = true;
        try {
            const response = await billingApi.get('/plans');
            plans.value = response.data;
        } catch (e: any) {
            error.value = e.message;
        } finally {
            isLoading.value = false;
        }
    }

    async function fetchSubscriptions() {
        if (!authStore.user?.tenantId) return;
        isLoading.value = true;
        try {
            const response = await billingApi.get(`/subscriptions/${authStore.user.tenantId}`);
            subscriptions.value = response.data;
        } catch (e: any) {
            error.value = e.message;
        } finally {
            isLoading.value = false;
        }
    }

    async function subscribe(planName: string) {
        if (!authStore.user?.tenantId) return;
        isLoading.value = true;
        try {
            // Using query params as per the backend controller
            await billingApi.post(`/subscribe?tenantId=${authStore.user.tenantId}&planName=${planName}`);
            await fetchSubscriptions(); // Refresh list
        } catch (e: any) {
            if (e.response && e.response.status === 409) {
                throw new Error("You already have an active subscription.");
            }
            throw e;
        } finally {
            isLoading.value = false;
        }
    }

    return { plans, subscriptions, isLoading, error, fetchPlans, fetchSubscriptions, subscribe };
});
