# 服务器运维常用软件(Linux)

## 一、服务器管理软件

### （一）1Panel

1Panel官网：https://1panel.cn/

#### 1. 在线安装

```bash
curl -sSL https://resource.fit2cloud.com/1panel/package/quick_start.sh -o quick_start.sh && sh quick_start.sh
```

#### 2. 离线安装

+ 在官网下载安装包
+ 解压安装包

```bash
tar zxvf 1panel-v1.10.0-lts-linux-amd64.tar.gz
```

+ 进入安装包内部

```bash
cd 1panel-v1.10.0-lts-linux-amd64
```

+ 执行安装脚本

```bash
/bin/bash install.sh
```

3.配置镜像加速源

+ 查看国内镜像源加速状况：https://status.1panel.top/status/docker

### （二）宝塔

宝塔官网：https://www.bt.cn/new/product_linux.html

安装

```bash
if [ -f /usr/bin/curl ];then curl -sSO https://download.bt.cn/install/install_panel.sh;else wget -O install_panel.sh https://download.bt.cn/install/install_panel.sh;fi;bash install_panel.sh ed8484bec
```

