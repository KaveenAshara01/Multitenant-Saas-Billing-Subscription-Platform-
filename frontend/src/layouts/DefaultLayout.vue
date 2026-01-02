<script setup lang="ts">
import { useAuthStore } from '../stores/auth';
import { useRouter } from 'vue-router';
import { computed } from 'vue';
import { LayoutDashboard, Users, CreditCard, LogOut, CheckCircle } from 'lucide-vue-next';

const authStore = useAuthStore();
const router = useRouter();

const user = computed(() => authStore.user);

const handleLogout = () => {
  authStore.logout();
};

const menuItems = [
  { name: 'Dashboard', path: '/dashboard', icon: LayoutDashboard },
  { name: 'Team', path: '/team', icon: Users }, // Placeholder
  { name: 'Subscription', path: '/subscription', icon: CheckCircle }, // Placeholder
  { name: 'Billing', path: '/billing', icon: CreditCard }, // Placeholder
];

// Determine active state simply by current route starting with path
const isActive = (path: string) => router.currentRoute.value.path.startsWith(path);
</script>

<template>
  <div class="flex h-screen bg-dark text-gray-100 font-sans overflow-hidden">
    <!-- Sidebar -->
    <aside class="w-64 bg-dark-lighter border-r border-gray-800 flex flex-col">
      <div class="p-6 border-b border-gray-800">
        <h1 class="text-2xl font-bold bg-gradient-to-r from-blue-400 to-indigo-500 bg-clip-text text-transparent">
          SaaS Platform
        </h1>
        <p class="text-xs text-gray-500 mt-1 uppercase tracking-wider">
            {{ user?.tenantId ? `Tenant ID: ${user.tenantId}` : 'Admin Console' }}
        </p>
      </div>

      <nav class="flex-1 p-4 space-y-2 overflow-y-auto">
        <router-link
          v-for="item in menuItems"
          :key="item.path"
          :to="item.path"
          class="flex items-center px-4 py-3 rounded-lg transition-all duration-200 group"
          :class="[
            isActive(item.path)
              ? 'bg-blue-600/10 text-blue-400'
              : 'text-gray-400 hover:bg-gray-800 hover:text-white'
          ]"
        >
          <component 
            :is="item.icon" 
            class="w-5 h-5 mr-3 transition-colors"
            :class="isActive(item.path) ? 'text-blue-400' : 'text-gray-500 group-hover:text-white'"
          />
          <span class="font-medium">{{ item.name }}</span>
        </router-link>
      </nav>

      <div class="p-4 border-t border-gray-800">
        <div class="flex items-center mb-4 px-2">
            <div class="w-10 h-10 rounded-full bg-gradient-to-br from-gray-700 to-gray-600 flex items-center justify-center text-sm font-bold border border-gray-600">
                {{ user?.username?.charAt(0).toUpperCase() || 'U' }}
            </div>
            <div class="ml-3 overflow-hidden">
                <p class="text-sm font-medium truncate">{{ user?.username || 'User' }}</p>
                <p class="text-xs text-gray-500 truncate">{{ user?.email || 'email@example.com' }}</p>
            </div>
        </div>
        <button
          @click="handleLogout"
          class="flex items-center justify-center w-full px-4 py-2 text-sm text-red-400 bg-red-400/5 hover:bg-red-400/10 rounded-lg transition-colors"
        >
          <LogOut class="w-4 h-4 mr-2" />
          Sign Out
        </button>
      </div>
    </aside>

    <!-- Main Content -->
    <main class="flex-1 flex flex-col relative overflow-hidden">
        <!-- Topbar (Optional, mainly for mobile toggle or breadcrumbs, keeping simple for now) -->
        
        <!-- Page Content -->
        <div class="flex-1 overflow-auto p-8 relative">
            <slot />
        </div>
    </main>
  </div>
</template>
