package pedroPathing.az.tools;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp (name="EnhancedClaw", group = "sample")
public class EnhancedClaw extends LinearOpMode {
    public static final String UNKNOWN = "Unknown";
    public static final String YELLOW = "Yellow";
    public static final String BLUE = "Blue";
    public static final String RED = "Red";
    public static final double INCREMENT = .01;
    private static final double ELBOW_CORRECTION = .07;
    private static final double WRIST_CORRECTION = 0;
    private ElapsedTime runtime = new ElapsedTime();
    private CRServo roller;
    private Servo wrist;
    //    private ColorSensor sampleSensor;
    private Servo elbow;
    private LinearOpMode linearOpMode;


    public EnhancedClaw() {
        super();
    }


    /**
     * Testing related methods
     * =================================================
     */
    /**
     * turnWristLeft
     */
    public void turnWristRight() {
        setWristPos(wrist.getPosition() + INCREMENT);
    }

    public void raiseElbow() {
        elbow.setPosition(elbow.getPosition() + INCREMENT);
    }

    public void lowerElbow() {
        elbow.setPosition(elbow.getPosition() - INCREMENT);
    }

    public void turnWristLeft() {
        setWristPos(wrist.getPosition() - INCREMENT);
    }

    public void rollerCollect() {
        roller.setPower(RollerPower.PICKUP.getPower());
    }

    public void rollerDrop() {
        roller.setPower(RollerPower.EJECT.getPower());
    }

    public enum RollerPower {
        PICKUP(-1),
        EJECT(1.0),
        AUTO_EJECT(0.3),
        STOP(0);

        public double getPower() {
            return power;
        }

        private double power;

        RollerPower(double power) {
            this.power = power;
        }
    }

    public enum WRIST_POS {
        RESET(0.2), //0.2 0.77 0.2

        PICKUP(0.5), //0.5
        DROP_OFF(1), //0.55
        PICKUP_90(0.75),
        PICKUP_SPECIMEN(0.75), //0.75
        TELEOP_DROP_OFF_SPECIMEN(0.2), //0.2 0.77


        //--------------------------------------------------------------------------------------------------------------------
        //LEFT AUTO!!!
        LEFT_AUTO_PICKUP_FIRST(0.7),
        LEFT_AUTO_PICKUP_SECOND(0.8), //0.2
        LEFT_AUTO_PICKUP_THIRD(0.85),
        LEFT_AUTO_RESET(0.5),
        LEFT_AUTO_DROP_OFF(0), //0.5
        //--------------------------------------------------------------------------------------------------------------------

        //--------------------------------------------------------------------------------------------------------------------
        //RIGHT AUTO!!!
        RIGHT_AUTO_PICKUP_SPECIMEN(0.75), //0.75
        RIGHT_AUTO_DROP_OFF_SPECIMEN(0.2), //0.2 0.77
        //--------------------------------------------------------------------------------------------------------------------

        ;


        public double getPos() {
            return pos;
        }

        private double pos;

        WRIST_POS(double pos) {
            this.pos = pos;
        }
    }

    public enum ELBOW_POS {
        PICKUP(0.125), //0.09
        DROP(0.7), //0.2
        HANG_POS(0.5), ///0.2
        SPECIMEN_PICKUP(0.415), //0.25
        MOVE(0.12), //0.35
        TELEOP_SPECIMEN_DROP(.46),

        RESET(0.75),


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

        private double pos;

        ELBOW_POS(double pos) {
            this.pos = pos;
        }
    }


    public EnhancedClaw(LinearOpMode linearOpMode) {
        this.linearOpMode = linearOpMode;
        setup();
    }

    @Override
    public String toString() {
        return "EnhancedClaw{" +
                "roller=" + roller.getPower() +
                ", wrist=" + wrist.getPosition() +
                ", elbow=" + elbow.getPosition() +
                '}';
    }

    public void setElbowPos(double pos) {
        elbow.setPosition(pos + ELBOW_CORRECTION);
    }

    public void setWristPos(double pos) {
        wrist.setPosition(pos + WRIST_CORRECTION);
    }

    private void setup() {
        roller = linearOpMode.hardwareMap.get(CRServo.class, "roller");
        roller.setDirection(CRServo.Direction.REVERSE);

        wrist = linearOpMode.hardwareMap.get(Servo.class, "wrist");
//        sampleSensor = linearOpMode.hardwareMap.get(ColorSensor.class, "sampleSensor");
        elbow = linearOpMode.hardwareMap.get(Servo.class, "elbow1");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        reset();
    }

    private void setPos(RollerPower pickup, WRIST_POS pickup1, ELBOW_POS pickup2) {
        roller.setPower(pickup.getPower());
        setWristPos(pickup1.getPos());
        setElbowPos(pickup2.pos);
    }

    public void moveUp() {
        double newPos = elbow.getPosition() + INCREMENT;
        setElbowPos(newPos);
    }

    public void moveDown() {
        double newPos = elbow.getPosition() - INCREMENT;
        setElbowPos(newPos);
    }

    public void samplePickUp90() {
        setPos(RollerPower.PICKUP, WRIST_POS.PICKUP_90, ELBOW_POS.PICKUP);
    }

    public void reset() {
        roller.setPower(RollerPower.PICKUP.getPower());
        setElbowPos(ELBOW_POS.RESET.getPos());
        setWristPos(WRIST_POS.RESET.getPos());
    }

    public void specimenAutoReset() {
        setElbowPos(ELBOW_POS.RESET.getPos());
        roller.setPower(RollerPower.PICKUP.getPower());
        setWristPos(WRIST_POS.RESET.getPos());
    }


    public void samplePickup() {
        setPos(RollerPower.PICKUP, WRIST_POS.PICKUP, ELBOW_POS.PICKUP);
    }

    public void move() {
        setPos(RollerPower.STOP, WRIST_POS.PICKUP, ELBOW_POS.MOVE);
    }

    public void sampleDrop() {
        setWristPos(WRIST_POS.DROP_OFF.getPos());
        setElbowPos(ELBOW_POS.DROP.getPos());
        roller.setPower(RollerPower.PICKUP.getPower());
    }

    public void gripperHang() {
        setWristPos(WRIST_POS.DROP_OFF.getPos());
        setElbowPos(ELBOW_POS.HANG_POS.getPos());
        roller.setPower(RollerPower.STOP.getPower());
    }

    public void drop() {
        roller.setPower(RollerPower.EJECT.getPower());
    }


    public void specimenPickUp() {
        setPos(RollerPower.PICKUP, WRIST_POS.PICKUP_SPECIMEN, ELBOW_POS.SPECIMEN_PICKUP);
    }

    public void teleOpSpecimenDropPos() {
        roller.setPower(RollerPower.PICKUP.getPower());
        setWristPos(WRIST_POS.TELEOP_DROP_OFF_SPECIMEN.getPos());
//        sleep(500);
        setElbowPos(ELBOW_POS.TELEOP_SPECIMEN_DROP.getPos());
    }


    //--------------------------------------------------------------------------------------------------------------------
    //LEFT AUTO!!!
    public void leftAutoReset() {
        setElbowPos(ELBOW_POS.LEFT_AUTO_RESET.getPos());
        roller.setPower(RollerPower.PICKUP.getPower());
        setWristPos(WRIST_POS.LEFT_AUTO_RESET.getPos());
    }

    public void leftAutoSampleDrop() {
        setElbowPos(ELBOW_POS.LEFT_AUTO_DROP.getPos());
        setWristPos(WRIST_POS.LEFT_AUTO_DROP_OFF.getPos());
        sleep(300);
        roller.setPower(RollerPower.AUTO_EJECT.getPower());
        sleep(500);
    }

    public void leftAutoPickup(WRIST_POS wristPos) {
        setPos(RollerPower.PICKUP, wristPos, ELBOW_POS.LEFT_AUTO_PICKUP);
    }

    public void leftAutoIntermediate() {
        setPos(RollerPower.PICKUP, WRIST_POS.LEFT_AUTO_DROP_OFF, ELBOW_POS.LEFT_AUTO_DROP_INTERMEDIATE);
    }

    //--------------------------------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------------------------------
    //RIGHT AUTO!!!
    public void rightAutoSpecimenDropPos() {
        roller.setPower(RollerPower.PICKUP.getPower());
        setWristPos(WRIST_POS.RIGHT_AUTO_DROP_OFF_SPECIMEN.getPos());
//        sleep(500);
        setElbowPos(ELBOW_POS.RIGHT_AUTO_SPECIMEN_DROP.getPos());
    }


    public void rightAutoSpecimenPickUp() {
        setPos(RollerPower.PICKUP, WRIST_POS.RIGHT_AUTO_PICKUP_SPECIMEN, ELBOW_POS.RIGHT_AUTO_SPECIMEN_PICKUP);
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

        while (opModeIsActive()) {

            // Pickup block on pressing A button
            if (gamepad1.a) {
                samplePickup();
            }

            // Eject block on pressing B button
            if (gamepad1.b) {
                sampleDrop();
                drop();
            }

            // Stop the roller on pressing X button
            if (gamepad1.x) {
                reset();
            }


            if (gamepad1.right_bumper) {
                samplePickUp90();
            }

            if (gamepad1.left_bumper) {
                specimenPickUp();

            }

            if (gamepad1.dpad_down) {
                roller.setPower(RollerPower.PICKUP.getPower());
            }


        }
    }
}

