package site.xiaobu.example.nio;

import lombok.SneakyThrows;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class FileChannelDemo2 {
    @SneakyThrows
    public static void main(String[] args) {
        RandomAccessFile randomAccessFile1 = new RandomAccessFile("01.txt", "rw");
        FileChannel fileChannel1 = randomAccessFile1.getChannel();

        RandomAccessFile randomAccessFile2 = new RandomAccessFile("02.txt", "rw");
        FileChannel fileChannel2 = randomAccessFile2.getChannel();

        fileChannel1.transferTo(0, fileChannel1.size(), fileChannel2);

        fileChannel1.close();
        fileChannel2.close();
    }
}
