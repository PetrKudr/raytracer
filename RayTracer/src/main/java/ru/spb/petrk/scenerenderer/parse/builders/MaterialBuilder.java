package ru.spb.petrk.scenerenderer.parse.builders;

import ru.spb.petrk.scenerenderer.parse.ElementContext;
import ru.spb.petrk.scenerenderer.parse.ElementHandler;
import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.MaterialImpl;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
class MaterialBuilder extends AbstractElementBuilder<Material> {
    
    private Vector3 diffuse;
    
    private Vector3 ambient;
    
    private Vector3 specular;
    
    private double specularPower;
    
    private double reflectionPower;
    
    private double refractiveIndex;
    
    private double transparency;
    

    public MaterialBuilder(ElementContext parentContext, FinishCallback<Material> callback) {
        super(parentContext, callback);
    }
    
    @Override
    public ElementHandler getHandler(String name) {
        if ("diffuse".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    MaterialBuilder.this.diffuse = value;
                }
                
            });
        } else if ("ambient".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    MaterialBuilder.this.ambient = value;
                }
                
            });            
        } else if ("specular".equals(name)) {
            return new VectorBuilder(parentContext, new FinishCallback<Vector3>() {

                @Override
                public void handle(Vector3 value) {
                    MaterialBuilder.this.specular = value;
                }
                
            });            
        } else if ("specular_power".equals(name)) {
            return new DoubleValueBuilder(parentContext, new FinishCallback<Double>() {

                @Override
                public void handle(Double value) {
                    MaterialBuilder.this.specularPower = value;
                }
                
            });            
        } else if ("reflection_power".equals(name)) {
            return new DoubleValueBuilder(parentContext, new FinishCallback<Double>() {

                @Override
                public void handle(Double value) {
                    MaterialBuilder.this.reflectionPower = value;
                }
                
            });            
        } else if ("refractive_index".equals(name)) {
            return new DoubleValueBuilder(parentContext, new FinishCallback<Double>() {

                @Override
                public void handle(Double value) {
                    MaterialBuilder.this.refractiveIndex = value;
                }
                
            });            
        } else if ("transparency".equals(name)) {
            return new DoubleValueBuilder(parentContext, new FinishCallback<Double>() {

                @Override
                public void handle(Double value) {
                    MaterialBuilder.this.transparency = value;
                }
                
            });            
        }
        return null;
    }    

    @Override
    protected Material build() {
        return new MaterialImpl(diffuse, ambient, specular, specularPower, reflectionPower, refractiveIndex, transparency);
    }
    
}
