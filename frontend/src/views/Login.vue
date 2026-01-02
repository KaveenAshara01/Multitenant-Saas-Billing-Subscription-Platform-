<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '../stores/auth';
import { Lock, User } from 'lucide-vue-next';

const router = useRouter();
const authStore = useAuthStore();

const username = ref('');
const password = ref('');
const errorMsg = ref('');
const isLoading = ref(false);

const handleLogin = async () => {
    isLoading.value = true;
    errorMsg.value = '';
    try {
        await authStore.login({ username: username.value, password: password.value });
        router.push('/dashboard');
    } catch (error) {
        console.error('Login failed', error);
        errorMsg.value = 'Invalid credentials. Please try again.';
    } finally {
        isLoading.value = false;
    }
};
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-[#0f172a] relative overflow-hidden">
    <!-- Ambient Background Effects -->
    <div class="absolute top-0 left-1/4 w-96 h-96 bg-blue-500/20 rounded-full blur-[128px]"></div>
    <div class="absolute bottom-0 right-1/4 w-96 h-96 bg-indigo-500/10 rounded-full blur-[128px]"></div>
    
    <div class="w-full max-w-md p-8 relative z-10">
        <div class="bg-dark-card/50 backdrop-blur-xl border border-white/10 rounded-2xl p-8 shadow-2xl">
            <div class="text-center mb-8">
                <div class="inline-flex items-center justify-center w-12 h-12 rounded-xl bg-gradient-to-br from-blue-500 to-indigo-600 mb-4 shadow-lg shadow-blue-500/20">
                    <Lock class="w-6 h-6 text-white" />
                </div>
                <h2 class="text-3xl font-bold text-white mb-2">Welcome Back</h2>
                <p class="text-gray-400">Sign in to access your dashboard</p>
            </div>

            <form @submit.prevent="handleLogin" class="space-y-5">
                <div v-if="errorMsg" class="p-3 rounded-lg bg-red-500/10 border border-red-500/20 text-red-400 text-sm text-center">
                    {{ errorMsg }}
                </div>

                <div>
                    <label class="block text-sm font-medium text-gray-300 mb-1.5 ml-1">Username</label>
                    <div class="relative group">
                        <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none text-gray-500 group-focus-within:text-blue-400 transition-colors">
                            <User class="w-5 h-5" />
                        </div>
                        <input 
                            v-model="username" 
                            type="text" 
                            required
                            class="w-full pl-10 pr-4 py-3 bg-dark/50 border border-gray-700 rounded-xl focus:ring-2 focus:ring-blue-500/50 focus:border-blue-500 focus:outline-none text-white transition-all placeholder-gray-600"
                            placeholder="Enter your username" 
                        />
                    </div>
                </div>

                <div>
                    <div class="flex items-center justify-between mb-1.5 ml-1">
                        <label class="block text-sm font-medium text-gray-300">Password</label>
                        <a href="#" class="text-xs text-blue-400 hover:text-blue-300 transition-colors">Forgot password?</a>
                    </div>
                    <div class="relative group">
                        <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none text-gray-500 group-focus-within:text-blue-400 transition-colors">
                            <Lock class="w-5 h-5" />
                        </div>
                        <input 
                            v-model="password" 
                            type="password" 
                            required
                            class="w-full pl-10 pr-4 py-3 bg-dark/50 border border-gray-700 rounded-xl focus:ring-2 focus:ring-blue-500/50 focus:border-blue-500 focus:outline-none text-white transition-all placeholder-gray-600"
                            placeholder="Enter your password" 
                        />
                    </div>
                </div>

                <button 
                    type="submit" 
                    :disabled="isLoading"
                    class="w-full py-3.5 bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-500 hover:to-indigo-500 text-white font-semibold rounded-xl shadow-lg shadow-blue-500/20 transition-all transform active:scale-[0.98] disabled:opacity-70 disabled:cursor-not-allowed flex items-center justify-center"
                >
                    <svg v-if="isLoading" class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                        <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                    </svg>
                    {{ isLoading ? 'Signing In...' : 'Sign In' }}
                </button>
            </form>

            <div class="mt-8 pt-6 border-t border-white/5 text-center">
                <p class="text-gray-500 text-sm">
                    Don't have an account? 
                    <router-link to="/register" class="text-blue-400 hover:text-blue-300 font-medium hover:underline transition-colors">
                        Create an account
                    </router-link>
                </p>
            </div>
        </div>
    </div>
  </div>
</template>
