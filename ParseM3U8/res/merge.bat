@echo off
set INPUTPATH=%1
set COUNT=%2
set OUTPATH=%3
(for /l %%i in (0,1,%COUNT%) do @echo file '%INPUTPATH%\%%i.ts') > %INPUTPATH%.txt
D:\ProgramFiles\ffmpeg-4.3.1-win64-static\bin\ffmpeg.exe -f concat -safe 0 -i %INPUTPATH%.txt -c copy %OUTPATH%


