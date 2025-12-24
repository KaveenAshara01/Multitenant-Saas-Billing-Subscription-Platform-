<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const username = ref('')
const password = ref('')

const handleLogin = async () => {
    try {
        await authStore.login({ username: username.value, password: password.value })
        router.push('/dashboard')
    } catch (error) {
        console.error('Login failed', error)
        alert('Login failed')
    }
}
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-dark text-white">
    <div class="bg-dark-lighter p-8 rounded-xl shadow-2xl w-full max-w-md border border-gray-800">
      <h2 class="text-3xl font-bold mb-6 text-center text-primary">Login</h2>
      <form @submit.prevent="handleLogin" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-400 mb-1">Username</label>
          <input v-model="username" type="text" class="w-full px-4 py-2 bg-dark border border-gray-700 rounded-lg focus:ring-2 focus:ring-primary focus:outline-none text-white" placeholder="Enter username" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-400 mb-1">Password</label>
          <input v-model="password" type="password" class="w-full px-4 py-2 bg-dark border border-gray-700 rounded-lg focus:ring-2 focus:ring-primary focus:outline-none text-white" placeholder="Enter password" />
        </div>
        <button type="submit" class="w-full py-3 bg-primary rounded-lg font-semibold hover:bg-blue-600 transition mt-4">
          Sign In
        </button>
      </form>
      <p class="mt-4 text-center text-gray-500">
        Don't have an account? <router-link to="/register" class="text-primary hover:underline">Register</router-link>
      </p>
    </div>
  </div>
</template>
