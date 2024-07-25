import { createRouter, createWebHistory } from 'vue-router';
import Register from '@/components/RegisterUser.vue';
import Login from '@/components/LoginUser.vue';
import ActivityList from '@/components/ActivityList.vue';
import UserList from '@/components/UserList.vue';

const routes = [
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  {
    path: '/',
    name: 'login',
    component: Login
  },
  {
    path: '/activities',
    name: 'activities',
    component: ActivityList
  },
  {
    path: '/users',
    name: 'users',
    component: UserList
  }
];

const router = createRouter({
    history: createWebHistory(),
    routes
  });
  
export default router;
