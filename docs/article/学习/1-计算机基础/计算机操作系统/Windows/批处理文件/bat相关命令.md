---
tag:
 - 计算机基础
 - Windows
---

# bat 相关命令

```bash
rem 关闭命令回显
@echo off
rem 确定解码格式为 utf-8, >nul 标准输出重定向到 nul, 2>&1 标准错误重定向到标准输出
chcp 65001 >nul
rem 允许在同一块代码中使用 ! 来引用变量
setlocal enabledelayedexpansion
rem 获取交互内容
set /p message="请输入提交信息："
echo %message%
rem 去掉空格
set "message=!message:=!"
if "!message!"=="" (
	echo 输入为空或只包含空格。
) else (
	echo 你输入的是!message!
)

endlocal
echo bingo
:delleft
if "%abc:~0,1%"==" " set abc=%abc:~1%&&goto delleft
echo 去掉左边空格:%abc%
```

