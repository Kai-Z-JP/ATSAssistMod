package jp.kaiz.atsassistmod.controller.trainprotection;

import net.minecraft.client.resources.I18n;

public enum TrainProtectionType {
    NONE(/*"開放"*/("ATSAssistMod.trainprotection.none"), 0, TrainProtection.class),
    STATION_PREMISES(/*"構内"*/("ATSAssistMod.trainprotection.station_premises"), 1, StationPremisesController.class),
    ATACS(/*"ATACS"*/("ATSAssistMod.trainprotection.atacs"), 10, ATACSController.class),
    ATSPs(/*"ATS-Ps"*/("ATSAssistMod.trainprotection.atsps"), 11, ATSPsController.class),
    RATS(/*"R-ATS"*/("ATSAssistMod.trainprotection.rats"), 12, RATSController.class),
    RnATS(/*"Rn-ATS"*/("ATSAssistMod.trainprotection.rnats"), 13, RnATSController.class);

    public String name;
    public int id;
    public Class<? extends TrainProtection> aClass;

    TrainProtectionType(String name, int id, Class<? extends TrainProtection> aClass) {
        this.name = name;
        this.id = id;
        this.aClass = aClass;
    }

    public String getDisplayName() {
        return I18n.format(this.name);
    }

    public static TrainProtectionType getType(int par1) {
        for (TrainProtectionType type : TrainProtectionType.values()) {
            if (type.id == par1) {
                return type;
            }
        }
        return NONE;
    }
}
