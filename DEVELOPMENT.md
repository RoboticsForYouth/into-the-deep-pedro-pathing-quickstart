# INTO THE DEEP Robot Development Guide

## Overview
This document provides comprehensive guidance for developing and maintaining the INTO THE DEEP FTC robot codebase.

## Architecture Overview

### Core Components

#### Autonomous Programs
- **RightAuto**: Specimen-focused strategy (5 specimens, 53 points)
- **LeftAuto**: Sample-focused strategy (3-4 samples + observation zone, 50+ points)
- **BaseAuto**: Common autonomous functionality with error handling

#### Robot Subsystems
- **SpecimenTool**: Main orchestrator for specimen and sample manipulation
- **DoubleArm**: Dual-motor arm system with gravity compensation
- **Slides**: Linear slide mechanism for vertical movement
- **EnhancedClaw**: Multi-servo gripper with wrist and elbow control
- **CandyCane**: Simple servo mechanism for spike mark manipulation

#### Utility Classes
- **AZUtil**: Motor control utilities and thread management
- **CommandQueue**: TeleOp command queuing system (NEW)
- **AutoConstants**: Centralized timing and configuration constants
- **AutoState**: State machine enums for autonomous programs

### Design Patterns

#### FTC OpMode Pattern
All subsystem tools extend `LinearOpMode` to enable independent testing:
```java
@TeleOp
public class DoubleArm extends LinearOpMode {
    // Can be run independently for testing and tuning
}
```

#### State Machine Pattern
Autonomous programs use enum-based state machines:
```java
public enum AutoState {
    RIGHT_SCORE_PRELOAD(0, "Score preloaded specimen"),
    RIGHT_MOVE_TO_SPIKE_1(1, "Move to first spike mark"),
    // ...
}
```

#### Command Pattern
TeleOp uses command queuing for thread safety:
```java
commandQueue.addCommand("Collect Sample", () -> {
    specimenTool.teleOpCollect();
});
```

## Development Workflow

### 1. Testing Individual Subsystems
Each subsystem can be tested independently:
```java
// Test arm positions
@TeleOp
public class TestArm extends LinearOpMode {
    public void runOpMode() {
        DoubleArm arm = new DoubleArm(this);
        // Test specific positions
    }
}
```

### 2. Tuning Constants
All timing and position constants are centralized:
- `AutoConstants.java`: Autonomous timing values
- `FConstants.java`: Pedro Pathing follower constants
- `LConstants.java`: Localization constants
- Position enums in each subsystem class

### 3. Autonomous Development
1. Design strategy and paths
2. Create poses and build paths
3. Implement state machine
4. Test individual states
5. Tune timing constants
6. Full autonomous testing

### 4. Safety Considerations
- Always implement timeout protection
- Add position limit validation
- Include emergency stop mechanisms
- Test error recovery scenarios

## Competition Preparation

### Pre-Match Checklist
- [ ] Test full autonomous sequence
- [ ] Verify timing (complete within 30 seconds)
- [ ] Check emergency stop functionality
- [ ] Validate scoring calculations
- [ ] Test TeleOp controls
- [ ] Verify endgame mechanisms

### Match Strategy
- **RightAuto**: 5 specimens (53 points) + teleop flexibility
- **LeftAuto**: 3-4 samples (50+ points) + observation zone control
- **Endgame**: Level 2 ascent (30 points) or continued scoring

### Troubleshooting

#### Common Issues
1. **Autonomous stops early**: Check state machine loop condition
2. **Fall-through states**: Ensure all switch cases have break statements
3. **Threading conflicts**: Use CommandQueue for TeleOp actions
4. **Timeout issues**: Adjust timing constants in AutoConstants.java

#### Debug Tools
- Telemetry displays current state and timing
- FTC Dashboard for real-time monitoring
- AZUtil print functions for complex debugging

## Code Quality Standards

### Documentation
- All public methods require JavaDoc comments
- Complex algorithms need inline documentation
- State machines should document state purposes

### Error Handling
- Implement timeout protection for all states
- Add position validation for safety
- Include recovery mechanisms
- Log errors clearly for debugging

### Thread Safety
- Use ConcurrentHashMap for shared collections
- Implement proper synchronization
- Avoid creating threads in loops
- Use CommandQueue for TeleOp actions

### Testing
- Test each subsystem independently
- Validate autonomous paths in simulation
- Test error recovery scenarios
- Verify competition scoring

## Future Enhancements

### Immediate Improvements
- Add sensor-based error detection
- Implement adaptive path planning
- Create configuration file system
- Add performance monitoring

### Long-term Goals
- Machine learning for path optimization
- Advanced alliance coordination
- Comprehensive simulation environment
- Automated testing framework

## Contact and Support
For questions about this codebase:
- Check FTC documentation: https://ftc-docs.firstinspires.org/
- Pedro Pathing docs: https://pedropathing.com/
- Team collaboration through GitHub issues

## Version History
- v1.0: Initial competitive codebase with critical fixes
- v1.1: Added LeftAuto and BaseAuto architecture
- v1.2: Implemented CommandQueue and comprehensive error handling
- Current: Enhanced documentation and development guide