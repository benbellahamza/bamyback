package org.sid.renaultvisiteursbackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

@NoArgsConstructor
@Builder(builderMethodName = "adminBuilder")


public class Admin extends Person {

}
