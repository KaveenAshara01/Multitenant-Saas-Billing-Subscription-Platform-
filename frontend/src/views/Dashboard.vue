<script setup lang="ts">
import { useAuthStore } from '../stores/auth'

const authStore = useAuthStore()
</script>

<template>
  <div class="min-h-screen bg-dark text-white p-8">
    <h1 class="text-3xl font-bold mb-4">Dashboard</h1>
    <div class="bg-dark-lighter p-6 rounded-lg border border-gray-700">
      <p class="text-xl">Welcome, <span class="text-primary font-bold">{{ authStore.user?.username }}</span>!</p>
      <div class="mt-4">
        <p class="text-gray-400">Tenant ID: {{ authStore.user?.tenantId }}</p>
        <p class="text-gray-400">Email: {{ authStore.user?.email }}</p>
      </div>
      <button @click="authStore.logout" class="mt-6 px-4 py-2 bg-red-600 rounded hover:bg-red-700 transition">
        Logout
      </button>
    </div>
  </div>
</template>
