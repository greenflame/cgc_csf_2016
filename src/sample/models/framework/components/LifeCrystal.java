package sample.models.framework.components;

import sample.models.framework.Component;
import sample.models.framework.GameObject;

/**
 * Created by Alexander on 03/11/16.
 */
public class LifeCrystal extends Component {

    private int totalHealth;

    private int healthRest;

    public LifeCrystal(GameObject gameObject, int totalHealth) {
        super(gameObject);
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
