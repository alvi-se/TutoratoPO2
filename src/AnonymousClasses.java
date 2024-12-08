public class AnonymousClasses {

    interface Shape {
        float getArea();
    }

    public static class Rectangle implements Shape {
        public float base;
        public float height;

        public Rectangle(float base, float height) {
            this.base = base;
            this.height = height;
        }

        @Override
        public float getArea() {
            return base * height;
        }
    }


    public static class Square extends Rectangle {
        public float edge;

        public Square(float edge) {
            super(edge, edge);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Square(2f).getArea());
        Shape s = new Square(10);
        Rectangle r = new Rectangle(2, 4);

        Shape shape = new Shape() {
            // This variable can't be accessed from outside
            int i = 10;

            public float getArea() {
                return i * 10;
            }
        };


        // All of these return true
        System.out.println(shape instanceof Shape);
        System.out.println(r instanceof Shape);
        System.out.println(s instanceof Shape);

        // This returns false
        System.out.println(r instanceof Square);
        // This returns true
        System.out.println(s instanceof Rectangle);

        System.out.println(shape.getClass());
        System.out.println(r.getClass());
        System.out.println(s.getClass());

        if (s.getClass().equals(Square.class)) {
            System.out.println("s is a Square");
        }

        if (s.getClass() == Rectangle.class) {
            System.out.println("s is a Rectangle");
        } else {
            System.out.println("s is not a Rectangle");
        }
    }
}
