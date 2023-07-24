import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import {join,resolve} from 'path'
// @ts-ignore
import viteCompression from 'vite-plugin-compression';
import fs from "fs";
const project_pages = {};
const entryPath = resolve(__dirname, "./src");
const entrys = fs.readdirSync(entryPath).reduce((obj, dirname) => {
  obj[dirname] = join(entryPath, dirname);
  return obj;
}, {});

Object.keys(entrys).forEach(pageName => {
  if (fs.lstatSync(resolve(__dirname, `src/${pageName}`)).isDirectory() && fs.existsSync(resolve(__dirname, `src/${pageName}/index.html`))) {
    project_pages[pageName] = resolve(__dirname, `src/${pageName}/index.html`);
  }
});
if (fs.existsSync(resolve(__dirname, `src/index.html`))) {
  project_pages[''] = resolve(__dirname, `src/index.html`);
}
export default defineConfig(({ mode }) => {
  let pages = { ...project_pages };
  return {
    root: 'src',
    plugins: [
      vue(),
      viteCompression({verbose: true,disable: false,threshold: 10240,algorithm: "gzip",ext: ".gz",})
    ],
    server: {
      hmr: { overlay :false},
      port: 5174,
      host: '0.0.0.0'
    },
    resolve: {
      alias: {
        '@': resolve(__dirname, 'src')
      },
    },
    build: {
      // 浏览器兼容性 ‘esnext’ | 'modules'
      target: 'modules',
      // 小于此阈值的导入或引用资源将内联为 base64 编码， 以避免额外的http请求， 设置为 0, 可以完全禁用此项，
      assetsInlineLimit: 4096,
      // 启动 / 禁用 CSS 代码拆分
      cssCodeSplit: true,
      // 构建后是否生成 soutrce map 文件
      sourcemap: false,
      // 自定义底层的 Rollup 打包配置
      outDir: '../dist',
      rollupOptions: {
        input: pages,
        output: {
          chunkFileNames: 'static/js/[name]-[hash].js',
          entryFileNames: 'static/js/[name]-[hash].js',
          assetFileNames: 'static/[ext]/[name]-[hash].[ext]',
        }
      }
    },
    css: {
      preprocessorOptions: {
        less: {
          javascriptEnabled: true,
        },
      },
    },
  }
})
