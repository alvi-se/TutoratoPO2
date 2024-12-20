import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class HigherOrder {

    public static <T, R> Collection<R> map(Collection<T> collection, Function<T, R> function) {
        Collection<R> newCollection = new ArrayList<>();

        for (T obj : collection) {
            R mapped = function.apply(obj);
            newCollection.add(mapped);
        }

        return newCollection;
    }

    public static <T, R> Iterator<R> mapIterator(Iterator<T> iterator, Function<T, R> function) {
        return new Iterator<R>() {

            @Override
            public boolean hasNext() {
                // Mi basta controllare se l'iteratore da mappare ha ancora elementi
                return iterator.hasNext();
            }

            @Override
            public R next() {
                // Consuma il prossimo elemento dell'iteratore, lo passa alla funzione
                // in modo da mapparlo e poi lo restituisce
                return function.apply(iterator.next());
            }
        };
    }

    public static <T> void forEach(Iterable<T> iterable, Consumer<T> consumer) {
        for (T obj : iterable) {
            consumer.accept(obj);
        }
    }

    public static void main(String[] args) {
        List<String> strings = List.of(
                "Pippo",
                "Pluto"
        );

        Collection<Integer> lengths = map(strings, (str) ->  str.length() );

        Iterator<Integer> lengthsIterator = mapIterator(strings.iterator(), (str) -> str.length() );

        while (lengthsIterator.hasNext()) {
            System.out.println(lengthsIterator.next());
        }
    }
}
