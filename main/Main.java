package main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import entities.Characters.Soldier;
import entities.Enum.Status;
import exceptions.HealException;
import exceptions.MovementException;
import exceptions.SoldiersNumberException;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		char choice = 't';
		int counter = 0, health = 120;
		List<Soldier> allys = new ArrayList<>();
		List<Soldier> enemies = new ArrayList<>();
		
		for(int i = 0; i < 12; i++) {
			allys.add(new Soldier());
			enemies.add(new Soldier());
		}
		
		while(choice != 's') {
			try {
				if(counter % 2 == 0) {
					int enemy;
					for(Soldier x: allys) {
						health += x.getHealth();
					}
					if(health > 0) {
						System.out.print("Choose 'a' to attack, 'h' to real, 'm' to move or 's' to stop the game: ");
						choice = sc.next().charAt(0);
						if(choice == 'a') {
							System.out.print("Choose an enemy to attack (1-12): ");
							enemy = sc.nextInt();
							if(enemies.get(enemy-1).getStatus() == Status.DEAD) {
								System.out.println("Enemy is already dead, you have lost your turn;");
							} else {
								for (Soldier x : allys) {
									if(x.getStatus() == Status.ALIVE) {
										System.out.println("Describe your gun:");
										sc.nextLine();
										String gun = sc.nextLine();
										System.out.println(x.attack(gun, enemies.get(enemy-1), enemy));
										break;
									}
								}
							}
						} else if(choice == 'h') {
							System.out.println("Choose an ally to heal (1-12):");
							int ally = sc.nextInt();
							if(allys.get(ally-1).getStatus() == Status.ALIVE) {
								System.out.println("Select the way to heal (food/medkit):");
								String heal = sc.next();
								System.out.println(allys.get(ally-1).heal(heal));
							} else {
								System.out.println("The choosen ally is already dead, you have lost your turn;");
							}
						} else if(choice == 'm') {
							System.out.println("Choose an ally to move (1-10):");
							int ally = sc.nextInt();
							if(allys.get(ally-1).getStatus() == Status.ALIVE) {
								Random rand = new Random();
								Double move = rand.nextDouble(10.0) + 1.0;
								System.out.println(allys.get(ally-1).move(move));
							} else {
								System.out.println("The choosen ally is already dead, you have lost your turn;");
							}
						} else if(choice != 's') {
							System.out.println("Invalid option");
						}
					}
				} else {
					Random rand = new Random();
					int randomSoldier = rand.nextInt(10);
					int randomGun = rand.nextInt(4);
					String[] gun = {
							"knife",
							"pistol",
							"assaultrifle",
							"sniperrifle"
					};
					if(allys.get(randomSoldier).getMovement() > 10) {
						System.out.println("The enemy failed.");
					} else {
						System.out.println(Soldier.damege(allys.get(randomSoldier), gun[randomGun], randomSoldier));
						health -= randomGun + 1;
					}
				}
			}
			catch(SoldiersNumberException e) {
				System.out.println(e.getMessage());
			}
			catch(IndexOutOfBoundsException e) {
				System.out.println("Invalid soldier, lost your turn.");
			}
			catch(HealException e) {
				System.out.println(e.getMessage());
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid typing");
			}
			catch(MovementException e) {
				System.out.println(e.getMessage());
			}
			counter++;
		}
		
		System.out.println("Game over.");
		
		
		sc.close();
	}

}
