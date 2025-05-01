package software.bigbade.slimefunvoid.api.research;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import software.bigbade.slimefunvoid.SlimefunVoid;
import software.bigbade.slimefunvoid.items.Items;
import software.bigbade.slimefunvoid.utils.ResearchIDHandler;

public enum Researches {
    VOID_BENCH("虚空研究台", 30, Items.VOID_RESEARCH_BENCH),
    VOID_BAG("虚空背包", 10, Items.VOID_BAG),
    VOID_PORTAL("虚空传送门", 30, Items.VOID_PORTAL),
    VOID_ALTAR("虚空祭坛", 15, Items.VOID_ALTAR),
    BASIC_WAND("基础魔杖", 20, Items.BASIC_WAND),
    ADVANCED_WAND("高级魔杖", 30, Items.ADVANCED_WAND),
    COMBUSTION_SPELL("燃烧咒语", 10, Items.COMBUSTION_SPELL),
    FIREBALL_SPELL("火球术", 15, Items.FIREBALL_SPELL),
    LIGHT_BENDING_SPELL("光束变形术", 5, Items.LIGHT_BENDING_SPELL),
    LIGHT_BEAM_SPELL("光束咒语", 15, Items.LIGHT_BEAM_SPELL),
    TRACKING_BEAM_SPELL("追踪光束咒语", 25, Items.TRACKING_BEAM_SPELL),
    TELEPORT_SPELL("传送咒语", 5, Items.TELEPORT_SPELL),
    SWAP_SPELL("移形换位咒语", 15, Items.SWAP_SPELL),
    LIGHTNING_SPELL("闪电咒语", 10, Items.LIGHTNING_SPELL),
    WATER_SHOCK_SPELL("水潮克咒", 15, Items.WATER_SHOCK_SPELL),
    LEVITATE_SPELL("漂浮咒语", 5, Items.LEVITATE_SPELL),
    LAUNCH_SPELL("发射咒语", 15, Items.LAUNCH_SPELL),
    THROW_SPELL("投掷咒语", 25, Items.THROW_SPELL),
    FAST_SWIMMING_SPELL("高速游泳咒语", 5, Items.FAST_SWIMMING_SPELL),
    ICE_SHIELD_SPELL("冰盾咒语", 15, Items.ICE_SHIELD_SPELL),
    TREE_TRAP_SPELL("树木陷阱咒语", 5, Items.TREE_TRAP_SPELL);

    @Getter
    private final Research research;

    Researches(String key, int cost, SlimefunItemStack item) {
        this.research = new Research(new NamespacedKey(SlimefunVoid.getInstance(), key.toLowerCase().replace(" ", "_")), ResearchIDHandler.nextID(), key, cost);
        research.addItems(item);
        research.register();
    }

    public Research getResearch() {
        // TODO Auto-generated method stub
        return research;
    }
}
