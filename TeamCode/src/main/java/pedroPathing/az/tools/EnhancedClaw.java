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
    Elbow elbow =  null;
    private LinearOpMode linearOpMode;
    private double currentWristPos = WRIST_POS.RESET.pos;


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
        RESET(0.485), //0.2 0.77 0.2

        MOVE(0.485), //0.5
        PICKUP(0.485), //0.5
        DROP_OFF(0.1), //0.55
        PICKUP_90(0.21),
        PICKUP_SPECIMEN(0.75), //0.75
        TELEOP_DROP_OFF_SPECIMEN(0), //0.2 0.77


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
        RIGHT_AUTO_PICKUP_SPECIMEN(0.485), //0.75
        RIGHT_AUTO_DROP_OFF_SPECIMEN(0), //0.2 0.77
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


    public EnhancedClaw(LinearOpMode linearOpMode) {
        this.linearOpMode = linearOpMode;
        setup();
    }

    @Override
    public String toString() {
        return "EnhancedClaw{" +
                "roller=" + roller.getPower() +
                ", wrist=" + wrist.getPosition() +
                ", elbow=" + elbow.getPos() +
                '}';
    }



    public void setWristPos(double pos) {
        currentWristPos = pos;
        wrist.setPosition(pos + WRIST_CORRECTION);
    }

    private void setup() {
        roller = linearOpMode.hardwareMap.get(CRServo.class, "roller");
        roller.setDirection(CRServo.Direction.REVERSE);

        wrist = linearOpMode.hardwareMap.get(Servo.class, "wrist");
//        sampleSensor = linearOpMode.hardwareMap.get(ColorSensor.class, "sampleSensor");
//        elbow = linearOpMode.hardwareMap.get(Servo.class, "elbow1");
        elbow = new Elbow(linearOpMode);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        reset();
    }

    private void setPos(RollerPower pickup, WRIST_POS pickup1, Elbow.ELBOW_POS pickup2) {
        roller.setPower(pickup.getPower());
        setWristPos(pickup1.getPos());
        elbow.setElbowPos(pickup2);
    }

    public void moveUp() {
        double newPos = elbow.getPos() + INCREMENT;
        elbow.setElbowPos(newPos);
    }

    public void moveDown() {
        double newPos = elbow.getPos() - INCREMENT;
        elbow.setElbowPos(newPos);
    }

    public void samplePickUp90() {
        WRIST_POS pickup90 = WRIST_POS.PICKUP_90;
        if (currentWristPos == pickup90.pos){
            pickup90 = WRIST_POS.PICKUP;
        }
        setPos(RollerPower.PICKUP, pickup90, Elbow.ELBOW_POS.MOVE);
    }

    public void reset() {
        roller.setPower(RollerPower.PICKUP.getPower());
        elbow.setElbowPos(Elbow.ELBOW_POS.RESET.getPos());
        setWristPos(WRIST_POS.RESET.getPos());
    }

    public void specimenPickUpFromFence() {
        roller.setPower(RollerPower.PICKUP.getPower());
        elbow.setElbowPos(Elbow.ELBOW_POS.SPECIMEN_FENCE_PICKUP.getPos());
        setWristPos(WRIST_POS.MOVE.getPos());
    }


    public void specimenAutoReset() {
        elbow.setElbowPos(Elbow.ELBOW_POS.RIGHT_AUTO_RESET.getPos());
        roller.setPower(RollerPower.PICKUP.getPower());
    }


    public void samplePickup() {
        setPos(RollerPower.PICKUP, WRIST_POS.PICKUP, Elbow.ELBOW_POS.MOVE);
    }

    public void move() {
        setPos(RollerPower.PICKUP, WRIST_POS.MOVE, Elbow.ELBOW_POS.MOVE);
    }
public void duringTeleOpReset() {
        setPos(RollerPower.PICKUP, WRIST_POS.MOVE, Elbow.ELBOW_POS.MOVE);
    }

    public void sampleDrop() {
        setWristPos(WRIST_POS.DROP_OFF.getPos());
        elbow.setElbowPos(Elbow.ELBOW_POS.DROP.getPos());
        roller.setPower(RollerPower.PICKUP.getPower());
    }

    public void gripperHang() {
        setWristPos(WRIST_POS.DROP_OFF.getPos());
        elbow.setElbowPos(Elbow.ELBOW_POS.HANG_POS.getPos());
        roller.setPower(RollerPower.STOP.getPower());
    }

    public void drop() {
        roller.setPower(RollerPower.EJECT.getPower());
    }


    public void specimenPickUp() {
        roller.setPower(RollerPower.PICKUP.getPower());
        //wrist and elbow positions do not change
    }

    public void         teleOpSpecimenDropPos() {
        roller.setPower(RollerPower.PICKUP.getPower());
        setWristPos(WRIST_POS.TELEOP_DROP_OFF_SPECIMEN.getPos());
//        sleep(500);
        elbow.setElbowPos(Elbow.ELBOW_POS.TELEOP_SPECIMEN_DROP.getPos());
    }
    public void         preTeleOpSpecimenDropPos() {
        setWristPos(WRIST_POS.TELEOP_DROP_OFF_SPECIMEN.getPos());
    }


    //--------------------------------------------------------------------------------------------------------------------
    //LEFT AUTO!!!
    public void leftAutoReset() {
        elbow.setElbowPos(Elbow.ELBOW_POS.LEFT_AUTO_RESET.getPos());
        roller.setPower(RollerPower.PICKUP.getPower());
        setWristPos(WRIST_POS.LEFT_AUTO_RESET.getPos());
    }

    public void leftAutoSampleDrop() {
        elbow.setElbowPos(Elbow.ELBOW_POS.LEFT_AUTO_DROP.getPos());
        setWristPos(WRIST_POS.LEFT_AUTO_DROP_OFF.getPos());
        sleep(300);
        roller.setPower(RollerPower.AUTO_EJECT.getPower());
        sleep(500);
    }

    public void leftAutoPickup(WRIST_POS wristPos) {
        setPos(RollerPower.PICKUP, wristPos, Elbow.ELBOW_POS.LEFT_AUTO_PICKUP);
    }

    public void leftAutoIntermediate() {
        setPos(RollerPower.PICKUP, WRIST_POS.LEFT_AUTO_DROP_OFF, Elbow.ELBOW_POS.LEFT_AUTO_DROP_INTERMEDIATE);
    }

    //--------------------------------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------------------------------
    //RIGHT AUTO!!!
    public void rightAutoSpecimenDropPos() {

        roller.setPower(RollerPower.PICKUP.getPower());
        setWristPos(WRIST_POS.RIGHT_AUTO_DROP_OFF_SPECIMEN.getPos());
        elbow.setElbowPos(Elbow.ELBOW_POS.RIGHT_AUTO_SPECIMEN_DROP.getPos());
    }

    public void rightAutoSpecimenDropPos0() {

        roller.setPower(RollerPower.PICKUP.getPower());
        elbow.setElbowPos(Elbow.ELBOW_POS.RIGHT_AUTO_SPECIMEN_DROP.getPos());
    }



    public void rightAutoSpecimenPickUp() {
        setPos(RollerPower.PICKUP, WRIST_POS.RIGHT_AUTO_PICKUP_SPECIMEN, Elbow.ELBOW_POS.RIGHT_AUTO_SPECIMEN_PICKUP);
    }

    public void rightAutoDown() {
        elbow.setElbowPos(Elbow.ELBOW_POS.RIGHT_AUTO_DOWN.getPos());
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
                move();
            }

            // Eject block on pressing B button
            if (gamepad1.b) {
                drop();
            }

            // Stop the roller on pressing X button
            if (gamepad1.x) {
                samplePickUp90();
            }

            if (gamepad1.y) {
                specimenPickUp();
            }

            if (gamepad1.right_bumper) {
            }

            if (gamepad1.left_bumper) {
                sampleDrop();
            }

            if (gamepad1.dpad_down) {
                roller.setPower(RollerPower.PICKUP.getPower());
            }

            if (gamepad2.dpad_down) {
                elbow.moveDown();
                sleep(300);
            }

            if (gamepad2.dpad_up) {
                elbow.moveUp();
                sleep(300);
            }

            if(gamepad2.a) {
                rightAutoSpecimenDropPos();
            }


        }
    }
}

