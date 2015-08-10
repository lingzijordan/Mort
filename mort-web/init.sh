#! /bin/sh

who=`whoami`
sudo chown $who ~/.cnpm

# npmjs.org 速度慢，换npm.taobao
sudo npm install -g cnpm --registry=https://registry.npm.taobao.org

# 下载开发工具至全局，以便命令行可以直接运行， 例如运行｀gulp｀
sudo cnpm i gulp webpack webpack-dev-server iced-coffee-script -g

# 根据package.json 下载依赖
cnpm i


echo "run \`gulp watch\` to start develop"