package com.hp.score.content.ssh.actions;

import com.hp.oo.sdk.content.annotations.Action;
import com.hp.oo.sdk.content.annotations.Output;
import com.hp.oo.sdk.content.annotations.Param;
import com.hp.oo.sdk.content.annotations.Response;
import com.hp.oo.sdk.content.plugin.ActionMetadata.MatchType;
import com.hp.oo.sdk.content.plugin.ActionMetadata.ResponseType;
import com.hp.oo.sdk.content.plugin.GlobalSessionObject;
import com.hp.score.content.ssh.actions.services.ScoreSSHShell;
import com.hp.score.content.ssh.entities.*;
import com.hp.score.content.ssh.services.SSHService;
import com.hp.score.content.ssh.services.impl.SSHServiceImpl;
import com.hp.score.content.ssh.utils.Constants;
import com.hp.score.content.ssh.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ioanvranauhp
 *         Date: 11/03/14
 */
public class SSHShell extends SSHShellAbstract {

    @Action(name = "SSH Shell",
            outputs = {
                    @Output(Constants.OutputNames.RETURN_CODE),
                    @Output(Constants.OutputNames.RETURN_RESULT),
                    @Output(Constants.OutputNames.EXCEPTION),
                    @Output(Constants.STDOUT),
                    @Output(Constants.VISUALIZED)
            },
            responses = {
                    @Response(text = Constants.ResponseNames.SUCCESS, field = Constants.OutputNames.RETURN_CODE, value = Constants.ReturnCodes.RETURN_CODE_SUCCESS, matchType = MatchType.COMPARE_EQUAL, responseType = ResponseType.RESOLVED),
                    @Response(text = Constants.ResponseNames.FAILURE, field = Constants.OutputNames.RETURN_CODE, value = Constants.ReturnCodes.RETURN_CODE_FAILURE, matchType = MatchType.COMPARE_EQUAL, responseType = ResponseType.ERROR, isOnFail = true)
            }
    )
    public Map<String, String> runSshExpectCommand(
            @Param(value = Constants.InputNames.HOST, required = true) String host,
            @Param(Constants.InputNames.PORT) String port,
            @Param(value = Constants.InputNames.USERNAME, required = true) String username,
            @Param(value = Constants.InputNames.PASSWORD, required = true, encrypted = true) String password,
            @Param(Constants.PRIVATE_KEY_FILE) String privateKeyFile,
            @Param(value = Constants.COMMAND, required = true) String command,
            @Param(Constants.InputNames.CHARACTER_SET) String characterSet,
            @Param(Constants.CHARACTER_DELAY) String characterDelay,
            @Param(Constants.NEWLINE_SEQUENCE) String newlineCharacters,
            @Param(Constants.InputNames.TIMEOUT) String timeout,
            @Param(Constants.SSH_SESSIONS_DEFAULT_ID) GlobalSessionObject<Map<String, SSHConnection>> globalSessionObject,
            @Param(Constants.SSH_SESSIONS_DEFAULT_ID) GlobalSessionObject<Map<String, SSHConnection>> sessionObject, // todo session object global
            @Param(Constants.SESSION_ID) String sessionId) {

        SSHShellInputs sshShellInputs = new SSHShellInputs();
        sshShellInputs.setHost(host);
        sshShellInputs.setPort(port);
        sshShellInputs.setUsername(username);
        sshShellInputs.setPassword(password);
        sshShellInputs.setPrivateKeyFile(privateKeyFile);
        sshShellInputs.setCommand(command);
        sshShellInputs.setCharacterSet(characterSet);
        sshShellInputs.setCharacterDelay(characterDelay);
        sshShellInputs.setNewlineCharacters(newlineCharacters);
        sshShellInputs.setTimeout(timeout);
        sshShellInputs.setSshGlobalSessionObject(globalSessionObject);
        sshShellInputs.setSshSessionObject(sessionObject);
        sshShellInputs.setSessionId(sessionId);

        return new ScoreSSHShell().execute(sshShellInputs);
    }
}