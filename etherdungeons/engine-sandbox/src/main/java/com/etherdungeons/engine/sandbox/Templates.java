package com.etherdungeons.engine.sandbox;

/**
 *
 * @author Philipp
 */
public class Templates {

    public String startState() {
        return "{"
                + "\"#0\":{\"Name\":[\"GameState\"],\"GameState\":[]},"
                + "\"#1\":{\"StartGameEffect\":[],\"PostResolveTriggerRequest\":[\"#1\"]},"
                + "\"#2\":{\"Name\":[\"EndTurnActivePlayerEffects\"],\"EndTurnTrigger\":[],\"ActivePlayerEffectTarget\":[],\"ResetApEffect\":[],\"ResetMpEffect\":[]}"
                + "}";
    }

    public String position(int x, int y) {
        return "{\"#0\":{\"Position\":[" + x + "," + y + "]}}";
    }

    public String name(String name) {
        return "{\"#0\":{\"Name\":[\"" + name + "\"],\"Actor\":[]}}";
    }

    public String actor() {
        return "{\"#0\":{\"Actor\":[]}}";
    }

    public String baseStats() {
        return baseStats(10, 20, 4, 2);
    }

    public String baseStats(int init, int health, int ap, int mp) {
        return "{\"#0\":{\"BaseInitiative\":[" + init + "],\"BaseHealth\":[" + health + "],\"BaseActionPoints\":[" + ap + "],\"BaseMovePoints\":[" + mp +"]}}";
    }

    public String endTurnAbility(String name) {
        return "{\"#1\":{\"Name\":[\"" + name + "\"],\"EndTurnEffect\":[],\"TargetActiveTurnCondition\":[],\"FixedEffectTargets\":[[\"#0\"]]}}";
    }

    public String moveAbility(String name) {
        return "{\"#1\":{\"Name\":[\"" + name + "\"],\"MoveEffect\":[],\"HasTriggerCost\":[\"#0\"],\"MovePointsCost\":[1],\"TargetActiveTurnCondition\":[],\"FixedEffectTargets\":[[\"#0\"]]}}";
    }

    public String regenerationAbility(String name, int heal) {
        return "{\"#1\":{\"Name\":[\"" + name + "\"],\"EndTurnTrigger\":[],\"TargetActiveTurnCondition\":[],\"FixedEffectTargets\":[[\"#0\"]],\"HealEffect\":[" + heal + "]}}";
    }

    public String endTurnRoundExpire(String name, int round) {
        return "{\"#1\":{\"Name\":[\"" + name + "\"],\"CleanupEffect\":[],\"EndTurnTrigger\":[],\"CascadeCleanup\":[\"#0\"],\"FixedEffectTargets\":[[\"#0\"]],\"MinRoundNumberCondition\":[" + round + "]}}";
    }

    public String duoApBuff(String name, int ap) {
        return "{\"#3\":{\"Name\":[\"" + name + "\"],\"CascadeCleanup\":[\"#0\"],\"FixedEffectTargets\":[[\"#1\",\"#2\"]],\"AdditiveActionPoints\":[" + ap + "]}}";
    }
}
