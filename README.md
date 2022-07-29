# Spring-Cloud-Refactor
## 搭建的Cloud框架(重构版)
#### 之前搭建的架子 过于啰嗦了 感觉不太精简
#### 于是有了重新处理一下的念头 而且SpringBoot新版本也出了
#### docker文件夹下放置了两个模块的docker-compose文件
#### sql文件夹下放置了所需sql
#### 这次改用Nacos配置中心的方式 或者使用dynamic-config 修改了一大波细节
#### 项目运行环境请自行搭建Nacos + Mysql + Redis即可
#### userInfo模块下的邮件配置自行填写 采用HuTools邮件配置
#### 拦截器中的日志可以自行注掉 后期请求多起来的话 log也可能导致内存溢出
#### Token验证采用Sa-Token 使用的方法比较简单 后期再研究研究文档
#### 目前暂时想到这么多 有想法了再继续补充
