import java.math.*;

public class Planet {

    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    private final static double gravity = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV,
                  double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        return Math.sqrt((xxPos - p.xxPos) * (xxPos - p.xxPos) + (yyPos -
                        p.yyPos) * (yyPos - p.yyPos));
    }

    public double calcForceExertedBy(Planet p){
        double r = this.calcDistance(p);
        return (gravity * mass * p.mass) / (r * r);
    }

    public double calcForceExertedByX(Planet p){
        double dx = p.xxPos - xxPos;
        double r = this.calcDistance(p);
        return (this.calcForceExertedBy(p) * dx) / r;
    }

    public double calcForceExertedByY(Planet p){
        double dy = p.yyPos - yyPos;
        double r = this.calcDistance(p);
        return (this.calcForceExertedBy(p) * dy) / r;
    }

    public double calcNetForceExertedByX(Planet[] ps){
        double netForceX = 0.0;
        for(Planet p : ps){
            if(!this.equals(p)){
                netForceX += this.calcForceExertedByX(p);
            } else{
                continue;
            }
        }
        return netForceX;
    }

    public double calcNetForceExertedByY(Planet[] ps){
        double netForceY = 0.0;
        for(Planet p : ps){
            if(!this.equals(p)){
                netForceY += this.calcForceExertedByY(p);
            } else{
                continue;
            }
        }
        return netForceY;
    }

    public void update(double dt, double fx, double fy){
        xxVel = xxVel + dt * (fx / mass);
        yyVel = yyVel + dt * (fy / mass);
        xxPos = xxPos + dt * xxVel;
        yyPos = yyPos + dt * yyVel;
    }

    public void draw(){
        String imgToDraw = "images/" + imgFileName;
        StdDraw.picture(xxPos, yyPos, imgToDraw);
    }
}
