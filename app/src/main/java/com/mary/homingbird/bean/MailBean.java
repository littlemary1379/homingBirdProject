package com.mary.homingbird.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailBean {

    public String fromName;
    public String toName;
    public String to;
    public String content;
    public String state;

}
