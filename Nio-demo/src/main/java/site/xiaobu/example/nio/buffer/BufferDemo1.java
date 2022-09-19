package site.xiaobu.example.nio.buffer;

import java.nio.IntBuffer;

public class BufferDemo1 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(8);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(buffer.capacity() - i);
        }

        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
    }
}
