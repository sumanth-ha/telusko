package com.sumanth.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sumanth.Entity.Customer;
import com.sumanth.Service.ICustomerService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class Customercontroller {

	@Autowired
	private ICustomerService service;

	@GetMapping("/Customerlist")
	public String getAllCustomer(Model model) {

		model.addAttribute("customers", (List<Customer>) service.getAllCustomers());
		return "customerlist";
	}

	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {

		service.saveCustomer(customer);
		return "redirect:/Customerlist";
	}

	@GetMapping("/showCustomerForm")
	public String showForm(@ModelAttribute("customer") Customer customer) {
		return "customerform";
	}

	@GetMapping("/updateCustomer")
	public String updateCustomer(@RequestParam("id") Integer id, Model model) {

		model.addAttribute("customer", service.getCustomerByid(id));
		return "customerform";
	}

	@GetMapping("/deleteCustomer")
	public String deleteCustomer(@RequestParam("id") Integer id) {

		service.deleteCustomer(id);
		return "redirect:/Customerlist";
	}

	// 1. View single customer (read-only profile page)
	@GetMapping("/viewCustomer")
	public String viewCustomer(@RequestParam Integer id, Model model) {
		model.addAttribute("customer", service.getCustomerByid(id));
		return "customerview";
	}

	// 2. Export all customers as CSV
//	@GetMapping("/exportCustomers")
//	public void exportCustomers(HttpServletResponse response) throws IOException {
//		response.setContentType("text/csv");
//		response.setHeader("Content-Disposition", "attachment; filename=customers.csv");
//		List<Customer> list = service.getAllCustomers();
//		PrintWriter pw = response.getWriter();
//		pw.println("ID,Name,DOB,Age,Email,Mobile,Address,CreationDate");
//		list.forEach(c -> pw.println(c.getId() + "," + c.getName() + "," + c.getDob() + "," + c.getAge() + ","
//				+ c.getEmail() + "," + c.getMobile() + "," + c.getAddress() + "," + c.getCreationdate()));
//	}

//	// 3. Dashboard (stats page)
//	@GetMapping("/dashboard")
//	public String dashboard(Model model) {
//		model.addAttribute("customers", service.getAllCustomers());
//		return "dashboard";
//	}

	@GetMapping("/exportCustomers")
	public void exportCustomers(HttpServletResponse response) throws IOException {
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=customers.csv");

		List<Customer> list = service.getAllCustomers();
		PrintWriter pw = response.getWriter();

		// Header
		pw.println("ID,Name,DOB,Age,Email,Mobile,Address,Creation Date");

		for (Customer c : list) {
			StringBuilder sb = new StringBuilder();

			sb.append(c.getId()).append(",");
			sb.append(escapeCsv(c.getName())).append(",");
			sb.append(c.getDob()).append(",");
			sb.append(c.getAge()).append(",");
			sb.append(escapeCsv(c.getEmail())).append(",");
			sb.append(escapeCsv(c.getMobile())).append(",");
			sb.append(escapeCsv(c.getAddress())).append(",");
			sb.append(escapeCsv(c.getCreationdate()));

			pw.println(sb.toString());
		}
		pw.flush();
		pw.close();
	}

	// Helper method to handle commas and nulls
	private String escapeCsv(Object value) {
		if (value == null)
			return "";
		String str = value.toString();
		// If data contains a comma, wrap it in double quotes
		if (str.contains(",") || str.contains("\"") || str.contains("\n")) {
			str = "\"" + str.replace("\"", "\"\"") + "\"";
		}
		return str;
	}

	@GetMapping("/dashboard")
	public String dashboardInfo(Model model) {
		List<Customer> customers = service.getAllCustomers();

		// 1. Total Count
		long total = customers.size();

		// 2. Average Age (Handling potential empty list)
		double avgAge = customers.stream().mapToInt(c -> {
			try {
				return Integer.parseInt(c.getAge());
			} catch (Exception e) {
				return 0;
			}
		}).average().orElse(0.0);

		// 3. New Customers this month (based on CreationDate)
		String currentMonth = java.time.LocalDate.now().toString().substring(0, 7); // "2026-04"
		long newThisMonth = customers.stream()
				.filter(c -> c.getCreationdate() != null && c.getCreationdate().contains(currentMonth)).count();

		model.addAttribute("total", total);
		model.addAttribute("avgAge", Math.round(avgAge));
		model.addAttribute("newCount", newThisMonth);
		model.addAttribute("customers", customers); // For the "Recent Activity" table

		return "dashboard";
	}

}
