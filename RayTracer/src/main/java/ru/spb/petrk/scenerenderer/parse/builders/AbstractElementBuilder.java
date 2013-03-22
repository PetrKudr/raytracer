package ru.spb.petrk.scenerenderer.parse.builders;

import ru.spb.petrk.scenerenderer.parse.ElementContext;
import ru.spb.petrk.scenerenderer.parse.ElementHandler;

/**
 *
 * @author PetrK
 */
public abstract class AbstractElementBuilder<T> implements ElementHandler<T> {
    
    protected final ElementContext parentContext;
    
    private final FinishCallback<T> callback;

    
    public AbstractElementBuilder(ElementContext parentContext, FinishCallback<T> callback) {
        this.parentContext = parentContext;
        this.callback = callback;
    }
    
    

    @Override
    public void start() {
        // do nothing by default
    }

    @Override
    public void body(String body) {
        // do nothing by default
    }

    @Override
    public void finish() {
        callback.handle(build());
    }

    /**
     * Builds object
     * @param context - available context
     * @return object
     */
    protected abstract T build();
    
    
    /**
     * Callback interface for finish event
     * @param <T>  - built object
     */
    public static interface FinishCallback<T> {

        void handle(T value);
        
    }

}
