package ru.spb.petrk.scenerenderer.parser.builders;

import java.util.List;
import ru.spb.petrk.scenerenderer.parser.ElementContext;
import ru.spb.petrk.scenerenderer.parser.ElementHandler;
import ru.spb.petrk.scenerenderer.scene.SceneObject;
import ru.spb.petrk.scenerenderer.scene.objects.csg.CSGObjectNode;
import ru.spb.petrk.scenerenderer.scene.objects.csg.CSGOperation;
import ru.spb.petrk.scenerenderer.scene.objects.csg.CSGOperationNode;
import ru.spb.petrk.scenerenderer.scene.objects.csg.CSGTreeNode;

/**
 *
 * @author PetrK
 */
class CSGOperationNodeBuilder extends AbstractElementBuilder<CSGOperationNode> {
    
    private CSGOperation operation;
    
    private CSGTreeNode left;
    
    private CSGTreeNode right;

    
    public CSGOperationNodeBuilder(ElementContext parentContext, FinishCallback<CSGOperationNode> callback) {
        super(parentContext, callback);
    }

    @Override
    protected ElementHandler createHandler(String name) {
        if ("name".equals(name)) {
            return new StringValueBuilder(parentContext, new FinishCallback<String>() {

                @Override
                public void handle(String value) {
                    CSGOperationNodeBuilder.this.operation = CSGOperation.valueOf(value);
                }
                
            });
        } else if ("csg_operation".equals(name)) {
            return new CSGOperationNodeBuilder(parentContext, new FinishCallback<CSGOperationNode>() {

                @Override
                public void handle(CSGOperationNode value) {
                    if (CSGOperationNodeBuilder.this.left == null) {
                        CSGOperationNodeBuilder.this.left = value;
                    } else if (CSGOperationNodeBuilder.this.right == null) {
                        CSGOperationNodeBuilder.this.right = value;
                    } else {
                        throw new IllegalStateException("CSG operation has more than two arguments!");
                    }
                }
                
            });
        } else if ("csg_object".equals(name)) {
            return new SceneObjectsBuilder(parentContext, new FinishCallback<List<SceneObject>>() {

                @Override
                public void handle(List<SceneObject> value) {
                    if (value.isEmpty()) {
                        throw new IllegalArgumentException("Empty CSG object!");
                    }
                    if (value.size() > 1) {
                        throw new IllegalArgumentException("Multiple primitives/models are not allowed under csg_object!");
                    }
                    
                    if (CSGOperationNodeBuilder.this.left == null) {
                        CSGOperationNodeBuilder.this.left = new CSGObjectNode(value.get(0));
                    } else if (CSGOperationNodeBuilder.this.right == null) {
                        CSGOperationNodeBuilder.this.right = new CSGObjectNode(value.get(0));
                    } else {
                        throw new IllegalStateException("CSG operation has more than two arguments!");
                    }
                }
                
            });
        }
        return null;
    }

    @Override
    protected CSGOperationNode build() {
        if (operation == null || left == null || right == null) {
            throw new IllegalStateException("CSG operation is not filled in properly!");
        }
        return new CSGOperationNode(left, right, operation);
    }

}
