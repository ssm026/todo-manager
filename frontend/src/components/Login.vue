<template>
  <div class="w-25 mx-sm-auto">
    <b-input-group v-for="size in ['sm']" :key="size" :size="size" class="mb-3" prepend="아이디">
      <b-form-input v-model="name"/>
    </b-input-group>

    <b-input-group v-for="size in ['sm']" :key="size" :size="size" class="mb-3" prepend="비밀번호">
      <b-form-input type="password" v-model="password"/>
    </b-input-group>

    <div class="col-xs pb-1">
      <b-btn v-on:click="join()" size="sm" text="사용자 등록" variant="primary">사용자 등록</b-btn>
      <b-btn v-on:click="login()" size="sm" text="로그인" variant="primary">로그인</b-btn>
    </div>

    <div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      name: '',
      password: ''
    }
  },
  methods: {
    join: function () {
      const data = {'name': this.name, 'password': this.password}
      this.$http.post('/api/v1/member/join', data)
        .then((result) => {
          if (result.data.code !== 'TM200') {
            alert(result.data.message)
          } else {
            alert('사용자 등록이 완료되었습니다. 로그인 해주세요.')
          }
        })
        .catch((error) => {
          if (error.status === 403) {
            this.$router.push('login')
          } else {
            alert(error.body.message)
          }
        })
    },
    login: function () {
      const data = {'name': this.name, 'password': this.password}
      this.$http.post('/api/v1/member/login', data)
        .then((result) => {
          if (result.data.code !== 'TM200') {
            alert(result.data.message)
          } else {
            console.log(result.data)
            this.$router.push('/')
          }
        })
        .catch((error) => {
          console.log(error)
        })
    }
  }
}
</script>

<style scoped>
  h1, h2 {
    font-weight: normal;
  }
  ul {
    list-style-type: none;
    padding: 0;
  }
  li {
    display: inline-block;
    margin: 0 10px;
  }
  a {
    color: #42b983;
  }
</style>
