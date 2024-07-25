<template>
<body>
    <div class="header">
        <button @click="logoutUser">Déconnexion</button>
    </div>
    <h1>Bienvenue sur SportTrack</h1> 
    
    <div class="search-bar">
      <form class="search-form" @submit.prevent="searchActivity">
        <input type="text" v-model="searchword" placeholder="Rechercher une activité...">
        <button class="searchButton" type="submit">Rechercher</button>
      </form>
    </div>

    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>Description</th>
                    <th>Distance</th>
                    <th>Fréquence Min</th>
                    <th>Fréquence Max</th>
                    <th>Date</th>
                </tr>
            </thead>
            
            <tbody v-for="activity in activities" :key="activity.id">
                <tr>
                    <td>{{ activity.description }}</td>
                    <td>{{ activity.distance }}</td>
                    <td>{{ activity.freqMin }}</td>
                    <td>{{ activity.freqMax }}</td>
                    <td>{{ activity.date }}</td>
                </tr>
            </tbody>
        </table>
    </div>
    
</body>
</template>

<style scoped>

.headerButton{
  margin-right:1rem;
}

.search-form{
  display:flex;
  flex-direction:row;
  width: 60%;
  padding: 10px;
  justify-content:center;
  align-items:center;
}

.search-bar {
    margin: 20px;
    display: flex;
    justify-content: center;
}

.searchButton{
  border: 1px solid #4f97e9;
  border-radius: 5px;
  background-color: #4f97e9;
  color:white;
  height:2.5rem;
}

.searchButton:hover {
    background-color: #5368ca;
    border: 1px solid white;
}

.search-bar input {
    border: 1px solid #4f97e9;
    border-radius: 5px;
}
</style>



<script>
import { activityService } from '../axios';
import authMixin from '../mixins/authMixin';

export default {
  mixins: [authMixin],
  data() {
    return {
      activities: [],
      searchword: ''
    };
  },
  methods: {
    logoutUser() {
      // Suppression du token lors de la déconnexion
      localStorage.removeItem('token');
      this.$router.push('/');
    },
    searchActivity(){
      activityService.get('/activities/'+ this.searchword, {
      })
      .then(response => {
        this.activities = response.data;
      })
      .catch(error => {
        console.error('Erreur lors de la récupération des activités:', error);
      });
    },
  },
  created() {
    activityService.get('/activities/', {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
    .then(response => {
      this.activities = response.data;
    })
    .catch(error => {
      console.error('Erreur lors de la récupération des activités:', error);
    });
  }
};
</script>