public class NBody {

    /** return a double corresponding to the radius of the universe in the file. */
    public static double readRadius(String filePath){
        In in = new In(filePath);
        int num = in.readInt();
        return in.readDouble();
    }

    /** return an array of Planets corresponding to the planets in the file. */
    public static Planet[] readPlanets(String filePath){

        In in = new In(filePath);
        int numberOfPlanet = in.readInt();
        double radius = in.readDouble();
        Planet[] ps = new Planet[numberOfPlanet];

        for(int i = 0; i < numberOfPlanet; i++){
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass  = in.readDouble();
            String imgFileName  = in.readString();
            ps[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
        }

        return ps;
    }

     public static void main(String[] args){

         /** Collecting all needed inputs. */
         double T = Double.parseDouble(args[0]);
         double dt = Double.parseDouble(args[1]);
         String filename = args[2];
         Planet[] planets = readPlanets(filename);
         double radius = readRadius(filename);

         /** all drawing takes place on the offscreen canvas */
         StdDraw.enableDoubleBuffering();
         /** set the scale of the universe */
         StdDraw.setScale(-1.5 * radius, 1.5 * radius);

         for(int t = 0; t < T; t += dt){
             double[] xForce = new double[planets.length];
             double[] yForce = new double[planets.length];
             for(int i = 0; i < planets.length; i++){
                 xForce[i] = planets[i].calcNetForceExertedByX(planets);
                 yForce[i] = planets[i].calcNetForceExertedByY(planets);
             }
             /** update the latest position of the planets. */
             for(int j = 0; j < planets.length; j++){
                 planets[j].update(dt, xForce[j], yForce[j]);
             }
             /** Drawing the background. */
             String imgToDraw = "images/starfield.jpg";
             StdDraw.picture(0, 0, imgToDraw);

             /** Drawing all of the planets. */
             for(Planet p : planets){
                 p.draw();
             }
             /** make drawing get copied from the offscreen canvas to the
              onscreen canvas */
             StdDraw.show();
             /** Pause the animation for 10 milliseconds. */
             StdDraw.pause(10);
         }

         StdOut.printf("%d\n", planets.length);
         StdOut.printf("%.2e\n", radius);
         for (int i = 0; i < planets.length; i++){
             StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                     planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                     planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
         }
     }
}
