package site.xiaobu.example.nio.selector;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SelectorDemo2 {
    @SneakyThrows
    public static void main(String[] args) {
        int port = 8889;

        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(port));
        socketChannel.configureBlocking(true);

        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            writeBuffer.clear();

            StrBuilder builder = StrBuilder.create();

            String line = scanner.nextLine();

            writeBuffer.put(("Current Time:" + DateUtil.now() + "," + line).getBytes(StandardCharsets.UTF_8));
            writeBuffer.flip();
            socketChannel.write(writeBuffer);

            while ((socketChannel.read(readBuffer)) != -1) {
                readBuffer.flip();

                while (readBuffer.hasRemaining()) {
                    builder.append((char) readBuffer.get());
                }

                readBuffer.clear();
            }

            String responseContent = builder.toString();
            if (StrUtil.isNotBlank(responseContent)) {
                System.out.println(responseContent);
            }

            if (StrUtil.equals(line, "quit")) {
                break;
            }
        }

        socketChannel.close();
    }
}
