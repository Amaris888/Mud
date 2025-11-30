import java.util.*;

public class HarryPotterMUD {
    private Player player;
    private Scanner scanner;
    private Random random;

    public HarryPotterMUD() {
        scanner = new Scanner(System.in);
        random = new Random();
    }

    public void startGame() {
        System.out.println("=== 欢迎来到哈利波特MUD ===");
        initializePlayer();
        showMainMenu();
    }

    private void initializePlayer() {
        System.out.println("\n请选择你的学院:");
        System.out.println("1. 格兰芬多 (攻击力+10%)");
        System.out.println("2. 斯莱特林 (魔力值+10%)");
        System.out.println("3. 拉文克劳 (学习速度+20%)");
        System.out.println("4. 赫奇帕奇 (生命值+10%)");

        int choice = getIntInput(1, 4);
        String house = "";
        double bonus = 1.0;

        switch(choice) {
            case 1:
                house = "格兰芬多";
                bonus = 1.1;
                break;
            case 2:
                house = "斯莱特林";
                bonus = 1.1;
                break;
            case 3:
                house = "拉文克劳";
                bonus = 1.2;
                break;
            case 4:
                house = "赫奇帕奇";
                bonus = 1.1;
                break;
        }

        player = new Player(house, bonus);
        System.out.println("\n欢迎来到 " + house + " 学院！");
        System.out.println("你的初始属性:");
        player.displayStatus();

        // 开局第一节魔法课
        firstMagicClass();
    }

    private void firstMagicClass() {
        System.out.println("\n=== 第一节魔法课 ===");
        System.out.println("弗立维教授: 欢迎新同学！今天我们将学习基础魔咒。");
        System.out.println("你学会了: 除你武器(Expelliarmus), 飞来咒(Accio)");

        player.addSpell(new Spell("除你武器", 20, 10, "初级"));
        player.addSpell(new Spell("飞来咒", 15, 8, "初级"));

        System.out.println("魔法课结束，获得 50 经验值！");
        player.gainExperience(50);
    }

    private void showMainMenu() {
        while(true) {
            System.out.println("\n=== 主菜单 ===");
            System.out.println("1. 前往魔法课教室");
            System.out.println("2. 前往魔药课教室");
            System.out.println("3. 前往决斗室");
            System.out.println("4. 前往阅览室");
            System.out.println("5. 前往小卖部");
            System.out.println("6. 前往银行");
            System.out.println("7. 查看状态");
            System.out.println("8. 退出游戏");

            int choice = getIntInput(1, 8);

            switch(choice) {
                case 1:
                    magicClassroom();
                    break;
                case 2:
                    potionClassroom();
                    break;
                case 3:
                    duelRoom();
                    break;
                case 4:
                    library();
                    break;
                case 5:
                    shop();
                    break;
                case 6:
                    bank();
                    break;
                case 7:
                    player.displayStatus();
                    break;
                case 8:
                    System.out.println("感谢游玩哈利波特MUD！");
                    return;
            }

            // 检查升级
            if(player.checkLevelUp()) {
                System.out.println("\n★ 恭喜升级！当前等级: " + player.getLevel());
                player.displayStatus();
            }
        }
    }

    private void magicClassroom() {
        System.out.println("\n=== 魔法课教室 ===");
        System.out.println("弗立维教授正在授课...");

        int experienceGained = 30 + random.nextInt(20);
        System.out.println("你认真听课，获得 " + experienceGained + " 经验值");
        player.gainExperience(experienceGained);

        // 学习新魔咒的机会
        if(random.nextDouble() < 0.3) {
            learnNewSpell("初级");
        }

        // 突发事件
        if(random.nextDouble() < 0.2) {
            System.out.println("突发事件: 皮皮鬼捣乱！你成功阻止了他。");
            player.gainExperience(25);
            player.gainSkillPoints(1);
        }
    }

    private void potionClassroom() {
        System.out.println("\n=== 魔药课教室 ===");
        System.out.println("斯内普教授正在指导制作魔药...");

        // 坩埚爆炸事件
        if(random.nextDouble() < 0.4) {
            System.out.println("★ 突发事件: 你的坩埚爆炸了！");
            if(random.nextDouble() < 0.6) {
                System.out.println("你成功处理了危机，获得奖励！");
                player.gainExperience(40);

                if(random.nextDouble() < 0.3) {
                    MagicItem item = new MagicItem("防护戒指", "增加防御力", 50);
                    player.addMagicItem(item);
                    System.out.println("获得法器: " + item.getName());
                }
            } else {
                System.out.println("处理失败，失去一些生命值");
                player.takeDamage(20);
            }
        } else {
            System.out.println("你成功制作了魔药，获得 35 经验值");
            player.gainExperience(35);
        }
    }

    private void duelRoom() {
        System.out.println("\n=== 决斗室 ===");
        System.out.println("选择对手:");
        System.out.println("1. 马尔福 (简单)");
        System.out.println("2. 黑化学生 (中等)");
        System.out.println("3. 黑巫师 (困难)");

        int choice = getIntInput(1, 3);
        NPC opponent = null;

        switch(choice) {
            case 1:
                opponent = new NPC("马尔福", 300, 250, "除你武器");
                break;
            case 2:
                opponent = new NPC("黑化学生", 600, 500, "黑魔法");
                break;
            case 3:
                opponent = new NPC("黑巫师", 1500, 1500, "黑魔法");
                break;
        }

        boolean result = startDuel(opponent);

        if(result) {
            System.out.println("★ 决斗胜利！");
            int exp = opponent.getMaxHealth() / 10;
            player.gainExperience(exp);
            player.gainSkillPoints(1);
            System.out.println("获得 " + exp + " 经验值和 1 技能点");
        } else {
            System.out.println("决斗失败...");
            if(random.nextDouble() < 0.5 && !player.getMagicItems().isEmpty()) {
                MagicItem lostItem = player.getMagicItems().get(random.nextInt(player.getMagicItems().size()));
                player.removeMagicItem(lostItem);
                System.out.println("失去了: " + lostItem.getName());
            }
        }
    }

    private boolean startDuel(NPC opponent) {
        System.out.println("\n开始与 " + opponent.getName() + " 决斗！");

        int playerMagicPower = player.getMagicPower();
        int opponentMagicPower = opponent.getMagicPower();

        System.out.println("你的魔力值: " + playerMagicPower);
        System.out.println(opponent.getName() + "的魔力值: " + opponentMagicPower);

        if(playerMagicPower > opponentMagicPower) {
            return true;
        } else if(playerMagicPower < opponentMagicPower) {
            return false;
        } else {
            return random.nextBoolean();
        }
    }

    private void library() {
        System.out.println("\n=== 阅览室 ===");
        System.out.println("你在安静地阅读魔法书籍...");

        player.increaseMagicPower(5 + random.nextInt(10));
        System.out.println("魔力值提升了！");

        // 学习高级魔咒
        if(random.nextDouble() < 0.25) {
            learnNewSpell("高级");
        }

        // 小概率习得黑魔法
        if(random.nextDouble() < 0.1) {
            System.out.println("★ 你在禁书区发现了黑魔法！");
            if(random.nextInt(10) == 0) { // 1/10概率
                Spell darkSpell = new Spell("钻心剜骨", 80, 40, "黑魔法");
                player.addSpell(darkSpell);
                System.out.println("习得黑魔法: " + darkSpell.getName());
            }
        }

        // 刷新黑巫师战斗
        if(random.nextDouble() < 0.15) {
            System.out.println("★ 黑巫师出现了！");
            NPC darkWizard = new NPC("黑巫师", 1500, 1500, "黑魔法");
            boolean result = startDuel(darkWizard);
            if(result) {
                System.out.println("成功击退黑巫师！");
                player.gainExperience(100);
                if(random.nextDouble() < 0.4) {
                    MagicItem item = new MagicItem("魔法书", "增加魔力", 30);
                    player.addMagicItem(item);
                    System.out.println("获得法器: " + item.getName());
                }
            } else {
                System.out.println("被黑巫师打败了...");
                player.takeDamage(50);
            }
        }
    }

    private void shop() {
        System.out.println("\n=== 小卖部 ===");
        // 简化的商店系统
        System.out.println("商品列表:");
        System.out.println("1. 生命药水 - 恢复50生命值 (50金币)");
        System.out.println("2. 魔力药水 - 恢复50魔力值 (50金币)");
        System.out.println("3. 伤害药水 - 下次攻击+50伤害 (80金币)");

        // 这里可以扩展完整的商店逻辑
        System.out.println("(商店功能待完善)");
    }

    private void bank() {
        System.out.println("\n=== 古灵阁银行 ===");
        System.out.println("欢迎来到古灵阁！");
        System.out.println("当前金币: " + player.getGold());
        System.out.println("1. 存款");
        System.out.println("2. 取款");
        System.out.println("3. 返回");

        int choice = getIntInput(1, 3);
        // 银行功能可以进一步扩展
        System.out.println("(银行功能待完善)");
    }

    private void learnNewSpell(String level) {
        String[] primarySpells = {"荧光闪烁", "修复如初", "清理一新"};
        String[] advancedSpells = {"盔甲护身", "呼神护卫", "幻影移形"};

        String spellName;
        if(level.equals("初级")) {
            spellName = primarySpells[random.nextInt(primarySpells.length)];
        } else {
            spellName = advancedSpells[random.nextInt(advancedSpells.length)];
        }

        int power = 25 + random.nextInt(25);
        int cost = 12 + random.nextInt(10);

        Spell newSpell = new Spell(spellName, power, cost, level);
        player.addSpell(newSpell);
        System.out.println("★ 学会了新魔咒: " + spellName + " (" + level + ")");
    }

    private int getIntInput(int min, int max) {
        while(true) {
            try {
                System.out.print("请选择 (" + min + "-" + max + "): ");
                int input = scanner.nextInt();
                if(input >= min && input <= max) {
                    return input;
                }
            } catch (InputMismatchException e) {
                scanner.next(); // 清除无效输入
            }
            System.out.println("无效输入，请重新选择。");
        }
    }

    public static void main(String[] args) {
        HarryPotterMUD game = new HarryPotterMUD();
        game.startGame();
    }
}