package com.geotechmap.gtm.Dto.Essai;

import java.util.List;

import lombok.Data;

@Data
public class EssaiDetailsDtoResponse {
    List<EssaiDetailsDto> essaiDetailsDto;
    String message;
    int pageSize;
    int pageNumber;
}
