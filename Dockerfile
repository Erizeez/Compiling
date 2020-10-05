# -- Dockerfile --
# 这个文件负责构建包含你的程序的 Docker 容器

# 使用 Java 12
FROM openjdk:12-alpine
# 向容器内复制文件
COPY ./* /com/
# 编译程序
WORKDIR /com/
RUN javac -d ./output ./com/Compiler.java
# 将当前目录设为输出目录
WORKDIR /app/output