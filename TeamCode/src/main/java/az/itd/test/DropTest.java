package az.itd.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.az.itd.tools.EnhancedClaw;
import org.firstinspires.ftc.teamcode.az.itd.tools.Slides;


@Autonomous
public class DropTest extends LinearOpMode{

    Slides slides = null;
    EnhancedClaw gripper = null;
    LinearOpMode opMode;


    @Override
    public void runOpMode() throws InterruptedException {
        this.opMode = this;

        telemetry.addLine("Init");
        telemetry.update();

        Slides slides = new Slides(this);
        EnhancedClaw gripper = new EnhancedClaw(this);

        waitForStart();

        slides.moveToPosition(Slides.SlidesPos.BASKET_DROP);
        gripper.reset();
        sleep(10000);

        slides.resetPos();
        gripper.reset();
        sleep(4000);

    }
}
