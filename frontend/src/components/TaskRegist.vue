<template>
  <div class="w-50 mx-auto">
    <b-input-group v-for="size in ['sm']" :key="size" :size="size" class="mb-3" prepend="할일">
      <b-form-input v-model="taskName"/>
    </b-input-group>

    <b-input-group v-for="size in ['sm']" :key="size" :size="size" class="mb-3" prepend="참조 리스트">
      <b-form-input disabled v-model="referenceTaskIdList"/>
      <select v-model="selected" v-on:change="setReferenceTaskIdList">
        <option v-for='taskId in taskIdList' v-bind:key="taskId" >
          {{ taskId }}
        </option>
      </select>
    </b-input-group>

    <div class="col-xs pb-1">
        <b-btn v-on:click="clearAllFields()" size="sm" text="초기화" variant="primary">초기화</b-btn>
        <b-btn v-on:click="registTask()" size="sm" text="등록" variant="primary">등록</b-btn>
    </div>

    <div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TaskRegist',
  data () {
    return {
      email: '',
      referenceTaskIdList: [],
      taskName: '',
      taskIdList: [],
      selected: 0
    }
  },
  methods: {
    reload: function () {
      this.getNotFinishedTaskList()
      this.taskName = ''
      this.referenceTaskIdList = []
      this.selected = 0
    },
    registTask: function () {
      const data = {'name': this.taskName, 'referenceTaskIdList': this.referenceTaskIdList}
      this.$http.post('/api/v1/task', data)
        .then((result) => {
          if (result.data.code !== 'TM200') {
            alert(result.data.message)
            return
          }
          this.reload()
          this.$emit('task-regist')
        })
        .catch((error) => {
          if (error.status === 403) {
            window.location.href = '/#/login'
          } else {
            alert(error.body.message)
          }
        })
    },
    getNotFinishedTaskList: function () {
      this.$http.get('/api/v1/task/id/list')
        .then((result) => {
          if (result.data.code !== 'TM200') {
            alert(result.data.message)
            return
          }
          this.taskIdList = result.data.data.taskIdList
        })
        .catch((error) => {
          if (error.status === 403) {
            window.location.href = '/#/login'
          } else {
            alert(error.body.message)
          }
        })
    },
    setReferenceTaskIdList: function () {
      this.referenceTaskIdList.push(this.selected)
    },
    clearAllFields: function () {
      this.taskName = ''
      this.referenceTaskIdList = []
      this.selected = 0
    }
  },
  beforeMount () {
    this.getNotFinishedTaskList()
  }
}
</script>
