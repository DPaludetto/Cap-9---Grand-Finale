@echo off

SET application=palutec-web
SET tagname=%1

ECHO #=============================================================#
ECHO #                Preparing dependencies
ECHO #=============================================================#
call npm install

IF %ERRORLEVEL% NEQ 0 (
  echo Error preparing dependencies
  goto end
)

ECHO #=============================================================#
ECHO #                Building project
ECHO #=============================================================#
call ng build --prod --configuration=production

IF %ERRORLEVEL% NEQ 0 (
  echo Error building project
  goto end
)

ECHO #=============================================================#
ECHO #                Building docker image
ECHO #=============================================================#
call docker build -t martelabit/%application%:%tagname% -f docker/image/Dockerfile .

IF %ERRORLEVEL% NEQ 0 (
  echo Error building docker image
  goto end
)

:end
ECHO #=============================================================#
ECHO #                finishing script
ECHO #=============================================================#
exit /b %errorlevel%
