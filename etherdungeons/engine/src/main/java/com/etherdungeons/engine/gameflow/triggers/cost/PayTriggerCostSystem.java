package com.etherdungeons.engine.gameflow.triggers.cost;

import com.etherdungeons.engine.gameflow.triggers.TriggerRejected;
import com.etherdungeons.engine.gameflow.triggers.TriggerRequest;
import com.etherdungeons.engine.stats.active.ActiveActionPoints;
import com.etherdungeons.engine.stats.active.ActiveHealth;
import com.etherdungeons.engine.stats.active.ActiveMovePoints;
import com.etherdungeons.engine.stats.cost.ActionPointsCost;
import com.etherdungeons.engine.stats.cost.HealthCost;
import com.etherdungeons.engine.stats.cost.MovePointsCost;
import com.etherdungeons.entitysystem.EntityData;
import com.etherdungeons.entitysystem.EntityId;

/**
 *
 * @author Philipp
 */
public class PayTriggerCostSystem implements Runnable {

    private final EntityData data;

    public PayTriggerCostSystem(EntityData data) {
        this.data = data;
    }

    @Override
    public void run() {
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
