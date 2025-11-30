public class NPC {
    private String name;
    private int health;
    private int maxHealth;
    private int magicPower;
    private String mainSpell;

    public NPC(String name, int health, int magicPower, String mainSpell) {
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.magicPower = magicPower;
        this.mainSpell = mainSpell;
    }

    // Getters
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getMagicPower() { return magicPower; }
    public String getMainSpell() { return mainSpell; }
}