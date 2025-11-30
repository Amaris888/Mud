public class Spell {
    private String name;
    private int power;
    private int magicCost;
    private String level;

    public Spell(String name, int power, int magicCost, String level) {
        this.name = name;
        this.power = power;
        this.magicCost = magicCost;
        this.level = level;
    }

    // Getters
    public String getName() { return name; }
    public int getPower() { return power; }
    public int getMagicCost() { return magicCost; }
    public String getLevel() { return level; }
}