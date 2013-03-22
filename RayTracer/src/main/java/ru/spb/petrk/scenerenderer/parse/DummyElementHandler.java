package ru.spb.petrk.scenerenderer.parse;

/**
 *
 * @author PetrK
 */
public class DummyElementHandler implements ElementHandler<Object> {

    @Override
    public ElementHandler getHandler(String name) {
        return null;
    }

    @Override
    public void start() {
    }

    @Override
    public void body(String body) {
    }

    @Override
    public void finish() {
    }

}
