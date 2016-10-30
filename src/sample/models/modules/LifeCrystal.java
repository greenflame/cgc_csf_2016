package sample.models.modules;

/**
 * Created by Alexander on 26/10/16.
 */
public class LifeCrystal {
    private int totalHealth;
    private int healthRest;

    public LifeCrystal(int totalHealth) {
        this.totalHealth = totalHealth;
        this.healthRest = totalHealth;
    }

    public int getTotalHealth() {
        return totalHealth;
    }

    public int getHealthRest() {
        return healthRest;
    }

    public void damage(int damage) {
        healthRest = Math.max(0, healthRest - damage);
    }

    public boolean isDamaged() {
        return healthRest == 0;
    }

    public void heal(int health) {
        healthRest = Math.min(totalHealth, healthRest + health);
    }
}
