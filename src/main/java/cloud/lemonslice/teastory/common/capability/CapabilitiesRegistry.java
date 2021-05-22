package cloud.lemonslice.teastory.common.capability;

import net.minecraftforge.common.capabilities.CapabilityManager;

public final class CapabilitiesRegistry
{
    public static void init()
    {
        CapabilityManager.INSTANCE.register(CapabilitySolarTermTime.Data.class, new CapabilitySolarTermTime.Storage(), CapabilitySolarTermTime.Data::new);
    }
}
