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

## 三、相关代码解析

### （一） setup()

setup() 是 Vue 3 中 Composition API 的核心部分，用于在组件实例被创建之前设置组件的状态和行为。它的主要作用包括:

1. **初始化状态**

在 `setup()` 中，你可以定义组件的响应式状态。可以使用 `ref` 和 `reactive` 来创建响应式的数据。

2. **定义计算属性**

可以在 `setup()` 中使用 `computed` 来创建计算属性，便于依赖状态的衍生数据。

3. **定义方法**

在 `setup()` 中，可以定义与组件逻辑相关的方法，便于在模板或其他方法中调用。

4. **访问属性和方法**

`setup()` 接收两个参数：`props` 和 `context`。可以通过这些参数访问组件的属性和上下文。

5. **返回数据和方法**

`setup()` 必须返回一个对象，该对象中的属性和方法会被暴露给组件的模板中使用。

### （二）<script setup lang="ts">

<script setup lang="ts"> 是 Vue 3 中的一种简化写法，用于使用 Composition API。它与传统的 setup() 方法有几个重要的区别：

1. 简化语法

<script setup> 省略了 export default 和 setup() 函数的定义，使代码更简洁。

2. 自动响应式


在 <script setup> 中，定义的变量（如使用 ref 或 reactive）会自动被 Vue 处理为响应式，无需手动返回。
3. 更好的类型推导

使用 TypeScript 时，<script setup lang="ts"> 提供了更好的类型推导和类型检查，减少了冗余的类型声明。
4. 使用组合函数

可以直接使用组合函数和其他 Composition API 特性，而不需要在 setup() 中进行额外的声明。

### （三）Composition API 和 Options API 区别

Composition API 和 Options API 是 Vue.js 中两种不同的组件定义方式。

1. **Options API**

这是 Vue 2.x 中的默认方式，也是许多 Vue 2 组件的传统写法。

**主要特点：**

- **选项对象**：组件的逻辑和数据通过一个包含多个选项的对象来组织，如 `data`、`methods`、`computed`、`watch` 等。
- **上下文独立**：每个选项的上下文是独立的，可能会导致逻辑分散。

2. **Composition API**

这是 Vue 3 引入的新特性，提供了一种更灵活和可组合的方式来构建组件。

**主要特点：**

- **逻辑组合**：通过 `setup` 函数，开发者可以在一个地方定义状态、计算属性和方法，更易于逻辑复用和组织。
- **响应式 API**：使用 `ref` 和 `reactive` 使得状态的定义更加直观。

### （四）导入区别

- **默认导入**使用 `import Left from ...`，适用于 `export default`。
- **命名导入**使用 `import { Left } from ...`，适用于 `export const` 或其他命名导出。

### （五）SCSS

1. `&:nth-of-type(1)`，优先选择某个元素的第一个同类元素
2. `justify-content: space-between;` 是一个 CSS 属性，用于在 Flexbox 或 Grid 布局中控制主轴上的项目分布。

### （六）HTML

1. `p` 标签：表示段落，通常用于包含文本内容。属于块级元素，会换行。用于组织文档的结构，表示独立的文本段落。
2. `span` 标签：用于在文档中标记小块文本，通常用作内联元素。不会在前后产生换行，通常用于修改部分文本的样式或属性。没有特殊语义，主要用于样式和脚本。

### （七）CSS

1. 主要的布局方式
   + **Block**：垂直排列，默认布局。
   + **Inline**：水平排列，不占据整行。
   + **Flexbox**：一维布局，适用于灵活的项目排列。
   + **Grid**：二维布局，适合复杂的网格设计。
   + **Table**：模拟表格布局，用于行列结构。
   + **Positioning**：绝对和相对定位，控制元素在特定位置。
2. `align-self: flex-end;` 是一个 CSS 属性，用于在 Flexbox 布局中单独调整某个元素的对齐方式。

### （八）代码格式化工具

eslint、prettier

（九）小细节

具名导入导出与默认导入导出区别

节流消抖

（十）Vue插件

unplugin-auto-import

unplugin-vue-components