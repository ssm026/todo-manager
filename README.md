# TODO Task Manager

## Requirements
* 사용자는 텍트스로 된 할 일을 추가 할 수 있다.
  * 할 일 추가 시 다른 할 일 들을 참조 걸 수 있다. 
  * 참조는 다른 할 일의 id를 명시하는 형태로 구현한다.
* 사용자는 할 일을 수정 할 수 있다.
* 사용자는 할 일 목록을 조회 할 수 있다.
  * 저회시 작성일, 최종 수정일, 내용이 조회 가능하다.
  * 할 일 목록은 페이징 기능이 있다.
* 사용자는 할 일을 완료 처리 할 수 있다.
  * 완료 처리시 참조가 걸린 완료되지 않은 할 일이 있다면 완료 처리 할 수 없다.  

## Problem Solving
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
    * "," 로 구분된 string?
    * 현재 등록된 id 리스트를 select 박스로 선택. 여러개 처리?
  * 입력받은 참조 id 가 이미 완료됐거나 없을경우 alert 띄울지? 아니면 있는것만 insert 할지
    * select 박스로 선택하게 하고 이미 완료된 id 는 select 박스에 노출 시키지 않는다.
    * 만약 이미 완료됐거나 없는 id 일 경우 무시하고 있는 id 만 등록시킨다.
  * 순환참조가 발생한다면?
    * 등록시 참조 id 가 있는경우 무조건 자신보다 먼저 등록된 id 를 참조하므로 이슈 없음.
* 할 일 수정시 고려사항
  * 참조 id의 변경을 허용 할 것인지?
    * 할 일 수정시 자신보다 나중에 등록된 id를 참조하면 순환참조 이슈가 발생 할 수 있으므로 수정 불가하도록 한다.
  * 수정 가능 필드는?
    * 순환참조 이슈 방지를 위해 할 일 이름만 변경 가능.
* 할 일 완료시 고려사항
  * 참조된 할 일이 완료 됐는지 체크.

## Backend API List

## Build And Run
### Prerequisites
### frontend 
### backend