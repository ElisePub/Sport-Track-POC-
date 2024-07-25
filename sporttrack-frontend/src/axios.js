import axios from 'axios';

const activityService = axios.create({
  baseURL: 'http://localhost:8081', // microservice des activités
  headers: {
    'Content-Type': 'application/json'
  }
});

const userService = axios.create({
  baseURL: 'http://localhost:8082', // microservice des utilisateurs
  headers: {
    'Content-Type': 'application/json'
  }
});

export { activityService, userService };

