package com.orbital22.wemeet.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PODResponse<T extends Serializable> {
  @NonNull private T data;
}
