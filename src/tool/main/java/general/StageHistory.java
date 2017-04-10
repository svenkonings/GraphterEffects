package general;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

public class StageHistory {
    private static StageHistory ourInstance = new StageHistory();

    private Deque<String> deque;
    private String currentStage;
    public static StageHistory getInstance() {
        return ourInstance;
    }

    private StageHistory() {
        deque = new ArrayDeque<>(10);
    }

    public void add(String pageName) {
        String lastPage = deque.poll();
        String pageBeforeLastPage = deque.poll();

        //If the pageBefore the Last Page isn't the same as the page you're on now, the page you're trying to add
        //isnt being added to the history. This prevents circular backs because the user often isn't interested in this.
        if (pageBeforeLastPage != null) {
            deque.push(pageBeforeLastPage);
        }
        if (lastPage != null) {
            deque.push(lastPage);
        }

        if (!Objects.equals(pageBeforeLastPage, pageName)) {
            deque.push(pageName);
        }
    }

    public String poll(){
        return deque.poll();
    }

    public String peek(){
        return deque.peek();
    }

    public void setCurrentStage(String presenterClassName){
        add(currentStage);
        currentStage = presenterClassName;

        System.out.println(currentStage);
        System.out.println(deque.toString());
    }
}
