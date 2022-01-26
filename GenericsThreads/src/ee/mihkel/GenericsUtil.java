package ee.mihkel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenericsUtil {

    public static <T> T getFirst(List<T> array) {
        if (array.isEmpty()) {
            return null;
        } else {
            return array.get(0);
        }
    }

    public static <T,U> List<T> getHashMapKeys(HashMap<T,U> array) {
        if (array.isEmpty()) {
            return null;
        } else {
            List<T> newArray = new ArrayList<>();
            array.forEach((key,value)-> newArray.add(key) );
            return newArray;
        }
    }

    public static void printList(List<?> array) {
        for (Object o: array) {
            System.out.println(o);
        }
    }

//    List<T> newArray = new ArrayList<>();

    // Number number = 12.2;

                            // Number, Integer, Double, Float, Long, Byte, BigInt
    public static void sum(List<? extends Number> array) {
        double sum = 0;
        for (Number n: array) {
            sum += n.doubleValue(); // teeme double-ks, et saaks arvutada (muidu on Number ,mida ei saa arvutada)
        }
        System.out.println(sum);
    }

                                    // Long, Number
    public static void printNumbers(List<? super Long> array) {
        for (Object o: array) {
            System.out.println(o);
        }
    }

    public static <T,U,V> void printMultipleArrays(HashMap<T,U> array,
                                                List<? super Long> longs,
                                                List<V> array2) {
        array.forEach((key,value)-> System.out.println(key + ":" + value));
        for (Object o: longs) {
            System.out.println(o);
        }
        for (Object o: array2) {
            System.out.println(o);
        }
    }

}
