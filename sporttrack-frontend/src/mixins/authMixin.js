export default {
  created() {
    const token = localStorage.getItem('token');
    if (!token && this.$route.name === 'activities') {
      this.$router.push('/');
    }
  }
};