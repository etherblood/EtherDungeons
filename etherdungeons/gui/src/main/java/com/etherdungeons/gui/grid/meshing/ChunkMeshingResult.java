package com.etherdungeons.gui.grid.meshing;

import com.jme3.scene.Mesh;

/**
 *
 * @author Philipp
 */
public class ChunkMeshingResult {
    private final Mesh opaque, transparent;

    public ChunkMeshingResult(Mesh opaque, Mesh transparent) {
        this.opaque = opaque;
        this.transparent = transparent;
    }

    public Mesh getOpaque() {
        return opaque;
    }

    public Mesh getTransparent() {
        return transparent;
    }
}
