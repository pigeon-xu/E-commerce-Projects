

框架结构图
-------

![](https://github.com/pigeon-xu/miaosha/raw/main/框架结构图.jpg)


实现功能
------
####  实现功能：用户注册和登录、商品展示、下单交易，秒杀倒计时等<br>
接入层使用了springmvc的controller，定义了对应的viewobject返回了通用的对象，并且在controller层通过通用的异常处理方式结合通用的返回对象返回了对应前后端分离的Json的模型<br>
业务层使用对应的mabtis接入和model层领域模型的概念完成对应的用户服务、商品服务、交易服务和活动服务相关核心服务的业务层<br>
数据层使用了@Transactional标签来完成事务的切面，使用数据库mybaitis的dao来完成对数据相关的操作<br>

项目要点
------
#### 1. 在mybatis-generator.xml配置文件中在对应生成表类名配置中加入如下代码,避免生成不常用方法

```Java
enableCountByExample="false"
enableUpdateByExample="false"        
enableDeleteByExample="false"
enableSelectByExample="false"
selectByExampleQueryId="false" 
```

#### 2. 前端 ajax 调用接口获取验证码 html/getotp.html，出现跨域请求问题的解决方法：<br>
   
```Java
@CrossOrigin(origins = {"*"},
allowCredentials = "true") 
allowedHeaders 允许前端将 token 放入 header 做 session 共享的跨域请求。
allowCredentials 授信后，需前端也设置 xhfFields 授信才能实现跨域 session 共享。 xhrFields: {withCredentials: true},
```

#### 3. 统一前端返回格CommonReturnType <br>

    {status:xx,object:xx} dataobject   ->  与数据库对应的映射对象 model ->
    用于业务逻辑service的领域模型对象 viewobject -> 用于前端交互的模型对象

#### 4. 使用 hibernate-validator 通过注解来完成模型参数校验

    insertSelective 中设置 keyProperty="id" useGeneratedKeys="true" 使得插入完后的 DO 生成自增 id 。
    insertSelective与insert区别： insertSelective对应的sql语句加入了NULL校验
    即只会插入数据不为null的字段值（null的字段依赖于数据库字段默认值）insert则会插入所有字段，会插入null。 
#### 5. 数据库设计规范，设计时字段要设置为not null，并设置默认值，避免唯一索引在null情况下失效等类似场景 
#### 6. 解决如果事务createorder下单如果回滚，该下单方法中获得流水号id回滚，使等到的id号可能再一次被使用
        
```Java
 在generatorOrderNo方法前加注解：
 Transactional(propagation = Propagation.REQUIRES_NEW)
```
#### 7. 使用聚合模型在itemModel加入PromoModel promoModel，若不为空表示其有未结束的秒杀活动；
    在orderModel中加入promoId，若不为空，则以秒杀方式下单
资源
----
 Metronic框架：基于bootstrap的付费ui模板
