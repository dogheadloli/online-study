const webpack = require("webpack")
module.exports = {
  plugins: [
    {
      src: '~/plugins/nuxt-swiper-plugin.js',
      ssr: false
    },
    { src: '~/plugins/vue2-editor.js',
      ssr: false
    },

  ],

  /*
  ** Headers of the page
  */
  head: {
    title: '在线视频学习平台',
    meta: [
      {charset: 'utf-8'},
      {name: 'viewport', content: 'width=device-width, initial-scale=1'},
      {hid: 'description', name: 'description', content: '{{escape description }}'}
    ],
    script: [
      {src: '/js/prism.js'},
    ],
    link: [
      {rel: 'icon', type: 'image/x-icon', href: '/favicon.ico'},
      {rel: "stylesheet", type: "text/css", href: "/css/prism.css"},
      {rel: "stylesheet", type: "text/css", href: "/css/github-markdown.css"}
    ]
  },
  /*
  ** Customize the progress bar color
  */
  loading: {color: '#3B8070'},
  /*
  ** Build configuration
  */


  build: {
    plugins: [
      new webpack.ProvidePlugin({
        'window.Quill': 'quill/dist/quill.js',
        'Quill': 'quill/dist/quill.js'
      })
    ]
  }

}

