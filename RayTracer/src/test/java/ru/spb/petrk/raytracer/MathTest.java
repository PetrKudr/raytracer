package ru.spb.petrk.raytracer;

import junit.framework.TestCase;
import ru.spb.petrk.scenerenderer.util.MathUtils;
import ru.spb.petrk.scenerenderer.util.Matrix3;
import ru.spb.petrk.scenerenderer.util.Solvers;
import ru.spb.petrk.scenerenderer.util.Vector3;

/**
 *
 * @author PetrK
 */
public class MathTest extends TestCase {
    
    public void testRotationCase1() {
        Vector3 from = new Vector3(1, 0, 0);
        Vector3 to = new Vector3(1, 1, 0).normalize();
        testRotation(from, to);
    }
    
    public void testRotationCase2() {
        Vector3 from = new Vector3(1, 1, 1).normalize();
        Vector3 to = new Vector3(1, -1, 0.3).normalize();
        testRotation(from, to);
    }    
    
    public void testQuarticCase1() {
        double a = 1;
        double b = -7.999990543114836;
        double c = 23.599962172481703;
        double d = -30.39996406383638;
        double e = 14.414399999999999;
        
        double roots[] = Solvers.solveQuartic(a, b, c, d, e);
        
        assertNotNull(roots);
    }
    
    
    private static void testRotation(Vector3 from, Vector3 to) {
        Matrix3 toMatrix = Matrix3.rotate(from, to);
        Matrix3 fromMatrix = Matrix3.rotate(to, from);
        
        Vector3 translatedVector = toMatrix.multiply(to);
        assertVectorsEqual(from, translatedVector);
        
        Vector3 revertedVector = fromMatrix.multiply(translatedVector);        
        assertVectorsEqual(to, revertedVector);        
    }
    
    private static void assertVectorsEqual(Vector3 vec1, Vector3 vec2) {
        assertEquals(true, MathUtils.equals(vec1.getX(), vec2.getX()));
        assertEquals(true, MathUtils.equals(vec1.getY(), vec2.getY()));
        assertEquals(true, MathUtils.equals(vec1.getZ(), vec2.getZ()));        
    }
    
}
