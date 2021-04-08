package kaptainwutax.biomeutils;

import kaptainwutax.seedutils.mc.MCVersion;

public class VersionedGen {
    public boolean DEBUG=false;
    public boolean isDefault1_1;
    protected final MCVersion version;
    public final VersionGate is1_0down = () -> this.getVersion().isOlderOrEqualTo(MCVersion.v1_0); // everything before  1.2
    public final VersionGate is1_1up = () -> this.getVersion().isNewerOrEqualTo(MCVersion.v1_1); // everything after  1.0
    public final VersionGate is1_1down = () -> this.getVersion().isOlderOrEqualTo(MCVersion.v1_1); // everything  before 1.2
    public final VersionGate is1_2down = () -> this.getVersion().isOlderOrEqualTo(MCVersion.v1_2_5); // everything before 1.3
    public final VersionGate is1_6down = () -> this.getVersion().isOlderOrEqualTo(MCVersion.v1_6_4); // everything before  1.7
    public final VersionGate is1_7up = () -> this.getVersion().isNewerOrEqualTo(MCVersion.v1_7_2); // everything after 1.6
    public final VersionGate is1_8up = () -> this.getVersion().isNewerOrEqualTo(MCVersion.v1_8); // everything after 1.7
    public final VersionGate is1_12down = () -> this.getVersion().isOlderOrEqualTo(MCVersion.v1_12_2); // everything before 1.13
    public final VersionGate is1_13up = () -> this.getVersion().isNewerOrEqualTo(MCVersion.v1_13); // everything after 1.12
    public final VersionGate is1_14up = () -> this.getVersion().isNewerOrEqualTo(MCVersion.v1_14); // everything after 1.13
    public final VersionGate is1_16up = () -> this.getVersion().isNewerOrEqualTo(MCVersion.v1_16); // everything after 1.15

    public VersionedGen(MCVersion version) {
        this.version = version;
        isDefault1_1= is1_1down.call();
    }

    public MCVersion getVersion() {
        return this.version;
    }

    public interface VersionGate {
        boolean call();
    }
}
