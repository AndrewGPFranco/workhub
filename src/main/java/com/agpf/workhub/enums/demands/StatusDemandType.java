package com.agpf.workhub.enums.demands;

import lombok.Getter;

@Getter
public enum StatusDemandType {

    DONE("Concluído"),
    PENDING("Pendente"),
    BLOCKED("Bloqueado"),
    ONGOING("Em andamento"),
    ;

    private final String description;

    StatusDemandType(String description) {
        this.description = description;
    }

}
