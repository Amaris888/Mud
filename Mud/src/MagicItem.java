public class MagicItem {
    private String name;
    private String description;
    private int healthBonus;
    private int magicBonus;

    public MagicItem(String name, String description, int bonus) {
        this.name = name;
        this.description = description;
        this.healthBonus = bonus / 2;
        this.magicBonus = bonus / 2;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getHealthBonus() { return healthBonus; }
    public int getMagicBonus() { return magicBonus; }
}