package software.bigbade.slimefunvoid.items;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import org.bukkit.Material;

public final class Items {
    /**
     * Blocks
     */
        /**
         * 物品
         */
        public static final SlimefunItemStack VOID_RESEARCH_BENCH = new SlimefunItemStack("VOID_RESEARCH_BENCH", Material.ENCHANTING_TABLE, "&5虚空研究台",
        "&a一个强大的工作台，允许对", "&5虚空 进行研究");
        public static final SlimefunItemStack VOID_QUARRY = new SlimefunItemStack("VOID_QUARRY", Material.ENDER_CHEST, "&5虚空矿机",
        "&5允许开采", "&5大面积的方块", "&5虚空 &c会消耗一些开采的方块");
        /**
        * 物品
        */
        public static final SlimefunItemStack VOID_BAG = new SlimefunItemStack("VOID_BAG", Material.ENDER_CHEST, "&5虚空背包",
        "&a一个能够通过 &5虚空 运输物品的背包", "&5虚空 &4会偶尔消耗一些物品", "&a按住Shift右键绑定到箱子");
        public static final SlimefunItemStack COMBUSTION_SPELL = new SlimefunItemStack("COMBUSTION_SPELL", Material.FLINT_AND_STEEL, "&c燃烧咒语",
        "&a使你注视的目标瞬间点燃");
        public static final SlimefunItemStack LIGHT_BENDING_SPELL = new SlimefunItemStack("LIGHT_BENDING_SPELL", Material.GLASS, "&f光线弯曲咒语",
        "&a操纵你周围的光线", "&a使你（但不是你的盔甲）完全隐形");
        public static final SlimefunItemStack LIGHT_BEAM_SPELL = new SlimefunItemStack("LIGHT_BEAM_SPELL", Material.GLOWSTONE_DUST, "&f光束咒语",
        "&a召唤一束纯粹的光", "&a来攻击敌人");
        public static final SlimefunItemStack TRACKING_BEAM_SPELL = new SlimefunItemStack("TRACKING_BEAM_SPELL", Material.GLOWSTONE, "&f追踪光束咒语",
        "&a召唤一束追踪敌人的", "&a纯粹的光");
        public static final SlimefunItemStack TELEPORT_SPELL = new SlimefunItemStack("TELEPORT_SPELL", Material.ENDER_PEARL, "&5传送咒语",
        "&a通过虚无的传送门实现", "&a瞬间传送");
        public static final SlimefunItemStack SWAP_SPELL = new SlimefunItemStack("SWAP_SPELL", Material.ENDER_EYE, "&5移形换位咒语",
        "&a通过一个小传送门交换两个", "&a生物的位置");
        public static final SlimefunItemStack LIGHTNING_SPELL = new SlimefunItemStack("LIGHTNING_SPELL", Material.DIAMOND_AXE, "&e闪电咒语",
        "&a将电流汇聚成闪电", "&a造成高伤害，", "&a并具有其他特性");
        public static final SlimefunItemStack WATER_SHOCK_SPELL = new SlimefunItemStack("WATER_SHOCK_SPELL", Material.WATER_BUCKET, "&e水击咒语",
        "&a通过水传导电流，", "&a击中水中的所有玩家");
        public static final SlimefunItemStack LEVITATE_SPELL = new SlimefunItemStack("LEVITATE_SPELL", Material.SHULKER_SHELL, "&7漂浮咒语",
        "&a通过增加目标下方空气的密度", "&a使目标开始漂浮");
        public static final SlimefunItemStack LAUNCH_SPELL = new SlimefunItemStack("LAUNCH_SPELL", Material.SHULKER_BOX, "&7发射咒语",
        "&a利用大量的向上冲力", "&a将目标发射到空中");
        public static final SlimefunItemStack THROW_SPELL = new SlimefunItemStack("THROW_SPELL", Material.FEATHER, "&7投掷咒语",
        "&a将目标发射到你注视的方向");
        public static final SlimefunItemStack FAST_SWIMMING_SPELL = new SlimefunItemStack("FAST_SWIMMING_SPELL", Material.OAK_BOAT, "&1快速游泳咒语",
        "&a使你能够在水中", "&a更快地游泳");
        public static final SlimefunItemStack ICE_SHIELD_SPELL = new SlimefunItemStack("ICE_SHIELD_SPELL", Material.ICE, "&1冰盾咒语",
        "&a冻结空气中的水", "&a形成一个冰球");
        public static final SlimefunItemStack TREE_TRAP_SPELL = new SlimefunItemStack("TREE_TRAP_SPELL", Material.OAK_SAPLING, "&a树木陷阱咒语",
        "&a将目标困在树中");
        private static final String DANGEROUS_ITEM = "&4危险物品";
        public static final SlimefunItemStack VOID_PORTAL = new SlimefunItemStack("VOID_PORTAL", Material.END_PORTAL_FRAME, "&5虚无传送门",
        "&5允许进入虚无", "&5并使用虚无祭坛进行虚无仪式", DANGEROUS_ITEM);
        public static final SlimefunItemStack VOID_ALTAR = new SlimefunItemStack("VOID_ALTAR", Material.END_STONE_BRICKS, "&5虚无祭坛",
        "&5用于接入虚无的物品容器", DANGEROUS_ITEM);
        public static final SlimefunItemStack VOID_ATTRACTOR = new SlimefunItemStack("VOID_ATTRACTOR", Material.HOPPER, "&5虚无引力机",
        "&5吸引周围的所有实体", "&5使其靠近", DANGEROUS_ITEM);
        /**
        * 魔杖
        */
        public static final SlimefunItemStack BASIC_WAND = new SlimefunItemStack("BASIC_WAND", Material.STICK, "&d基础魔杖",
        "&a能够精确地使用来自 &5虚无 的能量", "&a最大元素：300", DANGEROUS_ITEM);
        public static final SlimefunItemStack ADVANCED_WAND = new SlimefunItemStack("ADVANCED_WAND", Material.BLAZE_ROD, "&d高级魔杖",
        "&a经过改进的魔杖，允许施展更强大的魔法", "&a最大元素：500", DANGEROUS_ITEM);
        private static final SlimefunItemStack[] WANDS = new SlimefunItemStack[]{Items.BASIC_WAND, Items.ADVANCED_WAND};
        /**
        * 咒语
        */
        public static final SlimefunItemStack FIREBALL_SPELL = new SlimefunItemStack("FIREBALL_SPELL", Material.FIRE_CHARGE, "&c火球咒语",
        "&a允许魔杖发射", "&a火焰冲击", DANGEROUS_ITEM);

    //Private constructor to hide implicit public one
    private Items() {
    }

    public static SlimefunItemStack[] getWands() {
        return WANDS;
    }
}