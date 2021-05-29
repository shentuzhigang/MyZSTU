import { defineUserConfig } from 'vuepress'
import type { DefaultThemeOptions } from 'vuepress'

export default defineUserConfig<DefaultThemeOptions>({
  dest: "docs",
  base: "/docs",
  lang: 'zh-CN',
  title: 'MyZSTU',
  description: 'MyZSTU 文档',
  locales: {
    // 键名是该语言所属的子路径
    // 作为特例，默认语言可以使用 '/' 作为其路径。
    '/': {
      lang: 'zh-CN',
      title: 'MyZSTU',
      description: 'MyZSTU 文档',
    },
    '/en/': {
      lang: 'en-US',
      title: 'MyZSTU',
      description: 'MyZSTU Docs',
    },
  },
  themeConfig: {
    logo: '/images/logo.png',
    repo: "shentuzhigang/MyZSTU",
    editLinks: true,
    locales: {
      '/': {
        selectLanguageText: '语言',
        selectLanguageName: '简体中文',
        nav: [
          {
            text: "指南",
            link: "/guide/"
          }
        ]
      },
      '/en/': {
        selectLanguageText: 'Language',
        selectLanguageName: 'English',
        nav: [
          {
            text: "Guige",
            link: "/guide/"
          }
        ]
      },
    }
  },
})