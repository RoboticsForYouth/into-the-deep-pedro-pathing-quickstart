package az.itd.auto;

import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class RightAutoPosValuesNoPenalty extends LinearOpMode {



    Vector2d specimenDrop1 = new Vector2d(25.2,19);

    Vector2d spikeMark1 =    new Vector2d(17, -22);
    public static final int   SPIKE_MARK_HEADING_1 = -35;



    Vector2d spikeMark1Strafe = new Vector2d(10, -22);
    public static final int      HOCKEY_HEADING_1 = -65;


    Vector2d spikeMark2 = new     Vector2d(21, -28);
    public static final int    SPIKE_MARK_HEADING_2 = -40;


    Vector2d spikeMark2Strafe = new Vector2d(10, -28);
    public static final int       HOCKEY_HEADING_2 = -110;


    Vector2d spikeMark3 =   new Vector2d(23, -34.8);
    public static final int    SPIKE_MARK_HEADING_3 = -58;

    public static final int       HOCKEY_HEADING_3 = -152;

    Vector2d obsZone1 =        new Vector2d(7, -32.75);
    public static final int      OBS_ZONE_HEADING_1 = 180;

    public static final double       OBS_ZONE_1_XXXX = 3.25;

    Vector2d specimenDrop2 = new Vector2d(26.7, 15.5);

    Vector2d obsZone2 =        new Vector2d(3.25, -39);

    Vector2d specimenDrop3 =  new Vector2d(25.9, 12);

    Vector2d obsZone3 =        new Vector2d(2.75, -39.5);

    Vector2d specimenDrop4 =  new Vector2d(26.4, 8.5);

    Vector2d obsZone4 =        new Vector2d(3.25, -39);

    Vector2d specimenDrop5 =  new Vector2d(26, 5);

    Vector2d park =            new Vector2d(2, -38);







}
