@start java -jar .\apiTAI\target\tai-0.0.1-SNAPSHOT.jar
@start java -jar .\apiAuth\target\microservicio-0.0.1-SNAPSHOT.jar
DEL /Q .\nginx-1.21.6\html\*
Copy .\myClient\dist\myClient\* .\nginx-1.21.6\html\*
cd nginx-1.21.6\
start nginx