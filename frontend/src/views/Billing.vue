<script setup lang="ts">
import { onMounted, ref } from 'vue';
import DefaultLayout from '../layouts/DefaultLayout.vue';
import { usePaymentStore } from '../stores/payment';
import { CreditCard, DollarSign, Calendar, ArrowUpRight } from 'lucide-vue-next';

const paymentStore = usePaymentStore();
const amount = ref(100);

onMounted(() => {
    paymentStore.fetchHistory();
});

const handlePayment = async () => {
    try {
        await paymentStore.processPayment(amount.value);
        alert('Payment successful!');
    } catch (e: any) {
        alert('Payment failed: ' + e.message);
    }
};
</script>

<template>
  <DefaultLayout>
    <div class="max-w-6xl mx-auto">
        <h1 class="text-3xl font-bold text-white mb-8">Billing & Invoices</h1>

        <div class="grid grid-cols-1 lg:grid-cols-2 gap-8 mb-12">
             <!-- Payment Method (Mock) -->
             <div class="bg-dark-card border border-gray-800 rounded-2xl p-8">
                <h2 class="text-xl font-bold text-white mb-6 flex items-center">
                    <CreditCard class="w-6 h-6 mr-3 text-blue-400" />
                    Payment Method
                </h2>
                <div class="flex items-center p-4 bg-gray-800/50 rounded-xl mb-6">
                    <div class="w-12 h-8 bg-gray-700 rounded mr-4"></div>
                    <div>
                        <p class="text-white font-medium">Visa ending in 4242</p>
                        <p class="text-sm text-gray-500">Expires 12/24</p>
                    </div>
                    <button class="ml-auto text-blue-400 text-sm hover:underline">Edit</button>
                </div>
                 <button class="w-full py-2 bg-gray-800 text-gray-300 rounded-lg hover:bg-gray-700 transition">
                    + Add New Method
                 </button>
             </div>

             <!-- Quick Payment -->
             <div class="bg-gradient-to-br from-blue-900/40 to-indigo-900/40 border border-blue-500/20 rounded-2xl p-8">
                 <h2 class="text-xl font-bold text-white mb-6 flex items-center">
                    <DollarSign class="w-6 h-6 mr-3 text-blue-400" />
                    Make a Payment
                </h2>
                <div class="mb-4">
                    <label class="block text-sm text-gray-400 mb-2">Amount to Pay</label>
                    <div class="flex items-center">
                        <span class="text-2xl text-gray-300 mr-2">$</span>
                        <input 
                            v-model="amount" 
                            type="number" 
                            class="bg-transparent text-3xl font-bold text-white focus:outline-none w-full border-b border-gray-600 focus:border-blue-400 pb-1"
                        />
                    </div>
                </div>
                <button 
                    @click="handlePayment"
                    :disabled="paymentStore.isLoading"
                    class="w-full py-3 bg-blue-600 hover:bg-blue-500 text-white rounded-xl font-semibold shadow-lg shadow-blue-600/20 transition-all"
                >
                    {{ paymentStore.isLoading ? 'Processing...' : 'Pay Now' }}
                </button>
             </div>
        </div>

        <!-- History -->
        <div class="bg-dark-card border border-gray-800 rounded-2xl p-8">
            <h2 class="text-2xl font-bold text-white mb-6 flex items-center">
                <Calendar class="w-6 h-6 mr-3 text-gray-400" />
                Payment History
            </h2>
             <div class="overflow-x-auto">
                <table class="w-full text-left text-gray-300">
                    <thead class="text-gray-500 uppercase text-sm border-b border-gray-800">
                        <tr>
                            <th class="pb-3 pl-4">Date</th>
                            <th class="pb-3">Amount</th>
                            <th class="pb-3">Status</th>
                            <th class="pb-3">Method</th>
                            <th class="pb-3 text-right pr-4">Invoice</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-800">
                         <tr v-if="paymentStore.isLoading">
                            <td colspan="5" class="py-8 text-center text-gray-500">Loading history...</td>
                        </tr>
                        <tr v-else v-for="pay in paymentStore.history" :key="pay.id" class="group hover:bg-gray-800/30 transition">
                            <td class="py-4 pl-4">{{ pay.date }}</td>
                            <td class="py-4 font-medium text-white">${{ pay.amount.toFixed(2) }}</td>
                            <td class="py-4">
                                <span class="px-3 py-1 rounded-full text-xs font-semibold bg-green-500/10 text-green-400">
                                    {{ pay.status }}
                                </span>
                            </td>
                            <td class="py-4 text-gray-400">{{ pay.method }}</td>
                            <td class="py-4 text-right pr-4">
                                <button class="text-gray-500 hover:text-white transition">
                                    <ArrowUpRight class="w-4 h-4 ml-auto" />
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
  </DefaultLayout>
</template>
