public class Rpg {
    public static class InsufficientPointsException extends RuntimeException {
        public InsufficientPointsException() {
        }

        public InsufficientPointsException(String message) {
            super(message);
        }

        public InsufficientPointsException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class NegativeHealthException extends RuntimeException {
        public NegativeHealthException() {
        }

        public NegativeHealthException(String message) {
            super(message);
        }

        public NegativeHealthException(String message, Throwable cause) {
            super(message, cause);
        }

        public NegativeHealthException(Throwable cause) {
            super(cause);
        }
    }



    public interface Damageable {
        void takeDamage(float damage);

        float getHealth();
    }

    public abstract static class Character implements Damageable {
        protected String name;
        protected int level;

        protected float health;
        protected float mana;
        protected float stamina;

        public Character(String name, int level) {
            this.name = name;
            this.level = level;

            health = 100f;
            mana = 50f;
            stamina = 70f;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }

        public float getHealth() {
            return health;
        }

        public float getMana() {
            return mana;
        }

        public float getStamina() {
            return stamina;
        }

        public void levelUp() {
            level++;
        }

        public abstract void attack(Damageable damageable);

        public void takeDamage(float damage) {
            if (damage < 0) {
                throw new NegativeHealthException("Cannot take negative damage");
            }

            health -= damage;
        }
    }


    public static class Sorcerer extends Character {
        protected float intelligence;

        public Sorcerer(String name, int level) {
            super(name, level);
            intelligence = 10;
        }

        @Override
        public void attack(Damageable damageable) {
            if (mana < 10f) {
                throw new InsufficientPointsException("Not enough mana to attack");
            }
            damageable.takeDamage(level * intelligence / 4f);
            mana -= 10f;
        }
    }

    public static class Warrior extends Character {
        protected float strength;

        public Warrior(String name, int level) {
            super(name, level);
            strength = 10;
        }

        @Override
        public void attack(Damageable damageable) {
            if (stamina < 20f) {
                throw new InsufficientPointsException("Not enough stamina");
            }
            damageable.takeDamage(level * strength / 2);
            stamina -= 20f;
        }
    }


    public static void main(String[] args) {
        Character pino = new Warrior("Pino", 10);
        Character osvaldo = new Sorcerer("Osvaldo", 20);

        Damageable box = new Damageable() {
            float health = 10f;

            @Override
            public void takeDamage(float damage) {
                if (damage < 0) {
                    throw new NegativeHealthException();
                }
                health -= damage;
            }

            @Override
            public float getHealth() {
                return health;
            }
        };

        pino.attack(box);
        System.out.println("Box health: " + box.getHealth());
        pino.attack(osvaldo);
        System.out.println("Osvaldo health: " + osvaldo.getHealth());
    }
}
