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
        return fastestHeroFinder(fighterOne, fighterTwo);
    }

    private static SuperPeople fastestHeroFinder(SuperPeople heroOne, SuperPeople heroTwo) {
        if (heroOne.getAgility() > heroTwo.getAgility()) {
            return heroOne;
        } else if (heroOne.getAgility() < heroTwo.getAgility()) {
            return heroTwo;
        } else {
            return highestLevelHeroFinder(heroOne, heroTwo);
        }
    }

    private static SuperPeople highestLevelHeroFinder(SuperPeople heroOne, SuperPeople heroTwo) {
        if (heroOne.getLevel() > heroTwo.getLevel()) {
            return heroOne;
        } else if (heroOne.getLevel() < heroTwo.getLevel()) {
            return heroTwo;
        } else {
            return highestStrengthHeroFinder(heroOne, heroTwo);
        }
    }

    private static SuperPeople highestStrengthHeroFinder(SuperPeople heroOne, SuperPeople heroTwo) {
        if (heroOne.getStrength() > heroTwo.getStrength()) {
            return heroOne;
        } else if (heroOne.getStrength() < heroTwo.getStrength()) {
            return heroTwo;
        } else {
            return null;
        }
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
