package com.etherdungeons.basemod.data.gameflow.triggers;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import java.util.Optional;

/**
 *
 * @author Philipp
 */
public class PostResolveTriggerSystem implements GameSystem {

    @Override
    public void run(EntityData data) {
        if(!data.streamEntities(TriggerRequest.class).findAny().isPresent()) {
            Optional<EntityId> first = data.streamEntities(PostResolveTriggerRequest.class).sorted().findFirst();
            if(first.isPresent()) {
                EntityId triggerArgs = first.get();
                EntityId effect = data.remove(triggerArgs, PostResolveTriggerRequest.class).getTrigger();
                data.set(triggerArgs, new TriggerRequest(effect));
            }
        }
    }
    
}
