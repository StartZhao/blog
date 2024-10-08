import { defineConfig } from 'vitepress'

// 导入主题的配置
import { blogTheme } from './blog-theme'

// 如果使用 GitHub/Gitee Pages 等公共平台部署
// 通常需要修改 base 路径，通常为“/仓库名/”
// 如果项目名已经为 name.github.io 域名，则不需要修改！
// const base = process.env.GITHUB_ACTIONS === 'true'
//   ? '/blog/'
//   : '/'
// 使用 VITE_BASE_URL 环境变量，GitHub Pages,不支持.env变量
// const base = process.env.VITE_BASE_URL || '/'
// 已改项目为根配置域名
const base = '/'
// Vitepress 默认配置
// 详见文档：https://vitepress.dev/reference/site-config
export default defineConfig({




  // 继承博客主题(@sugarat/theme)
  extends: blogTheme,
  base,
  lang: 'zh-cn',
  title: '菜旺学习',
  description: '菜旺的博客，记录生活的点点滴滴',
  lastUpdated: true,
  // 详见：https://vitepress.dev/zh/reference/site-config#head
  head: [
    // 配置网站的图标（显示在浏览器的 tab 上）
    ['link', { rel: 'icon', href: `${base}favicon.ico` }], // 修改了 base 这里也需要同步修改
    // ['link', { rel: 'icon', href: '/favicon.ico' }]
  ],
  themeConfig: {



    //  //本地搜索
     search: {
      provider: 'local'
    },

    // 展示 2,3 级标题在目录中
    outline: {
      level: [2, 3],
      label: '目录'
    },
    // 默认文案修改
    returnToTopLabel: '回到顶部',
    sidebarMenuLabel: '相关文章',
    lastUpdatedText: '上次更新于',

    // 设置logo
    logo: '/logo.png',
    // editLink: {
    //   pattern:
    //     'https://github.com/ATQQ/sugar-blog/tree/master/packages/blogpress/:path',
    //   text: '去 GitHub 上编辑内容'
    // },

    // 导航栏
    nav: [
      { text: '个人主页', link: 'https://startzhao.top/' },
      { text: '关于我', link: '/article/about.md' },


    ],
    // 社交链接
    socialLinks: [
      {
        icon: 'github',
        link: 'https://github.com/StartZhao'
      }
    ]
  }
})
