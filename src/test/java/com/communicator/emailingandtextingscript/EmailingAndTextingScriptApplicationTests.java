package com.communicator.emailingandtextingscript;

import com.communicator.emailingandtextingscript.scripts.ScheduledMessagingScript;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EmailingAndTextingScriptApplicationTests {

    @Test
    public void testCommunicationObjectsReading(){
        ScheduledMessagingScript messagingScript = new ScheduledMessagingScript(null, null);
        File testFile = new File("src/test/resources/communication_test.json");
        messagingScript.initCommunicationObjects(testFile);

        assertEquals(150, messagingScript.getCommunications().size());

    }

}
