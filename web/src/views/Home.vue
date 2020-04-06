<template>
  <div class="home">
    <h1 class="text-center">Home View</h1>
  </div>
</template>

<script>
import Kakao from '../assets/sdk/kakao.js';

export default {
    name: 'Home',
    components: {},
    data: () => ({
        temp: ""
    }),
    methods: {

    },
    created() {
        const isKakaoBrowser = window.navigator.userAgent.indexOf('KAKAOTALK');
        this.temp = Kakao.Auth.getAccessToken();

        if (isKakaoBrowser > -1 && sessionStorage.getItem("isLogged") !== "true") {
            Kakao.Auth.authorize({
                redirectUri: "http://vere.es.onkakao.net/blank",
                autoLogin: true
            }); 
        }
    }   
}
</script>
