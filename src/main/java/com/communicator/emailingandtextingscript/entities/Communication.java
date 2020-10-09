package com.communicator.emailingandtextingscript.entities;

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
public class Communication {
    String phoneNumber;
    String phoneMessage;
    String email;
    String emailMessage;
}
