import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Shapes3D {

    interface Surface {
        double getPerimeter();
        double getArea();
    }
    
    
    public static class Circle implements Surface {
        private final double radius;
        
        public Circle(double radius) {
            this.radius = radius;
        }

        public double getRadius() {
            return radius;
        }

        @Override
        public double getPerimeter() {
            return 2 * Math.PI * radius;
        }

        @Override
        public double getArea() {
            return Math.PI * radius * radius;
        }
    }
    
    public static class Edge {
        private final double length;
        
        public Edge(double length) {
            this.length = length;
        }

        public double getLength() {
            return length;
        }
    }
    
    public interface Polygon extends Surface, Iterable<Edge> {
        @Override
        default double getPerimeter() {
            double sum = 0.;

            // Iterator<Edge> it = iterator();
            // while (it.hasNext()) {
            //     sum += it.next().getLength();
            // }
            for (Edge e : this) {
                sum += e.getLength();
            }

            return sum;

        }
    }

    public static class Rectangle implements Polygon {
        public double base;
        public double height;

        public Rectangle(double base, double height) {
            this.base = base;
            this.height = height;
        }

        @Override
        public double getArea() {
            return base * height;
        }


        @Override
        public Iterator<Edge> iterator() {

            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(base));
            edges.add(new Edge(height));
            edges.add(new Edge(base));
            edges.add(new Edge(height));


            return new Iterator<Edge>() {

                @Override
                public boolean hasNext() {
                    return !edges.isEmpty();
                }

                @Override
                public Edge next() {
                    return edges.removeFirst();
                }
            };
        }
    }

    public static class Square extends Rectangle {
        private double edge;

        public Square(double edge) {
            super(edge, edge);

        }

        public double getEdge() {
            return edge;
        }
    }

    public interface Polyhedron<P extends Polygon> extends Iterable<Polygon> {

        double volume();

        default double outerArea() {
            double area = 0.;

            for (Polygon p : this) {
                area += p.getArea();
            }

            return area;
        }
    }


    public static class Prism<P extends Polygon> implements Polyhedron<Polygon> {
        private P base;
        private double height;

        public Prism(P base, double height) {
            this.base = base;
            this.height = height;
        }

        public P getBase() {
            return base;
        }

        public double getHeight() {
            return height;
        }

        @Override
        public double volume() {
            return base.getArea() * height;
        }

        @Override
        public Iterator<Polygon> iterator() {
            return new PrismIterator();
        }

        public class PrismIterator implements Iterator<Polygon> {
            private List<Polygon> polygons;

            public PrismIterator() {
                polygons = new ArrayList<>();

                for (Edge e : base) {
                    polygons.add(new Rectangle(e.getLength(), height));
                }

                polygons.add(base);
                polygons.add(base);

            }

            @Override
            public boolean hasNext() {
                return !polygons.isEmpty();
            }

            @Override
            public Polygon next() {
                return polygons.removeFirst();
            }
        }
    }


    public static void main(String[] args) {
        Rectangle rect = new Rectangle(3, 4);
        System.out.println(rect.getPerimeter());

        Prism<Rectangle> parallelepiped = new Prism<>(
                new Square(10),
                10
        );

        System.out.println(parallelepiped.outerArea());
    }
}
