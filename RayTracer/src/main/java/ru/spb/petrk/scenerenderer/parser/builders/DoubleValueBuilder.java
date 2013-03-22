package ru.spb.petrk.scenerenderer.parser.builders;

import ru.spb.petrk.scenerenderer.parser.ElementContext;
import ru.spb.petrk.scenerenderer.parser.ElementHandler;

/**
 *
 * @author PetrK
 */
class DoubleValueBuilder extends AbstractElementBuilder<Double> {
    
    private double value;

    
    public DoubleValueBuilder(ElementContext parentContext, FinishCallback<Double> callback) {
        super(parentContext, callback);
    }   

    @Override
    public ElementHandler getHandler(String name) {
        throw new UnsupportedOperationException("Double parameter could not have nested parameters.");
    }

    @Override
    public void body(String body) {
        this.value = Double.valueOf(body);
    }    

    @Override
    protected Double build() {
        return value;
    }
    
}
