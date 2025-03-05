@echo off
:: 设置代码页为UTF-8
chcp 65001 >nul

:: 提示用户输入文件名和标题内容
set /p inputContent=请输入文件名和标题内容: 

:: 创建文件名，替换掉不允许的字符，例如空格可以替换为下划线
set filename=%inputContent:.=_%
set filename=%filename: =_%

:: 创建.md文件并写入内容
(
echo # %inputContent%
echo.
echo.
) > "%filename%.md"

echo 文件"%filename%.md" 已经创建。

@echo off
