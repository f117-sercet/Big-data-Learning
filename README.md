# Big-data-Learning  
计划用一到三年的时间，打造大数据从低阶到高阶的整合资料。其中有开发，有项目文档，有读书笔记的整理等

快速部署
clone 项目到本地 git@github.com:lenve/vhr.git
数据库脚本使用 Flyway 管理，不需要手动导入数据库脚本，只需要提前在本地 MySQL 中创建一个空的数据库 vhr，并修改项目中关于数据的配置（resources 目录下的 application.properties 文件中）即可
提前准备好 Redis，在 项目的 application.properties 文件中，将 Redis 配置改为自己的
提前准备好 RabbitMQ，在项目的 application.properties 文件中将 RabbitMQ 的配置改为自己的（注意，RabbitMQ 需要分别修改 mailserver 和 vhrserver 的配置文件）
在 IntelliJ IDEA 中打开 vhr 项目，启动 mailserver 模块
运行 vhrserver 中的 vhr-web 模块
OK，至此，服务端就启动成功了，此时我们直接在地址栏输入 http://localhost:8081/index.html 即可访问我们的项目，

进入到vuehr目录中，在命令行依次输入如下命令：
# 安装依赖
npm install

# 在 localhost:8080 启动项目
npm run serve
