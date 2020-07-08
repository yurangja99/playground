# Initial Dependencies
- web
- JPA
- devtools
- mustache
- mysql driver

# 테스트 방법
- 로컬 MySQL 서버에 접속한 후, "show variables like 'datadir'" 명령어를 통하여 로컬 저장소 위치를 찾는다.  
- MySQL Data 폴더 안에 있는 playground 폴더를 로컬 MySQL 저장소 위치로 옮긴다.
- 프로젝트 디렉토리에서 "gradlew bootjar" 명령어를 통하여 jar 배포 파일을 얻는다. 
- 프로젝트 디렉토리/build/libs 에 있는 jar 파일을 아래의 명령어를 이용하여 실행한다. 
- "java -jar [jar 파일명].jar" 
- localhost:8080 에 접속하여 문서에 따라 API를 이용한다. 