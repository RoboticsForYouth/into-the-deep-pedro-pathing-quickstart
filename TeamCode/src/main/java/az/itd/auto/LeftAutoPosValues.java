package az.itd.auto;

import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


public abstract class LeftAutoPosValues extends LinearOpMode {

    Vector2d drop0 =   new Vector2d(14.5, 11);
    public static final int      BASKET_HEADING_0 = -45;



    Vector2d collect1 = new Vector2d(14.25, 20.5);
    public static final int     COLLECT_HEADING_1 = -20;



    Vector2d collect1_1 =  new Vector2d(14.25, 25.5);



    Vector2d drop1 =         new Vector2d(13, 9.5);
    public static final int        BASKET_HEADING_1 = -45;



    Vector2d collect2 =    new Vector2d(12, 16);
    public static final int        COLLECT_HEADING_2 = 10;



    Vector2d collect2_1 =      new Vector2d(12, 18);



    Vector2d drop2 =        new Vector2d(10.5, 6);
    public static final int        BASKET_HEADING_2 = -45;



    Vector2d collect3 =     new Vector2d(13.75, 22.5);
    public static final int        COLLECT_HEADING_3 = 42;



    Vector2d collect3_1 = new Vector2d(13.75, 25.5);



    Vector2d drop3 =      new Vector2d(14 , 10);
    public static final int        BASKET_HEADING_3 = -40;



    Vector2d park1 =            new Vector2d(54, 0);
    public static final int          PARK_HEADING_1 = -90;

    Vector2d park2 =          new Vector2d(54, -7);



}
