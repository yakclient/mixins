package net.yakclient.mixin.internal.bytecode;

import net.yakclient.YakMixins;
import net.yakclient.mixin.internal.instruction.Instruction;
import org.objectweb.asm.ClassVisitor;

import java.util.Map;
import java.util.Queue;

public class MixinCV extends ClassVisitor {
    protected final Map<String, Queue<QualifiedInstruction>> injectors;

    public MixinCV(ClassVisitor cv, Map<String, Queue<QualifiedInstruction>> injectors) {
        super(YakMixins.ASM_VERSION, cv);
        this.injectors = injectors;
    }

    protected boolean hasInjection(String name) {
        for (String s : this.injectors.keySet()) {
            if (ByteCodeUtils.descriptorsSame(name, s)) return true;
        }
        return false;
    }

    protected Queue<QualifiedInstruction> getInjection(String name) {
        for (String s : this.injectors.keySet()) {
            if (ByteCodeUtils.descriptorsSame(name, s)) return this.injectors.get(s);
        }
        throw new IllegalArgumentException("Failed to find injection");
    }
}
