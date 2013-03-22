package ru.spb.petrk.scenerenderer.parser;

/**
 *
 * @author PetrK
 */
public interface ElementHandler<T> {
    
    /**
     * @param name of the element
     * @return appropriate builder or null
     */
    ElementHandler getHandler(String name);
    
    /**
     * Called before processing
     */
    void start();
    
    /**
     * Called for element body
     * @param body
     */
    void body(String body);
    
    /**
     * Called after processing
     */
    void finish();

}
