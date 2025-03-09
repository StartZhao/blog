# Git核心概念

## 一、相关命令

```bash
# 克隆远程仓库指定分支
git clone -b <branch_name> <repository_url>

# 查看当前仓库中所有远程仓库的名称及其URL
git remote -v
# 本地仓库添加远程仓库
git remote add <name> <url>
# 本地仓库删除远程仓库引用
git remote remove <name>
# 推送本地分支到远程仓库并设置上游（跟踪）分支
git push -u <远程仓库名> <本地分支名>
# 正常推送本地分支到远程仓库
git push <远程仓库名> <本地分支名>:<远程分支名>
```

