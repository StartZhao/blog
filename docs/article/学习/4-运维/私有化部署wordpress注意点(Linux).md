# 私有化部署wordpress注意点(Linux)

## 一、wordpress迁移

只要备份上相关的MySQL数据库和相应的主题即可完成wordpress迁移工作

## 二、更改ip地址出现css样式丢失问题

更改数据库中的wp_options表字段值

```sql
SELECT * FROM wp_options WHERE option_value LIKE '%原ip地址%';
```

将上述搜索出来的siteurl字段值为新的ip地址即可