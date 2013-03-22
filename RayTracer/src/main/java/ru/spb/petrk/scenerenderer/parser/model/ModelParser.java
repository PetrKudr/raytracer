package ru.spb.petrk.scenerenderer.parser.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ru.spb.petrk.scenerenderer.scene.Material;
import ru.spb.petrk.scenerenderer.scene.primitives.Triangle;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public class ModelParser {
    
    private final Material material;

    
    public ModelParser(Material material) {
        this.material = material;
    }           
    
    public List<Triangle> parse(String path) {
        List<Triangle> result = null;        
        BufferedReader reader = null;
        
        try {        
            if (path.startsWith("classpath:")) {
                path = path.substring("classpath:".length());
                reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(path)));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            }

            result = read(reader);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        return result != null ? result : Collections.<Triangle>emptyList();
    }
    
    private List<Triangle> read(BufferedReader reader) throws IOException {
        List<Vector3> vertices = new ArrayList<Vector3>();
        List<Vector3> normals = new ArrayList<Vector3>();
        
        List<Triangle> result = new ArrayList<Triangle>();        
        Vector3 triangleVertices[] = new Vector3[3];
        Vector3 triangleNormals[] = new Vector3[3];
        
        String line;
        
        while ((line = reader.readLine()) != null) {
            String elements[] = line.split(" ");
            
            if (elements.length == 4) {
                if (elements[0].equals("v")) {
                    vertices.add(new Vector3(Double.parseDouble(elements[1]), Double.parseDouble(elements[2]), Double.parseDouble(elements[3])));
                } else if (elements[0].equals("vn")) {
                    normals.add(new Vector3(Double.parseDouble(elements[1]), Double.parseDouble(elements[2]), Double.parseDouble(elements[3])));
                } else if (elements[0].equals("f")) {
                    for (int i = 0; i < 3; i++) {
                        String values[] = elements[i + 1].split("/");
                        triangleVertices[i] = vertices.get(Integer.parseInt(values[0]) - 1);
                        triangleNormals[i] = normals.get(Integer.parseInt(values[2]) - 1);
                    }
                    result.add(new Triangle(
                            material, 
                            triangleVertices[0],
                            triangleVertices[1],
                            triangleVertices[2],
                            triangleNormals[0],
                            triangleNormals[1],
                            triangleNormals[2]                            
                    ));
                }
            }
        }
        
        return result;
    }

}
