package grauly.roborumblegui.data;

import java.util.ArrayList;
import java.util.function.Consumer;

public class SynchronousArrayListWrapper<E> {
    private final ArrayList<E> arrayList = new ArrayList<>();
    private int clearIteration;

    public synchronized void add(E element) {
        arrayList.add(element);
    }

    public synchronized void clear() {
        arrayList.clear();
        clearIteration++;
        System.out.println("Clearing data to iteration " + clearIteration + ".");
    }

    public synchronized void forEach(Consumer<E> action) {
        arrayList.forEach(action);
    }
}
