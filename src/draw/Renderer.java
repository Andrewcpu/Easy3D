package draw;

import constructs.Camera;
import constructs.Point3D;
import world.Cube;
import world.Face;
import world.Shape;
import world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Renderer {

    public static final int RENDER_DISTANCE = 3000;

    private static Renderer instance = null;

    public static Renderer getInstance() {
        return instance;
    }

    public Renderer(){
        instance = this;
    }

    public Shape selectedShape = null;

    public void render(Graphics g){


        g.setColor(World.getInstance().skyColor);
        g.fillRect(0,0,Canvas.DIMENSION,Canvas.DIMENSION);

        //double focalLength =  1;

        g.setColor(Color.BLUE);
        List<Face> faces = new ArrayList<>();
        for(Shape cube : World.getInstance().getCubes())
            faces.addAll(Arrays.asList(cube.getFaces()));

        Object[] arr = faces.toArray();
        Arrays.sort(arr);

        Polygon selectedPolygon = null;
        Face selectedFace = null;
        selectedShape = null;

        for(int i = faces.size() - 1; i > 0; i-- )
        {
            Face face = (Face)arr[i];
            if(!face.isVisible())
                continue;
            List<Point> polygonFigures = new ArrayList<>();
            for(Point3D point3D : face.getPoints()){
                Point3D base = point3D.performVisionConversion();
                if(base.isVisible()){
                    double[] point = base.to2DCoordinate();
                    if(point == null) continue;
                    Point p = new Point((int)point[0] ,(int)point[1]);
                    polygonFigures.add(p);
                }
            }

            int[] xpoints = new int[polygonFigures.size()];
            int[] ypoints = new int[polygonFigures.size()];
            for( int a = 0; a < polygonFigures.size(); a++ ){
                xpoints[a] = (int) (polygonFigures.get(a).x );
                ypoints[a] =  (int) (polygonFigures.get(a).y );
            }
            Polygon polygon = new Polygon(xpoints, ypoints, xpoints.length);


            double percentage =  1 - (face.getAveragePoint().distance(Camera.getInstance().getLocation()) / RENDER_DISTANCE * 2);
            int opacity = (int)(255 * percentage);

            if(face.getAveragePoint().distance(Camera.getInstance().getLocation()) > RENDER_DISTANCE) opacity = 0;

            g.setColor(getOpacityChange(face.getColor(), opacity));

            g.fillPolygon(polygon);

            if(polygon.contains(Canvas.DIMENSION / 2, Canvas.DIMENSION / 2)){
                selectedFace = face;
                selectedPolygon = polygon;
                selectedShape = face.getParent();
            }

        }
        if(selectedFace != null) {
            g.setColor(new Color(0, 0, 0, 90));
            g.fillPolygon(selectedPolygon);
        }
        g.setColor(Color.BLACK);
        g.drawLine(Canvas.DIMENSION / 2 - 10, Canvas.DIMENSION / 2, Canvas.DIMENSION / 2 + 10, Canvas.DIMENSION / 2);
        g.drawLine(Canvas.DIMENSION / 2, Canvas.DIMENSION / 2 - 10, Canvas.DIMENSION /2, Canvas.DIMENSION / 2 + 10);

        drawMinimap(g);
    }

    public void drawMinimap(Graphics g){
        int minSize = 25; // amount of pixels (1 block = 1 pixel) (player located at 25x25)


        int mapSize = 5;

        Point3D player = Camera.getInstance().getLocation();

        int xx = 0;
        int yy = 0;
        for(double x = player.getX() - ((minSize / 2) * World.getInstance().size); x<= player.getX() + ((minSize / 2) * World.getInstance().size); x+= World.getInstance().size)
        {
            for(double z = player.getZ() - ((minSize / 2) * World.getInstance().size); z<= player.getZ() + ((minSize / 2) * World.getInstance().size); z+= World.getInstance().size)
            {
                Point3D p = new Point3D(x, 0, z);
                Shape shape = World.getInstance().getHighestShapeAt(p.getX(),p.getZ());
                Color c;
                if(shape == null){
                    c = Color.CYAN;
                }
                else{
                    c = shape.getColor();
                }
                g.setColor(c);
                g.fillRect(xx,yy,mapSize,mapSize);
                yy+=mapSize;
            }
            xx+=mapSize;
            yy = 0;
        }

        g.setColor(Color.RED);
        g.fillOval(minSize / 2 * mapSize - 3, minSize / 2 * mapSize - 3, 6, 6);

        g.setColor(Color.BLACK);
        g.drawRect(0,0, minSize * mapSize, minSize * mapSize);

    }


    private Color getOpacityChange(Color c, int opacity){

        if(opacity > 255)
            opacity = 255;
        if(opacity < 0)
            opacity = 0;
        int avg = (int)((c.getRed() + c.getGreen() + c.getBlue()) / 3.0);

        double r = c.getRed() * (opacity / 255.0) + (avg * (255.0 - opacity) / 255.0);
        double g = c.getGreen() * (opacity / 255.0) + (avg * (255.0 - opacity) / 255.0);
        double b = c.getBlue() * (opacity / 255.0) + (avg * (255.0 - opacity) / 255.0);

        return new Color((int)r,(int)g,(int)b);
    }


}
