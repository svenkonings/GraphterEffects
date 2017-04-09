package general.compiler;

import general.ViewModel;

import javax.inject.Inject;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CompilationModel extends Observable{

    @Inject ViewModel viewModel;
    private Compilation compilation;
    protected final Lock lock = new ReentrantLock();
    protected Condition allObserversAdded = lock.newCondition();

    public Compilation getCompilation(){
        return compilation;
    }

    protected void setCompilation(Compilation compilation){
        this.compilation = compilation;
        setChanged();
        if (!compilation.isDebug()) {
            notifyObservers(CompilationProgress.COMPILATIONSTARTED);
        } else {
            notifyObservers(CompilationProgress.DEBUGCOMPILATIONSTARTED);
        }
    }

    public void addObserverToCompilation(Observer observer){
        compilation.addObserver(observer);
        lock.lock();
        if (this.countObservers() == compilation.countObservers()){
            allObserversAdded.signal();
        }
        lock.unlock();
    }


    private static CompilationModel ourInstance = new CompilationModel();

    public static CompilationModel getInstance() {
        return ourInstance;
    }

    private CompilationModel() {
    }
}
