package software.bigbade.slimefunvoid.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import software.bigbade.slimefunvoid.spells.electricity.LightningSpell;
import software.bigbade.slimefunvoid.spells.electricity.WaterShockSpell;
import software.bigbade.slimefunvoid.spells.ender.SwapSpell;
import software.bigbade.slimefunvoid.spells.ender.TeleportSpell;
import software.bigbade.slimefunvoid.spells.fire.CombustionSpell;
import software.bigbade.slimefunvoid.spells.fire.FireballSpell;
import software.bigbade.slimefunvoid.spells.grass.TreeTrapSpell;
import software.bigbade.slimefunvoid.spells.light.LightBeamSpell;
import software.bigbade.slimefunvoid.spells.light.LightBendingSpell;
import software.bigbade.slimefunvoid.spells.light.TrackingBeamSpell;
import software.bigbade.slimefunvoid.spells.water.FastSwimmingSpell;
import software.bigbade.slimefunvoid.spells.water.IceShieldSpell;
import software.bigbade.slimefunvoid.spells.wind.LaunchSpell;
import software.bigbade.slimefunvoid.spells.wind.LevitateSpell;
import software.bigbade.slimefunvoid.spells.wind.ThrowSpell;

@RequiredArgsConstructor
public enum Spells {
    COMBUSTION(new CombustionSpell()),
    FIREBALL(new FireballSpell()),
    LIGHT_BENDING(new LightBendingSpell()),
    LIGHT_BEAM(new LightBeamSpell()),
    TRACKING_BEAM(new TrackingBeamSpell()),
    TELEPORT(new TeleportSpell()),
    SWAP(new SwapSpell()),
    LIGHTNING(new LightningSpell()),
    WATER_SHOCK(new WaterShockSpell()),
    LEVITATE(new LevitateSpell()),
    LAUNCH(new LaunchSpell()),
    THROW(new ThrowSpell()),
    FAST_SWIMMING(new FastSwimmingSpell()),
    ICE_SHIELD(new IceShieldSpell()),
    TREE_TRAP(new TreeTrapSpell());

    Spells(WandSpell spell) {
		this.spell = spell;
	}

	@Getter
    private final WandSpell spell;
}
