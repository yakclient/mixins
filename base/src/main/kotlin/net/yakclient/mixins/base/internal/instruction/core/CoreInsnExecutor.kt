package net.yakclient.mixins.base.internal.instruction.core

import net.yakclient.mixins.base.internal.instruction.Instruction
import net.yakclient.mixins.base.internal.instruction.InstructionExecutor
import org.objectweb.asm.MethodVisitor

class CoreInsnExecutor(private val instruction: Instruction, private val visitor: MethodVisitor) : InstructionExecutor {
    override fun execute() {
        instruction.insn.accept(visitor)
    }
}