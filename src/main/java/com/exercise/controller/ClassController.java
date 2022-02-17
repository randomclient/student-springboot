
package com.exercise.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.exercise.dao.ClassService;
import com.exercise.dto.ClassDto;
import com.exercise.model.ClassBean;

@Controller
@RequestMapping("/stumgmt")
public class ClassController {

	@Autowired
	private ClassService classService;

	@GetMapping("/classform")
	public ModelAndView addclass() {
		return new ModelAndView("BUD003", "classes", new ClassBean());
	}

	@PostMapping("/setupaddclass")
	public String setupaddclass(@ModelAttribute("classes") @Validated ClassBean bean, BindingResult bs, ModelMap map) {
		if (bs.hasErrors()) {
			return "BUD003";
		}

		ClassDto dto = new ClassDto();
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		classService.getClassById(dto.getId());

		Optional<ClassDto> optional = classService.getClassById(dto.getId());

		if (!optional.isEmpty())
			map.addAttribute("err", "Class has been already exist!!");
		else {
			classService.save(dto);
			map.addAttribute("msg", "Insert successful");

		}
		return "BUD003";
	}
}
