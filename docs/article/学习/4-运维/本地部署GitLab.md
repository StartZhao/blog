---
tag:
 - 运维
---

# 本地部署GitLab

使用 docker 的方式部署 GitLab 在 Linux 服务器上

前提条件已经有该镜像和 docker 环境

## 1  启动容器

```bash
# 启动容器
docker run \
 -itd  \
 -p 9980:80 \
 -p 9922:22 \
 -v /home/gitlab/etc:/etc/gitlab  \
 -v /home/gitlab/log:/var/log/gitlab \
 -v /home/gitlab/opt:/var/opt/gitlab \
 --restart always \
 --privileged=true \
 --name gitlab \
 gitlab/gitlab-ce
```

## 2 修改配置

**修改gitlab.rb**

可以在宿主机的/home/gitlab/etc目录下找到该文件。可以先拉到本地进行修改，再上传，这样保证修改顺利。

```bash
#加入如下
#gitlab访问地址，可以写域名。如果端口不写的话默认为80端口
external_url 'http://虚拟机IP地址:端口'
#ssh主机ip
gitlab_rails['gitlab_ssh_host'] = '虚拟机IP地址'
#ssh连接端口
gitlab_rails['gitlab_shell_ssh_port'] = 9922
# nginx监听端口
nginx['listen_port'] = 80
```

生效配置文件

```bash
#进容器内部
docker exec -it gitlab /bin/bash
# 让配置生效
gitlab-ctl reconfigure
#重启gitlab
gitlab-ctl restart
#退出容器
exit
```

## 3 浏览器访问

**访问地址**：虚拟机IP地址:9980

**注意点**：机器配置要大于4g，否则很容易启动不了，报502

第一次访问需要修改 root 密码

## 4 修改 root 密码

```bash
# 进入容器内部
docker exec -it gitlab /bin/bash

# 进入控制台
gitlab-rails console -e production

# 查询id为1的用户，id为1的用户是超级管理员
user = User.where(id:1).first
# 修改密码,注意密码不要太过简单，要不然它会保存不通过
user.password='******'
# 保存
user.save!
# 退出
exit
```

## 5 bug

### 5.1 配置 gitlab.rb 出现问题

```bash
vi /etc/gitlab/gitlab.rb
```

直接在容器执行上述命令，可能会太卡导致修改配置文件出现问题。然后导致GitLab一直跑不起来。

### 5.2 修改http和ssh配置 出现问题

```bash
# 修改http和ssh配置
vi /opt/gitlab/embedded/service/gitlab-rails/config/gitlab.yml

  gitlab:
    host: 虚拟机IP地址
    port: 9980 # 这里改为9980
    https: false
```

由于上述操作，在虚拟机重启后会丢失。我就想持久化。

将 gitlab.rb 进行如下配置

```bash
external_url 'http://虚拟机IP地址:端口'
```

然后出现无法正常访问页面

最后排查，发现是由于进行上述配置之后 nginx 的监听端口发生了变化。

所以需要将 nginx 的监听端口固定下来。

```bash
nginx['listen_port'] = 80
```

