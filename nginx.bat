@echo off
set NGINX_CMD_PATH=C:<path-to>\nginx-1.28.0\nginx.exe
set NGINX_CONF_PATH=C:<path-to>\nginx-1.28.0\conf\nginx.conf

for %%I in ("%NGINX_CMD_PATH%") do set NGINX_DIR=%%~dpI

pushd "%NGINX_DIR%"

if "%1"=="start" (
    "%NGINX_CMD_PATH%" -c "%NGINX_CONF_PATH%"
)

if "%1"=="quit" (
    "%NGINX_CMD_PATH%" -s quit
)

if "%1"=="" (
    echo Usage: %0 {start|quit}
    exit /b 1
)

popd
