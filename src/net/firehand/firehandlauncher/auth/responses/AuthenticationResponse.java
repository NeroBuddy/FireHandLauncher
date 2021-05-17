package net.firehand.firehandlauncher.auth.responses;

import net.firehand.firehandlauncher.auth.Profile;

public class AuthenticationResponse extends LoginResponse{

    private Profile[] availableProfiles;

    public AuthenticationResponse(String accessToken, String clientToke, Profile selectedProfile, Profile[] availableProfiles) {
        super(accessToken, clientToke, selectedProfile);
        this.availableProfiles = availableProfiles;
    }

    public Profile[] getAvailableProfiles() {
        return availableProfiles;
    }

}
