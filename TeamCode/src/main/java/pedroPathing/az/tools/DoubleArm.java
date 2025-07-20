package pedroPathing.az.tools;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;


//@TeleOp
@Autonomous
public class DoubleArm extends LinearOpMode {

    DcMotorEx doubleArmMotor1;
    DcMotorEx doubleArmMotor2;
    LinearOpMode opMode;
    public static final double POWER = 1.0;
    public static final double SPECIMEN_POWER = 0.85;

    public static final double LOW_POWER = 0.35;
    public static final int INCREMENT = 50;
    private static final int SLOW_INCREMENT = 50;
    public static final double ARM_CONVERSION_FACTOR = 14.444444444444;

    //PID adjustment
    private static final double kP = 0.0;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    private static final double kF = 0.0; // Feedforward term, usually not needed for position control
    PIDFCoefficients pidfCoefficients = new PIDFCoefficients(kP, kI, kD, kF);




    public String printCurrentPos() {
        return  new StringBuffer().append("doubleArm 1: ")
                .append(doubleArmMotor1.getCurrentPosition())
                .append(",\n doubleArm 2:")
                .append(doubleArmMotor2.getCurrentPosition()).toString();
    }

    @Override
    public String toString() {
        return "Arm{" +
                "arm1=" + doubleArmMotor1.getCurrentPosition() +
                ", arm2=" + doubleArmMotor2.getCurrentPosition() +
                ", pidfCoefficients=" + pidfCoefficients +
                '}';
    }

   

    public enum DoubleArmPos {
        //multiple 1.39 times when we replace 435 motor with 312 motor
        RESET(0),
        COLLECT((int)(17.5 * ARM_CONVERSION_FACTOR)),  //(-785),
        LOW_BASKET_DROP((int)(90 * ARM_CONVERSION_FACTOR)),

        SPECIMEN_PICKUP_UP((int)(7*ARM_CONVERSION_FACTOR)),

        LEVEL_TWO_HANG((int)(80*ARM_CONVERSION_FACTOR)),


        MOVE((int)(14 * ARM_CONVERSION_FACTOR)), //(-450),
        BASKET_DROP((int)(98 * ARM_CONVERSION_FACTOR)),

        TELEOP_SPECIMEN_DROP((int)(95 * ARM_CONVERSION_FACTOR)), //(600)



        //--------------------------------------------------------------------------------------------------------------------
        //LEFT AUTO!!!
        LEFT_AUTO_PICKUP_FIRST((int)(10.5 * ARM_CONVERSION_FACTOR)),
        LEFT_AUTO_PICKUP_SECOND((int)(10.5 * ARM_CONVERSION_FACTOR)),
        LEFT_AUTO_PICKUP_THIRD((int)(10 * ARM_CONVERSION_FACTOR)),
        LEFT_AUTO_BASKET_DROP((int)(95 * ARM_CONVERSION_FACTOR)),

        //--------------------------------------------------------------------------------------------------------------------


        //--------------------------------------------------------------------------------------------------------------------
        //RIGHT AUTO!!!
        RIGHT_AUTO_RESET((int)(22 * ARM_CONVERSION_FACTOR)),
        RIGHT_AUTO_SPECIMEN_DROP_INTEMEDIATE_WAIT((int)(20 * ARM_CONVERSION_FACTOR)),
        RIGHT_AUTO_SPECIMEN_DROP((int)(92 * ARM_CONVERSION_FACTOR)), //(600)
        RIGHT_AUTO_SPECIMEN_PICKUP_UP((int)(0*ARM_CONVERSION_FACTOR)),
        RIGHT_AUTO_SPECIMEN_PICKUP_INTERMEDIATE_WAIT((int)(80*ARM_CONVERSION_FACTOR)),
        //--------------------------------------------------------------------------------------------------------------------
        SPECIMEN_PICKUP_FROM_FENCE((int)(18*ARM_CONVERSION_FACTOR));

        private final int value;

        DoubleArmPos(int val) {
            this.value = val;
        }

        public double getValue() {
            return this.value;
        }
    }

    public DoubleArm() {
        super();
    }

    public DoubleArm(LinearOpMode newOpMode) {
        this.opMode = newOpMode;
        setup();
    }

    public void setup() {

        doubleArmMotor1 = opMode.hardwareMap.get(DcMotorEx.class, "arm1");
        doubleArmMotor2 = opMode.hardwareMap.get(DcMotorEx.class, "arm2");
        pidfCoefficients = doubleArmMotor1.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION);

        doubleArmMotor1.setDirection(DcMotor.Direction.FORWARD);
        doubleArmMotor2.setDirection(DcMotor.Direction.REVERSE);
        resetDoubleArmPos();
    }

    private void resetDoubleArmPos() {
        doubleArmMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        doubleArmMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        doubleArmMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        doubleArmMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        doubleArmMotor1.setTargetPosition(0);
        doubleArmMotor2.setTargetPosition(0);


        doubleArmMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        doubleArmMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }



    private void setPos(int pos){


//        int newPos = pos - InitialValues.InitArmPos;


        int currentPosition = doubleArmMotor1.getCurrentPosition();
        double v = pos/ARM_CONVERSION_FACTOR;
        double gravityCompensation = 0.01 * Math.cos(Math.toRadians(v));
        double power = 1.0 + gravityCompensation;
        if( currentPosition - pos > 0){
            power = 0.25;
        }
        AZUtil.setBothMotorTargetPosition(doubleArmMotor1, doubleArmMotor2, pos, power);
//        InitialValues.CurrentArmPos = pos;

    }

    public void setPosPower(int pos){

        AZUtil.setBothMotorTargetPosition(doubleArmMotor1, doubleArmMotor2, pos, SPECIMEN_POWER);

//        InitialValues.CurrentArmPos = pos;

    }

    public void setPosAndWait(int pos){
        setPos(pos);
        AZUtil.waitUntilMotorAtPos(this.opMode, doubleArmMotor1, pos, 15, 3000);
    }

    public void setPosAndWaitPower(int pos){
        setPosPower(pos);
        AZUtil.waitUntilMotorAtPos(this.opMode, doubleArmMotor1, pos, 15, 3000);
    }

    public void setPosAndWaitThreshold(int pos, int tolerance){
        setPos(pos);
        AZUtil.waitUntilMotorAtPos(this.opMode, doubleArmMotor1, pos, tolerance, 3000);
        AZUtil.waitUntilMotorAtPos(this.opMode, doubleArmMotor2, pos, tolerance, 3000);

    }


    public void setPosAndWaitLowPowerLeftAuto(int pos){
        setPosLowPower(pos);
        AZUtil.waitUntilMotorAtPos(this.opMode, doubleArmMotor1, pos, 15, 2000);
    }

    public void moveToPosition(DoubleArmPos DoubleArmPos){
        setPos(DoubleArmPos.value);
    }

    public void setPosLowPower(int pos){

//        int newPos = pos - InitialValues.InitArmPos;


        AZUtil.setBothMotorTargetPosition(doubleArmMotor1, doubleArmMotor2, pos, LOW_POWER);

//        InitialValues.CurrentArmPos = newPos;

    }

    public void moveUp(){
        int newPos = doubleArmMotor1.getCurrentPosition() + INCREMENT;
        setPos(newPos);
    }

    public void moveDown(){
        int newPos = doubleArmMotor1.getCurrentPosition() - INCREMENT;
        setPos(newPos);
    }

    public void moveUpSlow(){
        int newPos = doubleArmMotor1.getCurrentPosition() + SLOW_INCREMENT;
        setPosLowPower(newPos);
    }

    public void moveDownSlow(){
        int newPos = doubleArmMotor1.getCurrentPosition() - SLOW_INCREMENT;
        setPos(newPos);
    }

    public void moveUpdoubleArmr() {
        if( getCurrentPos() < 3800) {
            setPos(getCurrentPos() + 300);
        }
    }

    public void moveDowndoubleArmr() {
        if( getCurrentPos() > 800) {
            setPos(getCurrentPos() - 300);
        }
    }

    public void extend(float factor) {
        int position = Math.round(DoubleArmPos.COLLECT.value - factor*400);
        setPos(position);
    }

    public int getCurrentPos(){
        return doubleArmMotor1.getCurrentPosition();
    }

    public void reset() {
        setPos(DoubleArmPos.RESET.value);
        resetDoubleArmPos();
    }

    public void emergencyResetPos() {
        doubleArmMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        doubleArmMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        doubleArmMotor1.setPower(-0.3);
        doubleArmMotor2.setPower(-0.3);


    }

    public void emergencyResetEncoders() {
        doubleArmMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        doubleArmMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }








    public int getCurrentPosition() {
        return doubleArmMotor1.getCurrentPosition();
    }

    public void specimenPickUp() {
        moveToPosition(DoubleArmPos.SPECIMEN_PICKUP_UP);
    }

    public void setArmPos(DoubleArmPos pos) {
        moveToPosition(pos);
    }

    public void move() {
        setPos(DoubleArmPos.MOVE.value);
    }

    public void specimenPickupFromFence() {
        setPos(DoubleArmPos.SPECIMEN_PICKUP_FROM_FENCE.value);
    }
    public void lowBasketDrop() {
        setPos(DoubleArmPos.LOW_BASKET_DROP.value);
    }

    public void collect() {
        setPos(DoubleArmPos.COLLECT.value);
    }

    public void specimenCollect() {
        setPosPower(DoubleArmPos.SPECIMEN_PICKUP_UP.value);
    }



    //--------------------------------------------------------------------------------------------------------------------
    //LEFT AUTO!!!
    public void leftAutoPickup(DoubleArmPos armPos) {
        setPosLowPower((int) armPos.getValue());
    }

    public void leftAutoReset() {
        setPosLowPower(DoubleArmPos.RESET.value);
    }
    //--------------------------------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------------------------------
    //RIGHT AUTO!!!

    public void rightAutoReset() {
        setPos(DoubleArmPos.RIGHT_AUTO_RESET.value);
    }
    public void rightAutoSpecimenDrop() { setPos(DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP.value);}
    public void rightAutoSpecimenCollectPos() {setPos(DoubleArmPos.RIGHT_AUTO_SPECIMEN_PICKUP_UP.value);}
    public void rightAutoSpecimenDropIntermediateWait() {setPos(DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP_INTEMEDIATE_WAIT.value);}

    //--------------------------------------------------------------------------------------------------------------------







    @Override
    public void runOpMode() {
        this.opMode = this;

        telemetry.addLine("Init");
        telemetry.update();

        autoMode();

//        setup();
//
//        waitForStart();

//        while (opModeIsActive()){
//
//            if (gamepad1.dpad_up) {
//                setArmPos(DoubleArmPos.BASKET_DROP);
//                //sleep(1000);
//            }
//
//            if( gamepad1.dpad_down){
//                setArmPos(DoubleArmPos.RESET);
//            }
//
//            if(gamepad1.dpad_right){
//                setArmPos(DoubleArmPos.COLLECT);
//            }
//
//            if(gamepad1.dpad_left){
//                setArmPos(DoubleArmPos.LOW_BASKET_DROP);
//            }
//            telemetry.addLine(this.toString());
//            telemetry.update();
//        }
    }

    private void teleOpTest() {
        while (opModeIsActive()) {


            if (gamepad1.dpad_up) {
                moveUp();
                //sleep(1000);
            }
            if (gamepad1.dpad_down) {
                moveDown();
                //sleep(1000);
            }


            telemetry.addData("Pos1", doubleArmMotor1.getCurrentPosition());
            telemetry.addData("Pos2", doubleArmMotor2.getCurrentPosition());
            telemetry.update();
        }
    }

    private void autoMode() {

        telemetry.addLine("Init");
        telemetry.update();
        setup();

        waitForStart();

//        teleOpTest();

//        setPos(DoubleArmPos.VERTICAL_TEST.value);
        setPos(DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP.value);
        sleep(4000);
        telemetry.addData("Pos1", doubleArmMotor1.getCurrentPosition());
        telemetry.addData("Pos2", doubleArmMotor2.getCurrentPosition());
        telemetry.update();
        sleep(5000);
//        setPos(0);
//        sleep(5000);

    }

    private void teleOp() {
        while (opModeIsActive()){
            if (gamepad1.dpad_up){
                moveUpdoubleArmr();
            }
            else if( gamepad1.dpad_down){
                moveDowndoubleArmr();
            }
        }
    }
}


