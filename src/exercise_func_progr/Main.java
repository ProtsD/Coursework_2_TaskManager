package exercise_func_progr;

import javax.sound.midi.Soundbank;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    /* Напишите приложение, которое на входе получает через консоль текст, а в ответ выдает статистику:
            1. Количество слов в тексте.
            2. Топ-10 самых часто упоминаемых слов, упорядоченных по количеству упоминаний в обратном порядке. В случае
               одинакового количества упоминаний слова должны быть отсортированы по алфавиту.*/

    public static void main(String[] args) {
        String str = scanner.nextLine();

        String[] strArr = str.split(" ");

        Map<String, Integer> wordsMapWithCount = Arrays.stream(strArr)
                .filter(x -> !x.isBlank())
                .collect(Collectors.toMap(Function.identity(), word -> 1, Math::addExact));
        System.out.println("Words number: " + wordsMapWithCount.size());

        System.out.println("\nWord occurrence frequency: ");
        wordsMapWithCount.entrySet()
                         .stream()
                         .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                         .forEach(System.out::println);
    }
}
