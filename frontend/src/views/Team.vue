<script setup lang="ts">
import DefaultLayout from '../layouts/DefaultLayout.vue';

const teamMembers = [
    { id: 1, name: 'Alice Johnson', role: 'Admin', email: 'alice@example.com', status: 'Active' },
    { id: 2, name: 'Bob Smith', role: 'Editor', email: 'bob@example.com', status: 'Active' },
    { id: 3, name: 'Charlie Brown', role: 'Viewer', email: 'charlie@example.com', status: 'Pending' },
];
</script>

<template>
  <DefaultLayout>
    <div class="max-w-6xl mx-auto">
      <div class="flex items-center justify-between mb-8">
         <h1 class="text-3xl font-bold text-white">Team Members</h1>
         <button class="px-4 py-2 bg-blue-600 hover:bg-blue-500 text-white rounded-lg font-semibold transition">
            + Invite Member
         </button>
      </div>
      
       <div class="bg-dark-card border border-gray-800 rounded-2xl overflow-hidden shadow-lg">
            <table class="w-full text-left text-gray-300">
                <thead class="bg-dark-lighter text-gray-500 uppercase text-xs font-semibold">
                    <tr>
                        <th class="px-6 py-4">Name</th>
                        <th class="px-6 py-4">Role</th>
                        <th class="px-6 py-4">Status</th>
                        <th class="px-6 py-4 text-right">Actions</th>
                    </tr>
                </thead>
                <tbody class="divide-y divide-gray-800">
                    <tr v-for="member in teamMembers" :key="member.id" class="hover:bg-gray-800/50 transition">
                        <td class="px-6 py-4">
                            <div class="flex items-center">
                                <div class="w-8 h-8 rounded-full bg-gradient-to-r from-purple-500 to-pink-500 flex items-center justify-center text-xs font-bold text-white mr-3">
                                    {{ member.name.charAt(0) }}
                                </div>
                                <div>
                                    <p class="text-white font-medium">{{ member.name }}</p>
                                    <p class="text-xs text-gray-500">{{ member.email }}</p>
                                </div>
                            </div>
                        </td>
                        <td class="px-6 py-4 text-sm">{{ member.role }}</td>
                        <td class="px-6 py-4">
                            <span 
                                class="px-2 py-1 rounded-full text-xs font-semibold"
                                :class="member.status === 'Active' ? 'bg-green-500/10 text-green-400' : 'bg-yellow-500/10 text-yellow-400'"
                            >
                                {{ member.status }}
                            </span>
                        </td>
                        <td class="px-6 py-4 text-right">
                             <span class="text-gray-500 hover:text-white cursor-pointer">Edit</span>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
  </DefaultLayout>
</template>
