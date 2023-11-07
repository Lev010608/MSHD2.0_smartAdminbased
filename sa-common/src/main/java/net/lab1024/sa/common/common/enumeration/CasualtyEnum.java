package net.lab1024.sa.common.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum CasualtyEnum implements BaseEnum{
    /**
     *
     */
    DEATH(CasualtyNameConst.DEATH,"死亡"),
    INJURING(CasualtyNameConst.INJURING,"受伤"),
    MISSING(CasualtyNameConst.MISSING,"失踪");

    private final String value;

    private final String desc;

    public static final class CasualtyNameConst {
        public static final String DEATH = "death";
        public static final String INJURING = "injuring";
        public static final String MISSING = "missing";

    }
}
