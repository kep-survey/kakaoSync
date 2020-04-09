<template>
    <div class="login">
        <div class="login-inputs">
            <v-text-field v-model="id" color="dark-primary" placeholder="아이디"></v-text-field>
            <v-text-field v-model="password" color="dark-primary" placeholder="비밀번호" type="password"></v-text-field>
        </div>
        <div class="login-btns">
            <v-btn color="blue" class="primary--text mb-4" width="100%">로그인</v-btn>
            <div class="additonal-menus mb-8">
                <span>아이디 찾기</span>
                <v-divider vertical class="mx-1" color="gray" style="display: inline;"></v-divider>
                <span>비밀번호 찾기</span>
                <v-divider vertical class="mx-1" color="gray" style="display: inline;"></v-divider>
                <span>회원가입</span>
            </div>
            <div class="kakao-login" style="width: 100%">
                <v-btn color="accent" class="dark-primary--text font-weight-bold" width="100%" v-on:click="onClickKakaoLogin()"><v-icon class="mr-2">mdi-chat</v-icon>카카오 로그인</v-btn>
            </div>
        </div>
    </div>
</template>

<script>
    import Kakao from '../assets/sdk/kakao.js';

    export default {
        name: 'Login',
        components: {},
		data: () => ({
            id: "",
            password: ""
        }),
		methods: {
            onClickKakaoLogin: () => {
                Kakao.Auth.authorize({
                    redirectUri: "http://localhost/additionalInfo"
                });
            }
        }
    }
</script>

<style scoped>
    .login-btns {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: flex-start;
    }
</style>
