package site.xiaobu.example.nio.chat;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient {
    @SneakyThrows
    public static void main(String[] args) {
        SocketChannel socket = SocketChannel.open(new InetSocketAddress(8889));
        socket.configureBlocking(false);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {

            String content = scanner.nextLine();

            if (StrUtil.isNotBlank(content)) {
                socket.write(StandardCharsets.UTF_8.encode(content));
            }
        }
    }
}
