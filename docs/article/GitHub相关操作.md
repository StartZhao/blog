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
