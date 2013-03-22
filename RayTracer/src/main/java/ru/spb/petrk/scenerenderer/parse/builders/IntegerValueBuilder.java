package ru.spb.petrk.scenerenderer.parse.builders;

import ru.spb.petrk.scenerenderer.parse.ElementContext;
import ru.spb.petrk.scenerenderer.parse.ElementHandler;

/**
 *
 * @author PetrK
 */
public class IntegerValueBuilder extends AbstractElementBuilder<Integer> {

    private int value;

    
    public IntegerValueBuilder(ElementContext parentContext, FinishCallback<Integer> callback) {
        super(parentContext, callback);
    }   

    @Override
    public ElementHandler getHandler(String name) {
        throw new UnsupportedOperationException("Integer parameter could not have nested parameters.");
    }

    @Override
    public void body(String body) {
        this.value = Integer.valueOf(body);
    }    

    @Override
    protected Integer build() {
        return value;
    }    
    
}
