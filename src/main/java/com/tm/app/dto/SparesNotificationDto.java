package com.tm.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SparesNotificationDto {

    private Long machinerySpareId;
    private String spareItem;
    private String days;

}
