package net.lab1024.sa.common.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SupportExtentEnum implements BaseEnum{

    /**
     * WELL
     */
    WELL(SupportExtentEnum.SystemEnvironmentNameConst.WELL, "基本完好"),

    /**
     * SLIGHTLY
     */
    SLIGHTLY(SupportExtentEnum.SystemEnvironmentNameConst.SLIGHTLY, "轻微破坏"),

    /**
     * MEDIUM
     */
    MEDIUM(SupportExtentEnum.SystemEnvironmentNameConst.MEDIUM, "中等破坏"),

    /**
     * HEAVY
     */
    HEAVY(SupportExtentEnum.SystemEnvironmentNameConst.HEAVY, "严重破坏"),

    /**
     * RUIN
     */
    RUIN(SupportExtentEnum.SystemEnvironmentNameConst.RUIN, "毁坏");

    private final String value;

    private final String desc;

    public static final class SystemEnvironmentNameConst {
        public static final String WELL = "well";
        public static final String SLIGHTLY = "slightly";
        public static final String MEDIUM = "medium";
        public static final String HEAVY = "heavy";

        public static final String RUIN = "ruin";
    }
}

