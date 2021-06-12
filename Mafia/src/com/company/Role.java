package com.company;

import java.io.Serializable;

public enum Role {

    GodFather, DoctorLecter, SimpleMafia, Doctor, Detective, Sniper, SimpleVillager, Mayor,
    Psychologist, Diehard ;

    public static boolean isMafia(Role role){
        switch (role){
            case GodFather:
            case  DoctorLecter:
            case SimpleMafia:
                return true;
        }
        return false;
    }

}
