import java.util.Random;

public class Main {
    public static int bossHealth = 1000;
    public static int bossDamage = 150;
    public static int bossDefaultDamage = bossDamage;
    public static String bossDefenceType;
    public static int[] heroesHealth = {280, 270, 250, 200, 550, 280, 260, 250};
    public static int[] heroesDamage = {10, 15, 20, 0, 3, 20, 20, 20};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int roundNumber;
    static Random random = new Random();

    public static void chooseBossDefence() {

        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefenceType = heroesAttackType[randomIndex];
    }

    public static void skillGolem() {
        int damageForGolem = (bossDamage / 5);
        int golemIndex = 0;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesAttackType[i].equals("Golem")) {
                golemIndex = i;
                break;
            }
        }
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[golemIndex] > 0) {
                heroesHealth[golemIndex] -= damageForGolem;

            } else {
                heroesHealth[golemIndex] = 0;

            }

        }
        System.out.println("Golem took a hit");

    }

    public static void main(String[] args) {
        printStatistics();

        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        printStatistics();
        skillMedic();
        skillLucky();
        skillBersek();
        skillThors();
        skillGolem();

    }


    public static void skillThors() {
        if (bossDamage == 0) {
            bossDamage = bossDefaultDamage;
        }

        boolean chanceThor = random.nextBoolean();
        int thorIndex = 0;
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesAttackType[i].equals("Thor")) {
                thorIndex = i;
            }
        }

        if (chanceThor && heroesHealth[thorIndex] > 0) {
            bossDamage = 0;
            System.out.println("Boss is bashed");
        }

    }

    public static void skillBersek() {
        int blockDamage = bossDamage / 5;
        int berserkIndex = 0;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesAttackType[i].equals("Berserk")) {
                berserkIndex = i;
                break;
            }
        }
        if (heroesHealth[berserkIndex] > 0) {
            bossHealth -= blockDamage;
            heroesHealth[berserkIndex] += blockDamage;
            System.out.println("Berserk has bloket damage");
        }
    }


    public static void skillLucky() {
        int luckyIndex = 0;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesAttackType[i].equals("Lucky")) {
                luckyIndex = i;
                break;
            }
        }
        boolean chance = random.nextBoolean();
        if (chance && heroesHealth[luckyIndex] > 0) {
            heroesHealth[luckyIndex] += bossDamage;
            System.out.println("Lucky gave a chanse");
        }

    }

    public static void skillMedic() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesAttackType[i].equals("Medic")) {
                continue;
            } else if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && heroesHealth[3] > 0) {
                heroesHealth[i] += 40;
                System.out.println(" Medic Healed " + heroesAttackType[i]);
                break;
            }

        }
    }


    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (bossDefenceType == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ----------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage
                + " defence: "
                + (bossDefenceType == null ? "No defence" : bossDefenceType));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: "
                    + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!");
        }
        return allHeroesDead;
    }


}