package com.company;

import java.io.Serializable;

/**
 * The Role Enum
 */
public enum Role {

    /**
     * God father role.
     */
    GodFather,
    /**
     * Doctor lecter role.
     */
    DoctorLecter,
    /**
     * Simple mafia role.
     */
    SimpleMafia,
    /**
     * Doctor role.
     */
    Doctor,
    /**
     * Detective role.
     */
    Detective,
    /**
     * Sniper role.
     */
    Sniper,
    /**
     * Simple villager role.
     */
    SimpleVillager,
    /**
     * Mayor role.
     */
    Mayor,
    /**
     * Psychologist role.
     */
    Psychologist,
    /**
     * Diehard role.
     */
    Diehard ;

    /**
     * Is mafia boolean.
     *
     * @param role the role
     * @return the boolean
     */
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
