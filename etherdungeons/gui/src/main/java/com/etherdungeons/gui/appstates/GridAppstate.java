package com.etherdungeons.gui.appstates;

import com.etherdungeons.basemod.data.gameflow.MapSize;
import com.etherdungeons.entitysystem.EntityDataReadonly;
import com.etherdungeons.gui.grid.meshing.ArrayChunk;
import com.etherdungeons.gui.grid.meshing.BlockSettings;
import com.etherdungeons.gui.grid.meshing.ChunkMeshingResult;
import com.etherdungeons.gui.grid.meshing.ChunkSize;
import com.etherdungeons.gui.grid.meshing.CubesMaterial;
import com.etherdungeons.gui.grid.meshing.GreedyMesher;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author Philipp
 */
public class GridAppstate extends AbstractAppState {

    private final EntityDataReadonly data;

    public GridAppstate(EntityDataReadonly data) {
        this.data = data;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        MapSize size = data.get(data.entity(MapSize.class), MapSize.class);
        
        ChunkSize chunkSize = new ChunkSize(size.getWidth() + 2, 4, size.getHeight() + 2);
        ArrayChunk chunk = new ArrayChunk(chunkSize);
        chunk.fill(BlockSettings.AIR);
        for (int x = 1; x < chunkSize.getX() - 1; x++) {
            for (int z = 1; z < chunkSize.getZ() - 1; z++) {
                chunk.setBlock(x, 1, z, BlockSettings.DIRT);
                chunk.setBlock(x, 2, z, BlockSettings.GRASS);
            }
        }

        Material material = new CubesMaterial(app.getAssetManager(), "Textures/terrain2.png");
        BlockSettings blockSettings = new BlockSettings();
        GreedyMesher mesher = new GreedyMesher(blockSettings, chunkSize);
        ChunkMeshingResult meshingResult = mesher.generateMesh(chunk);

        Node node = new Node("chunk");
        Geometry opaque = new Geometry("opaque");
        opaque.setMesh(meshingResult.getOpaque());
        opaque.setQueueBucket(RenderQueue.Bucket.Opaque);
        opaque.setMaterial(material);
        node.attachChild(opaque);

        Geometry transparent = new Geometry("transparent");
        transparent.setMesh(meshingResult.getTransparent());
        transparent.setQueueBucket(RenderQueue.Bucket.Transparent);
        transparent.setMaterial(material);
        node.attachChild(transparent);

        node.setLocalTranslation(-1, -3, -1);

        ((SimpleApplication) app).getRootNode().attachChild(node);
    }
}
