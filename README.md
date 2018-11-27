# TODO Task Manager

## 1.Requirements
* 사용자는 텍트스로 된 할 일을 추가 할 수 있다.
  * 할 일 추가 시 다른 할 일 들을 참조 걸 수 있다. 
  * 참조는 다른 할 일의 id를 명시하는 형태로 구현한다.
* 사용자는 할 일을 수정 할 수 있다.
* 사용자는 할 일 목록을 조회 할 수 있다.
  * 저회시 작성일, 최종 수정일, 내용이 조회 가능하다.
  * 할 일 목록은 페이징 기능이 있다.
* 사용자는 할 일을 완료 처리 할 수 있다.
  * 완료 처리시 참조가 걸린 완료되지 않은 할 일이 있다면 완료 처리 할 수 없다.  
* 웹 어플리케이션으로 개발
* 서버는 REST API로 구현
* 가능하면 in-memory DB 사용
* 단위테스트 필수, 통합테스트는 선택
* README.md 파일에 문제해결 전략 및 프로젝트 빌드 실행방법 명시

## 2.Problem Solving
* frontend 기술 스택
  * vue.js 2.5.2, bootstrap, bootstrap-vue 활용.
  * Single Page Application 으로 개발.
* backend 기술 스택
  * Java 1.8
  * Spring boot 2.1.0
  * H2 DB
  * JPA
  * REST API
* 할 일 등록시 고려사항
  * 할 일 이름 중복체크를 할것인지?
    * id 로 구분 가능하기 때문에 중복 상관 없음.
  * 참조 id 는 어떤방식으로 입력받을지?
    * "," 로 구분된 string - parsing 필요. 입력시 오타 발생 가능성 있음.
    * 현재 등록된 id 리스트를 select 박스로 선택.
  * 입력받은 참조 id 가 이미 완료됐거나 없을경우 alert 띄울지? 아니면 있는것만 insert 할지
    * select 박스로 선택하게 하고 이미 완료된 id 는 select 박스에 노출 시키지 않는다.
    * 만약 이미 완료됐거나 없는 id 일 경우 에러코드를 리턴한다.
  * 순환참조가 발생한다면?
    * 등록시 참조 id 가 있는경우 무조건 자신보다 먼저 등록된 id 를 참조하므로 이슈 없음.
    * 수정시 할 일 이름만 수정할 수 있도록 한다.
* 할 일 수정시 고려사항
  * 참조 id의 변경을 허용 할 것인지?
    * 할 일 수정시 자신보다 나중에 등록된 id를 참조하면 순환참조 이슈가 발생 할 수 있으므로 수정 불가하도록 한다.
  * 수정 가능 필드는?
    * 순환참조 이슈 방지를 위해 할 일 이름만 변경 가능.
* 할 일 완료시 고려사항
  * 참조된 할 일이 완료 됐는지 체크.
* Spring Security 사용하여 회원 관리

## 3.API specifications
### Custom Response Code List
| code | 설명 |
| :-----: | :----: |
| TM200 | 성공 |
| TM400 | 잘못된 입력값 입니다. |
| TM404 | 요청한 할 일이 존재하지 않습니다. |
| TM405 | 허용하지 않는 method 입니다. |
| TM415 | 허용하지 않는 media type 입니다. |
| TM500 | 요청이 실패했습니다. |
| TM600 | 참조 불가능한 ID 가 포함되어있습니다. |
| TM601 | 참조된 할 일을 먼저 완료하세요. |
| TM602 |  아이디와 비밀번호를 확인해주세요. |

### 공통 응답 구조
* **code** : custom 응답코드
* **message** : code 에 맵핑되는 메세지. code 가 TM200이 아닐경우 이 내용을 alert으로 보여준다.
* **data** : 각 api 별 응답 데이터 object. 아래의 각 Response 규격에는 data key 하위의 내용만 기술한다.
```
< HTTP/1.1 200
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Sun, 25 Nov 2018 13:09:29 GMT
<
* Connection #1 to host 127.0.0.1 left intact
{
    "code" : "TM200",
    "message" : "success",
    "data" : {
        "id" : 1,
        "name" : "task"
    }
}
```

### 회원가입
* 회원가입.
#### Specification
* **Method** : POST
* **URL** : http://:server_url/api/v1/member/join
* **Content-Type** : application/json
* **Parameters**
 
| 필드명 | 타입 | 필수여부 | 기본값 | 설명 |
| :----: | :----: | :----: | :----: | :----: |
| name | String | Required | N/A | 유저명(아이디) |
| password | String | Required | N/A | 비밀번호 |

* **Request Example**
```
curl -X POST 'http://:server_url/api/v1/member/finish' -d '{
    "name": "jyp",
    "password": "1234567890"
}'
```

* **Response**

| 필드명 | 타입 | 필수여부 | 기본값 | 설명 |
| :----: | :----: | :----: | :----: | :----: |
|  |  |  |  |  |

* **Response Example**
```
{
    "code": "TM200",
    "message": "success"
}
```

### 로그인
* 로그인.
#### Specification
* **Method** : POST
* **URL** : http://:server_url/api/v1/member/login
* **Content-Type** : application/json
* **Parameters**
 
| 필드명 | 타입 | 필수여부 | 기본값 | 설명 |
| :----: | :----: | :----: | :----: | :----: |
| name | String | Required | N/A | 유저명(아이디) |
| password | String | Required | N/A | 비밀번호 |

* **Request Example**
```
curl -X POST 'http://:server_url/api/v1/member/login' -d '{
    "name": "jyp",
    "password": "1234567890"
}'
```

* **Response**

| 필드명 | 타입 | 필수여부 | 기본값 | 설명 |
| :----: | :----: | :----: | :----: | :----: |
|  |  |  |  |  |

* **Response Example**
```
{
    "code": "TM200",
    "message": "success"
}
```

### 할 일 목록 조회
* 할 일 목록을 조회하여 paging 응답
#### Specification
* **Method** : GET
* **URL** : http://:server_url/api/v1/task
* **Content-Type** : application/json
* **Parameters**
 
| 필드명 | 타입 | 필수여부 | 기본값 | 설명 |
| :----: | :----: | :----: | :----: | :----: |
| size | Number | Optional | 10 | 페이지당 사이즈 |
| page | Number | Optional | 1 | 페이지 번호(1부터 시작) |

* **Request Example**
```
curl -X GET 'http://:server_url/api/v1/task?size=10&page=1'
```

* **Response**

| 필드명 | 필드명 | 필드명 | 타입 | 필수여부 | 기본값 | 설명 |
| :----: | :----: | :----: | :----: | :----: | :----: | :----: |
| taskList |  | | Array | Required | N/A | 조회 된 할 일 리스 |
|  | taskId | | Number | Optional | N/A | 할 일 아이디 |
|  | name | | String | Optional | N/A | 할 일 이름 |
|  | finishFlag | | Boolean | Optional | N/A | 할 일 완료 여부 |
|  | createTime | | String | Optional | N/A | 할 일 등록 시간 (YYYY-MM-DD HH:mm:ss) |
|  | updateTime | | String | Optional | N/A | 할 일 수정 시간 (YYYY-MM-DD HH:mm:ss) |
|  | referenceList | | Array | Optional | N/A | 참조 한 할 일리스트|
|  |  | referenceTaskId | Number | Required | 1 | 참조 한 할 일 아이디 |
| totalElements |  | | Number | Required | 1 | 페이지 번호(1부터 시작) |

* **Response Example**
```
{
    code: "TM200",
    message: "success",
    data: {
        taskList: [
            {
                taskId: 1,
                name: "집안일",
                finishFlag: false,
                createTime: "2018-11-24 14:56:56",
                updateTime: null,
                referenceList: [ ]
            },
            {
                taskId: 2,
                name: "빨래",
                finishFlag: false,
                createTime: "2018-11-24 14:56:56",
                updateTime: null,
                referenceList: [
                    {
                    referenceId: 1,
                    referenceTaskId: 1
                    }
                ]
            }
        ],
        totalElements: 4
    }
}
```

### 참조 가능 ID 리스트 조회
* 참조 가능한 ID 리스트를 조회한다.
#### Specification
* **Method** : GET
* **URL** : http://:server_url/api/v1/task/id/list
* **Content-Type** : application/json
* **Parameters**
 
| 필드명 | 타입 | 필수여부 | 기본값 | 설명 |
| :----: | :----: | :----: | :----: | :----: |
|  |  |  |  |  |

* **Request Example**
```
curl 'http://:server_url/api/v1/task/id/list
```

* **Response**

| 필드명 | 타입 | 필수여부 | 기본값 | 설명 |
| :----: | :----: | :----: | :----: | :----: |
| taskIdList | Array | Required | N/A | 참조 가능한 ID 리스트 |

* **Response Example**
```
{
    "code":"TM200",
    "message":"success",
    "data":{
        "taskIdList":[
            2,3
         ]
    }
}
```

### 할 일 등록
* 할 일을 등록한다.
#### Specification
* **Method** : POST
* **URL** : http://:server_url/api/v1/task
* **Content-Type** : application/json
* **Parameters**
 
| 필드명 | 타입 | 필수여부 | 기본값 | 설명 |
| :----: | :----: | :----: | :----: | :----: |
| name | String | Required | N/A | 할일 이름 |
| referenceTaskIdList | Array | Optional | null | 참조 할 아이디 리스트 |

* **Request Example**
```
curl -X POST 'http://:server_url/api/v1/task' -d '{
    "name": "집안일",
    "referenceTaskIdList": [1,2]
}'
```

* **Response**

| 필드명 | 타입 | 필수여부 | 기본값 | 설명 |
| :----: | :----: | :----: | :----: | :----: |
|  |  |  |  |  |

* **Response Example**
```
{
    "code": "TM200",
    "message": "success"
}
```

### 할 일 이름 수정
* 할 일 이름을 수정한다.
#### Specification
* **Method** : PATCH
* **URL** : http://:server_url/api/v1/task/{taskId}
* **Content-Type** : application/json
* **Parameters**
 
| 필드명 | 타입 | 필수여부 | 기본값 | 설명 |
| :----: | :----: | :----: | :----: | :----: |
| name | String | Required | N/A | 변경 할 할일 이름 |

* **Request Example**
```
curl -X PATCH 'http://:server_url/api/v1/task/1' -d '{
    "name": "집안일"
}'
```

* **Response**

| 필드명 | 타입 | 필수여부 | 기본값 | 설명 |
| :----: | :----: | :----: | :----: | :----: |
|  |  |  |  |  |

* **Response Example**
```
{
    "code": "TM200",
    "message": "success"
}
```

### 할 일 완료
* 할 일을 완료한다.
#### Specification
* **Method** : POST
* **URL** : http://:server_url/api/v1/task/{taskId}/finish
* **Content-Type** : application/json
* **Parameters**
 
| 필드명 | 타입 | 필수여부 | 기본값 | 설명 |
| :----: | :----: | :----: | :----: | :----: |
| taskId | Number | Required | N/A | 완료 할 할일 ID |

* **Request Example**
```
curl -X POST 'http://:server_url/api/v1/task/1/finish' -d '{
    "name": "집안일"
}'
```

* **Response**

| 필드명 | 타입 | 필수여부 | 기본값 | 설명 |
| :----: | :----: | :----: | :----: | :----: |
|  |  |  |  |  |

* **Response Example**
```
{
    "code": "TM200",
    "message": "success"
}
```
## 4.How to Run
### Prerequisites
* JAVA 1.8
* node.js ( >= 10.11.0 )
### Clone code
```
$ git clone https://github.com/ssm026/todo-manager
```
### Run Command
```
$ cd /path/to/todo-manager
$ ./gradlew npmInstall npmRunBuild bootRun
```