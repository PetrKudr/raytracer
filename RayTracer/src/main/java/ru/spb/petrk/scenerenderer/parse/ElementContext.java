package ru.spb.petrk.scenerenderer.parse;

/**
 *
 * @author PetrK
 */
public interface ElementContext {

    <T> T getProperty(String name, Class<T> clazz);
    
    <T> void putProperty(String name, Class<T> clazz, T property);
    
}
