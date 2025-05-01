package software.bigbade.slimefunvoid.api.research;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import software.bigbade.slimefunvoid.impl.VoidResearch;

@RequiredArgsConstructor
public enum VoidResearches {
    THE_DISCOVERY(1, new VoidResearch("发现", 120, "&5虚无 &a始终能被感知到，", "但发现并随后驾驭它", "仍然是一个不小的壮举")),
    HARNESSING_THE_VOID(1, new VoidResearch("驾驭 &5虚无", 300, Researches.VOID_BAG.getResearch(), "这个背包可以与一个箱子连接", "&5虚无 &a会消耗一些传送过去的物品", "解锁：&5虚无背包")),
    ENTERING_THE_VOID(1, new VoidResearch("进入 &5虚无", 500, "通过极度的专注，", "&5虚无 &a可以被精神上接入，", "允许吸取其混乱的能量")),
    CREATING_THE_ALTAR(2, new VoidResearch("创造祭坛", 300, Researches.VOID_ALTAR.getResearch(), "允许使用纯粹的虚无能量制作物品")),
    BUILDING_THE_PORTAL(2, new VoidResearch("建造传送门", 300, Researches.VOID_PORTAL.getResearch(), "可以建造通往虚无的传送门", "允许接入 &5虚无 的原始力量")),
    TAPPING_THE_VOID(2, new VoidResearch("接入 &5虚无", 400, Researches.BASIC_WAND.getResearch(), "一把基础的魔杖，可以让魔杖接入", "大量的虚无能量")),
    ADVANCED_WIZARDRY(3, new VoidResearch("高级魔法", 500, Researches.ADVANCED_WAND.getResearch(), "一把精炼的魔杖，允许施展高级魔法")),
    SPECIALIZATION(3, new VoidResearch("专精", 500, "专精于某种元素", "可以提升魔法力量", "解锁：&5高级魔杖"));

    @Getter
    private final int categoryID;
    @Getter
    private final IVoidResearch research;
}
