package ru.spb.petrk.scenerenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.NumberFormat;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import ru.spb.petrk.scenerenderer.parser.World;
import ru.spb.petrk.scenerenderer.parser.WorldParser;
import ru.spb.petrk.scenerenderer.scene.Camera;
import ru.spb.petrk.scenerenderer.scene.Snapshot;
import ru.spb.petrk.scenerenderer.ui.ImagePanel;
import ru.spb.petrk.scenerenderer.util.Listener;



public class SceneRenderer {      

    public static void main(String[] args) throws Exception {
        
//        Material sphereMaterial = new MaterialImpl(
//                new Vector3(0.98, 0.48, 0.4), 
//                new Vector3(0.4, 0.2, 0.16), 
//                new Vector3(0.7, 0.7, 0.7), 
//                128, 
//                0,
//                1,
//                -1,
//                0
//        );
//        
//        Material cylinderMaterial = new MaterialImpl(
//                new Vector3(0.4, 0.6, 0.96), 
//                Vector3.ZERO_VECTOR, 
//                new Vector3(0.5, 0.3, 0.1), 
//                64, 
//                0.4,
//                1,
//                -1,
//                0
//        ); 
//        
//        Material coneMaterial = new MaterialImpl(
//                new Vector3(0.4, 0.96, 0.6), 
//                Vector3.ZERO_VECTOR, 
//                new Vector3(0.5, 0.3, 0.1), 
//                64, 
//                0.4,
//                1,
//                -1,
//                0
//        );
//        
//        Material triangleMaterial = new MaterialImpl(
//                new Vector3(0.43, 0.67, 0.73),
//                Vector3.ZERO_VECTOR, 
//                new Vector3(0.5, 0.3, 0.1), 
//                64, 
//                0.4,
//                1,
//                -1
//        ); 
//        
//        Material planeMaterial = new MaterialImpl(
//                new Vector3(0.7, 0.7, 0.7), 
//                Vector3.ZERO_VECTOR, 
//                new Vector3(0.2, 0.2, 0.2), 
//                64,
//                0.2,
//                1,
//                -1
//        );            
//        
//        Material trasnparentMaterial = new MaterialImpl(
//                new Vector3(0, 0.1, 0.2), 
//                Vector3.ZERO_VECTOR, 
//                Vector3.ZERO_VECTOR, 
//                0, 
//                0,
//                1.04,
//                0.7
//        );        

//        CSGObjectNode first = new CSGObjectNode(new SimpleSceneObject(new Sphere(sphereMaterial, new Vector3(0.5, 0, 0), 1)));
//        CSGObjectNode second = new CSGObjectNode(new SimpleSceneObject(new Sphere(cylinderMaterial, new Vector3(-0.5, 0, 0), 1)));
//        CSGObjectNode third = new CSGObjectNode(new SimpleSceneObject(new Sphere(coneMaterial, new Vector3(0, 1, 1), 1)));
////        CSGObjectNode fourth = new CSGObjectNode(new SimpleSceneObject(new Sphere(sphereMaterial, new Vector3(0, 1, 1), 1)));
//        
//        CSGOperationNode firstOperation = new CSGOperationNode(first, second, CSGOperation.intersection);
//        CSGOperationNode secondOperation = new CSGOperationNode(firstOperation, third, CSGOperation.subtraction);
////        CSGOperationNode thirdSubtraction = new CSGOperationNode(secondSubtraction, fourth, CSGOperation.subtraction);
//        
//        Scene scene = new SceneImpl(
//                1,
//                0,
//                Arrays.<SceneObject>asList(new CSGSceneObject(secondOperation)), 
//                Arrays.<Light>asList(
//                    new LightImpl(new Vector3(-1, 4, 0), Vector3.ZERO_VECTOR, Vector3.ZERO_VECTOR, Vector3.ZERO_VECTOR),
//                    new LightImpl(new Vector3(1, 4, 0), Vector3.ZERO_VECTOR, Vector3.ZERO_VECTOR, Vector3.ZERO_VECTOR)
//                )
//        );        
//        
//        Camera camera = new CameraImpl(
//                new RayTracerImpl(Vector3.ZERO_VECTOR, new FresnelRefractionStrategy()), 
//                new Vector3(0, -1, 0), 
//                new Vector3(0, 0, 1), 
//                new Vector3(0, 4, 0),
//                1,
//                Math.PI / 4,
//                Math.PI / 4
//        );
//        
//        final Snapshot snapshot = camera.makeSnapshot(scene, 512, 512);
//        final BufferedImage image = new BufferedImage(snapshot.getWidth(), snapshot.getHeight(), BufferedImage.TYPE_INT_RGB);
//        image.setRGB(0, 0, snapshot.getWidth(), snapshot.getHeight(), snapshot.getPicture(), 0, snapshot.getScanSize());      
//        
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                showImage(image);
//            }
//        });

        try {
            UserParameters parameters = parseCommandLine(args);

            if (parameters != null) {
                InputStream stream = loadScene(parameters.getScene());            

                WorldParser parser = new WorldParser();
                World world = parser.parse(stream);

                stream.close();
                
                Camera camera = world.getCamera();
                camera.addListener(new ProgressListener(parameters.getXResolution() * parameters.getYResolution()));

                Snapshot snapshot = camera.makeSnapshot(world.getScene(), parameters.getXResolution(), parameters.getYResolution());

                final BufferedImage image = new BufferedImage(snapshot.getWidth(), snapshot.getHeight(), BufferedImage.TYPE_INT_RGB);
                image.setRGB(0, 0, snapshot.getWidth(), snapshot.getHeight(), snapshot.getPicture(), 0, snapshot.getScanSize());

                ImageIO.write(image, "png", new File(parameters.getOutput()));

//                SwingUtilities.invokeLater(new Runnable() {
//                    public void run() {
//                        showImage(image);
//                    }
//                });
            }
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }    
    
    private static UserParameters parseCommandLine(String args[]) throws ParseException {        
        Options options = createOptions();        
        CommandLineParser parser = new PosixParser(); 
        CommandLine line = parser.parse(options, args);
                
        String scene = null;
        int xResolution = 512;
        int yResolution = 512;
        String output = null;
        
        if (args.length == 0 || line.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar scenerenderer.jar", options);
            return null;            
        } else {
            if (!line.hasOption("scene")) {
                throw new ParseException("Parameter 'scene' is mandatory");
            } else {
                scene = line.getOptionValue("scene");
            }

            if (!line.hasOption("output")) {
                throw new ParseException("Parameter 'output' is mandatory");
            } else {
                output = line.getOptionValue("output");
            }

            try {
                if (line.hasOption("resolution_x")) {
                    xResolution = Integer.parseInt(line.getOptionValue("resolution_x"));
                }

                if (line.hasOption("resolution_y")) {
                    yResolution = Integer.parseInt(line.getOptionValue("resolution_y"));
                }
            } catch (NumberFormatException ex) {
                throw new ParseException(ex.getMessage());
            }
        }
        
        if (!output.endsWith(".png")) {
            output = output + ".png";
        }
        
        return new UserParameters(scene, xResolution, yResolution, output);
    }
    
    private static InputStream loadScene(String path) throws FileNotFoundException {
        if (path.equals("default_1")) {
            return SceneRenderer.class.getResourceAsStream("default_scene.xml");
        } else if (path.equals("default_2")) {
            return SceneRenderer.class.getResourceAsStream("model_scene.xml");
        } else if (path.equals("default_3")) {
            return SceneRenderer.class.getResourceAsStream("default_csg_scene.xml");
        } else if (path.equals("default_4")) {
            return SceneRenderer.class.getResourceAsStream("bug.xml");
        }
        return new FileInputStream(path);
    }
    
    private static Options createOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "prints this message");
        options.addOption("scene", true, "specifies path to xml file with scene. Also there are two predefined scenes could be used via values 'default_1' and 'default_2'");
        options.addOption("resolution_x", true, "X resolution");
        options.addOption("resolution_y", true, "Y resolution");
        options.addOption("output", true, "output file without extension");
        return options;
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void showImage(BufferedImage image) {        
        int width, height;
        
        //Create and set up the window.
        JFrame frame = new JFrame("Scene renderer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ImagePanel panel = new ImagePanel(image);
        frame.getContentPane().add(panel);
        
        width = image.getWidth();
        height = image.getHeight();                    

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        
        frame.setSize(width, height);
    }    
    
    
    private static class UserParameters {
        
        private String scene;
        
        private int xResolution;
        
        private int yResolution;
        
        private String output;

        public UserParameters(String scene, int xResolution, int yResolution, String output) {
            this.scene = scene;
            this.xResolution = xResolution;
            this.yResolution = yResolution;
            this.output = output;
        }

        public String getScene() {
            return scene;
        }

        public int getXResolution() {
            return xResolution;
        }

        public int getYResolution() {
            return yResolution;
        }

        public String getOutput() {
            return output;
        }       
    }
    
    private static class ProgressListener implements Listener<Integer> {
        
        private final int totalProgress;
        
        private final NumberFormat format = NumberFormat.getPercentInstance();
        
        private String lastPercent;

        public ProgressListener(int totalProgress) {
            this.totalProgress = totalProgress;
        }

        @Override
        public void process(Integer data) {
            String percent = format.format(((double)data) / totalProgress);
            if (!percent.equals(lastPercent)) {
                System.out.print("Progress: " + percent + "\r");
                lastPercent = percent;
            }
        }
        
    }   
}
