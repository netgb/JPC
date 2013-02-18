package org.jpc.emulator.execution.opcodes.rm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class sbb_Ew_Iw extends Executable
{
    final int op1Index;
    final int immw;

    public sbb_Ew_Iw(int blockStart, Instruction parent)
    {
        super(blockStart, parent);
        op1Index = Processor.getRegIndex(parent.operand[0].toString());
        immw = (short)parent.operand[1].lval;
    }

    public Branch execute(Processor cpu)
    {
        Reg op1 = cpu.regs[op1Index];
        int add = (cpu.cf() ? 1: 0);
        cpu.flagOp1 = (short)op1.get16();
        cpu.flagOp2 = (short)immw;
        cpu.flagResult = (short)(cpu.flagOp1 - (cpu.flagOp2 + add));
        op1.set16((short)cpu.flagResult);
        cpu.flagIns = UCodes.SBB16;
        cpu.flagStatus = OSZAPC;
        return Branch.None;
    }

    public boolean isBranch()
    {
        return false;
    }

    public String toString()
    {
        return this.getClass().getName();
    }
}