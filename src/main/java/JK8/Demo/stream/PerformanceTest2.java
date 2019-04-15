package JK8.Demo.stream;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerformanceTest2 {

    public static List<Integer> buildIntRange() {
        List<Integer> numbers = new ArrayList<>(5);
        for (int i = 0; i < 60000; i++)
            numbers.add(i);
        return Collections.unmodifiableList(numbers);
    }

    public static void write(String source,String path) {
        BufferedWriter output = null;
        try {
            File file = new File(path);
            output = new BufferedWriter(new FileWriter(file,true)); //true表示是否追加
            output.write(source);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        List<Integer> source = buildIntRange();

        // 传统方式的遍历
        long start = System.currentTimeMillis();
        for (int i = 0; i < source.size(); i++) {
            write(source.get(i)+"\r\n", "./example1.txt");
        }
        System.out.println("传统方式 : " + (System.currentTimeMillis() - start) + "ms");

        // 单管道stream
        start = System.currentTimeMillis();
        source.stream().forEach(r -> {
            write(r+";", "./example2.txt");
        });
        System.out.println("stream : " + (System.currentTimeMillis() - start) + "ms");

        // 多管道parallelStream
        start = System.currentTimeMillis();
        source.parallelStream().forEach(r -> {
            write(r+";", "./example3.txt");
        });
        System.out.println("parallelStream : " + (System.currentTimeMillis() - start) + "ms");
    }
}
