<template>
  <div class="result">
    <template v-if="result === true">
      <h1 class="text-center mb-4">등록 성공</h1>
      <v-btn to="/" width="100%" color="blue" class="secondary--text">서비스 이용하기</v-btn>
    </template>
    <template v-else>
      <h1 class="text-center mb-4">등록 실패</h1>
      <v-btn to="/" width="100%" color="blue" class="secondary--text">홈으로 돌아가기</v-btn>
    </template>
    
  </div>
</template>

<script>
  import Kakao from '../assets/sdk/kakao.js';

  export default {
    name: 'Result',
    components: {},
    data: () => ({
      result: false
    }),
    methods: {

    },
    created() {
      this.result = this.$route.params.result;

      if(this.$route.params.result === true) {
        Kakao.Auth.setAccessToken(this.$route.params.token);

        sessionStorage.setItem("isLogged", "true");
      }
    }
  }
</script>