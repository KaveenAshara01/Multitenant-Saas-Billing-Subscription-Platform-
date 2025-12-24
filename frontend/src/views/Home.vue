<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()
</script>

<template>
  <div class="min-h-screen bg-dark flex flex-col items-center justify-center text-white">
    <h1 class="text-5xl font-bold mb-4 bg-gradient-to-r from-primary to-purple-500 bg-clip-text text-transparent">
      SaaS Billing Platform
    </h1>
    <p class="text-xl text-gray-400 mb-8 max-w-lg text-center">
      A modern, senior-level subscription management solution.
    </p>
    <div class="flex gap-4">
      <button @click="router.push('/login')" class="px-6 py-3 bg-primary rounded-lg font-semibold hover:bg-blue-600 transition">
        Get Started
      </button>
      <button @click="router.push('/register')" class="px-6 py-3 bg-dark-lighter rounded-lg font-semibold hover:bg-gray-800 transition border border-gray-700">
        Register
      </button>
    </div>
  </div>
</template>
