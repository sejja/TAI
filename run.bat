@start java -jar .\apiAuth\target\microservicio-0.0.1-SNAPSHOT.jar
@start java -jar .\apiTAI\target\tai-0.0.1-SNAPSHOT.jar
cd myClient\
call npm run build 
cd ..
DEL /Q .\nginx-1.21.6\html\*
Copy .\myClient\dist\myClient\* .\nginx-1.21.6\html\*
cd nginx-1.21.6\
.\nginx.exe -s quit
start nginx