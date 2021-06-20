package net.runelite.client.plugins.tobhelper;

public enum TobHelperNpcEnum {
    // Maiden
    HM_MAIDEN(10822),
    REG_MAIDEN_P1(8360),
    REG_MAIDEN_P2(8361),
    REG_MAIDEN_P3(8362),

    HM_MAIDEN_CRAB(10828),
    SM_MAIDEN_CRAB(99999),
    REG_MAIDEN_CRAB(8366),

    HM_MAIDEN_BLOOD_SPAWN(10829),
    REG_MAIDEN_BLOOD_SPAWN(8367),

    // Bloat
    HM_BLOAT(10813),
    REG_BLOAT(8359),

    // Nylo
    HM_NYLO_SMALL_MELEE(10791),
    REG_NYLO_SMALL_MELEE(8342),
    HM_NYLO_SMALL_RANGE(10792),
    REG_NYLO_SMALL_RANGE(8342),
    HM_NYLO_SMALL_MAGE(10793),
    REG_NYLO_SMALL_MAGE(8344),

    NM_NYLO_BIG_MELEE(10794),
    REG_NYLO_BIG_MELEE(8345),
    HM_NYLO_BIG_RANGE(10795),
    REG_NYLO_BIG_RANGE(8346),
    HM_NYLO_BIG_MAGE(10796),
    REG_NYLO_BIG_MAGE(8347),

    HM_NYLO_HUGE_MELEE(10804),
    HM_NYLO_HUGE_RANGE(10805),
    HM_NYLO_HUGE_MAGE(10806),

    HM_NYLO_BOSS_MELEE(10808),
    REG_NYLO_BOSS_MELEE(8355),
    HM_NYLO_BOSS_RANGE(10810),
    REG_NYLO_BOSS_RANGE(8357),
    HM_NYLO_BOSS_MAGE(10809),
    REG_NYLO_BOSS_MAGE(8356),

    // Sote
    HM_SOTE_IDLE(10867),
    HM_SOTE_ACTIVE(10868),

    // Xarp
    HM_XARP_IDLE(10770),
    HM_XARP_P1(10771),
    HM_XARP_P2(10772),

    // Verzik
    HM_VERZIK_P1(10848),
    HM_VERZIK_P1_TRANSITION(10849),
    HM_VERZIK_P2(10850),
    HM_VERZIK_P2_TRANSITION(10851),
    HM_VERZIK_P3(10852),

    HM_VERZIK_MELEE_CRAB(10858),
    HM_VERZIK_RANGE_CRAB(10859),
    HM_VERZIK_MAGE_CRAB(10860),





    END(0);

    public int npcId;
    TobHelperNpcEnum(int npcId) {
        this.npcId = npcId;
    }

    public static String getEnumKeyFromId(int value) {
        for(TobHelperNpcEnum npc : TobHelperNpcEnum.values()) {
            if(npc.npcId == value) {
                return npc.name().substring(0, npc.name().indexOf("_"));
            }
        }
        return null;
    }

    TobHelperNpcEnum() {}
}
