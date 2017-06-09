package com.etherdungeons.gui;

import com.etherdungeons.basemod.BaseMod;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.basemod.data.position.map.GameMap;
import com.etherdungeons.context.Context;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import com.etherdungeons.gui.grid.meshing.ArrayChunk;
import com.etherdungeons.gui.grid.meshing.BlockSettings;
import com.etherdungeons.gui.grid.meshing.ChunkMeshingResult;
import com.etherdungeons.gui.grid.meshing.ChunkSize;
import com.etherdungeons.gui.grid.meshing.CubesMaterial;
import com.etherdungeons.gui.grid.meshing.GreedyMesher;
import com.etherdungeons.modding.ModCombiner;
import com.etherdungeons.templates.TemplateService;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1280, 720);
        settings.setVSync(true);
        app.setSettings(settings);

        app.setShowSettings(false);
        app.start(); // start the game
    }

    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        
        Context gameContext = initGame();
        GameMap map = gameContext.getBean(GameMap.class);
        
        ChunkSize chunkSize = new ChunkSize(map.getWidth(), 4, map.getHeight());
        ArrayChunk chunk = new ArrayChunk(chunkSize);
        chunk.fill(BlockSettings.AIR);
        for (int x = 1; x < chunkSize.getX() - 1; x++) {
            for (int z = 1; z < chunkSize.getZ() - 1; z++) {
                chunk.setBlock(x, 1, z, BlockSettings.DIRT);
                chunk.setBlock(x, 2, z, BlockSettings.GRASS);
            }
        }

        Material material = new CubesMaterial(assetManager, "Textures/terrain2.png");
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

        rootNode.attachChild(node);
        cam.setLocation(new Vector3f(-3, 12, -3));
        cam.lookAt(new Vector3f(map.getWidth() / 2, 2, map.getHeight() / 2), Vector3f.UNIT_Y);
    }

    private Context initGame() throws RuntimeException {
        try {
            return initGameThrows();
        } catch (ReflectiveOperationException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Context initGameThrows() throws ReflectiveOperationException, IOException {
        Context gameContext = new ModCombiner(new BaseMod()).build();
        TemplateService templates = gameContext.getBean(TemplateService.class);
        EntityData data = gameContext.getBean(EntityData.class);
        
        EntityId amara = data.createEntity();
        templates.get("amara").applyTemplate(data, amara);
        
        EntityId gobball_1 = data.createEntity();
        templates.get("gobball").applyTemplate(data, gobball_1);
        
        EntityId gobball_2 = data.createEntity();
        templates.get("gobball").applyTemplate(data, gobball_2);
        
        EntityId gobball_3 = data.createEntity();
        templates.get("gobball").applyTemplate(data, gobball_3);
        
        templates.get("test_map").applyTemplate(data, amara, gobball_1, gobball_2, gobball_3);
        
        update(gameContext);
        return gameContext;
    }
    
    private static void update(Context context) {
        List<Runnable> runnables = context.getBeans(Runnable.class);
        EntityData data = context.getBean(EntityData.class);

        do {
            for (Runnable runnable : runnables) {
                runnable.run();
            }
        } while (data.streamEntities(TriggerRequest.class).findAny().isPresent());
    }

}
