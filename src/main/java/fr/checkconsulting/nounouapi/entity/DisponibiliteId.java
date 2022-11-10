package fr.checkconsulting.nounouapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisponibiliteId {
    private int jour;
    private String nounouId;
}
