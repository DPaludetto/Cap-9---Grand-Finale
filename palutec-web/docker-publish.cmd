@echo off

SET application=financeiro-pagadoria-web
SET tagname=%1

ECHO #=============================================================#
ECHO #                Pushing image to Remote
ECHO #=============================================================#
rem docker push qwasolucoes/%application%:%tagname%
docker push martelabit/%application%:%tagname%

IF %ERRORLEVEL% NEQ 0 (
  echo Error pushing docker image
  goto end
)

:end
ECHO #=============================================================#
ECHO #                finishing script
ECHO #=============================================================#
exit /b %errorlevel%
