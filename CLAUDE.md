# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an FTC (FIRST Tech Challenge) robotics project using Pedro Pathing for autonomous robot movement. The codebase is built on Android/Java and uses the FTC SDK framework. Pedro Pathing is a path-following library that provides advanced autonomous capabilities for FTC robots.

## Build System

- **Primary Build Tool**: Gradle with Android plugin
- **Build Command**: `./gradlew build` (or `gradlew.bat build` on Windows)
- **Clean Command**: `./gradlew clean`
- **Deploy to Robot**: Use Android Studio or FTC Driver Station deployment

## Project Structure

### Key Modules
- `FtcRobotController/` - Base FTC SDK controller module (rarely modified)
- `TeamCode/` - Main team code containing robot logic and autonomous programs

### Team Code Architecture
- `pedroPathing/az/` - Team-specific robot code
  - `auto/` - Autonomous OpModes (e.g., `RightAuto.java`)
  - `teleop/` - Teleop OpModes (e.g., `IntoTheDeepTeleOp.java`)
  - `tools/` - Robot subsystem classes (`SpecimenTool`, `CandyCane`, `DoubleArm`, `EnhancedClaw`, `Slides`)
- `pedroPathing/constants/` - Configuration constants
  - `FConstants.java` - Follower/path constants (motor names, PID values, physical parameters)
  - `LConstants.java` - Localization constants
- `pedroPathing/examples/` - Example Pedro Pathing implementations
- `pedroPathing/tuners_tests/` - Tuning and testing utilities
  - `automatic/` - Velocity and acceleration tuners
  - `localization/` - Localization tuning tools
  - `pid/` - PID tuning utilities

## Key Configuration

### Motor Configuration
Motor names and directions are defined in `FConstants.java`:
- Uses PINPOINT localization system
- Standard mecanum drive configuration
- PID coefficients for translational, heading, and drive control

### Robot Systems
- **SpecimenTool**: Handles specimen manipulation with arm, gripper, and slides
- **CandyCane**: Autonomous-specific tool for game piece manipulation
- **DoubleArm**: Dual-arm system with position control
- **EnhancedClaw**: Gripper/claw mechanism
- **Slides**: Linear slide system for vertical movement

## Development Workflow

### Testing and Tuning
1. Use tuners in `tuners_tests/` to calibrate robot parameters
2. Test localization with `LocalizationTest.java`
3. Tune PID values using provided tuning OpModes
4. Verify motor directions with `MotorDirections.java`

### Autonomous Development
1. Define poses and paths in autonomous OpModes
2. Use Pedro Pathing's `PathBuilder` for complex trajectories
3. Implement state machines for autonomous sequences
4. Test individual path segments before full autonomous

### Common Constants to Modify
- Motor names in `FConstants.java` if hardware configuration changes
- PID coefficients for better path following
- Physical robot dimensions (wheelbase, track width)
- Localization sensor configuration

## Pedro Pathing Integration

This project uses Pedro Pathing library for:
- Path generation with Bezier curves and lines
- Real-time path following with advanced control
- Localization using various sensor options
- Automatic tuning utilities

The main integration points are in `FConstants.java` and `LConstants.java` where robot-specific parameters are configured for the Pedro Pathing system.