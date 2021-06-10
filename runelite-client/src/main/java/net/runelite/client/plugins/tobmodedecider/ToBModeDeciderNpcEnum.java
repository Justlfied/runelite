package net.runelite.client.plugins.tobmodedecider;

public enum ToBModeDeciderNpcEnum {
    // Maiden
    HM_MAIDEN(10822),

    HM_MAIDEN_CRAB(1828),
    HM_MAIDEN_BLOOD_SPAWN(10829),

    // Bloat
    HM_BLOAT(10813),

    // Nylo
    HM_NYLO_SMALL_MELEE(10791),
    HM_NYLO_SMALL_RANGE(10792),
    HM_NYLO_SMALL_MAGE(10793),

    NM_NYLO_BIG_MELEE(10794),
    HM_NYLO_BIG_RANGE(10795),
    HM_NYLO_BIG_MAGE(10796),

    HM_NYLO_HUGE_MELEE(10804),
    HM_NYLO_HUGE_RANGE(10805),
    HM_NYLO_HUGE_MAGE(10806),

    HM_NYLO_BOSS_MELEE(10808),
    HM_NYLO_BOSS_RANGE(10810),
    HM_NYLO_BOSS_MAGE(10809),

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
    ToBModeDeciderNpcEnum(int npcId) {
        this.npcId = npcId;
    }
}
