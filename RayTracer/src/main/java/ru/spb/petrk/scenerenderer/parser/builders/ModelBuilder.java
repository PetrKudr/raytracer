package ru.spb.petrk.scenerenderer.parser.builders;

import java.util.List;
import ru.spb.petrk.scenerenderer.parser.ElementContext;
import ru.spb.petrk.scenerenderer.parser.ElementHandler;
import ru.spb.petrk.scenerenderer.parser.PropertyNames;
import ru.spb.petrk.scenerenderer.parser.model.ModelParser;
import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.primitives.Triangle;

/**
 *
 * @author PetrK
 */
public class ModelBuilder extends AbstractElementBuilder<List<Triangle>> {
    
    private String path;
    

    public ModelBuilder(ElementContext parentContext, FinishCallback<List<Triangle>> callback) {
        super(parentContext, callback);
    }

    @Override
    public ElementHandler createHandler(String name) {
        if ("file_name".equals(name)) {
            return new StringValueBuilder(parentContext, new FinishCallback<String>() {

                @Override
                public void handle(String value) {
                    ModelBuilder.this.path = value;                           
                }
                
            });
        }
        return null;
    }

    @Override
    protected List<Triangle> build() {
        ModelParser parser = new ModelParser(parentContext.getProperty(PropertyNames.MATERIAL, Material.class));
        return parser.parse(path);
    }    
    
}
