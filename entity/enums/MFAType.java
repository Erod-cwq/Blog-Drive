package com.example.jpa_learn.entity.enums;

public enum MFAType implements ValueEnum<Integer> {
    None(0);
    private final Integer value;
    MFAType(Integer value){this.value = value;}
    public static boolean useMFA(MFAType mfaType){
        return mfaType != null && MFAType.None != mfaType;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
