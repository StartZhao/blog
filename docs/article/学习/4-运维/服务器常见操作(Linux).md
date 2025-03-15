---
tag:
 - 运维
---

# 服务器常见操作(Linux)

一、查看当前时间及更换当前时区

+ 查看当前时区时间等

```bash
timedatectl
```

+ 查看当前时间

```bash
date
```

+ 查看可用时区列表

```bash
timedatectl list-timezones
```

+ 设置新时区

```bash
timedatectl set-timezone Asia/Shanghai # 更换当前时区为上海时区
```

