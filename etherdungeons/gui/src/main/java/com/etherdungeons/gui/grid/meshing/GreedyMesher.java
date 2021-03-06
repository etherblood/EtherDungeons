package com.etherdungeons.gui.grid.meshing;

import com.etherdungeons.gui.grid.meshing.helpers.AmbientOcclusionHelper;
import com.etherdungeons.gui.grid.meshing.helpers.RectangleMeshBuilder;
import com.jme3.math.Vector3f;
import java.util.Arrays;

/**
 *
 * @author Philipp
 */
public final class GreedyMesher {

    private final BlockSettings blockSettings;
    private final int invalidTile;
    private final int chunkWidth, chunkHeight, chunkDepth;
    private final RectangleMeshBuilder opaqueBuilder = new RectangleMeshBuilder(), transparentBuilder = new RectangleMeshBuilder();
    private final int[] position = new int[3];
    private final int[] indexHelper = new int[3];
    private final int[] chunkSize = new int[3];
    private final int[] direction = new int[3];
    private final int[] du = new int[3];
    private final int[] dv = new int[3];
    private final int[] mask;
    private final int[] neighborMask;
    private final float[] ambientLightLevels = new float[]{0.1f, 0.4f, 0.7f, 1};
    private final int[] ambientShadowMasks = new int[9];
    private final Direction[] axisDirection = new Direction[3];
    private RectangleMeshBuilder builder;
    private ArrayChunk chunk;
    private int rectangleWidth, rectangleHeight, axis0, axis1, axis2, maskIndex, size0, size1, size2, pos0, pos1, pos2, index0, index1, index2, neighborPos0;
    private byte blockA, blockB;
    private boolean skipA, skipB;
    private int baseBlock;
    private boolean allEqual, allOpaque;
    private Vector3f normal, inverseNormal;
    private Direction neighborDirection, inverseNeighborDirection;
    private ArrayChunk neighborChunk;
    private final int[] cornerOrders = new int[]{0, 1, 2, 3};
    private final int[] neighborCornerOrders = new int[]{0, 2, 1, 3};
    private final static int[] FLIPPED_ORDER = new int[]{1, 3, 0, 2};
    private final static int[] FLIPPED_NEIGHBOR_ORDER = new int[]{2, 3, 0, 1};

    public GreedyMesher(BlockSettings blockSettings, ChunkSize size) {
        this.blockSettings = blockSettings;
        invalidTile = blockSettings.invalidTile();
        
        chunkWidth = size.getX();
        chunkHeight = size.getY();
        chunkDepth = size.getZ();

        indexHelper[0] = 1;
        indexHelper[1] = chunkWidth * chunkDepth;
        indexHelper[2] = chunkWidth;

        chunkSize[0] = chunkWidth;
        chunkSize[1] = chunkHeight;
        chunkSize[2] = chunkDepth;
        mask = new int[chunkWidth * chunkHeight * chunkDepth / Math.min(chunkWidth, Math.min(chunkHeight, chunkDepth))];
        neighborMask = new int[mask.length];

        du[0] = 0;
        du[1] = 0;
        du[2] = 0;
        dv[0] = 0;
        dv[1] = 0;
        dv[2] = 0;

        axisDirection[0] = Direction.EAST;
        axisDirection[1] = Direction.UP;
        axisDirection[2] = Direction.NORTH;

        Arrays.fill(mask, invalidTile);
        Arrays.fill(neighborMask, invalidTile);

        ambientShadowMasks[0] = AmbientOcclusionHelper.singleOcclusionMask(AmbientOcclusionHelper.upperLeft, AmbientOcclusionHelper.upperLeft);
        ambientShadowMasks[1] = AmbientOcclusionHelper.singleOcclusionMask(AmbientOcclusionHelper.upperLeft, AmbientOcclusionHelper.lowerLeft)
                | AmbientOcclusionHelper.singleOcclusionMask(AmbientOcclusionHelper.lowerLeft, AmbientOcclusionHelper.upperLeft);
        ambientShadowMasks[2] = AmbientOcclusionHelper.singleOcclusionMask(AmbientOcclusionHelper.lowerLeft, AmbientOcclusionHelper.lowerLeft);
        ambientShadowMasks[3] = AmbientOcclusionHelper.singleOcclusionMask(AmbientOcclusionHelper.upperLeft, AmbientOcclusionHelper.upperRight)
                | AmbientOcclusionHelper.singleOcclusionMask(AmbientOcclusionHelper.upperRight, AmbientOcclusionHelper.upperLeft);
        ambientShadowMasks[4] = AmbientOcclusionHelper.singleOcclusionMask(AmbientOcclusionHelper.lowerRight, AmbientOcclusionHelper.upperLeft)
                | AmbientOcclusionHelper.singleOcclusionMask(AmbientOcclusionHelper.lowerLeft, AmbientOcclusionHelper.upperRight)
                | AmbientOcclusionHelper.singleOcclusionMask(AmbientOcclusionHelper.upperLeft, AmbientOcclusionHelper.lowerRight)
                | AmbientOcclusionHelper.singleOcclusionMask(AmbientOcclusionHelper.upperRight, AmbientOcclusionHelper.lowerLeft);
        ambientShadowMasks[5] = AmbientOcclusionHelper.singleOcclusionMask(AmbientOcclusionHelper.lowerLeft, AmbientOcclusionHelper.lowerRight)
                | AmbientOcclusionHelper.singleOcclusionMask(AmbientOcclusionHelper.lowerRight, AmbientOcclusionHelper.lowerLeft);
        ambientShadowMasks[6] = AmbientOcclusionHelper.singleOcclusionMask(AmbientOcclusionHelper.upperRight, AmbientOcclusionHelper.upperRight);
        ambientShadowMasks[7] = AmbientOcclusionHelper.singleOcclusionMask(AmbientOcclusionHelper.upperRight, AmbientOcclusionHelper.lowerRight)
                | AmbientOcclusionHelper.singleOcclusionMask(AmbientOcclusionHelper.lowerRight, AmbientOcclusionHelper.upperRight);
        ambientShadowMasks[8] = AmbientOcclusionHelper.singleOcclusionMask(AmbientOcclusionHelper.lowerRight, AmbientOcclusionHelper.lowerRight);
    }

    public ChunkMeshingResult generateMesh(ArrayChunk chunk) {
        this.chunk = chunk;
        greedy();
        ChunkMeshingResult result = new ChunkMeshingResult(opaqueBuilder.build(), transparentBuilder.build());
        opaqueBuilder.clear();
        transparentBuilder.clear();
        return result;
    }

    private void initAxis() {
        axis1 = (axis0 + 1) % 3;
        axis2 = (axis0 + 2) % 3;

        size0 = chunkSize[axis0];
        size1 = chunkSize[axis1];
        size2 = chunkSize[axis2];

        index0 = indexHelper[axis0];
        index1 = indexHelper[axis1];
        index2 = indexHelper[axis2];

        direction[0] = 0;
        direction[1] = 0;
        direction[2] = 0;
        direction[axis0] = 1;

        neighborDirection = axisDirection[axis0];
        inverseNeighborDirection = neighborDirection.inverse();

        normal = new Vector3f(direction[0], direction[1], direction[2]);
        inverseNormal = normal.negate();

        neighborChunk = chunk;

        skipA = true;
        skipB = true;
        if (axis0 == 0) {
            allEqual = true;
            allOpaque = true;
            pos0 = 0;
        } else if (allEqual || allOpaque) {
            pos0 = size0 - 1;
        } else {
            pos0 = 0;
        }
    }

    private void greedy() {
        baseBlock = chunk.getBlockFast(0);
        for (axis0 = 0; axis0 < 3; axis0++) {
            initAxis();
            while (pos0 < size0) {
//                neighborPos0 = (pos0 + 1) % size0;//use this instead?
//                if (neighborPos0 == 0) {
                if (pos0 + 1 == size0) {
                    neighborPos0 = 0;
                    break;
                } else {
                    neighborPos0 = pos0 + 1;
                }

                maskIndex = 0;
                for (pos2 = 0; pos2 < size2; pos2++) {
                    for (pos1 = 0; pos1 < size1; pos1++) {
                        int positionIndex = pos0 * index0 + pos1 * index1 + pos2 * index2;//TODO: split calculation between loop levels?
                        blockA = chunk.getBlockFast(positionIndex);
                        int neighborPositionIndex = neighborPos0 * index0 + pos1 * index1 + pos2 * index2;//TODO: split calculation between loop levels?
                        blockB = neighborChunk.getBlockFast(neighborPositionIndex);

                        if (allEqual && blockA != baseBlock) {
                            allEqual = false;
                        }
                        if(allOpaque && !blockSettings.isBlockOpaque(blockA)) {
                            allOpaque = false;
                        }
                        if (blockA != blockB) {
                            if (blockSettings.isBlockVisible(blockA) && blockSettings.isBlockTransparent(blockB)) {
                                skipA = false;
                                mask[maskIndex] = blockSettings.tileFromBlock(blockA, neighborDirection) | (getAmbientOcclusionValue(neighborChunk, neighborPos0) << 24);
                            }

                            if (blockSettings.isBlockVisible(blockB) && blockSettings.isBlockTransparent(blockA)) {
                                skipB = false;
                                neighborMask[maskIndex] = blockSettings.tileFromBlock(blockB, inverseNeighborDirection) | (getAmbientOcclusionValue(chunk, pos0) << 24);
                            }
                        }
                        maskIndex++;
                    }
                }

                pos0++;

                if (!skipA) {
                    createRectanglesFromMask(mask, cornerOrders, FLIPPED_ORDER, normal);
                }
                if (!skipB) {
                    createRectanglesFromMask(neighborMask, neighborCornerOrders, FLIPPED_NEIGHBOR_ORDER, inverseNormal);
                }
            }
        }
    }

    private int getAmbientOcclusionValue(ArrayChunk c, int pos_0) {
        int result = 0;
        for (int i = -1; i <= 1; i++) {
            int pos_1 = pos1 + i;
            ArrayChunk n = c;
            if (pos_1 == -1) {
                continue;
            } else if (pos_1 == size1) {
                continue;
            }
            for (int j = -1; j <= 1; j++) {
                int pos_2 = pos2 + j;
                ArrayChunk n2 = n;
                if (pos_2 == -1) {
                    continue;
                } else if (pos_2 == size2) {
                    continue;
                }

                if (n2.getBlockFast(pos_0 * index0 + pos_1 * index1 + pos_2 * index2) != 0) {
                    result |= ambientShadowMasks[3 * i + j + 4];
                }
            }
        }
        return AmbientOcclusionHelper.bakeOcclusionMasks(result);
    }

    private void createRectanglesFromMask(int[] currentMask, int[] cornersOrder, int[] flippedsOrder, Vector3f currentNormal) {
        position[axis0] = pos0;
        maskIndex = 0;
        for (pos2 = 0; pos2 < size2; pos2++) {
            position[axis2] = pos2;
            for (pos1 = 0; pos1 < size1;) {
                position[axis1] = pos1;
                if (currentMask[maskIndex] != invalidTile) {
                    calcRectangleWidthAndHeight(currentMask);

                    position[axis1] = pos1;
                    du[axis1] = rectangleWidth;
                    dv[axis2] = rectangleHeight;
                    int tile = currentMask[maskIndex];
                    int lightInfo = tile >>> 24;
                    tile &= 0xffffff;

                    builder = blockSettings.isTileOpaque(tile) ? opaqueBuilder : transparentBuilder;
                    builder.prepareFaceIndices(AmbientOcclusionHelper.isLowerLeftDiagonalDarker(lightInfo) ? flippedsOrder : cornersOrder);
                    builder.prepareRectNormals(currentNormal);
                    addTextureCoords(tile, rectangleWidth, rectangleHeight);

                    builder.addCorner(position[0], position[1], position[2], ambientLightLevels[lightInfo & 0x3]);
                    lightInfo >>>= 2;
                    builder.addCorner(position[0] + du[0], position[1] + du[1], position[2] + du[2], ambientLightLevels[lightInfo & 0x3]);
                    lightInfo >>>= 2;
                    builder.addCorner(position[0] + dv[0], position[1] + dv[1], position[2] + dv[2], ambientLightLevels[lightInfo & 0x3]);
                    lightInfo >>>= 2;
                    builder.addCorner(position[0] + du[0] + dv[0], position[1] + du[1] + dv[1], position[2] + du[2] + dv[2], ambientLightLevels[lightInfo & 0x3]);

                    du[axis1] = 0;
                    dv[axis2] = 0;

                    for (int l = 0; l < rectangleHeight; ++l) {
                        int tmp = maskIndex + l * size1;
                        Arrays.fill(currentMask, tmp, tmp + rectangleWidth, invalidTile);
                    }
                    pos1 += rectangleWidth;
                    maskIndex += rectangleWidth;
                } else {
                    pos1++;
                    maskIndex++;
                }
            }
        }
    }

    private void addTextureCoords(int tile, int width, int height) {
        switch (axis0) {
            case 0:
                builder.addTextureCoordinate(tile, height, width);
                builder.addTextureCoordinate(tile, height, 0);
                builder.addTextureCoordinate(tile, 0, width);
                builder.addTextureCoordinate(tile, 0, 0);
                break;
            case 1:
                builder.addTextureCoordinate(tile, 0, 0);
                builder.addTextureCoordinate(tile, width, 0);
                builder.addTextureCoordinate(tile, 0, height);
                builder.addTextureCoordinate(tile, width, height);
                break;
            case 2:
                builder.addTextureCoordinate(tile, 0, height);
                builder.addTextureCoordinate(tile, width, height);
                builder.addTextureCoordinate(tile, 0, 0);
                builder.addTextureCoordinate(tile, width, 0);
                break;
            default:
                throw new AssertionError(axis0);
        }
    }

    private void calcRectangleWidthAndHeight(int[] mask) {
        for (rectangleWidth = 1; pos1 + rectangleWidth < size1 && mask[maskIndex + rectangleWidth] == mask[maskIndex]; rectangleWidth++) {
        }

        for (rectangleHeight = 1; pos2 + rectangleHeight < size2; rectangleHeight++) {
            for (int k = 0; k < rectangleWidth; k++) {
                if (mask[maskIndex + k + rectangleHeight * size1] != mask[maskIndex]) {
                    return;
                }
            }
        }
    }
}