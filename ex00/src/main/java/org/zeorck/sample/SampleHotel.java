package org.zeorck.sample;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class SampleHotel {
	
	private Chef chef;
	
	public SampleHotel(Chef chef) {
		this.chef = chef;
	}

}
