package pedroPathing.az.tools;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Elbow", group = "sample")
public class Elbow extends LinearOpMode {

    public static final double INCREMENT = .01;
    private static final double ELBOW_CORRECTION = .0;
    private ElapsedTime runtime = new ElapsedTime();

    private Servo elbow1;
    private Servo elbow2;
    private LinearOpMode linearOpMode;

    double elbow1Factor=0.0;


    public Elbow() {
        super();
    }


    public void raiseElbow() {
        elbow1.setPosition(elbow1.getPosition() + INCREMENT);
        elbow2.setPosition(elbow2.getPosition() + INCREMENT);
    }

    public void lowerElbow() {
        elbow1.setPosition(elbow1.getPosition() - INCREMENT);
        elbow2.setPosition(elbow2.getPosition() - INCREMENT);
    }

    public void setElbowPos(ELBOW_POS pickup2) {
        setElbowPos(pickup2.pos);
    }

    public double getPos() {
        return elbow1.getPosition();
    }


    public enum ELBOW_POS {
        PICKUP(0.2), //0.09
        DROP(0.7), //0.2
        HANG_POS(0.5), ///0.2
        SPECIMEN_PICKUP(0.3), //0.25
        MOVE(0.2), //0.35
        TELEOP_SPECIMEN_DROP(.46),

        RESET(0.8), //0.75


        //--------------------------------------------------------------------------------------------------------------------
        //LEFT AUTO!!!
        LEFT_AUTO_RESET(0.7),
        LEFT_AUTO_DROP(0.6), //0.49
        LEFT_AUTO_PICKUP(0.09), //0.2 //0.17
        LEFT_AUTO_DROP_INTERMEDIATE(0.3),
        //--------------------------------------------------------------------------------------------------------------------

        //--------------------------------------------------------------------------------------------------------------------
        //RIGHT AUTO!!!
        RIGHT_AUTO_SPECIMEN_PICKUP(0.42), //0.25
        RIGHT_AUTO_SPECIMEN_DROP(0.47), //0.7
        RIGHT_AUTO_DOWN(0.6)
        //--------------------------------------------------------------------------------------------------------------------

        ;

        public double getPos() {
            return pos;
        }

        private final double pos;

        ELBOW_POS(double pos) {
            this.pos = pos;
        }
    }


    public Elbow(LinearOpMode linearOpMode) {
        this.linearOpMode = linearOpMode;
        setup();
    }

    @NonNull
    @Override
    public String toString() {
        return "EnhancedClaw{" +
                ", elbow1=" + elbow1.getPosition() +
                ", elbow2=" + elbow2.getPosition() +
                '}';
    }

    public void setElbowPos(double pos) {
        elbow1.setPosition(pos + ELBOW_CORRECTION);
        elbow2.setPosition(pos + ELBOW_CORRECTION);
    }

    private void setup() {

        elbow1 = linearOpMode.hardwareMap.get(Servo.class, "elbow1");
        elbow2 = linearOpMode.hardwareMap.get(Servo.class, "elbow2");
        elbow1.setDirection(Servo.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        setElbowPos(0);
    }

    private void setPos( ELBOW_POS pickup2) {
        setElbowPos(pickup2.pos);
    }

    public void moveUp() {
        double newPos = elbow1.getPosition() + INCREMENT;
        setElbowPos(newPos);
    }

    public void moveDown() {
        double newPos = elbow1.getPosition() - INCREMENT;
        setElbowPos(newPos);
    }

    public void samplePickUp90() {
        setPos(ELBOW_POS.PICKUP);
    }

    public void reset() {

        setElbowPos(ELBOW_POS.RESET.getPos());

    }

    public void specimenAutoReset() {
        setElbowPos(ELBOW_POS.RESET.getPos());
    }


    public void samplePickup() {
        setPos(ELBOW_POS.PICKUP);
    }

    public void move() {
        setPos(ELBOW_POS.MOVE);
    }

    public void sampleDrop() {

        setElbowPos(ELBOW_POS.DROP.getPos());

    }

    public void gripperHang() {

        setElbowPos(ELBOW_POS.HANG_POS.getPos());

    }


    public void specimenPickUp() {
        setPos(ELBOW_POS.SPECIMEN_PICKUP);
    }

    public void teleOpSpecimenDropPos() {

        setElbowPos(ELBOW_POS.TELEOP_SPECIMEN_DROP.getPos());
    }


    //--------------------------------------------------------------------------------------------------------------------
    //LEFT AUTO!!!
    public void leftAutoReset() {
        setElbowPos(ELBOW_POS.LEFT_AUTO_RESET.getPos());

    }

    public void leftAutoSampleDrop() {
        setElbowPos(ELBOW_POS.LEFT_AUTO_DROP.getPos());

    }

    public void leftAutoPickup() {
        setPos(ELBOW_POS.LEFT_AUTO_PICKUP);
    }

    public void leftAutoIntermediate() {
        setPos( ELBOW_POS.LEFT_AUTO_DROP_INTERMEDIATE);
    }

    //--------------------------------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------------------------------
    //RIGHT AUTO!!!
    public void rightAutoSpecimenDropPos() {

        setElbowPos(ELBOW_POS.RIGHT_AUTO_SPECIMEN_DROP.getPos());
    }


    public void rightAutoSpecimenPickUp() {
        setPos(ELBOW_POS.RIGHT_AUTO_SPECIMEN_PICKUP);
    }

    public void rightAutoDown() {
        setElbowPos(ELBOW_POS.RIGHT_AUTO_DOWN.getPos());
    }
    //--------------------------------------------------------------------------------------------------------------------

    // Abstract class for timed runnable tasks
    abstract class TimedRunnable implements Runnable {
        public double whenToRun; // Time when to run (in seconds)
    }

    @Override
    public void runOpMode() {
        this.linearOpMode = this;
        // Initialize hardware
        setup();


        waitForStart();

        //start at 0
        setElbowPos(0);
/*
        telemetry.addData("Elbow 1 Pos", elbow1.getPosition());
        telemetry.addData("Elbow 2 Pos", elbow2.getPosition());
        telemetry.update();

        //increment up
        moveUp();
        telemetry.addData("Elbow 1 Pos", elbow1.getPosition());
        telemetry.addData("Elbow 2 Pos", elbow2.getPosition());
        telemetry.update();
        sleep(1000);

        moveUp();
        telemetry.addData("Elbow 1 Pos", elbow1.getPosition());
        telemetry.addData("Elbow 2 Pos", elbow2.getPosition());
        telemetry.update();
        sleep(1000);

        moveUp();
        telemetry.addData("Elbow 1 Pos", elbow1.getPosition());
        telemetry.addData("Elbow 2 Pos", elbow2.getPosition());
        telemetry.update();
        sleep(1000);

        moveUp();
        telemetry.addData("Elbow 1 Pos", elbow1.getPosition());
        telemetry.addData("Elbow 2 Pos", elbow2.getPosition());
        telemetry.update();
        sleep(1000);

        //increment down
        moveDown();
        telemetry.addData("Elbow 1 Pos", elbow1.getPosition());
        telemetry.addData("Elbow 2 Pos", elbow2.getPosition());
        telemetry.update();
        sleep(1000);

        moveDown();
        telemetry.addData("Elbow 1 Pos", elbow1.getPosition());
        telemetry.addData("Elbow 2 Pos", elbow2.getPosition());
        telemetry.update();
        sleep(1000);

        moveDown();
        telemetry.addData("Elbow 1 Pos", elbow1.getPosition());
        telemetry.addData("Elbow 2 Pos", elbow2.getPosition());
        telemetry.update();
        sleep(1000);

        moveDown();
        */
        telemetry.addData("Elbow 1 Pos", elbow1.getPosition());
        telemetry.addData("Elbow 2 Pos", elbow2.getPosition());
        telemetry.update();
        sleep(1000);

        setElbowPos(1.0);
        sleep(1000);
        setElbowPos(0.75);
        sleep(1000);
        setElbowPos(0.5);
        sleep(1000);
        setElbowPos(0.25);
        sleep(1000);
        setElbowPos(0);
        sleep(5000);
        }
    }

