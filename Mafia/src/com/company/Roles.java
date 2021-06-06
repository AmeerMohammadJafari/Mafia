package com.company;


import java.io.Serializable;

public enum Roles implements Serializable {
    GodFather, DoctorLecter, SimpleMafia, Doctor, Detective, Sniper, SimpleVillager, Mayor,
    Psychologist, Diehard ;

    public static boolean isMafia(Roles role){
        switch (role){
            case GodFather:
            case  DoctorLecter:
            case SimpleMafia:
                return true;
        }
        return false;
    }

}
