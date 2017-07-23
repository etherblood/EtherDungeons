package com.etherdungeons.basemod.data.gameflow.triggers.cost;

import com.etherdungeons.basemod.GameSystem;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRejected;
import com.etherdungeons.basemod.data.gameflow.triggers.TriggerRequest;
import com.etherdungeons.basemod.data.stats.active.ActiveActionPoints;
import com.etherdungeons.basemod.data.stats.active.ActiveHealth;
import com.etherdungeons.basemod.data.stats.active.ActiveMovePoints;
import com.etherdungeons.basemod.data.stats.cost.ActionPointsCost;
import com.etherdungeons.basemod.data.stats.cost.HealthCost;
import com.etherdungeons.basemod.data.stats.cost.MovePointsCost;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;
import org.slf4j.Logger;

/**
 *
 * @author Philipp
 */
public class PayTriggerCostSystem implements GameSystem {

    @Override
    public void run(EntityData data, Logger log) {
        for (EntityId entity : data.entities(TriggerRequest.class)) {
            EntityId trigger = data.get(entity, TriggerRequest.class).getTrigger();
            HasTriggerCost cost = data.get(trigger, HasTriggerCost.class);
            if (cost != null) {
                EntityId investor = cost.getInvestor();
                boolean costsAffordable = true;

                ActiveActionPoints ap = null;
                ActionPointsCost apCost = data.get(trigger, ActionPointsCost.class);
                if (costsAffordable && apCost != null) {
                    ActiveActionPoints currentAp = data.get(investor, ActiveActionPoints.class);
                    if (currentAp == null || currentAp.getAp() < apCost.getAp()) {
                        costsAffordable = false;
                    } else {
                        ap = new ActiveActionPoints(currentAp.getAp() - apCost.getAp());
                    }
                }

                ActiveMovePoints mp = null;
                MovePointsCost mpCost = data.get(trigger, MovePointsCost.class);
                if (costsAffordable && mpCost != null) {
                    ActiveMovePoints currentMp = data.get(investor, ActiveMovePoints.class);
                    if (currentMp == null || currentMp.getMp() < mpCost.getMp()) {
                        costsAffordable = false;
                    } else {
                        mp = new ActiveMovePoints(currentMp.getMp() - mpCost.getMp());
                    }
                }

                ActiveHealth hp = null;
                HealthCost hpCost = data.get(trigger, HealthCost.class);
                if (costsAffordable && hpCost != null) {
                    ActiveHealth currentHp = data.get(investor, ActiveHealth.class);
                    if (currentHp == null || currentHp.getHealth() < hpCost.getHealth()) {
                        costsAffordable = false;
                    } else {
                        hp = new ActiveHealth(currentHp.getHealth() - hpCost.getHealth());
                    }
                }

                if (costsAffordable) {
                    if (ap != null) {
                        data.set(investor, ap);
                    }
                    if (mp != null) {
                        data.set(investor, mp);
                    }
                    if (hp != null) {
                        data.set(investor, hp);
                    }
                } else {
                    data.remove(entity, TriggerRequest.class);
                    data.set(entity, new TriggerRejected(entity, "unable to pay trigger costs"));
                }
            }
        }
    }

}
