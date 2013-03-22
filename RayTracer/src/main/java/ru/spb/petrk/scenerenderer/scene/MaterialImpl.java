package ru.spb.petrk.scenerenderer.scene;

import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public class MaterialImpl implements Material {
    
    private final Vector3 diffuse;
    
    private final Vector3 ambient;
    
    private final Vector3 specular;
    
    private final double specularPower;
    
    private final double reflectionPower;
    
    private final double refractiveIndex;
    
    private final double transparency;

    
    public MaterialImpl(Vector3 diffuse, Vector3 ambient, Vector3 specular, double specularPower, double reflectionPower, double refractiveIndex, double transparency) {
        this.diffuse = diffuse;
        this.ambient = ambient;
        this.specular = specular;
        this.specularPower = specularPower;
        this.reflectionPower = reflectionPower;
        this.refractiveIndex = refractiveIndex;
        this.transparency = transparency;
    }    

    @Override
    public Vector3 getDiffuse() {
        return diffuse;
    }

    @Override
    public Vector3 getAmbient() {
        return ambient;
    }

    @Override
    public Vector3 getSpecular() {
        return specular;
    }

    @Override
    public double getSpecularPower() {
        return specularPower;
    }

    @Override
    public double getReflectionPower() {
        return reflectionPower;
    }

    @Override
    public double getRefractiveIndex() {
        return refractiveIndex;
    }

    @Override
    public double getTransparency() {
        return transparency;
    }

}
