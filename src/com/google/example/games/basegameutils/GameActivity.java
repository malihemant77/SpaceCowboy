package com.google.example.games.basegameutils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.appstate.AppStateClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.plus.PlusClient;

public abstract class GameActivity extends Activity  implements
GameHelper.GameHelperListener {

    // The game helper object. This class is mainly a wrapper around this object.
    protected GameHelper mHelper;

    // We expose these constants here because we don't want users of this class
    // to have to know about GameHelper at all.
    public static final int CLIENT_GAMES = GameHelper.CLIENT_GAMES;
    public static final int CLIENT_APPSTATE = GameHelper.CLIENT_APPSTATE;
    public static final int CLIENT_PLUS = GameHelper.CLIENT_PLUS;
    public static final int CLIENT_ALL = GameHelper.CLIENT_ALL;

    // Requested clients. By default, that's just the games client.
    protected int mRequestedClients = CLIENT_GAMES;

    // stores any additional scopes.
    private String[] mAdditionalScopes;

    /** Constructs a GameActivity with default client (GamesClient). */
    protected GameActivity() {
        super();
        mHelper = new GameHelper(this);
    }

    /**
     * Constructs a GameActivity with the requested clients.
     * @param requestedClients The requested clients (a combination of CLIENT_GAMES,
     *         CLIENT_PLUS and CLIENT_APPSTATE).
     */
    protected GameActivity(int requestedClients) {
        super();
        setRequestedClients(requestedClients);
    }

    /**
     * Sets the requested clients. The preferred way to set the requested clients is
     * via the constructor, but this method is available if for some reason your code
     * cannot do this in the constructor. This must be called before onCreate in order to
     * have any effect. If called after onCreate, this method is a no-op.
     *
     * @param requestedClients A combination of the flags CLIENT_GAMES, CLIENT_PLUS
     *         and CLIENT_APPSTATE, or CLIENT_ALL to request all available clients.
     * @param additionalScopes.  Scopes that should also be requested when the auth
     *         request is made.
     */
    protected void setRequestedClients(int requestedClients, String ... additionalScopes) {
        mRequestedClients = requestedClients;
        mAdditionalScopes = additionalScopes;
    }

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        mHelper = new GameHelper(this);
        mHelper.setup(this, mRequestedClients, mAdditionalScopes);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.onStop();
    }

    @Override
    protected void onActivityResult(int request, int response, Intent data) {
        super.onActivityResult(request, response, data);
        mHelper.onActivityResult(request, response, data);
    }

    protected GamesClient getGamesClient() {
        return mHelper.getGamesClient();
    }

    protected AppStateClient getAppStateClient() {
        return mHelper.getAppStateClient();
    }

    protected PlusClient getPlusClient() {
        return mHelper.getPlusClient();
    }

    protected boolean isSignedIn() {
        return mHelper.isSignedIn();
    }

    protected void beginUserInitiatedSignIn() {
        mHelper.beginUserInitiatedSignIn();
    }

    protected void signOut() {
        mHelper.signOut();
    }

    protected void showAlert(String title, String message) {
        mHelper.showAlert(title, message);
    }

    protected void showAlert(String message) {
        mHelper.showAlert(message);
    }

    protected void enableDebugLog(boolean enabled, String tag) {
        mHelper.enableDebugLog(enabled, tag);
    }

    protected String getInvitationId() {
        return mHelper.getInvitationId();
    }

    protected void reconnectClients(int whichClients) {
        mHelper.reconnectClients(whichClients);
    }

    protected String getScopes() {
        return mHelper.getScopes();
    }
    
    protected String[] getScopesArray() {
        return mHelper.getScopesArray();
    }

    protected boolean hasSignInError() {
        return mHelper.hasSignInError();
    }

    protected ConnectionResult getSignInError() {
        return mHelper.getSignInError();
    }

    protected void setSignInMessages(String signingInMessage, String signingOutMessage) {
        mHelper.setSigningInMessage(signingInMessage);
        mHelper.setSigningOutMessage(signingOutMessage);
    }
    
    public GameHelper getGameHelper(){
    	return this.mHelper;
    }
}
