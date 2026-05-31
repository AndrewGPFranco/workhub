package com.agpf.workhub.enums.demands;

import lombok.Getter;

@Getter
public enum PriorityDemandType {

    LOW("Baixa"),
    HIGH("Alta"),
    MEDIUM("Média"),
    URGENT("Urgente"),
    ;

    private final String label;

    PriorityDemandType(String label) {
        this.label = label;
    }
}
