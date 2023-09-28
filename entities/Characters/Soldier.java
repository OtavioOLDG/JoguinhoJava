package entities.Characters;

import java.util.Random;

import entities.Enum.Status;
import exceptions.HealException;
import exceptions.MovementException;
import exceptions.SoldiersNumberException;

public class Soldier {
	private static Integer soldiers = 0;
	private String gun;
	private Integer health;
	private Double movement;
	private Status status;
	
	private static final Double Max = 10.0;
	private static final Double Min = 1.0;
	
	public Soldier() {
		this.gun = "";
		this.health = 10;
		this.movement = 0.0;
		this.status = Status.ALIVE;
		increaseSoldiers();
	}
	
	public Status getStatus() {
		return status;
	}

	public Double getMovement() {
		return movement;
	}
	
	private static void increaseSoldiers() {
		soldiers++;
	}

	public String getGun() {
		return gun;
	}
	
	private void setGun(String gun) {
		this.gun = gun;
	}

	public Integer getHealth() {
		return health;
	}
	
	private void setHealth() {
		this.health = 10;
	}
	
	public String heal(String heal) throws HealException {
		String food = "food";
		String medkit = "medkit";
		heal = heal.toLowerCase();
		if(heal.equals(medkit)) {
			if(this.health < 10 && this.health > 6) {
				setHealth();
				return "Heald ally.";
			} else if(this.health < 7 && this.health > 0) {
				this.health += 3;
				return "Heald ally";
			} else {
				throw new HealException("Could not heal, dead ally.");
			}
		} else if(heal.equals(food)) {
			if(this.health < 10 && this.health > 0) {
				this.health++;
				return "Heald ally" + "Health";
			} else {
				throw new HealException("Could not heal, dead ally.");
			}
		} else {
			throw new HealException("Invalid heal method.");
		}
	}

	public static Integer getSoldiers() {
		return soldiers;
	}
	
	public String attack(String gun, Soldier enemySoldier, Integer integ) throws SoldiersNumberException {
		if(soldiers >= 10 && this.health > 0) {
			setGun(gun);
			Random rand = new Random();
			Integer random;
			random = rand.nextInt(10) + 1;
			enemySoldier.health -= random;
			if(enemySoldier.health <= 0) {
				enemySoldier.status = Status.DEAD;
				return "Your soldier attacked with " + gun + " and kiled the " + integ + "º enemy soldier";
			}else {
				return "Your soldier attacked with " + gun + " and caused " + random + " of damage to the " + integ + "º enemy soldier";
			}
		} else {
			throw new SoldiersNumberException("Insuficient soldiers number");
		}
	}
	
	public String move(Double movement) throws MovementException {
		if(movement >= Min && movement <= Max) {
			this.movement += movement;
			return "Your character moved " + movement +" houses and it's now on the" + this.movement + " house.";
		}
		else {
			throw new MovementException("Could not move");
		}
	}
	
	public static String damege(Soldier soldier, String gun, Integer rand) {
		gun.toLowerCase();
		Integer dmg = 0;
		if(gun.equals("knife")) {
			soldier.health--;
			dmg = 1;
			gun = "knife";
		} else if (gun.equals("pistol")) {
			soldier.health-=2;
			dmg = 2;
			gun = "pistol";
		} else if (gun.equals("assaultrifle")) {
			soldier.health-=3;
			dmg = 3;
			gun = "assault rifle";
		} else if (gun.equals("sniperrifle")) {
			soldier.health-=4;
			dmg = 4;
			gun = "sniper rifle";
		}
		
		if(soldier.health <= 0) {
			soldier.status = Status.DEAD;
			return "Your soldier died.";
		}
		return "Your " + (rand+1) +  "º soldier was hurt by a " + gun + " and took " + dmg +" points of damage";
	}
	
}
