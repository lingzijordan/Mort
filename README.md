# Mort
This is the code for Mort project in Bigeye big data technology.

User guide:

build all projects:
mort: ./go.sh idea
cData: ./go.sh idea
mort-web: npm i

prepare evn:
1. install mysql: brew install mysql
2. start mysql: mysql.server start
3. init db:
* mort/cdata:  ./go.sh init
4. install and start Nginx:
mort/scripts: ./mort_nginx.sh init
5. run all projects:
* mort/cdata: ./go.sh run
* mort-web: gulp watch


in Browser:
http://localhost

Install node.js which is npm
