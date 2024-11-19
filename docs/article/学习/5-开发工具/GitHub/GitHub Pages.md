---
tag:
 - 开发工具
 - GitHub
---

# GitHub Pages

## 一、使用自定义工作流

> 自定义工作流允许使用 GitHub Actions 生成 GitHub Pages 网站。

### （一）配置 `upload-pages-artifact` 操作

通过 `upload-pages-artifact` 操作可以打包和上传项目。 GitHub Pages 项目应该是包含单个 `tar` 文件的压缩 `gzip` 存档。 `tar` 文件大小必须低于 10 GB，并且不应包含任何符号或硬链接。

```yaml
- name: Upload GitHub Pages artifact
  uses: actions/upload-pages-artifact@v1
```

### （二）部署 GitHub Pages 项目

`deploy-pages` 操作处理部署项目所需的设置。 为确保功能正常运行，应满足以下要求：

- 作业必须至少具有 `pages: write` 和 `id-token: write` 权限。
- `needs` 参数必须设置为生成步骤的 `id`。 不设置此参数可能会导致独立部署持续搜索尚未创建的项目。
- 必须建立 `environment` 以强制实施分支/部署保护规则。 默认环境为 `github-pages`。
- 若要将页面的 URL 指定为输出，请使用 `url:` 字段。

```yaml
jobs:
  deploy:
    permissions:
      contents: read
      pages: write
      id-token: write
    runs-on: ubuntu-latest
    needs: jekyll-build
    environment:
      name: github-pages
      url: ${{steps.deployment.outputs.page_url}}
    steps:
      - name: Deploy artifact
        id: deployment
        uses: actions/deploy-pages@v4
```

### （三）链接单独的生成和部署作业

可以在单个工作流文件中链接 `build` 和 `deploy` 作业，无需创建两个单独的文件即可获得相同的结果。 若要开始使用工作流文件，可以在 `jobs` 下定义 `build` 和 `deploy` 作业以执行作业。

```yaml
jobs:
  # Build job
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4
      - name: Setup Pages
        id: pages
        uses: actions/configure-pages@v3
      - name: Build with Jekyll
        uses: actions/jekyll-build-pages@v1
        with:
          source: ./
          destination: ./_site
      - name: Upload artifact
        uses: actions/upload-pages-artifact@v3

  # Deployment job
  deploy:
    environment:
      name: github-pages
      url: ${{steps.deployment.outputs.page_url}}
    runs-on: ubuntu-latest
    needs: build
    steps:
      - name: Deploy to GitHub Pages
        id: deployment
        uses: actions/deploy-pages@v4
```

在某些情况下，可以选择将所有内容合并到单个作业中，尤其是在不需要生成过程的情况下。 因此，将只专注于部署步骤。

```yaml
jobs:
  # Single deploy job no building
  deploy:
    environment:
      name: github-pages
      url: ${{steps.deployment.outputs.page_url}}
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4
      - name: Setup Pages
        uses: actions/configure-pages@v3
      - name: Upload Artifact
        uses: actions/upload-pages-artifact@v3
        with:
          # upload entire directory
          path: '.'
      - name: Deploy to GitHub Pages
        id: deployment
        uses: actions/deploy-pages@v4
```

可以将作业定义在不同的运行器上按顺序或并行运行。

## 参考文献

[GitHub Pages 文档](https://docs.github.com/zh/pages)