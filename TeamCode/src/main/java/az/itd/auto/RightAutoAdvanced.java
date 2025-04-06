package az.itd.auto;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;


@Config
public class RightAutoAdvanced extends RightAuto {






    public void runOpMode() throws InterruptedException {
        initAuto();
        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        firstReleaseSpecimenAction,
                        specimenDropPos1,

                        raiseCandyCaneAction,
                        spikeMarkPos1,
                        specimenCollectInParallelAction,
                        lowerCandyCaneAction,
                        new SleepAction(0.3),
                        observationZoneDropPos1,

                        raiseCandyCaneAction,
                        spikeMarkPos2,
                        lowerCandyCaneAction,
                        new SleepAction(0.3),
                        observationZoneDropPos2,

                        raiseCandyCaneAction,
                        spikeMarkPos3,
                        lowerCandyCaneAction,
                        new SleepAction(0.3),
                        observationZoneDropPos3,

                        resetCandyCaneAction,
                        observationZonePos1,
                        observationZonePos1_1,
                        specimenToolDropAfterPickupAction,
                        specimenDropPos2,
                        releaseSpecimenAction,

                        afterDropSpecimenCollectAction,
                        observationZonePos2,
                        specimenToolDropAfterPickupAction,
                        specimenDropPos3,
                        releaseSpecimenAction,

                        afterDropSpecimenCollectAction,
                        observationZonePos3,
                        specimenToolDropAfterPickupAction,
                        specimenDropPos4,
                        releaseSpecimenAction,


                        afterDropSpecimenCollectAction,
                        observationZonePos4,
                        specimenToolDropAfterPickupAction,
                        specimenDropPos5,
                        releaseSpecimenAction,
                        resetSpecimenToolAction,
                        parkPos

                )
        );
//
        sleep(5000);
        telemetry.addData("current position",drive.pose);
        telemetry.update();
        sleep(10000);



    }

}
