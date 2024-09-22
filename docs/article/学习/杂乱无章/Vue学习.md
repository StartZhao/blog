# Vue 学习

## 一、如何构建一个 Vue 项目

1. 以 Vite 为构建工具，构建一个 Vue 项目。`npm create vite@latest project-name -- --template vue`

## 二、各个文件的作用

### (一) 以 Vite 为构建工具初始目录各个文件作用

```
.
├── .gitignore					列出在 Git 版本控制中应忽略的文件或目录
├── .vscode 					VS Code 配置文件
│   └── extensions.json			管理其指定的依赖
├── index.html					首页，Vue 应用最终挂载位置
├── package.json				项目依赖管理文件
├── public						公共静态资源文件
│   └── vite.svg
├── README.md					项目说明
├── src							项目来源
│   ├── App.vue					主 Vue 组件
│   ├── assets					静态资源文件
│   │   └── vue.svg
│   ├── components				存放 Vue 组件
│   │   └── HelloWorld.vue
│   ├── main.js					Vue 应用的入口文件
│   └── style.css				全局样式文件
└── vite.config.js				Vite 的配置文件，用于控制打包和开发环境设置
```

