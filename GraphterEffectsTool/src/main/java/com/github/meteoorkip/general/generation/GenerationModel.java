package com.github.meteoorkip.general.generation;

import com.github.meteoorkip.general.ViewModel;

import javax.inject.Inject;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Generation Singleton Model
 *
 * <P>This is the model for the current generation.
 * It contains the current generation which the GeneratorRunnable
 * is working. It also makes sure that the generation can't start
 * before all observers of the Generation Model have been added
 * to the new generation
 */

public class GenerationModel extends Observable {

    @Inject ViewModel viewModel;
    private Generation generation;
    protected final Lock lock = new ReentrantLock();
    protected Condition allObserversAdded = lock.newCondition();

    /**
     * @return the last {@link Generation}.
     */
    public Generation getGeneration(){
        return generation;
    }

    /**
     * Sets a new {@link Generation} for the Generation Model. It also
     * notifies it's observers a new generation has been started with a
     * {@link GenerationProgress}.
     *
     * @param generation The newly started generation
     */
    protected void setGeneration(Generation generation) {
        lock.lock();
        this.generation = generation;
        setChanged();
        if (!generation.isDebug()) {
            notifyObservers(GenerationProgress.NORMALGENERATIONSTARTED);
        } else {
            notifyObservers(GenerationProgress.DEBUGGENERATIONSTARTED);
        }
        lock.unlock();
    }

    /**
     * Method to be called whenever an object want's to observe
     * the current generation. Whenever the newly generated generation has
     * the same amount of observers as this model {@link GenerationModel#allObserversAdded}
     * will be signalled.
     * @param observer the object which wants to observe the generation
     */
    public void addObserverToGeneration(Observer observer){
        lock.lock();
        generation.addObserver(observer);
        if (this.countObservers() + 1 == generation.countObservers()){ //The +1 is for the GeneratorRunnable
            allObserversAdded.signal();
        }
        lock.unlock();
    }


    private static GenerationModel ourInstance = new GenerationModel();

    public static GenerationModel getInstance() {
        return ourInstance;
    }

    private GenerationModel() {
    }
}
