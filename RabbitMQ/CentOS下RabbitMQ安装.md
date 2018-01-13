#### 系统环境

OS:CentOS 6.9 x86_64

IP：192.168.60.129

Erlang版本：otp_src_20.1.tar.gz

http://erlang.org/download/

RabbitMQ版本：rabbitmq-server-generic-unix-3.6.12.tar.xz

http://www.rabbitmq.com/releases/rabbitmq-server/

#### 安装依赖软件包 

yum -y install make gcc gcc-c++ zlib zlib-devel ncurses-develkernel-devel m4 openssl openssl-devel unixODBC-devel libgnomeui-develmesa-libGL-devel mesa-libGLU-devel java-devel fop


#### 安装配置Erlang（RabbitMQ依赖其环境）

cd /opt/rabbitmq

wget http://erlang.org/download/otp_src_20.1.tar.gz

tar -zxvf otp_src_20.1.tar.gz

cd /usr/src/otp_src_20.1

./configure --prefix=/usr/local/erlang --enable-dirty-schedulers --enable-kernel-poll--enable-sctp --enable-hipe --enable-fips --with-termcap --with-javac--with-ssl

make && make install

--配置环境变量
vim /etc/profile.d/erlang.sh -->  export PATH=/usr/local/erlang/bin:$PATH

./etc/profile.d/erlang.sh

--测试

erl（命令完成后显示eshell版本信息即为成功）

#### 安装配置RabbitMQ

cd /opt/rabbitmq

wget http://www.rabbitmq.com/releases/rabbitmq-server/v3.6.12/rabbitmq-server-generic-unix-3.6.12.tar.xz

tar -xf rabbitmq-server-generic-unix-3.6.12.tar.xz

ln -sv rabbitmq_server-3.6.12/ rabbitmq

--配置环境变量

vim /etc/profile

-->export PATH=$PATH:/opt/rabbitmq/rabbitmq_server-3.6.12/sbin

--开放端口

vim /etc/sysconfig/iptables

````
-A INPUT -m state --state NEW -m tcp -p tcp --dport 5672 -j ACCEPT
-A INPUT -m state --state NEW -m tcp -p tcp --dport 15672 -j ACCEPT

````

--启动服务

cd /rabbitmq/sbin/

./rabbitmq-server -detached

--查看服务状态

./rabbitmqctl status

--关闭服务

 ./rabbitmqctl stop
 
 --启用插件（包含网页客户端）
 
./rabbitmq-plugins enable rabbitmq_management

访问http://localhost:15672即可（guest,guest）

#### 配置账号

默认网页是不允许访问的，需要增加一个用户修改一下权限

--添加用户

rabbitmqctl add_user jackzhang jackzhang

--添加权限

rabbitmqctl set_permissions -p "/" jackzhang ".*" ".*" ".*"

--修改用户角色

rabbitmqctl set_user_tags jackzhang administrator


#### 遇到的问题


1.unable to connect to node rabbit@JackZhangLinux: nodedown

1)解决方案：执行如下两条命令：

　　　　# /sbin/service rabbitmq-server stop
　　　　# /sbin/service rabbitmq-server start
　　　　# rabbitmqctl status

如果1)不生效，查看hosts文件，看是否配置有问题

2)本人是在hosts下加了如下配置

vim /etc/hosts
````
192.168.60.129 JackZhangLinux
````

2.
 TCP connection succeeded but Erlang distribution failed

 Authentication failed (rejected by the remote node), please check the Erlang cookie



