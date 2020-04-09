<template>
    <div class="additionalInfo">
        <h1 class="text-center">납부자 번호 등록</h1>
        <v-text-field v-model="payerNumber" color="dark-primary" placeholder="납부자번호"></v-text-field>
        <v-btn v-on:click="onClickSubmitPayerNumber()" color="blue" class="primary--text mb-4" width="100%">제출</v-btn>
    </div>
</template>

<script>
export default {
    name: 'AdditionalInfo',
    components: {},
    data: () => ({
        payerNumber: "",
        code: ""
    }),
    methods: {
        onClickSubmitPayerNumber() {
            const axios = require('axios');

            const param = {
                code: this.code,
                payerNumber: this.payerNumber
            }
            
            const header = {
                'Content-Type': 'application/json'
            }
            
            axios.post('http://localhost/api/saveUser', param, header)
            .then(res => {
                this.$router.push({
                    name: 'Result',
                    params: {
                        'result': res.data.result,
                        'token': res.data.accessToken
                    }
                })
            })
        }
    },
    beforeRouteUpdate(to, from, next) {
        next();
    },
    created() {
        this.code = this.$route.query.code;
    }
}
</script>