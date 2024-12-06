---
tag:
 - 数据库
 - MongoDB
---

# MongoDB基础认识

## 一、MongoDB 是什么？

MongoDB 是一个基于分布式文件存储的开源 NoSQL 数据库系统，由 C++ 编写。MongoDB 提供面向文档的存储方式，操作起来比较简单，支持“无模式”的数据建模，可以存储比较复杂的数据类型，是一款非常流行的文档类型数据库。

在高负载的情况下，MongoDB 天然支持水平扩展和高可用，可以方便地添加更多的节点/实例，以保证服务性能和可用性。在许多场景下，MongoDB 可以用于代替传统的关系型数据库或键值存储方式。MongoDB 旨在为 WEB 应用提供可扩展的高性能数据存储解决方案。

## 二、MongoDB 的存储结构是什么？

MongoDB 的存储结构区别于传统的关系型数据库，主要由如下三个单元组成：

+ 文档：MongoDB 中最基本的单元，由 BSON 键值对组成，类似于关系型数据库的行。
+ 集合：一个集合可以包括多个文档，类似于关系型数据库的表。
+ 数据库：一个数据库可以包含多个集合，可以在 MongoDB 中创建多个数据库，类似于关系型数据库的数据库。

也就是说，MongoDB 将数据记录存储为文档（更具体来说是 BSON 文档），这些文档在集合中聚集在一起，数据库再存储一个或多个文档集合。

SQL 与 MongoDB 常见术语对比：

| SQL    | MongoDB    |
| ------ | ---------- |
| 行     | 文档       |
| 列     | 字段       |
| 主键   | 对象ID     |
| 索引   | 索引       |
| 表     | 集合       |
| 嵌套表 | 嵌入式文档 |
| 数据库 | 数据库     |

### 文档

MongoDB 中的记录就是一个 BSON 文档，它是由键值对组成的数据结构，类似于 JSON 对象，是 MongoDB 的基本数据单元。

文档的值可能包括其他文档、数组和文档数组。

文档的键是字符串。除了少数例外情况，键可以使用任意 UTF-8 字符。

+ 键不能含有 `\0` （空字符）。这个字符用来表示键的结尾。
+ `.` 和 `$` 有特别的意义，只有在特定环境下才能使用。
+ 以下划线 `_` 开头的键是保留的（不是严格要求）。

BSON 是 Binary JSON 的简称，是 JSON 文档的二进制表示，支持将文档和数组嵌入到其他文档和数组中，还包含允许表示不属于 JSON 规范的数据类型的扩展。BSON 的遍历速度优于 JSON，这也是 MongoDB 选择 BSON 的主要原因，但 BSON 需要更多的存储空间。

### 集合

MongoDB 集合存在于数据库中，没有固定的结构，也就是无模式的，这意味着可以往集合插入不同格式和类型的数据。不过，通常情况下，插入集合中的数据都会有一定的关联性。

集合不需要事先创建，当第一个文档插入或者第一个索引创建时，如果该集合不存在，则会创建一个新的集合。

集合名可以是满足下列条件的任意 UTF-8 字符串：

+ 集合名不能是空字符串 `""`。
+ 集合名不能含有 `\0` (空字符)，这个字符表示集合名的结尾。
+ 集合名不能以"system."开头，这是为系统集合保留的前缀。例如 `system.users` 这个集合保存着用户信息，`system.namespaces` 集合保存着所有数据库集合的信息。
+ 集合名必须以下划线或者字母符号开始，并且不能包含 `$`。

### 数据库

数据库用于存储所有集合，而集合又用于存储所有文档。一个 MongoDB 中可以创建多个数据库，每个数据库都有自己的集合和权限。

MongoDB 预留了几个特殊的数据库：

+ admin：admin 数据库主要是保存 root 用户和角色。例如， system.users 集合存储用户，system.roles 集合存储角色。一般不建议用户直接操作这个数据库。将一个用户添加到这个数据库，且使它拥有 admin 库上的名为 dbAdminAnyDatabase 的角色权限，这个用户自动继承所有数据库的权限。一些特定的服务器端命令也只能从这个数据库运行，比如关闭服务器。
+ local：local 数据库是不会被复制到其他分片的，因此可以用来存储本地单台服务器的任意集合。一般不建议用户直接使用 local 库存储任何数据，也不建议进行 CRUD 操作，因为数据无法被正常备份与恢复。
+ config：当 MongoDB 使用分片设置时，config 数据库可用来保存分片的相关信息。
+ test：默认创建的测试库，连接 mongod 服务时，如果不指定连接的具体数据库，默认就会连接到 test 数据库。

数据库名可以是满足以下条件的任意 UTF-8 字符串：

+ 不能是空字符串 `""`。
+ 不得含有 `' '`（空格）、`.`、`$`、`/`、`\`、`\0`（空字符）。
+ 应全部小写。
+ 最多 64 字节。

数据库名最终会变成文件系统里的文件，这也就是有如此多限制的原因。

## 三、MongoDB 有什么特点？

+ 数据记录被存储为文档：MongoDB 中的记录就是一个 BSON 文档。
+ 模式自由：集合不需要定义任何模式，能够用更少的数据对象表现复杂的领域模型对象。
+ 支持多种查询方式：MongoDB 查询 API 支持读写操作（CRUD）以及数据聚合、文本搜索和地理空间查询。
+ 支持 ACID 事务：NoSQL 数据库通常不支持事务，为了可扩展和高性能进行了权衡。但 MongoDB 是一个例外，与关系型数据库一样，MongoDB 事务同样具有 ACID 特性。 MongoDB 单文档原生支持原子性，也具备事务的特性。MongoDB 4.0 加入了对多文档事务的支持，但只支持复制集群部署模式下的事务，也就是说事务的作用域被限制为一个副本集内。MongoDB 4.2 引入分布式事务，增加了对分片集群上多文档事务的支持，并合并了对副本集上多文档事务的现有支持。
+ 高效的二进制存储：存储在集合中的文档格式是 BSON。
+ 自带数据压缩功能：存储同样的数据所需资源更少。
+ 支持 mapreduce：通过分治的方式完成复杂的聚合任务。不过，从 MongoDB 5.0 开始，map-reduce 已经不被官方推荐使用了，替代方案是聚合管道。聚合管道提供比 map-reduce 更好的性能和可用性。
+ 支持多种类型的索引：MongoDB 支持多种类型的索引，包括单字段索引、复合索引、多键索引、哈希索引、文本索引、地理位置索引等，每种类型的索引有不同的使用场合。
+ 支持 failover：提供自动故障恢复的功能，主节点发生故障时，自动从从节点中选举出一个新的主节点，确保集群的正确使用，这对客户端来说是无感知的。
+ 支持分片集群：MongoDB 支持集群自动切分数据，让集群存储更多的数据，具备更强的性能。在数据插入和更新时，能够自动路由和存储。
+ 支持存储大文件：MongoDB 的单文档存储空间要求不超过 16MB。对于超过 16MB 的大文件，MongoDB 提供了 GridFS 来进行存储，通过 GridFS，可以将大模型数据进行分块处理，然后将这些切分后的小文档保存在数据库中。

## 四、MongoDB 适合什么应用场景？

MongDB 的优势在于其数据模型和存储引擎的灵活性、架构的可扩展性以及强大的索引支持。

选用 MongoDB 应该充分考虑 MongoDB 的优势，结合实际项目的需求来决定：

+ 是否需要大数据量的存储？

+ 是否需要更多类型索引来满足更多应用场景？

+ ###### ……

## 五、MongoDB 常见命令

创建/切换数据库

  ```javascript
use <database_name>
  ```

向集合插入一个文档

  ```javascript
db.<collection_name>.insertOne({ key: "value" })
  ```

向集合插入多个文档

  ```javascript
db.<collection_name>.insertMany([{ key1: "value1" }, { key2: "value2" }])
  ```

查找集合中的所有文档

  ```javascript
db.<collection_name>.find().pretty()
  ```

根据条件查找文档

  ```javascript
db.<collection_name>.find({ key: "value" }).pretty()
  ```

更新集合中的一个文档

  ```javascript
db.<collection_name>.updateOne({ key: "value" }, { $set: { newKey: "newValue" } })
  ```

更新集合中的多个文档

  ```javascript
db.<collection_name>.updateMany({ key: "value" }, { $set: { newKey: "newValue" } })
  ```

删除集合中的一个文档

  ```javascript
db.<collection_name>.deleteOne({ key: "value" })
  ```

删除集合中的多个文档

  ```javascript
db.<collection_name>.deleteMany({ key: "value" })
  ```

在集合上创建索引以提高查询性能

  ```javascript
db.<collection_name>.createIndex({ key: 1 }) // 升序
db.<collection_name>.createIndex({ key: -1 }) // 降序
  ```

执行聚合操作以处理复杂的数据分析任务

  ```javascript
db.<collection_name>.aggregate([
    { $match: { key: "value" } },
	{ $group: { _id: "$anotherKey", count: { $sum: 1 } } }
])
  ```

列出服务器上的所有数据库

  ```javascript
show dbs
  ```

列出当前数据库中的所有集合

  ```javascript
show collections
  ```

删除当前使用的数据库

  ```javascript
db.dropDatabase()
  ```

删除集合及其所有文档

  ```javascript
db.<collection_name>.drop()
  ```
