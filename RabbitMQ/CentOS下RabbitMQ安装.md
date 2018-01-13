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

