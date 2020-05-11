package jp.kaiz.atsassistmod.controller.trainprotection;

public enum TrainProtectionType {
    NONE("開放", 0, TrainProtection.class),
    STATION_PREMISES("構内", 1, StationPremisesController.class),
    ATACS("ATACS", 10, ATACSController.class),
    ATSPs("ATS-Ps", 11, ATSPsController.class),
    RATS("R-ATS", 12, RATSController.class),
    RnATS("Rn-ATS", 13, RnATSController.class);

    public String name;
    public int id;
    public Class<? extends TrainProtection> aClass;

    TrainProtectionType(String name, int id, Class<? extends TrainProtection> aClass) {
        this.name = name;
        this.id = id;
        this.aClass = aClass;
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
