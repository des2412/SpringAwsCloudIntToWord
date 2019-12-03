package org.desz.serverless.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a Data Object.
 * 
 * @author des
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IntToWordRequest {
	private Long number;
	private String lang;

}
