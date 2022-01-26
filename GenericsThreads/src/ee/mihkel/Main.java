package ee.mihkel;

import java.util.Arrays;
import java.util.List;

public class Main {

    // GENERICS
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("üks", "kaks", "kolm");
        List<Integer> integers = Arrays.asList(1, 2, 3);
        List<Long> longs = Arrays.asList(1L, 2L, 3L);
        List<Number> numbers = Arrays.asList(1, 2.9, 3L);
        List<Boolean> booleans = Arrays.asList(true, false, true);

        System.out.println(GenericsUtil.getFirst(integers));
        GenericsUtil.printList(booleans);

        GenericsUtil.sum(numbers);

        GenericsUtil.printNumbers(numbers);

        // THREADS
        Threads thread1 = new Threads(1);
        Threads thread2 = new Threads(2);
        Threads thread3 = new Threads(3);
        thread1.start();
        thread2.start();
        thread3.start();
    }

}
