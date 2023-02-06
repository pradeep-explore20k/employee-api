package org.example.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    private int id;
    @Builder.Default
    private String employeeName = "Bob";
    @Builder.Default
    private String department = "Logistics";

    private String receivedTime;
}
