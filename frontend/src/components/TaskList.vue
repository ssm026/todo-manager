<template>
  <div>
    <task-regist v-on:task-regist="reload"></task-regist>
    <br>

    <div class="w-75 mx-auto">
      <b-table striped hover :items="taskList" :fields="fields">
        <template slot="name" slot-scope="row">
          <input :disabled="row.item.finishFlag == true" v-model="row.item.name" type="text" :value="row.item.name">
          <b-button :disabled="row.item.finishFlag == true" variant="primary" size="sm" @click.stop="modify(row.item.taskId, row.item.name)" >
            수정
          </b-button>
        </template>
        <template slot="referenceIds" slot-scope="row">
          <select>
              <option v-for='reference in row.item.referenceList' v-bind:key="reference.referenceTaskId" >
                {{reference.referenceTaskId}}
              </option>
          </select>
          <!-- <span v-for="reference in row.item.referenceList" v-bind:key="reference.referenceTaskId">
            {{ reference.referenceTaskId }}
          </span> -->
        </template>

        <!-- <template slot="finishFlag" slot-scop="row">
          {{ row.item.finishFlag }}
        </template>-->

        <template slot="finish" slot-scope="row">
          <b-button :disabled="row.item.finishFlag == true" variant="primary" size="sm" @click.stop="finish(row.item.name, row.item.taskId)" >
            완료
          </b-button>
        </template>
      </b-table>
    </div>

    <b-pagination size="sm" align="center" :total-rows="totalElements" v-model="currentPage" :per-page="5" @input="getTaskList(currentPage, perPage)">
    </b-pagination>
    <br>
    <b-btn v-on:click="logout()" size="sm" text="로그아웃" variant="primary">로그아웃</b-btn>
</template>

<script>
import TaskRegist from '@/components/TaskRegist'

export default {
  name: 'TaskList',
  components: {
    TaskRegist
  },
  data () {
    return {
      taskList: [],
      totalElements: 1,
      currentPage: 1,
      perPage: 5,
      fields: [
        {
          key: 'taskId',
          label: '아이디'
        },
        {
          key: 'name',
          label: '할일'
        },
        {
          key: 'referenceIds',
          label: '참조아이디'
        },
        {
          key: 'finishFlag',
          label: '완료여부'
        },
        {
          key: 'createTime',
          label: '작성일시'
        },
        {
          key: 'updateTime',
          label: '수정일시'
        },
        {
          key: 'finish',
          label: '완료'
        }
      ]
    }
  },
  computed: {
    hasResult: function () {
      return this.taskList.length > 0
    }
  },
  methods: {
    getTaskList: function () {
      const page = this.currentPage
      const param = `?page=${page}&size=${this.perPage}`
      this.$http.get('/api/v1/task' + param)
        .then((result) => {
          if (result.data.code !== 'TM200') {
            alert(result.data.message)
            return
          }
          this.taskList = result.data.data.taskList
          this.totalElements = result.data.data.totalElements
        })
        .catch((error) => {
          if (error.status === 403) {
            window.location.href = '/#/login'
          } else {
            alert(error.body.message)
          }
        })
      console.log(this.ids)
    },
    reload: function () {
      this.getTaskList(this.currentPage, this.perPage)
    },
    modify: function (taskId, name) {
      const data = {'name': name}
      this.$http.patch('/api/v1/task' + '/' + taskId, data)
        .then((result) => {
          if (result.data.code !== 'TM200') {
            alert(result.data.message)
          } else {
            alert('수정 되었습니다.')
            this.reload()
          }
        })
        .catch((error) => {
          if (error.status === 403) {
            window.location.href = '/#/login'
          } else {
            alert(error.body.message)
          }
        })
    },
    finish: function (name, taskId) {
      this.$http.post('/api/v1/task' + '/' + taskId + '/finish')
        .then((result) => {
          if (result.data.code !== 'TM200') {
            alert(result.data.message)
          } else {
            alert(name + ' 완료!')
            this.reload()
          }
        })
        .catch((error) => {
          if (error.status === 403) {
            window.location.href = '/#/login'
          } else {
            alert(error.body.message)
          }
        })
    },
    logout: function () {
      this.$http.post('/logout')
        .then((result) => {
          window.location.href = '/#/login'
        })
    }
  },
  beforeMount () {
    this.getTaskList(this.currentPage, this.perPage)
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
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
