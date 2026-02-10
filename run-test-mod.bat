@echo off
setlocal

cd /d "%~dp0"

if /I "%~1"=="--help" goto :help
if /I "%~1"=="/?" goto :help

set "MODE=%~1"
if "%MODE%"=="" set "MODE=quick"

if /I "%MODE%"=="full" (
    echo [Musicless] Running full test: build + client launch
    call gradlew.bat build
    if errorlevel 1 goto :fail
) else if /I "%MODE%"=="quick" (
    echo [Musicless] Running quick test: client launch only
) else (
    echo [Musicless] Unknown mode: %MODE%
    goto :help
)

call gradlew.bat runClient
if errorlevel 1 goto :fail

echo [Musicless] Test run finished.
exit /b 0

:fail
echo [Musicless] Command failed with exit code %errorlevel%.
exit /b %errorlevel%

:help
echo Usage: run-test-mod.bat [quick^|full]
echo.
echo quick  Launches Minecraft client immediately for fast iteration. (default)
echo full   Builds the mod first, then launches the client.
exit /b 0
