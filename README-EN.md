Langhai Blog  
[中文](./README.md)  

Introduction:  
Langhai Blog is a single architecture project built quickly based on SpirngBoot, 
which is simple and convenient to deploy and suitable for personal blog system construction.

Online presentation：http://www.langhai.cc  
The actual effect is subject to the code (the demo site is the previous version)   
contact information QQ：676558206 email 676558206@qq.com langhai666@gmail.com

Screenshot of some pages  
![登录页面截图](./images/登录页面截图.png)

Deployment method:  [detailed description](https://langhai.cc/article/articleShow?id=38)  
linux ==>> nohup java -jar langhai-blogs.jar > langhai.log &  
windows ==>> java -jar langhai-blogs.jar

Technical selection:  
springboot Back-end rapid building framework   
thymeleaf Data template engine  
hutool Java toolset

Copyright/citation statement  
The main front-end template of Langhai blog system comes from the html5up.net website.  

Basic components:  
Relational database MySQL  
Non-relational database Redis  
Picture storage server minio  
Search engine (optional) elasticSearch  
Message Queuing (optional) rabbitMQ   <a href="https://langhai.cc/article/articleShow?id=33">RabbitMQ All instructions</a>
