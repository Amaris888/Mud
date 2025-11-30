import java.util.*;

public class Player {
    private String house;
    private int level;
    private int experience;
    private int maxExperience;
    private int health;
    private int maxHealth;
    private int magicPower;
    private int maxMagicPower;
    private int gold;
    private int skillPoints;
    private double houseBonus;

    private List<Spell> spells;
    private List<MagicItem> magicItems;

    public Player(String house, double houseBonus) {
        this.house = house;
        this.houseBonus = houseBonus;
        this.level = 1;
        this.experience = 0;
        this.maxExperience = 100;
        this.maxHealth = (int)(100 * (house.equals("赫奇帕奇") ? houseBonus : 1.0));
        this.health = this.maxHealth;
        this.maxMagicPower = (int)(100 * (house.equals("斯莱特林") ? houseBonus : 1.0));
        this.magicPower = this.maxMagicPower;
        this.gold = 100;
        this.skillPoints = 0;
        this.spells = new ArrayList<>();
        this.magicItems = new ArrayList<>();
    }

    public void gainExperience(int amount) {
        this.experience += (int)(amount * (house.equals("拉文克劳") ? houseBonus : 1.0));
    }

    public boolean checkLevelUp() {
        if(experience >= maxExperience) {
            levelUp();
            return true;
        }
        return false;
    }

    private void levelUp() {
        level++;
        experience -= maxExperience;
        maxExperience = (int)(maxExperience * 2.0);

        // 属性翻倍
        int oldMaxHealth = maxHealth;
        int oldMaxMagic = maxMagicPower;

        maxHealth *= 2;
        maxMagicPower *= 2;

        // 恢复生命和魔力
        health = maxHealth;
        magicPower = maxMagicPower;

        System.out.println("★ 升级！生命值: " + oldMaxHealth + " → " + maxHealth);
        System.out.println("★ 升级！魔力值: " + oldMaxMagic + " → " + maxMagicPower);
    }

    public void gainSkillPoints(int points) {
        this.skillPoints += points;
    }

    public void addSpell(Spell spell) {
        spells.add(spell);
    }

    public void addMagicItem(MagicItem item) {
        magicItems.add(item);
        // 应用物品加成
        this.maxHealth += item.getHealthBonus();
        this.maxMagicPower += item.getMagicBonus();
        this.health += item.getHealthBonus();
        this.magicPower += item.getMagicBonus();
    }

    public void removeMagicItem(MagicItem item) {
        if(magicItems.remove(item)) {
            // 移除物品加成
            this.maxHealth -= item.getHealthBonus();
            this.maxMagicPower -= item.getMagicBonus();
            // 确保当前值不超过最大值
            this.health = Math.min(this.health, this.maxHealth);
            this.magicPower = Math.min(this.magicPower, this.maxMagicPower);
        }
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if(this.health < 0) this.health = 0;
    }

    public void increaseMagicPower(int amount) {
        this.magicPower = Math.min(this.magicPower + amount, this.maxMagicPower);
    }

    public int getMagicPower() {
        int basePower = this.magicPower;
        int itemBonus = magicItems.stream().mapToInt(MagicItem::getMagicBonus).sum();
        return basePower + itemBonus;
    }

    public void displayStatus() {
        System.out.println("\n=== 角色状态 ===");
        System.out.println("学院: " + house);
        System.out.println("等级: " + level);
        System.out.println("经验: " + experience + "/" + maxExperience);
        System.out.println("生命值: " + health + "/" + maxHealth);
        System.out.println("魔力值: " + magicPower + "/" + maxMagicPower);
        System.out.println("金币: " + gold);
        System.out.println("技能点: " + skillPoints);

        System.out.println("\n掌握的魔咒:");
        for(Spell spell : spells) {
            System.out.println("  - " + spell.getName() + " (" + spell.getLevel() + ")");
        }

        System.out.println("\n法器:");
        for(MagicItem item : magicItems) {
            System.out.println("  - " + item.getName() + " (+" + item.getHealthBonus() + "生命 +" + item.getMagicBonus() + "魔力)");
        }
    }

    // Getters
    public List<MagicItem> getMagicItems() { return magicItems; }
    public int getGold() { return gold; }
    public int getLevel() { return level; }
}