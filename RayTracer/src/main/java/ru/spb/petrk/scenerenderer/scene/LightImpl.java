package ru.spb.petrk.scenerenderer.scene;

import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public class LightImpl implements Light {
    
    private Vector3 position;
    
    private Vector3 diffuse;
    
    private Vector3 ambient;
    
    private Vector3 specular;

    
    public LightImpl(Vector3 position, Vector3 diffuse, Vector3 ambient, Vector3 specular) {
        this.position = position;
        this.diffuse = diffuse;
        this.ambient = ambient;
        this.specular = specular;
    }

    @Override
    public Vector3 getPosition() {
        return position;
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

}
