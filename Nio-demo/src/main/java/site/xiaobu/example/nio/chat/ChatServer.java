package site.xiaobu.example.nio.chat;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class ChatServer {

    @SneakyThrows
    public static void start() {
        Selector selector = Selector.open();

        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(8889));

        server.configureBlocking(false);

        server.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server started ...");

        while (selector.select() > 0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                if (selectionKey.isAcceptable()) {
                    SocketChannel socket = server.accept();
                    socket.configureBlocking(false);
                    socket.register(selector, SelectionKey.OP_READ);

                    System.out.println("[" + socket.getRemoteAddress() + "] 有用户连接");

                    socket.write(StandardCharsets.UTF_8.encode("欢迎连接 ..."));
                }

                if (selectionKey.isReadable()) {
                    handleReadable(selector, selectionKey);
                }

                iterator.remove();
            }
        }
    }

    private static void handleReadable(Selector selector, SelectionKey selectionKey) {
        SocketChannel socket = (SocketChannel) selectionKey.channel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        StringBuilder content = new StringBuilder();

        try {
            int len = socket.read(buffer);
            while (len > 0) {

                buffer.flip();

                content.append(StandardCharsets.UTF_8.decode(buffer));

                len = socket.read(buffer);
            }

        } catch (IOException e) {
            System.err.println("IO 异常,关闭连接");
            try {
                socket.close();
            } catch (IOException ignore) {
            }
        }


        if (StrUtil.isNotBlank(content)) {
            System.out.println(content);
            castContent(content.toString(), selector, socket);
        }
    }

    @SneakyThrows
    private static void castContent(String content, Selector selector, SocketChannel socket) {
        Set<SelectionKey> selectionKeys = selector.keys();
        for (SelectionKey selectionKey : selectionKeys) {
            SelectableChannel targetSocket = selectionKey.channel();
            if (targetSocket instanceof SocketChannel && targetSocket != socket) {
                ((SocketChannel) targetSocket).write(StandardCharsets.UTF_8.encode(getInetAddress(socket) + content));
            }
        }
    }

    public static String getInetAddress(SocketChannel socket) {
        try {
            SocketAddress socketAddress = socket.getRemoteAddress();
            return socketAddress.toString() + ": ";
        } catch (IOException ignore) {
        }
        return "匿名用户: ";
    }

    public static void main(String[] args) {
        start();
    }
}
