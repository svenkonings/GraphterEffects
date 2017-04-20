package general.compiler;

import general.ViewModel;

import javax.inject.Inject;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Compilation Singleton Model
 *
 * <P>This is the model for the current compilation.
 * It contains the current compilation which the CompilerRunnable
 * is working. It also makes sure that the compilation can't start
 * before all observers of the Compilation Model have been added
 * to the new compilation
 */

public class CompilationModel extends Observable {

    @Inject ViewModel viewModel;
    private Compilation compilation;
    protected final Lock lock = new ReentrantLock();
    protected Condition allObserversAdded = lock.newCondition();

    /**
     * @return the last {@link Compilation}.
     */
    public Compilation getCompilation(){
        return compilation;
    }

    /**
     * Sets a new {@link Compilation} for the Compilation Model. It also
     * notifies it's observers a new compilation has been started with a
     * {@link CompilationProgress}.
     *
     * @param compilation The newly started compilation
     */
    protected void setCompilation(Compilation compilation) {
        lock.lock();
        this.compilation = compilation;
        setChanged();
        if (!compilation.isDebug()) {
            notifyObservers(CompilationProgress.NORMALCOMPILATIONSTARTED);
        } else {
            notifyObservers(CompilationProgress.DEBUGCOMPILATIONSTARTED);
        }
        lock.unlock();
    }

    /**
     * Method to be called whenever an object want's to observe
     * the current compilation. Whenever the newly generated compilation has
     * the same amount of observers as this model {@link CompilationModel#allObserversAdded}
     * will be signalled.
     * @param observer the object which wants to observe the compilation
     */
    public void addObserverToCompilation(Observer observer){
        lock.lock();
        compilation.addObserver(observer);
        if (this.countObservers() + 1 == compilation.countObservers()){ //The +1 is for the CompilerRunnable
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
