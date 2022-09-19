package site.xiaobu.example.nio.selector;

import cn.hutool.core.date.DateUtil;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class SelectorDemo1 {
    @SneakyThrows
    public static void main(String[] args) {
        int port = 8889;

        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(port));
        socketChannel.configureBlocking(false);


        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(("Current Time:" + DateUtil.now() + ",Hello World").getBytes(StandardCharsets.UTF_8));

        buffer.flip();
        socketChannel.write(buffer);

        buffer.clear();
        socketChannel.close();
    }
}
