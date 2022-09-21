package site.xiaobu.example.nio.buffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

// 黏包半包
public class BufferDemo2 {
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(1024);
        source.put("hello world\nI am zhangsan\nHo".getBytes(StandardCharsets.UTF_8));
        split(source);
        source.put("w are you\n".getBytes(StandardCharsets.UTF_8));
        split(source);
    }

    private static void split(ByteBuffer source) {
        source.flip();

        for (int i = 0; i < source.limit(); i++) {
            byte b = source.get(i);
            if (b == '\n') {
                int len = i - source.position();
                ByteBuffer tBuffer = ByteBuffer.allocate(len);
                for (int j = 0; j < len; j++) {
                    tBuffer.put(source.get());
                }

                tBuffer.flip();
                System.out.println(StandardCharsets.UTF_8.decode(tBuffer));
            }
        }

        source.compact();
    }
}
