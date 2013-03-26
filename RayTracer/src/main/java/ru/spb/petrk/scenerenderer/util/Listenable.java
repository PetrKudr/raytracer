package ru.spb.petrk.scenerenderer.util;

/**
 *
 * @author PetrK
 */
public interface Listenable<T> {
    
    void addListener(Listener<T> listener);
    
    void removeListener(Listener<T> listener);

}
