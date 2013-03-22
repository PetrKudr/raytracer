package ru.spb.petrk.scenerenderer.parse;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PetrK
 */
public class ElementContextImpl implements ElementContext {
    
    private final ElementContext parentContext;
    
    private final List<Entry> entries = new ArrayList<Entry>();

    
    public ElementContextImpl() {
        this(null);
    }
    
    public ElementContextImpl(ElementContext parentContext) {
        this.parentContext = parentContext;
    }    

    @Override
    public <T> T getProperty(String name, Class<T> clazz) {
        for (Entry entry : entries) {
            if (entry.getName().equals(name) && clazz.isAssignableFrom(entry.getClazz())) {
                return (T)entry.getProperty();
            }
        }        
        return parentContext != null ? parentContext.getProperty(name, clazz) : null;
    }

    @Override
    public <T> void putProperty(String name, Class<T> clazz, T property) {
        entries.add(new Entry(name, clazz, property));
    }

    
    private static class Entry<T> {
        
        private final String name;
        
        private final Class<T> clazz;
        
        private final T property;

        public Entry(String name, Class<T> clazz, T property) {
            this.name = name;
            this.clazz = clazz;
            this.property = property;
        }

        public String getName() {
            return name;
        }

        public Class<T> getClazz() {
            return clazz;
        }

        public T getProperty() {
            return property;
        }        
    }
    
}
