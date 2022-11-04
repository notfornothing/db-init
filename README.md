
<div align="center">
  <h1> DbInit </h1>
</div>
<p align="center">
	<strong>DbInit 是一款基于Springboot的数据库初始化插件。</strong>
</p>
<p align="center">
  <!-- <img src="https://img.shields.io/github/v/release/mereithhh/van-blog?display_name=tag" /> -->
  <img src="https://img.shields.io/github/stars/DingDangDog/db-init" />
  <img src="https://img.shields.io/github/forks/DingDangDog/db-init" />
  <img src="https://img.shields.io/github/issues/DingDangDog/db-init?color=important" />
  <img src="https://img.shields.io/badge/license-MIT-yellow.svg" />
</p>

> 工具尚在开发阶段，不推荐使用，若想学习功能思路，可参考`1.0`版本分支。

## Application Scenarios
- 当你想要在Springboot项目启动时，想要自动建表并初始化数据；
- 当你想要在Springboot项目启动时，想要更新自己的数据库数据；
- 当你想要在Springboot项目启动时，想要执行自己的SQL脚本。

## Dependency
### 方式1：springboot-starter
```xml
<dependency>
  <groupId>io.github.dingdangdog</groupId>
  <artifactId>db-init-spring-boot-starter</artifactId>
  <version>1.0</version>
</dependency>
```

#### Configuration Example
- first, please config spring database, and then:
```yaml
# 多数据源示例
db-init:
  db-list:
    - name: dataSource  # 数据源名称
      enable: true      # 是否启用db-init
      type: mysql       # 数据源类型
      logging: false    # 记录初始日志（未实现）
    - name: oldDataSource
      enable: true
      type: mysql
      logging: false
    - name: oracleDataSource
      enable: true
      type: Oracle
      logging: false
```

### 方式2：core
```xml
<dependency>
  <groupId>io.github.dingdangdog</groupId>
  <artifactId>db-init</artifactId>
  <version>1.0</version>
</dependency>
```


#### Configuration Example
- first, please config spring database;
- second, you need add configure: [DbInitAutoConfigure](https://github.com/DingDangDog/db-init/blob/1.0/db-init-spring-boot-starter/src/main/java/top/oldmoon/configure/DbInitAutoConfigure.java), and then:
```yaml
# 多数据源示例
db-init:
  db-list:
    - name: dataSource  # 数据源名称
      enable: true      # 是否启用db-init
      type: mysql       # 数据源类型
      logging: false    # 记录初始日志（未实现）
    - name: oldDataSource
      enable: true
      type: mysql
      logging: false
    - name: oracleDataSource
      enable: true
      type: Oracle
      logging: false
```


## Releases
### 1.0 (BETA)
- 发布：[db-init 1.0 版本补发](https://github.com/DingDangDog/db-init/releases/tag/v1.0)
- 源码：[DingDangDog/db-init/tree/1.0](https://github.com/DingDangDog/db-init/tree/1.0)


## Plan
- Sorry, no plan. Because nobody pays attention to and uses it, Even me.
- So, any update depends on my mood.
- But, I try to update weekly.

## Contributing
- [DingDangDog](https://github.com/DingDangDog)

## Acknowledgement
### JetBrains
<a href='https://www.jetbrains.com/community/opensource'><img src='https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.png' width='240px'/></a>
- 非常感谢【[JetBrains](https://www.jetbrains.com/community/opensource)】提供的开发工具免费许可证！
