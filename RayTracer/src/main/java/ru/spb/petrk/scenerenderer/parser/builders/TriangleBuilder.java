package ru.spb.petrk.scenerenderer.parser.builders;

import ru.spb.petrk.scenerenderer.parser.ElementContext;
import ru.spb.petrk.scenerenderer.parser.ElementHandler;
import ru.spb.petrk.scenerenderer.parser.PropertyNames;
import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.primitives.Triangle;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
class TriangleBuilder extends AbstractElementBuilder<Triangle> {
    
    private final Vector3 vertices[] = new Vector3[3];
    
    private int paramCount;
    

    public TriangleBuilder(ElementContext parentContext, FinishCallback<Triangle> callback) {
        super(parentContext, callback);
    }

    @Override
    public ElementHandler createHandler(String name) {
        if ("pos".equals(name) && paramCount < 3) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {
                
                int index = paramCount++;
                
                @Override
                public void handle(Vector3 value) {
                    TriangleBuilder.this.vertices[index] = value;
                }
                
            });
        }
        return null;
    }

    @Override
    protected Triangle build() {
        return new Triangle(parentContext.getProperty(PropertyNames.MATERIAL, Material.class), vertices[0], vertices[1], vertices[2]);
    }

}
