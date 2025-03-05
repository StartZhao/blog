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

```bash
#进容器内部
docker exec -it gitlab /bin/bash

#修改gitlab.rb
vi /etc/gitlab/gitlab.rb

#加入如下
#gitlab访问地址，可以写域名。如果端口不写的话默认为80端口
external_url 'http://虚拟机IP地址'
#ssh主机ip
gitlab_rails['gitlab_ssh_host'] = '虚拟机IP地址'
#ssh连接端口
gitlab_rails['gitlab_shell_ssh_port'] = 9922

# 让配置生效
gitlab-ctl reconfigure
```

```bash
# 修改http和ssh配置
vi /opt/gitlab/embedded/service/gitlab-rails/config/gitlab.yml

  gitlab:
    host: 虚拟机IP地址
    port: 9980 # 这里改为9980
    https: false
```

```bash
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

