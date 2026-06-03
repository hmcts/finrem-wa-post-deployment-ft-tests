package uk.gov.hmcts.reform.finrempostdeploymentfttests.domain.entities.role.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum ActorIdType {
    IDAM, CASEPARTY, @JsonEnumDefaultValue UNKNOWN
}
