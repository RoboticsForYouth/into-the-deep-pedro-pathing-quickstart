package az.itd.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.az.itd.tools.DoubleArm;

@TeleOp
public class EncoderTest extends LinearOpMode {

    static final boolean FIELD_CENTRIC = false;
    DoubleArm arm = null;


    @Override
    public void runOpMode() throws InterruptedException {
        // constructor takes in frontLeft, frontRight, backLeft, backRight motors
        // IN THAT ORDER

        arm = new DoubleArm(this);


        waitForStart();

//        arm.runWithoutEncoder();


        while (!isStopRequested()) {

            telemetry.addLine(arm.printCurrentPos());
            telemetry.update();

        }
    }
}



