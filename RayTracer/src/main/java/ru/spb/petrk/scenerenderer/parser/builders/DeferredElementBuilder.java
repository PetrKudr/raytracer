package ru.spb.petrk.scenerenderer.parser.builders;

import ru.spb.petrk.scenerenderer.parser.ElementHandler;

/**
 *
 * @author PetrK
 */
class DeferredElementBuilder<T> implements ElementHandler<T> {
    
    private final AbstractElementBuilder<T> builder;
    
    
    public DeferredElementBuilder(AbstractElementBuilder<T> builder) {
        this.builder = builder;
    }
    
    public void launchFinish() {
        builder.finish();
    }
    
    /*
    ***********************************************************
    * Delegated / Overrided methods
    ***********************************************************
    */

    @Override
    public ElementHandler getHandler(String name) {
        return builder.getHandler(name);
    }
    
    @Override
    public void start() {
        builder.start();
    }

    @Override
    public void body(String body) {
        builder.body(body);
    }

    @Override
    public void finish() {
        // do nothing (deferred builder must be "finished" manually later)
    }
    
}
