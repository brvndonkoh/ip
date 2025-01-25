@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run


REM compile the code into the bin folder
javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
) ELSE (
    echo Build OK!
)

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin Clarawr < input.txt > ACTUAL.TXT

REM compare the output to the expected output, ignoring white space differences
FC /L /W ACTUAL.TXT EXPECTED.TXT

REM Check if FC found a difference
IF ERRORLEVEL 1 (
    echo Something is wrong! Files do not match.
) ELSE (
    echo Yes! All the files are the same. Test passed! :D
)
