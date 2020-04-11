package software.bigbade.slimefunvoid.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import software.bigbade.slimefunvoid.spells.ender.TeleportSpell;
import software.bigbade.slimefunvoid.spells.fire.FireballSpell;
import software.bigbade.slimefunvoid.spells.light.LightBendingSpell;

@RequiredArgsConstructor
public enum Spells {
    FIREBALL(new FireballSpell()),
    LIGHT_BENDING(new LightBendingSpell()),
    TELEPORT(new TeleportSpell());

    @Getter
    private final WandSpell spell;
}
