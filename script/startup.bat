@echo off
cd /d %~dp0

echo [1] Starting Wi-Fi monitor...
start "" powershell -WindowStyle Hidden -ExecutionPolicy Bypass -File "%~dp0check_wifi.ps1"

echo [2] Starting backend...
start "OSR Backend" "%~dp0jre\bin\java.exe" -Xms256m -Xmx1024m -jar "%~dp0backend-0.0.1-SNAPSHOT.jar" --spring.profiles.active=dev

echo [3] Waiting for backend on port 9090...
:waitloop
netstat -ano | findstr :9090 | findstr LISTENING > nul
if errorlevel 1 (
    timeout /t 2 /nobreak > nul
    goto waitloop
)

echo [4] Starting Edge fullscreen...
start "" msedge --kiosk http://127.0.0.1:9090 --edge-kiosk-type=fullscreen --no-first-run

exit