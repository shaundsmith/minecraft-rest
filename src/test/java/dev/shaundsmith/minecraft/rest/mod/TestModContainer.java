package dev.shaundsmith.minecraft.rest.mod;

import com.google.common.eventbus.EventBus;
import lombok.Value;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.MetadataCollection;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.common.versioning.VersionRange;

import java.io.File;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Value
class TestModContainer implements ModContainer {

    String modId;
    String name;
    String displayVersion;

    @Override
    public File getSource() {
        return null;
    }

    @Override
    public ModMetadata getMetadata() {
        return null;
    }

    @Override
    public void bindMetadata(MetadataCollection mc) {

    }

    @Override
    public void setEnabledState(boolean enabled) {

    }

    @Override
    public Set<ArtifactVersion> getRequirements() {
        return null;
    }

    @Override
    public List<ArtifactVersion> getDependencies() {
        return null;
    }

    @Override
    public List<ArtifactVersion> getDependants() {
        return null;
    }

    @Override
    public String getSortingRules() {
        return null;
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        return false;
    }

    @Override
    public boolean matches(Object mod) {
        return false;
    }

    @Override
    public Object getMod() {
        return null;
    }

    @Override
    public ArtifactVersion getProcessedVersion() {
        return null;
    }

    @Override
    public boolean isImmutable() {
        return false;
    }

    @Override
    public VersionRange acceptableMinecraftVersionRange() {
        return null;
    }

    @Override
    public Certificate getSigningCertificate() {
        return null;
    }

    @Override
    public Map<String, String> getCustomModProperties() {
        return null;
    }

    @Override
    public Class<?> getCustomResourcePackClass() {
        return null;
    }

    @Override
    public Map<String, String> getSharedModDescriptor() {
        return null;
    }

    @Override
    public Disableable canBeDisabled() {
        return null;
    }

    @Override
    public String getGuiClassName() {
        return null;
    }

    @Override
    public List<String> getOwnedPackages() {
        return null;
    }

    @Override
    public boolean shouldLoadInEnvironment() {
        return false;
    }

    @Override
    public URL getUpdateUrl() {
        return null;
    }

    @Override
    public void setClassVersion(int classVersion) {

    }

    @Override
    public int getClassVersion() {
        return 0;
    }

    @Override
    public String getVersion() {
        return null;
    }
}
