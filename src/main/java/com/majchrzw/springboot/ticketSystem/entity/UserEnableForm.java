package com.majchrzw.springboot.ticketSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEnableForm {
	private String email;
	private Boolean enabled;
}
