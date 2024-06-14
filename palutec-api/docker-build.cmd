@echo off

SET application=crud-api
SET tagname=%1

ECHO #=============================================================#
ECHO #                Packaging jar file with maven
ECHO #=============================================================#
call .\mvnw clean package -Dmaven.test.skip

IF %ERRORLEVEL% NEQ 0 (
  echo Error packaging project
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
