import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflection {
    public static class A {
        private void foo(int i) {
            System.out.println("Chiamata di metodo privato con argomento " + i);
        }
    }

    public static class B extends A {

    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        A a = new A();
        A b = new B();
        // Dobbiamo usare la wildcard perché 'a' potrebbe contenere
        // oggetti più specializzati di A
        Class<? extends A> aClass = a.getClass();
        Class<? extends A> bClass = b.getClass();

        System.out.println("Metodi di A:");
        // foo compare perché è dichiarato esattamente in A
        for (Method m : aClass.getDeclaredMethods()) {
            System.out.println(m);
        }
        System.out.println("---");

        System.out.println("Metodi di B:");
        // foo non compare perché è dichiarato in A, mentre in B
        // è ereditato.
        for (Method m : bClass.getDeclaredMethods()) {
            System.out.println(m);
        }
        System.out.println("---");

        // getMethod non restituisce metodi privati, bisogna usare getDeclaredMethod
        Method fooMethodA = aClass.getDeclaredMethod("foo", int.class);
        fooMethodA.setAccessible(true); // Rendiamo chiamabile il metodo privato
        fooMethodA.invoke(a, 10);


        try {
            // foo non è dichiarato in B ma in A, quindi non lo trova
            Method fooMethodB = bClass.getDeclaredMethod("foo", int.class);
        } catch (NoSuchMethodException e) {
            System.out.println("Metodo foo non trovato in b");
        }
        // Bisogna chiamare getDeclaredMethod sulla superclasse A,
        // in questo modo riesce a trovare il metodo
        Method fooMethodB = bClass.getSuperclass().getDeclaredMethod("foo", int.class);
        fooMethodB.setAccessible(true);
        fooMethodB.invoke(b, 20);
    }
}
