<template>
  <v-app id="app">
    <v-card
    class="overflow-hidden fill-height"
    tile
    >
    <v-app-bar
      color="dark-primary"
      dark
      class="app-bar"
    >
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>

      <v-toolbar-title class="logo-title">KAKAO</v-toolbar-title>
    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      absolute
      temporary
    >
      <v-list
        nav
        dense
      >
        <v-list-item-group
          v-model="group"
          active-class="dark-primary--text text--accent-4"
        >
          <v-list-item to="/">
            <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
            </v-list-item-icon>
            <v-list-item-title>홈</v-list-item-title>
          </v-list-item>

          <template v-if="isLogged === true">
            <v-list-item v-on:click="onClickLogout()">
            <v-list-item-icon>
              <v-icon>mdi-logout</v-icon>
            </v-list-item-icon>
            <v-list-item-title >로그아웃</v-list-item-title>
            </v-list-item>
            <v-list-item v-on:click="onClickDisconnect()">
            <v-list-item-icon>
              <v-icon>mdi-bookmark-off-outline</v-icon>
            </v-list-item-icon>
            <v-list-item-title >카카오 연결끊기</v-list-item-title>
            </v-list-item>
          </template>

           <template v-else>
            <v-list-item to="Login">
            <v-list-item-icon>
              <v-icon>mdi-login</v-icon>
            </v-list-item-icon>
            <v-list-item-title>로그인</v-list-item-title>
            </v-list-item>
          </template>

        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
     <!-- 페이지 라우터 -->
    <v-content id="content-container" class="fill-height">
        <v-container justify-center class="fill-height secondary pa-12" fluid>
            <router-view style="margin-top: 1rem; width: 100%" class="fill-height" fluid></router-view>
        </v-container>
    </v-content>

  </v-card>

   
  </v-app>
</template>

<script>
  import Kakao from './assets/sdk/kakao.js';

  export default {
    name: 'App',
    components: {},
    data: () => ({
      drawer: false,
      group: null,
      isLogged: false
    }),
    methods: {
      onClickLogout() {
        if(!Kakao.Auth.getAccessToken()) {
          sessionStorage.clear();
          alert("이미 로그아웃 상태입니다!");
          this.$router.go();
        }

        Kakao.Auth.logout(function() {
          sessionStorage.clear();
          alert("로그아웃 성공!");
          window.location.reload();
        })
      },
      onClickDisconnect() {
        Kakao.API.request({
          url: '/v1/user/unlink',
          success: function(response) {
            console.log(response);
            sessionStorage.clear();
            alert("연결끊기 성공!");
            window.location.reload();
          },
          fail: function(error) {
            console.log(error);
            alert("연결끊기 실패!");
          }
        })
      }
    },
    created() {
      if(!Kakao.isInitialized()) {
        // 카카오 디벨로퍼스에서 앱 생성 후 {{REST API KEY}} 부분을 발급받으신 JavaScript KEY로 대체하시면 됩니다.
        Kakao.init("{{JavaScript KEY}}");
      }

      if (sessionStorage.getItem('isLogged') === "true") {
        this.isLogged = true;
      }
    },
    updated() {
      if (sessionStorage.getItem('isLogged') === "true") {
        this.isLogged = true;
      }
    }
  };
</script>

<style scoped>
  * {
      font-family : "Noto Sans KR", sans-serif !important;
    }

  .app-bar {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  
  .logo-title{
    font-size: 2rem !important;
  }
</style>