package me.kvq.HospitalTask.security;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("CREATE_PATIENT", "UPDATE_PATIENT", "DELETE_PATIENT", "SEE_ALL_PATIENTS",
            "CREATE_DOCTOR", "UPDATE_DOCTOR", "DELETE_DOCTOR", "SEE_ALL_DOCTORS",
            "CREATE_APPOINTMENT", "UPDATE_APPOINTMENT", "DELETE_APPOINTMENT", "SEE_ALL_APPOINTMENTS"),
    DOCTOR("SEE_ALL_PATIENTS", "SEE_OWN_APPOINTMENTS", "UPDATE_SELF"),
    PATIENT("SEE_ALL_DOCTORS", "UPDATE_SELF", "CREATE_OWN_APPOINTMENT", "DELETE_OWN_APPOINTMENT", "SEE_OWN_APPOINTMENTS");

    private String[] permissions;

    Role(String... permissions) {
        this.permissions = permissions;
    }

}
