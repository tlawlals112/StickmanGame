# 베이스 이미지로 openjdk:8 사용
FROM openjdk:8

# 작업 디렉토리 설정
WORKDIR /app

COPY ./src/* /app/
COPY ./assets /app/assets
COPY ./musics /app/musics
COPY ./*.txt /app/

RUN javac *.java
RUN jar cfm stickmanGame.jar Manifest.txt *.class musics/ assets/

CMD ["java", "-jar", "stickmanGame.jar"]
