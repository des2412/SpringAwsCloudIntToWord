package org.desz.serverless.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class IntToWordRequest {
	private Long number;
	private String lang;

}
