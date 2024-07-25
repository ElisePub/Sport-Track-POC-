<template>
<html lang="fr">
  <head>
    <title>inscription SportTrack</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta name="generator" />
  </head>
  <body>
    <div class="header">
      <button @click="viewUser">Voir la liste des utilisateurs</button>
    </div>
    <h1>Rejoignez-nous</h1> 
    <div class="cadre">
      <form class="formConnect" @submit.prevent="registerUser" method="post"> 
        <fieldset>
          <legend>Créez votre compte</legend>
          <div class="champs">
            <label for="email" class="requis"></label>
            <input type="email" v-model="email" autocapitalize="off" autocomplete autocorrect="off" placeholder="E-mail"  required title="Veuillez remplir ce champ">
          </div>
          <div class="champs">
            <label for="passwd" class="requis"></label>
            <input type="password" v-model="password" autocapitalize="off" autocomplete autocorrect="off" placeholder="Password (lettres et chiffres)" pattern="(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*?&]{8,}"  title="Veuillez remplir ce champ">
          </div>
          <div class="champs">
            <label for="nom" class="requis"></label>
            <input type="text" v-model="name" autocapitalize="words" autocomplete autocorrect="off" placeholder="Nom" required title="Veuillez remplir ce champ">
          </div>
        </fieldset>
        <p>Déjà un compte ? <a href="/">connecte toi</a> </p>
        <div class="bouton-div">
          <button class="bouton-connection" locale="fr" type="submit">S'inscrire</button>
        </div>
        <div class="animationBouton" id="animationBouton">
          <img src="../../public/fonctionnement.png">
        </div>
      </form> 
    </div>
  </body>
</html>
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
      name: '',
      email: '',
      password: ''
    };
  },
  methods: {
    async registerUser() {
      try {
        const response = await userService.get('/users/login?email=' +this.email, {
        });
        let exist=response.data.exist;
        if (exist) {
          console.log('Cet utilisateur existe déjà');
        } else {
          await this.createUser();
        }
      } catch (error) {
        console.error('Erreur lors de la récupération des utilisateurs:', error);
      }
    },
    async createUser() {
      try {
        // Générer un salt
        const salt = await bcrypt.genSalt(10);
        // Hacher le mot de passe
        const hashedPassword = await bcrypt.hash(this.password, salt);

        const result = await userService.post('/users/', {
          name: this.name,
          email: this.email,
          password: hashedPassword
        })
        if(result != null){
          // Ajout du token lors de l'inscription
          localStorage.setItem('token', result.data.token);
          this.$router.push('/activities');
        } 
      } catch (error) {
        console.error('Erreur lors de l\'inscription:', error);
      }
    },
    viewUser(){
      this.$router.push('/users');
    }
  }
};
</script>
