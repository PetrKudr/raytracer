package ru.spb.petrk.scenerenderer.parse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Stack;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.spb.petrk.scenerenderer.parse.builders.AbstractElementBuilder;
import ru.spb.petrk.scenerenderer.parse.builders.WorldBuilder;

/**
 *
 * @author PetrK
 */
public class WorldParser {

    public World parse(InputStream stream) throws ParserConfigurationException, SAXException, IOException {
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        WorldHandler handler = new WorldHandler();        
        parser.parse(stream, handler);
        return handler.getWorld();
    }
    
    
    private static class WorldHandler extends DefaultHandler {
        
        private final Stack<ElementHandler> handlers = new Stack<ElementHandler>();
        
        private World world;
        

        public WorldHandler() {
            ElementHandler worldHandler = new WorldBuilder(new ElementContextImpl(), new AbstractElementBuilder.FinishCallback<World>() {

                @Override
                public void handle(World value) {
                    WorldHandler.this.world = value;
                }
                
            });
            
            handlers.push(worldHandler);
            
            worldHandler.start();
        }

        public World getWorld() {
            return world;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            ElementHandler handler = handlers.peek();
            
            ElementHandler nextHandler = handler.getHandler(qName);
            if (nextHandler == null) {
                nextHandler = new DummyElementHandler();
            }
            
            handlers.push(nextHandler);
            nextHandler.start();

            for (int i = 0; i < attributes.getLength(); i++) {
                String attrName = attributes.getQName(i);
                ElementHandler paramHandler = nextHandler.getHandler(attrName);
                if (paramHandler != null) {
                    paramHandler.start();
                    paramHandler.body(attributes.getValue(i));
                    paramHandler.finish();
                }
            }            
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            ElementHandler handler = handlers.pop();
            handler.finish();
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            ElementHandler handler = handlers.peek();
            handler.body(new String(ch, start, length));
        }
        
    }
    
}
