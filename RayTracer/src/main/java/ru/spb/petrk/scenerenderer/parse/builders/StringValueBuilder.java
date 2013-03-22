package ru.spb.petrk.scenerenderer.parse.builders;

import ru.spb.petrk.scenerenderer.parse.ElementContext;
import ru.spb.petrk.scenerenderer.parse.ElementHandler;

/**
 *
 * @author PetrK
 */
public class StringValueBuilder extends AbstractElementBuilder<String> {

    private String value;

    
    public StringValueBuilder(ElementContext parentContext, AbstractElementBuilder.FinishCallback<String> callback) {
        super(parentContext, callback);
    }   

    @Override
    public ElementHandler getHandler(String name) {
        throw new UnsupportedOperationException("String parameter could not have nested parameters.");
    }

    @Override
    public void body(String body) {
        this.value = body;
    }    

    @Override
    protected String build() {
        return value;
    }    
    
}
