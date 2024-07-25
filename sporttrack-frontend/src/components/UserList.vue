<!-- src/components/UserList.vue -->
<template>
  <div>
    <h1>Liste des Utilisateurs</h1>
  </div>
  <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>Nom</th>
                    <th>Email</th>
                </tr>
            </thead>
            <tbody v-for="user in users" :key="user.id">
                <tr>
                    <td>{{ user.name }}</td>
                    <td>{{ user.email }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import { userService } from '../axios';

export default {
  data() {
    return {
      users: []
    };
  },
  created() {
    userService.get('/users/', {
    })
    .then(response => {
      this.users = response.data;
    })
    .catch(error => {
      console.error('Erreur lors de la récupération des utilisateurs:', error);
    });
  }
};
</script>
