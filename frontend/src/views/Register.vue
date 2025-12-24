<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const username = ref('')
const email = ref('')
const password = ref('')
const tenantId = ref('')

const handleRegister = async () => {
    try {
        await authStore.register({ username: username.value, email: email.value, password: password.value, tenantId: tenantId.value })
        router.push('/login')
    } catch (error) {
        console.error('Registration failed', error)
        alert('Registration failed')
    }
}
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-dark text-white">
    <div class="bg-dark-lighter p-8 rounded-xl shadow-2xl w-full max-w-md border border-gray-800">
      <h2 class="text-3xl font-bold mb-6 text-center text-primary">Register</h2>
      <form @submit.prevent="handleRegister" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-400 mb-1">Username</label>
          <input v-model="username" type="text" class="w-full px-4 py-2 bg-dark border border-gray-700 rounded-lg focus:ring-2 focus:ring-primary focus:outline-none text-white" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-400 mb-1">Email</label>
          <input v-model="email" type="email" class="w-full px-4 py-2 bg-dark border border-gray-700 rounded-lg focus:ring-2 focus:ring-primary focus:outline-none text-white" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-400 mb-1">Password</label>
          <input v-model="password" type="password" class="w-full px-4 py-2 bg-dark border border-gray-700 rounded-lg focus:ring-2 focus:ring-primary focus:outline-none text-white" />
        </div>
        <div>
            <label class="block text-sm font-medium text-gray-400 mb-1">Tenant ID</label>
            <input v-model="tenantId" type="text" class="w-full px-4 py-2 bg-dark border border-gray-700 rounded-lg focus:ring-2 focus:ring-primary focus:outline-none text-white" />
        </div>
        <button type="submit" class="w-full py-3 bg-primary rounded-lg font-semibold hover:bg-blue-600 transition mt-4">
          Create Account
        </button>
      </form>
    </div>
  </div>
</template>
