# Spring Boot with Kotlin 연습 

## 이 프로젝트의 초기 의존성
- web
- JPA
- devtools
- mustache
- mysql driver


## 추가된 의존성
implementation("io.springfox:springfox-swagger2:2.6.1")
implementation("io.springfox:springfox-swagger-ui:2.6.1")

## 변경사항
- 2020/07/12: DB 구조 업데이트 및 controller 수정
- 2020/07/12: topic 테이블에 지자체 정보 추가 및 /users, /topics 기능 수정

## 테스트 방법
- /src/main/resources/application.properties에서 MySQL username과 password 값을 변경한다.
- MySQL을 설치한 후, mysql -uroot -p 명령어를 통하여 접속이 되는지 확인한다.
- 로컬 MySQL 서버에 접속한 후, show databases; 명령어롤 통해 playground 데이터베이스가 없다는 것을 확인한다.
- playground 데이터베이스가 없음을 확인한 후, MySQL data/DB예시데이터.txt 속 쿼리들을 그대로 입력한다.
- 프로젝트 디렉토리에서 "./gradlew bootjar" 명령어를 통하여 jar 배포 파일을 얻는다. 
- 프로젝트 디렉토리/build/libs 에 있는 jar 파일을 아래의 명령어를 이용하여 실행한다. 
- "java -jar [jar 파일명].jar" 
- http://localhost:8080/swagger-ui.html 에 접속하여 문서에 따라 API를 이용한다. 