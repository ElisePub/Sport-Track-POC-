<template>
  <div class="header">
      <button @click="viewUser">Voir la liste des utilisateurs</button>
  </div>
  <h1>Connectez-vous à votre compte</h1> 
  <div class="cadre">
    <form class="formConnect" @submit.prevent="loginUser" method="get">
      <fieldset>
        <legend>Informations de connexion</legend>
        <div class="champs">
          <label for="email" class="label"></label>
          <input type="email" v-model="email" name="email" placeholder="E-mail" autocapitalize="off" autocomplete="off" autocorrect="off" required title="Veuillez remplir ce champ">
        </div>
        <div class="champs">
          <label for="passwd" class="label"></label>
          <input type="password" v-model="password" name="passwd" autocapitalize="off" autocomplete="off" autocorrect="off" placeholder="Password" required title="Veuillez remplir ce champ">
        </div>
        
      </fieldset>
      <p>Pas encore inscrit ? crée ton compte <router-link to="/register">ici</router-link></p>
      <div class="bouton-div">
        <button class="bouton-connection" locale="fr" type="submit">Continuer</button>
      </div>
      <div class="animationBouton" id="animationBouton">
          <img src="../../public/fonctionnement.png">
      </div>
    </form>
  </div>
</template>

<style scoped>

.header {
  justify-content:center;
}
</style>

<script>
import { userService } from '../axios';
import bcrypt from 'bcryptjs';

export default {
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {
    loginUser() {
        userService.get('/users/login?email=' +this.email, {
      })
      .then(response => {
        let self=this;
        let exist = response.data.exist;
        if(exist){
          // Comparaison du mdp hashé
          let hshpasswd = response.data.user.password;
          bcrypt.compare(this.password, hshpasswd, function(err, result) { 
            console.log(result)
            if(result){
              localStorage.setItem('token', response.data.token);
              self.$router.push('/activities');
            }
            else {
              console.log("Password doesn't match");
            }
          });   
        }
      })
      .catch(error => {
        console.error('Erreur lors de la récupération de l\'utilisateur:', error);
      });
    },
    viewUser(){
      this.$router.push('/users');
    }
  }
};
</script>