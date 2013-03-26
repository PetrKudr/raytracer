package ru.spb.petrk.scenerenderer.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PetrK
 */
public abstract class AbstractListenable<T> implements Listenable<T> {

    private final List<Listener<T>> listeners = new ArrayList<Listener<T>>();

    
    @Override
    public void addListener(Listener<T> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener<T> listener) {
        listeners.remove(listener);
    }
    
    protected void launchListeners(T data) {
        for (Listener<T> listener : listeners) {
            listener.process(data);
        }
    }
    
}
