package com.github.meteoorkip.general.generation;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * Generation Runnable
 * <P>This runnable is reponsible for executing the generation.
 * When runned it will create a new (debug) {@link Generation} and will
 * excecute the different generation methods on the {@link Generation}, e.g:{@link Generation#compileGraafVis()}
 */

public class GeneratorRunnable implements Runnable, Observer {

    private Path scriptFile;
    private Path graphFile;
    private Generation generation;
    private GenerationProgress maxGenerationProgress;

    /**
     * Constructor for a {@link GeneratorRunnable} for a normal {@link Generation}.
     *
     * @param scriptFile The path of where the Graafvis Script is stored
     * @param graphFile  The path of where the Abstract Syntax Graph is stored See //TODO{@link } which fileformats are
     *                   suported.
     */
    public GeneratorRunnable(Path scriptFile, Path graphFile) {
        this(scriptFile, graphFile, GenerationProgress.GENERATIONFINISHED);
    }

    /**
     * Constructor for a {@link GeneratorRunnable} for a debug {@link Generation}. The generation becomes a debug
     * generation if the {@code maxProgress} is not {@link GenerationProgress#GENERATIONFINISHED}.
     *
     * @param scriptFile             The path of where the Graafvis Script is stored
     * @param graphFile              The path of where the Abstract Syntax Graph is stored See //TODO{@link } which
     *                               fileformats are suported.
     * @param targetProgress The {@link GenerationProgress} until which the generation is supposed to
     *                               continue.
     */
    public GeneratorRunnable(Path scriptFile, Path graphFile, GenerationProgress targetProgress) {
        this.scriptFile = scriptFile;
        this.graphFile = graphFile;
        this.maxGenerationProgress = targetProgress;
        generation = new Generation(scriptFile, graphFile, maxGenerationProgress);

    }

    /**
     * Creates a new (debug) {@link Generation}, and tries to excecute the generation methods on it.
     * The generation won't be started until {@link GenerationModel#allObserversAdded} has been signalled.
     */
    @Override
    public synchronized void run() {
        GenerationModel.getInstance().setGeneration(generation);
        GenerationModel.getInstance().addObserverToGeneration(this);
        try {
            while (GenerationModel.getInstance().countObservers() + 1 != generation.countObservers()) {
                GenerationModel.getInstance().lock.lock();
                GenerationModel.getInstance().allObserversAdded.await();
                GenerationModel.getInstance().lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //TODO: error resolving
        try {
            boolean hasSolution = false;
            if (maxGenerationProgress == GenerationProgress.GENERATIONFINISHED) {
                generation.compileGraafVis();
                generation.loadGraph();
                generation.loadProlog();
                hasSolution = generation.solve();
                if (hasSolution) {
                    generation.generateSVG();
                }
                GeneratorUtils.saveGeneratedSVG(graphFile.getFileName().toString().split("\\.")[0], generation.getGeneratedSVG());
            } else {
                if (maxGenerationProgress.ordinal() >= GenerationProgress.GRAAFVISCOMPILED.ordinal()) {
                    generation.compileGraafVis();
                }
                if (maxGenerationProgress.ordinal() >= GenerationProgress.GRAPHLOADED.ordinal()) {
                    generation.loadGraph();
                }
                if (maxGenerationProgress.ordinal() >= GenerationProgress.PROLOGLOADED.ordinal()) {
                    generation.loadProlog();
                }
                if (maxGenerationProgress.ordinal() >= GenerationProgress.SOLVED.ordinal()) {
                    hasSolution = generation.solve();
                }
                if (hasSolution && maxGenerationProgress.ordinal() >= GenerationProgress.SVGGENERATED.ordinal()) {
                    generation.generateSVG();
                }
            }
        } catch (Exception e) {
            generation.setException(e);
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        if (Objects.equals(arg, GenerationProgress.ABORTED)) {
            Thread.currentThread().interrupt();
        }
    }
}
