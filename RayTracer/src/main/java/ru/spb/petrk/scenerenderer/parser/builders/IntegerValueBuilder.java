package ru.spb.petrk.scenerenderer.parser.builders;

import ru.spb.petrk.scenerenderer.parser.ElementContext;
import ru.spb.petrk.scenerenderer.parser.ElementHandler;

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
