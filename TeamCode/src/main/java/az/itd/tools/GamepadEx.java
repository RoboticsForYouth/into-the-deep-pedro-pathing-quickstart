package az.itd.tools;//package org.firstinspires.ftc.teamcode.az.itd.tools;
//
//import androidx.annotation.NonNull;
//
//import com.qualcomm.robotcore.hardware.Gamepad;
//
//public class GamepadEx {
//    private final Gamepad curr;
//    Gamepad prev = new Gamepad();
//
//    public GamepadEx(Gamepad curr) {
//        this.curr = curr;
//    }
//
//    public void setPrev(){
//        prev.copy(this.curr);
//    }
//
//    public boolean is_a(){
//        return ( curr.a && !prev.a);
//    }
//
//    public boolean is_b(){
//        return ( curr.b && !prev.b);
//    }
//
//    public boolean is_x(){
//        return ( curr.x && !prev.x);
//    }
//
//    public boolean is_y(){
//        return ( curr.y && !prev.y);
//    }
//
//    public boolean is_dpad_up(){
//        return ( curr.dpad_up && !prev.dpad_up);
//    }
//
//    public boolean is_dpad_down(){
//        return ( curr.dpad_down && !prev.dpad_down);
//    }
//
//    public boolean is_dpad_right(){
//        return ( curr.dpad_right && !prev.dpad_right);
//    }
//    public boolean is_dpad_left(){
//        return ( curr.dpad_left && !prev.dpad_left);
//    }
//
//    public boolean is_right_bumper(){
//        return ( curr.right_bumper && !prev.right_bumper);
//    }
//
//    public boolean is_left_bumper(){
//        return ( curr.left_bumper && !prev.left_bumper);
//    }
//
//    public void rumble(int durationMs){
//        curr.rumble(durationMs);
//    }
//
//    public float right_trigger(){
//        return curr.right_trigger;
//    }
//
//    public float left_trigger(){
//        return curr.left_trigger;
//    }
//
//
//    @NonNull
//    @Override
//    public String toString() {
//        return curr.toString();
//    }
//}
