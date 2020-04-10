package software.bigbade.slimefunvoid.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import software.bigbade.slimefunvoid.spells.FireballSpell;

@RequiredArgsConstructor
public enum Spells {
    FIREBALL(new FireballSpell());

    @Getter
    private final WandSpell spell;
}
