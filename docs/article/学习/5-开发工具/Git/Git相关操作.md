---
tag:
 - 开发工具
 - Git
---

# GitHub相关操作

## 一、本地仓库推送到远程GitHub仓库

1. 初始化Git仓库`git init`
2. 添加文件到暂存区`git add .`
3. 提交更改`git commit -m "提交信息"`
4. 关联远程仓库`git remote add origin 远程仓库URL`
5. 推送更改到远程仓库`git push -u origin 分支名`

## 二、清理远程仓库多余缓存

### （一）相关命令

查看本地仓库连接的远程库url地址

```git
git ls-remote --get-url
```

查看本地仓库大小

```git
du -sh .git
```

查看本地工作目录文件大小

```git
du -sh .
```

### （二）实现方法

> 在进行清理远程仓库缓存前一定要对仓库进行备份

**方法一：大文件清理**

1. 使用`git filter-repo`删除大文件或报表数据。删除历史记录中所有超过1MB的文件，`git filter-repo --strip-blobs-bigger-than 1M`
2. 推送清理后的仓库，`git push --force --all`

**方法二：一般清理和优化**

1. 进行垃圾回收，`git gc`
2. 输出无法引用的对象，`git prune`

**方法三：清理特定文件或路径**

1. 删除文件或文件夹，`git rm -r --cached path/to/file`，`git commit -m "提交信息"`
2. 清理文件历史记录，`git filter-repo --path path/to/file --invert-paths`
3. 推送清理后的仓库，`git push --force --all`

## 三、分支相关操作

强制推送本地到远程仓库，对远程仓库内容直接进行覆盖

```git
git push --force origin main
```

修改本地分支名

```git
git branch -m old-branch-name new-branch-name
```

设置分支与远程仓库同分支名同步

```git
git push --set-upstream origin branch-name
```

查看当前工作目录和暂存区的状态

```git
git status
```

合并分支

```git
git merge feature-branch --allow-unrelated-histories
```

删除分支

```git
git branch -d feature-branch
```

查看所有分支

```git
git branch -a
```

查看远程分支

```git
git branch -r
```

更新本地的远程跟踪分支，获取最新的更改，但不会影响当前工作目录或本地分支

```git
git fetch origin
```

创建本地分支，并切换到该分支

```git
git checkout -b feature-branch
```

## 四、相关命令

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
git push -u <远程仓库名> <本地分支名>:<远程分支名>
# 正常推送本地分支到远程仓库
git push <远程仓库名> <本地分支名>:<远程分支名>
# 查看本地跟踪远程仓库情况
git branch -vv
```
