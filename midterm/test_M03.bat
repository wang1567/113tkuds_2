@echo off
chcp 65001 > nul
echo 正在執行 M03_TopKConvenience (修正版)...
echo.
echo 測試資料:
type test_utf8.txt
echo.
echo 執行結果:
type test_utf8.txt | java -cp "C:\Users\pc\AppData\Roaming\Code\User\workspaceStorage\e42d301464f05c711a4fc01d1bbfd109\redhat.java\jdt_ws\113tkuds_2_b1425a5\bin" midterm.M03_TopKConvenience
echo.
pause
