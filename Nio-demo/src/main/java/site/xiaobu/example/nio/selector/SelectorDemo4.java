package site.xiaobu.example.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class SelectorDemo4 {
    public static void main(String[] args) {
        int port = 8889;

        Selector selector = null;
        try {
            selector = Selector.open();
        } catch (IOException ignore) {
            System.exit(0);
        }

        ServerSocketChannel serverSocketChannel;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException ignore) {
        }


        while (true) {
            try {
                if (!(selector.select() > 0)) break;
            } catch (IOException e) {
                break;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();

                if (selectionKey.isAcceptable()) {
                    System.out.println("Accept Event ...");
                    ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel;
                    try {
                        socketChannel = serverChannel.accept();
                        socketChannel.configureBlocking(false);
                        SelectionKey key = socketChannel.register(selector, 0, null);
                        key.interestOps(SelectionKey.OP_READ);

                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < 7000000; i++) {
                            sb.append(i);
                        }

                        ByteBuffer buffer = StandardCharsets.UTF_8.encode(sb.toString());

                        int len = socketChannel.write(buffer);
                        System.out.println(len);

                        if (buffer.hasRemaining()){
                            key.interestOps(key.interestOps() + SelectionKey.OP_WRITE);
                            key.attach(buffer);
                        }
                    } catch (IOException ignore) {
                    }
                } else if (selectionKey.isWritable()) {
                    ByteBuffer attachment = (ByteBuffer) selectionKey.attachment();
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    try {
                        int len = socketChannel.write(attachment);
                        System.out.println(len);
                        if (!attachment.hasRemaining()) {
                            selectionKey.attach(null);
                            selectionKey.interestOps(selectionKey.interestOps() - SelectionKey.OP_WRITE);
                        }
                    } catch (IOException ignore) {
                    }
                }
            }
        }
    }

}
