package pedroPathing.az.tools;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="CandyCane", group = "sample")
public class CandyCane extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    private Servo candyCane;
    private LinearOpMode opMode;


    public enum CandyCanePos {
        RIGHT_AUTO_LOWER(0.01),
        RIGHT_AUTO_RAISE(0.1),
        LEFT_AUTO_LEVEL_ONE_ASCENT(0.2),
        PRE_LEFT_AUTO_LEVEL_ONE_ASCENT(0.4),
        RESET(0.65),
        TELE_OP_RAISE(0.45);


        public double getValue() {
            return value;
        }

        private double value;

        CandyCanePos(double val) {
            this.value = val;
        }
    }

    public CandyCane() {
        super();
    }

    public CandyCane(LinearOpMode opMode) {
        this.opMode = opMode;
        setup();
    }

    private void setup() {
        candyCane = opMode.hardwareMap.get(Servo.class, "candyCane");
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        reset();
    }

    public void reset() {
        candyCane.setPosition(CandyCanePos.RESET.getValue());
    }

    public void teleOpRaise() {
        candyCane.setPosition(CandyCanePos.TELE_OP_RAISE.getValue());
    }




    //--------------------------------------------------------------------------------------------------------------------
    //RIGHT AUTO!!!!!

    public void rightAutoLower() {
        candyCane.setPosition(CandyCanePos.RIGHT_AUTO_LOWER.getValue());
    }

    public void rightAutoRaise() {
        candyCane.setPosition(CandyCanePos.RIGHT_AUTO_RAISE.getValue());
    }
    //--------------------------------------------------------------------------------------------------------------------





    //--------------------------------------------------------------------------------------------------------------------
    //LEFT AUTO!!!!!
    public void leftAutoLevelOneAscent() {
        candyCane.setPosition(CandyCanePos.LEFT_AUTO_LEVEL_ONE_ASCENT.getValue());
    }

    public void preLeftAutoLevelOneAscent() {
        candyCane.setPosition(CandyCanePos.PRE_LEFT_AUTO_LEVEL_ONE_ASCENT.getValue());
    }
    //--------------------------------------------------------------------------------------------------------------------






    @Override
    public void runOpMode() throws InterruptedException {
        this.opMode = this;
        // Initialize hardware
        setup();

        waitForStart();

        rightAutoLower();
        sleep(3000);
        rightAutoRaise();
        sleep(3000);
        reset();
        sleep(3000);
    }
}
