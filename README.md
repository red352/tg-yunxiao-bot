# tg-yunxiao-bot

# 简介

- 这是一个`telegram bot`后端程序。
- 机器人是完全在电报应用程序中运行的小型应用程序。用户通过灵活的界面与机器人`交互`，这些界面可以支持任何类型的任务或服务。
- 电报机器人平台拥有超过 10 万个机器人，对用户和开发人员都是`免费`的！
- [查看详情](https://core.telegram.org/bots)

# 环境

`Java17`  `SpringBoot3`

# 配置

配置 `application.yml` ，`name` 和 `token` 为申请的bot用户名和密钥

```yaml
spring:
  main:
    web-application-type: none
tg:
  bot:
    name: "yunxiao_bot"
    token: "***"
wallhaven:
  key: ****
```



# 部署

***首先配置`application.yml`，设置 `name`和`token`***

1. Docker部署


``` shell
git clone https://github.com/red352/tg-yunxiao-bot.git
```


``` shell
docker build -t tg-yunxiao-bot .
```


``` shell
docker run -d --name tg-yunxiao-bot tg-yunxiao-bot
```

2. 本地环境部署


在`/myapp`目录运行该脚本，首先在   `此目录` 下复制一份写好配置文件 `application.yml`

``` shell
*#!/bin/bash

# 定义变量
PROJECT_NAME="tg-yunxiao-bot"
BASE="/myapp"
PROJECT_PATH="$BASE/$PROJECT_NAME"
JAVA_CMD="/usr/bin/java"

# 检查项目目录是否存在
if [ -d "$PROJECT_PATH" ]; then
  echo "项目目录已存在，拉取最新代码并重启守护进程..."
  \# 杀死之前运行的 Java 守护进程
  if [ -f "$PROJECT_PATH/pid.log" ]; then
    pkill -F "$PROJECT_PATH/pid.log"
    rm "$PROJECT_PATH/pid.log"
  fi
  \# 拉取最新代码
  cd "$PROJECT_PATH"
  git pull --ff-only
else
  echo "项目目录不存在，克隆代码并启动守护进程..."
  \# 克隆代码
  git clone https://github.com/red352/tg-yunxiao-bot.git "$PROJECT_PATH"
  cd "$PROJECT_PATH"
fi

# 构建和启动 Java 程序
mvn clean package -DskipTests
echo "构建完成"
cd target
cp $BASE/application.yml .
echo "启动中..."
# 查找 JAR 包并启动 Java 程序
JAR_FILE=$(find . -name "$PROJECT_NAME-*.jar" | head -1)
if [ -z "$JAR_FILE" ]; then
  echo "未找到 JAR 包"
else
    nohup "$JAVA_CMD" -jar "$JAR_FILE" >> app.log 2>&1 &
    echo $! > pid.log
fi
```



# commands

```markdown

<!-- include command.md -->

```