package br.com.engine.superpeoplebattle.domain.entities;

public class SuperPeople {

    private Long level;

    private Long strength;

    private Long agility;

    private Long resistance;

    public Long damageCalculator() {
        return strength * level;
    }

    public static SuperPeople firstHitShower(SuperPeople fighterOne, SuperPeople fighterTwo) {
        return firstHitFinder(fighterOne, fighterTwo);
    }

    private static SuperPeople firstHitFinder(SuperPeople fighterOne, SuperPeople fighterTwo) {
        if (fighterOne.getAgility() > fighterTwo.getAgility()) {
            return fighterOne;
        } else if (fighterOne.getAgility() < fighterTwo.getAgility()) {
            return fighterTwo;
        } else if (fighterOne.getLevel() > fighterTwo.getLevel()) {
            return fighterOne;
        } else if (fighterOne.getLevel() < fighterTwo.getLevel()) {
            return fighterTwo;
        } else if (fighterOne.getStrength() > fighterTwo.getStrength()) {
            return fighterOne;
        }
        return fighterTwo;
    }


    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getStrength() {
        return strength;
    }

    public void setStrength(Long strength) {
        this.strength = strength;
    }

    public Long getAgility() {
        return agility;
    }

    public void setAgility(Long agility) {
        this.agility = agility;
    }

    public Long getResistance() {
        return resistance;
    }

    public void setResistance(Long resistance) {
        this.resistance = resistance;
    }
}
