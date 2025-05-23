---
tag:
 - Java
---

# Java基础

## 一、基础概念与常识

### （一）Java语言有哪些特点？

可移植性、平台无关性、多线程、安全、解释与编译共存。

> 现在许多语言也有这些特点，真正能让大家接受Java语言更多依赖的是其丰富的生态。

### （二）Java SE vs Java EE vs Java ME

Java SE 是 Java发行的标准版。

Java EE 是 Java发行的企业版，支持 Web 项目的开发应用。

Java ME 是Java发行的微端版，原先是想支持IOT方向，但是由于Java语言的执行速度不够高，已被淘汰。

### （三）JDK vs JRE vs JVM

JDK 是 Java 开发工具包，包含了 JRE 和相关的Java编译调试等工具。

JRE 是 Java 运行时环境，包含了 JVM 和第三方类库。

JVM 是 Java 虚拟机，保证 Java 编译后的 .class 文件能够运行，也是 Java 实现平台无关性的保证。

### （四）什么是字节码？采用字节码文件的好处？

字节码文件是 Java 编译后产生的文件，能够运行在 JVM 上的文件。

字节码文件提高 Java 的运行效率，且一份字节码文件通过 JVM 能够在各种操作系统上运行，大大提高了开发效率。

### （五）为什么说 Java 语言“编译与解释”并存

首先由开发人员进行 .java 文件编写，编写完成进行编译，生成 .class 文件，再由 JVM 解释成机器指令。从而使得 Java 的运行效率高于解释型语言，在开发效率上强于编译型语言。

### （六）AOT 有什么优点？为什么不全部使用 AOT 呢？

JDK 9 新的编译模式 AOT。这种编译模式 JIT 不同，属于静态编译（在程序执行前就编译成机器命令）。AOT 避免了 JIT 的预热等各方面开销，提高了 Java 启动速度。并且 AOT 能够减少内存占用和增强 Java 程序的安全性（AOT 编译后的代码不容易被反编译和修改），特别适合云原生场景。

AOT很适合云原生场景，对微服务架构的支持也很好。但 AOT 不支持 Java 的一些动态特性，如反射、动态代理、动态加载、JNI 等。然而许多框架和库用到了这些特性，如果只使用 AOT 编译，就无法使用这些框架和库。所以需要针对性地去做适配和优化。为了支持类似的动态特性，所以使用 JIT 编译。

### （七）Oracle JDK vs Open JDK

Oracle JDK 是指 Sun 公司被 Oracle 收购后，所发行的 JDK 版本。在 Open JDK 基础之上提供了特有的功能和工具。Oracle JDK 提供免费版本但是有时间限制，JDK 8u221 之前主要不升级可以无限期免费。不开源且使用 BCL/OTN 协议获取许可。提供长期支持版本。

Open JDK， Sun 公司在 1995 年开源了 Java，于 2009 年被 Oracle 公司收购，由于原先 Java 开源已经有广泛的生态，所以若是 Oracle 公司若是直接闭源，会引起不满，导致 Java语言失去竞争力，就将一部分代码开源出来成 Open JDK。Open JDK 开源免费使用GPL v2 协议获取许可，但不提供长期支持版本。

### （八）Java 和 C++ 的区别？

Java 和 C++ 都是面向对象的语言，都支持封装、继承、多态。

+ Java 类单继承，接口多继承，C++ 多继承
+ Java 不支持指针直接访问内存，内存更加安全
+ Java 有垃圾回收机制，C++ 需要手动释放内存
+ C++ 同时支持方法重载和操作符重载，Java 只支持方法重载，和字符串一起用时 + 和 += 的操作符重载

## 二、基本语法

