Boiler Plate
---
## 환경
- Framework: SpringBoot 2.6.7
- JDK: JAVA 11
- DB handling: JPA, QueryDsl
- build: Maven

## BUILD
- mvn clean build
- 빌드가 되야지만 QueryDsl에서 이용되는 QClass가 생성됨

## Profiles
- *local* - H2 Memory DB
- *dev* - MySql by Docker

## H2 Memory DB
- localhost:8080/h2-console로 접속
- JDBC URL을 jdbc:h2:mem:test로 맞추고 접속


## DB 설정
```
docker pull mysql
docker run --name mysql -e MYSQL_ROOT_PASSWORD=<password> -d -p 3306:3306 mysql
docker exec -it mysql bash
mysql -u root -p
create database plates;
use plates;
create user 'plates'@'%' identified by 'plates!Q@W#E4r';
grant all privileges on *.* to 'plates'@'%';
flush privileges;
```

