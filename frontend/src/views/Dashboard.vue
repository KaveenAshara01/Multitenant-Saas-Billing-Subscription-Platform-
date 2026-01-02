<script setup lang="ts">
import { useAuthStore } from '../stores/auth';
import DefaultLayout from '../layouts/DefaultLayout.vue';

const authStore = useAuthStore();
</script>

<template>
  <DefaultLayout>
    <div class="max-w-6xl mx-auto">
      <h1 class="text-3xl font-bold text-white mb-6">Dashboard</h1>
      
      <!-- Welcome Section -->
      <div class="bg-gradient-to-r from-blue-600 to-indigo-600 rounded-2xl p-8 mb-8 text-white shadow-lg">
         <h2 class="text-2xl font-bold mb-2">Welcome back, {{ authStore.user?.username }}!</h2>
         <p class="opacity-90">Here's what's happening with your account today.</p>
         
         <div class="mt-6 flex gap-4 text-sm font-medium bg-white/10 p-4 rounded-xl inline-flex backdrop-blur-sm">
            <div class="px-4 border-r border-white/20">
                <span class="block opacity-70 text-xs uppercase tracking-wider">Tenant ID</span>
                {{ authStore.user?.tenantId }}
            </div>
            <div class="px-4">
                 <span class="block opacity-70 text-xs uppercase tracking-wider">Email</span>
                {{ authStore.user?.email }}
            </div>
         </div>
      </div>

      <!-- Quick Stats -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
        <div class="bg-dark-card border border-gray-800 rounded-2xl p-6">
            <h3 class="text-gray-400 font-medium mb-1">Current Plan</h3>
            <p class="text-2xl font-bold text-white">Free Tier</p>
            <router-link to="/subscription" class="text-blue-400 text-sm hover:underline mt-2 inline-block">Upgrade Plan</router-link>
        </div>
        <div class="bg-dark-card border border-gray-800 rounded-2xl p-6">
            <h3 class="text-gray-400 font-medium mb-1">Monthly Usage</h3>
            <p class="text-2xl font-bold text-white">24%</p>
            <div class="w-full bg-gray-700 h-1.5 rounded-full mt-3">
                <div class="bg-green-400 h-1.5 rounded-full" style="width: 24%"></div>
            </div>
        </div>
        <div class="bg-dark-card border border-gray-800 rounded-2xl p-6">
            <h3 class="text-gray-400 font-medium mb-1">Team Members</h3>
            <p class="text-2xl font-bold text-white">3 Active</p>
             <router-link to="/team" class="text-blue-400 text-sm hover:underline mt-2 inline-block">Manage Team</router-link>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>
