package com.example.kafkastudy.application.request.issue;

import com.example.kafkastudy.application.request.user.UserInfo;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CouponIssueCancelRequest implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private long couponId;

}
