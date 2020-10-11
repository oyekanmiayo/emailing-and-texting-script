package com.communicator.emailingandtextingscript.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: aoyekanmi
 * @date: 09/10/2020
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "governmentOfficialName", "governmentPosition",
                    "phoneNumber", "phoneMessage", "email", "emailMessage"})
public class Communication {
    String governmentOfficialName;
    String governmentPosition;
    String phoneNumber;
    String phoneMessage;
    String email;
    String emailMessage;
}
