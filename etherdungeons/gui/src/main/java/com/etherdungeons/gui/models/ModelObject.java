package com.etherdungeons.gui.models;

import java.util.ArrayList;
import com.jme3.animation.*;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.function.Consumer;

/**
 *
 * @author Carl
 */
public class ModelObject extends Node implements AnimEventListener {

    private final Consumer<Runnable> taskConsumer;
    private final ModelSkin skin;
    private Spatial modelSpatial;
    private final ArrayList<AnimChannel> animationChannels = new ArrayList<>();

    public ModelObject(MaterialFactory materialFactory, Consumer<Runnable> taskConsumer, String skinResourcePath) {
        this.taskConsumer = taskConsumer;
        skin = new ModelSkin(materialFactory, skinResourcePath);
        loadSkin();
    }

    private void loadSkin() {
        modelSpatial = skin.loadSpatial();
        for (ModelModifier modelModifier : skin.getModelModifiers()) {
            modelModifier.modify(this);
        }
        attachChild(modelSpatial);
        registerModel(modelSpatial);
    }

    public void registerModel(Spatial spatial) {
        AnimControl animationControl = spatial.getControl(AnimControl.class);
        if (animationControl != null) {
            animationControl.addListener(this);
            AnimChannel animationChannel = copyAnimation(spatial);
            animationChannels.add(animationChannel);
        }
//        SkeletonControl skeletonControl = spatial.getControl(SkeletonControl.class);
//        if(skeletonControl != null){
//            skeletonControl.setHardwareSkinningPreferred(Settings.getBoolean("hardware_skinning"));
//        }
    }

    public AnimChannel copyAnimation(Spatial spatial) {
        AnimControl animationControl = spatial.getControl(AnimControl.class);
        if (animationControl != null) {
            AnimChannel animationChannel = animationControl.createChannel();
            if (animationChannels.size() > 0) {
                copyAnimation(animationChannels.get(0), animationChannel);
            }
            return animationChannel;
        }
        return null;
    }

    private static void copyAnimation(AnimChannel sourceAnimationChannel, AnimChannel targetAnimationChannel) {
        if (sourceAnimationChannel.getAnimationName() != null) {
            targetAnimationChannel.setAnim(sourceAnimationChannel.getAnimationName());
            targetAnimationChannel.setSpeed(sourceAnimationChannel.getSpeed());
            targetAnimationChannel.setTime(sourceAnimationChannel.getTime());
            targetAnimationChannel.setLoopMode(sourceAnimationChannel.getLoopMode());
        }
    }

    public void unregisterModel(Spatial spatial) {
        AnimControl animationControl = spatial.getControl(AnimControl.class);
        if (animationControl != null) {
            for (int i = 0; i < animationControl.getNumChannels(); i++) {
                animationChannels.remove(animationControl.getChannel(i));
            }
        }
    }

    public void playAnimation(String animationName, float loopDuration) {
        playAnimation(animationName, loopDuration, true);
    }

    public void playAnimation(String animationName, float loopDuration, boolean isLoop) {
        setAnimationName(animationName);
        setAnimationProperties(loopDuration, isLoop);
    }

    public void setAnimationName(String animationName) {
        for (AnimChannel animationChannel : animationChannels) {
            try {
                animationChannel.setAnim(animationName);
            } catch (IllegalArgumentException ex) {
                stopAndRewindAnimation(animationChannel);
            }
        }
    }

    public void setAnimationProperties(float loopDuration, boolean isLoop) {
        for (AnimChannel animationChannel : animationChannels) {
            animationChannel.setSpeed(animationChannel.getAnimMaxTime() / loopDuration);
            animationChannel.setLoopMode(isLoop ? LoopMode.Loop : LoopMode.DontLoop);
        }
    }

    public void stopAndRewindAnimation() {
        for (AnimChannel animationChannel : animationChannels) {
            stopAndRewindAnimation(animationChannel);
        }
    }

    public void stopAndRewindAnimation(final AnimChannel animationChannel) {
        taskConsumer.accept(() -> {
            animationChannel.reset(true);
        });
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animationName) {

    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animationName) {

    }

    public ModelSkin getSkin() {
        return skin;
    }

    public Spatial getModelSpatial() {
        return modelSpatial;
    }
}
