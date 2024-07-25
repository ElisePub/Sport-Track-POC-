<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
};
</script>

<style>
h1 {
  text-align: center;
  padding-bottom: 50px;
  font-family: Recoleta, "Noto Serif", Georgia, Times, serif;
  font-size: 45px;
  font-weight: 400;
  letter-spacing: -.5px;
}

.cadre {
  display: flex;
  flex-wrap: wrap;
  justify-content:center;
  width: 100%;
}

.formConnect {
  width: 40rem;
}

.label {
  font-family: 'Roboto', sans-serif;
  direction: ltr;
  margin-bottom: 20px;
  text-align: left;
  transition: none;
}

p {
  text-align: center;
}

/*champs */
.champs {
  text-align: center;
}

.label {
  font-family: 'Roboto', sans-serif;
  direction: ltr;
  margin-bottom: 20px;
  text-align: left;
  transition: none;
}

.bouton-div {
  text-align: center;
}

input, select {
  text-align: center;
  padding: 12px 20px;
  margin: 8px 0;
  border: 1px solid #ccc;
  border-radius: 40px;
  box-sizing: border-box;
  width: 80%;
}

.animationBouton{
  padding-top:10px;
  margin-left:5rem;
  margin-right:5rem;
}

#animationBouton {
  animation-name: iconRun;
  animation-duration: 2s;
}

@keyframes iconRun {
  0% { transform: translateX(0); }
  100% { transform: translateX(90%); }
}

input, select {
  text-align: center;
  padding: 12px 20px;
  margin: 8px 0;
  border: 1px solid #ccc;
  border-radius: 40px;
  box-sizing: border-box;
  width: 80%;
}

.bouton-div {
  text-align: center;
}

.bouton-connection {
  text-align: center;
  background-color: #4f97e9;
  color: white;
  border: 1px solid #5368ca;
  border-radius: 40px;
  width: 80%;
  height: 40px;
  cursor: pointer;
  font-family: -apple-system, BlinkMacSystemFont, Segoe UI, Roboto, Oxygen-Sans, Ubuntu, Cantarell, Helvetica Neue, sans-serif;
}

.bouton-connection:hover {
  background-color: #5368ca;
}

.table-container {
    margin: 20px;
    overflow-x: auto;
}
table {
    width: 100%;
    border-collapse: collapse;
    text-align: left;
}
th, td {
    padding: 12px;
    border-bottom: 1px solid #dddddd;
}
th {
    background-color: #4f97e9;
    color: white;
}
tr:nth-child(even) {
    background-color: #f2f2f2;
}
tr:hover {
    background-color: #e9f2fd;
}

.header {
    background-color: #E0E7EE;
    padding: 1rem;
    border-radius: 15px;
    display: flex;
    justify-content: right;
    height: 2.5rem;
}

.header button {
    background-color: #4f97e9;
    color: white;
    border: none;
    padding: 10px 20px;
    cursor: pointer;
    border-radius: 5px;
}
.header button:hover {
    background-color: #5368ca;
    border: 1px solid white;
}
</style>
