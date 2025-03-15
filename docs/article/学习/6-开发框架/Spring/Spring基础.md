---
tag:
 - 开发框架
 - Spring
---

# Spring基础

## 1 bean 的生命周期

**创建 Bean 的实例**：Bean 容器首先会找到配置文件中的 Bean 定义，然后使用 Java 反射 API 来创建 Bean 的实例。

**Bean 属性赋值/填充**：为 Bean 设置相关属性和依赖，例如 `@Autowired` 等注解注入的对象、`@Value` 注入的值、`setter` 方法或构造函数注入依赖和值、`@Resource` 注入的各种资源。

**Bean 初始化**：

+ 如果 Bean 实现了 `BeanNameAware` 接口，调用 `setBeanName()` 方法，传入 Bean 的名字。
+ 如果 Bean 实现了 `BeanClassLoaderAware` 接口，调用 `setBeanClassLoader()` 方法，传入 `ClassLoader` 对象的实例。
+ 如果 Bean 实现了 `BeanFactoryAware` 接口，调用 `setBeanFactory()` 方法，传入 `BeanFactory` 对象的实例。
+ 与上面的类似，如果实现了其他 `*.Aware` 接口，就调用相应的方法。
+ 如果有和加载这个 Bean 的 Spring 容器相关的 `BeanPostProcessor` 对象，执行 `postProcessBeforeInitialization()` 方法。
+ 如果 Bean 实现了 `InitializingBean` 接口，执行 `afterPropertiesSet()` 方法。
+ 如果 Bean 在配置文件中的包含 `init-method` 属性，执行指定的方法。

**销毁 Bean**：销毁并不是说要立马把 Bean 给销毁掉，而是把 Bean 的销毁方法先记录下来，将来需要销毁 Bean 或者销毁容器的时候，就调用这些方法去释放 Bean 所持有的资源。

+ 如果 Bean 实现了 `DispoaableBean` 接口，执行 `destroy()` 方法。
+ 如果 Bean 在配置文件中的定义包含 `destory-method` 属性，执行指定的 Bean 销毁方法。或者，也可以直接通过 `@PreDestroy` 注解标记 Bean 销毁之前执行的方法。

