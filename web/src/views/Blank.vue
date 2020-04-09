<template>
    <div>
        처리중입니다...
    </div>
</template>

<script>
import Kakao from '../assets/sdk/kakao.js';

export default {
    name: 'Home',
    components: {},
    data: () => ({
    }),
    methods: {
        onClickApi() {
            const axios = require('axios');

            const param = {
                code: this.$route.query.code,
                payerNumber: ""
            }
            
            const header = {
                'Content-Type': 'application/json'
            }

            axios.post('http://localhost/api/saveUser', param, header)
            .then(res => {
                Kakao.Auth.setAccessToken(res.data.accessToken);
                sessionStorage.setItem("isLogged", "true");
                this.$router.push({
                    name: 'Home'
                })
            })
            .error(err =>{
                alert(err);
            })
        }
    },
    created() {
        if (this.$route.query.code === undefined) {
            this.$router.push({
                name: 'Login'
            })
        } else {
            this.onClickApi();
        }
    }
}
</script>