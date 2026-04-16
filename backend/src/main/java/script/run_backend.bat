@echo off
cd /d %~dp0

echo =====================================
echo OSR BACKEND START
echo =====================================

start "OSR Backend" "%~dp0jre\bin\java.exe" -Xms256m -Xmx1024m -jar "%~dp0backend-0.0.1-SNAPSHOT.jar" --spring.profiles.active=dev
exit