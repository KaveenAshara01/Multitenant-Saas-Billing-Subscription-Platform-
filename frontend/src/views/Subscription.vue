<script setup lang="ts">
import { onMounted } from 'vue';
import DefaultLayout from '../layouts/DefaultLayout.vue';
import { useBillingStore } from '../stores/billing';
import { Check } from 'lucide-vue-next';

const billingStore = useBillingStore();

onMounted(() => {
    billingStore.fetchPlans();
    billingStore.fetchSubscriptions();
});

const handleSubscribe = async (planName: string) => {
    try {
        await billingStore.subscribe(planName);
        alert(`Successfully subscribed to ${planName}!`);
    } catch (e: any) {
        alert(e.message);
    }
};

const getFeatureList = (planName: string) => {
    switch (planName.toLowerCase()) {
        case 'basic': return ['Up to 5 Users', 'Basic Support', '10GB Storage'];
        case 'pro': return ['Up to 50 Users', 'Priority Support', '100GB Storage', 'Analytics'];
        case 'enterprise': return ['Unlimited Users', '24/7 Dedicated Support', 'Unlimited Storage', 'Custom Integrations'];
        default: return ['Standard Features'];
    }
};
</script>

<template>
  <DefaultLayout>
    <div class="max-w-6xl mx-auto">
        <h1 class="text-3xl font-bold text-white mb-2">Subscription Plans</h1>
        <p class="text-gray-400 mb-8">Choose the perfect plan for your business needs.</p>

        <!-- Loading State -->
        <div v-if="billingStore.isLoading" class="text-center py-12">
            <div class="animate-spin w-12 h-12 border-4 border-blue-500 border-t-transparent rounded-full mx-auto"></div>
            <p class="text-gray-400 mt-4">Loading plans...</p>
        </div>

        <!-- Error State -->
        <div v-if="billingStore.error" class="bg-red-500/10 border border-red-500/20 p-4 rounded-xl text-red-400 mb-6">
            {{ billingStore.error }}
        </div>

        <div v-else class="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div 
                v-for="plan in billingStore.plans" 
                :key="plan.id"
                class="bg-dark-card border border-gray-800 rounded-2xl p-8 hover:border-blue-500/50 transition-all duration-300 relative group"
            >
                <h3 class="text-xl font-bold text-white mb-2">{{ plan.name }}</h3>
                <div class="flex items-baseline mb-6">
                    <span class="text-4xl font-bold text-white">${{ plan.price }}</span>
                    <span class="text-gray-500 ml-2">/month</span>
                </div>

                <ul class="space-y-4 mb-8">
                    <li v-for="feature in getFeatureList(plan.name)" :key="feature" class="flex items-center text-gray-300">
                        <Check class="w-5 h-5 text-green-400 mr-3" />
                        {{ feature }}
                    </li>
                </ul>

                <button 
                    @click="handleSubscribe(plan.name)"
                    class="w-full py-3 rounded-xl font-semibold transition-all duration-200"
                    :class="[
                        plan.name === 'Pro' 
                        ? 'bg-blue-600 hover:bg-blue-500 text-white shadow-lg shadow-blue-600/20' 
                        : 'bg-gray-800 hover:bg-gray-700 text-white'
                    ]"
                >
                    Choose {{ plan.name }}
                </button>
            </div>
        </div>

        <!-- Current Subscription -->
        <div v-if="billingStore.subscriptions.length > 0" class="mt-12 bg-dark-card border border-gray-800 rounded-2xl p-8">
            <h2 class="text-2xl font-bold text-white mb-6">Active Subscription</h2>
            <div class="overflow-x-auto">
                <table class="w-full text-left text-gray-300">
                    <thead class="text-gray-500 uppercase text-sm border-b border-gray-800">
                        <tr>
                            <th class="pb-3">Plan</th>
                            <th class="pb-3">Status</th>
                            <th class="pb-3">Start Date</th>
                            <th class="pb-3">Next Billing</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-800">
                        <tr v-for="sub in billingStore.subscriptions" :key="sub.id" class="group">
                            <td class="py-4 text-white font-medium">{{ sub.plan.name }}</td>
                            <td class="py-4">
                                <span class="px-3 py-1 rounded-full text-xs font-semibold bg-green-500/10 text-green-400">
                                    {{ sub.status }}
                                </span>
                            </td>
                            <td class="py-4">{{ sub.startDate }}</td>
                            <td class="py-4">{{ sub.nextBillingDate }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
  </DefaultLayout>
</template>
