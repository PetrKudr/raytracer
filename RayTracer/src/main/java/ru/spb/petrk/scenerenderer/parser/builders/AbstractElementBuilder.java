package ru.spb.petrk.scenerenderer.parser.builders;

import java.util.ArrayList;
import java.util.List;
import ru.spb.petrk.scenerenderer.parser.DeferredElementHandler;
import ru.spb.petrk.scenerenderer.parser.ElementContext;
import ru.spb.petrk.scenerenderer.parser.ElementHandler;

/**
 *
 * @author PetrK
 */
public abstract class AbstractElementBuilder<T> implements ElementHandler<T> {
    
    protected final ElementContext parentContext;
    
    private final FinishCallback<T> callback;
    
    private List<DeferredElementHandler> deferredHandlers;

    
    public AbstractElementBuilder(ElementContext parentContext, FinishCallback<T> callback) {
        this.parentContext = parentContext;
        this.callback = callback;
    }

    @Override
    public final ElementHandler getHandler(String name) {
        ElementHandler handler = createHandler(name);
        
        // Support deferred handlers (we collect them and launch on finish event of current handler)
        if (handler instanceof DeferredElementHandler) {
            if (deferredHandlers == null) {
                deferredHandlers = new ArrayList<DeferredElementHandler>(8);
            }
            deferredHandlers.add((DeferredElementHandler)handler);
        }
        
        return handler;
    }        

    @Override
    public final void finish() {
        if (deferredHandlers != null) {
            for (DeferredElementHandler handler : deferredHandlers) {
                handler.launchFinish();
            }
        }
        callback.handle(build());
    }    

    @Override
    public void start() {
        // do nothing by default
    }

    @Override
    public void body(String body) {
        // do nothing by default
    }    
    
    /**
     * @param name - name of the element
     * @return handler for the element with the given name
     */
    protected abstract ElementHandler createHandler(String name);

    /**
     * Builds object
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
