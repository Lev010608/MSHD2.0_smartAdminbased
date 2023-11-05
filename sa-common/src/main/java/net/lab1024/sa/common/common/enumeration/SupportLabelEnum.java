package net.lab1024.sa.common.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SupportLabelEnum implements BaseEnum {

    /**
     * TRANS
     */
    TRANS(SupportLabelEnum.SystemEnvironmentNameConst.TRANS, "交通"),

    /**
     * WATER
     */
    WATER(SupportLabelEnum.SystemEnvironmentNameConst.WATER, "供水"),

    /**
     * OIL
     */
    OIL(SupportLabelEnum.SystemEnvironmentNameConst.OIL, "输油"),

    /**
     * GAS
     */
    GAS(SupportLabelEnum.SystemEnvironmentNameConst.GAS, "燃气"),

    /**
     * ELECTRICITY
     */
    ELECTRICITY(SupportLabelEnum.SystemEnvironmentNameConst.ELECTRICITY, "电力"),

    /**
     * COMMUNICATION
     */
    COMMUNICATION (SupportLabelEnum.SystemEnvironmentNameConst.COMMUNICATION , "通信"),

    /**
     * HYDRAULIC
     */
    HYDRAULIC(SupportLabelEnum.SystemEnvironmentNameConst.HYDRAULIC, "水利");

    private final String value;

    private final String desc;

    public static final class SystemEnvironmentNameConst {
        public static final String TRANS = "trans";
        public static final String WATER = "water";
        public static final String OIL = "oil";
        public static final String GAS = "gas";
        public static final String ELECTRICITY = "electricity";
        public static final String COMMUNICATION = "communication";
        public static final String HYDRAULIC = "hydraulic";
    }
}
