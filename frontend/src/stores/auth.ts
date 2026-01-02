import { defineStore } from 'pinia'
import { ref } from 'vue'
import { authApi } from '../services/api'
// import { useRouter } from 'vue-router'

export const useAuthStore = defineStore('auth', () => {
    const user = ref<any>(JSON.parse(localStorage.getItem('user') || 'null'))
    const token = ref<string | null>(localStorage.getItem('token'))
    // const router = useRouter() // Removed unused

    async function login(credentials: any) {
        const response = await authApi.post('/signin', credentials)
        token.value = response.data.token
        user.value = {
            id: response.data.id,
            username: response.data.username,
            email: response.data.email,
            tenantId: response.data.tenantId,
            roles: response.data.roles
        }

        if (token.value) {
            localStorage.setItem('token', token.value)
            localStorage.setItem('user', JSON.stringify(user.value))
        }
    }

    async function register(data: any) {
        await authApi.post('/signup', { ...data, role: ['user'] })
    }

    function logout() {
        token.value = null
        user.value = null
        localStorage.removeItem('token')
        localStorage.removeItem('user')
        // We can't easily use router here if not inside component, so caller should redirect
        window.location.href = '/login'
    }

    return { user, token, login, register, logout }
})
