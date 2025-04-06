package az.sample;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Trajectory;

import java.util.ArrayList;

/**
 * utility to add Trajectory and Actions to create an Auto
 */
public class AZSequentialAction implements Action {

    ArrayList<Action> actions = new ArrayList<>();
    ArrayList<Trajectory> trajectories = new ArrayList<>();

    private Trajectory lastTrajectory = null;
    public AZSequentialAction(){
        super();
    }

    public void addAction(Action action){
        actions.add(action);
    }

    public void addTrajectory(Trajectory trajectory){
        trajectories.add(trajectory);
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        new SequentialAction(actions).run(telemetryPacket);
        return false;
    }
}
