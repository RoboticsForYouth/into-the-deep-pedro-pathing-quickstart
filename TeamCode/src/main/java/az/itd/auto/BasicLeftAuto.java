package az.itd.auto;


import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.TurnConstraints;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.az.itd.tools.CandyCane;
import org.firstinspires.ftc.teamcode.az.itd.tools.DoubleArm;
import org.firstinspires.ftc.teamcode.az.itd.tools.Slides;
import org.firstinspires.ftc.teamcode.az.itd.tools.SpecimenTool;
import org.firstinspires.ftc.teamcode.az.sample.MecanumDrive;

import java.util.Arrays;


@Config
public class BasicLeftAuto extends LeftAutoPosValues {
    ElapsedTime runtime = new ElapsedTime();

    VelConstraint baseVelConstraint = new MinVelConstraint(Arrays.asList(
            new TranslationalVelConstraint(50.0),
            new AngularVelConstraint(Math.PI / 2)
    ));

    TurnConstraints turnConstraints = new TurnConstraints(
            Math.PI/2,
            -Math.PI/2,
            Math.PI/2);

    SpecimenTool specimenTool = null;
    CandyCane candyCane = null;

    DoubleArm arm = null;
    Slides slides = null;
    DistanceSensor distanceSensor;
    MecanumDrive drive;
    private Pose2d beginPose;
    Action specimenDropPos;
    Action prePark;
    Action specimenHang;
    Action specimenToolWait;


    public void initAuto() {

        arm = new DoubleArm(this);
        slides = new Slides(this);
        specimenTool = new SpecimenTool(this);

        specimenTool.leftAutoReset();

        candyCane = new CandyCane((this));


//        specimenTool.initArmDistanceSensor(); //set arm to init position


        telemetry.addData("Status", "Initialized");
        telemetry.update();
        beginPose = new Pose2d(0,0,Math.toRadians(-90));
        drive = new MecanumDrive(hardwareMap, beginPose);
        telemetry.addData("current position", drive.pose);
        telemetry.update();
        setUpActions();

//        InitialValues.ResetInitPos();

        waitForStart();
        runtime.reset();

    }

    private void setUpActions(){
        specimenDropPos = drive.actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(19, 0), Math.toRadians(0))
//                .afterDisp(1, teleOpSpecimenCollect)
                .build();

        prePark = drive.actionBuilder(drive.pose)
                .splineToConstantHeading(new Vector2d(18, 25), Math.toRadians(0))
                //.splineToConstantHeading(new Vector2d(50, 19), Math.toRadians(-90))
                .splineTo(new Vector2d(50, 19), Math.toRadians(-90))
                .build();


        specimenToolWait = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                specimenTool.resetAndWait();
//                sleep(1000);
                return false;
            }
        };

    }

    public void runOpMode() throws InterruptedException {
        initAuto();

        Actions.runBlocking(
                new SequentialAction(
//                        specimenDropPos,
//                        specimenHang,
//                        specimenToolWait,
//                        prePark,
//                        levelOneAscentAction
                        //levelOneAscent
                )
        );

//
        sleep(5000);
        telemetry.addData("current position",drive.pose);
        telemetry.update();
        sleep(15000);



    }



}
