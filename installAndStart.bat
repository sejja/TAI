cd apiTAI
call .\mvnw.cmd clean -f .\pom.xml
call .\mvnw.cmd package -f .\pom.xml
cd ..
@start java -jar .\apiTAI\target\tai-0.0.1-SNAPSHOT.jar
cd myClient\
call npm install
call npm run build 
cd ..
DEL /Q .\nginx-1.21.6\html\*
Copy .\myClient\dist\myClient\* .\nginx-1.21.6\html\*
cd nginx-1.21.6\
start nginx