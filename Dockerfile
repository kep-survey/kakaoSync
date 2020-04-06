# java 8 사용
FROM java:8

# 애플리케이션 포트
EXPOSE 8081

# 애플리케이션 파일 추가
ADD ./target/KakaoSync-0.0.1-SNAPSHOT.jar application.jar

# 실행
ENTRYPOINT ["java", "-jar", "application.jar"]